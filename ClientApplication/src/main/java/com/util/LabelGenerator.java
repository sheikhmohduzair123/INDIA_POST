package com.util;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.printing.PDFPageable;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.constants.Status;
import com.domain.Parcel;
import com.domain.Services;
import com.repositories.PostalServiceRepository;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.image.Image;

@Service
public class LabelGenerator {
	@Autowired
	private PostalServiceRepository postalServiceRepository;

	private Logger log = LoggerFactory.getLogger(LabelGenerator.class);

	@Value("${labelimages.logo}")
	private Resource labelLogo;

	@Value("${labelimages.cut}")
	private Resource labelCut;

	public void generateLabel(Parcel parcel) throws IOException {
		String parentService = "";
		String parentServiceCode = "";
		String ServiceName = "";
		String labelCode = "";
		double AmountCollection = 0.0f;
		String dataToShow = "";

		System.out.println(parcel.toString());

		parentService = parcel.getInvoiceBreakup().getName();
		Long parentServiceId = parcel.getService();
		Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
		if (services == null) {
			services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
		}

		parentServiceCode = services.getServiceCode();

		/*
		 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
		 * parentServiceCode="GE"; }
		 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
		 * parentServiceCode="UP"; }
		 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
		 * parentServiceCode="IC"; }
		 */

		List<Services> subServiceList = new ArrayList<>();

		if (!(parcel.getSubServices() == null)) {
			subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(), Status.ACTIVE);

			// if some services are disabled, find from disabled service
			if (!(subServiceList.size() == parcel.getSubServices().size())) {
				List<Long> subserviceId = new ArrayList<Long>();
				for (Services s : subServiceList) {
					subserviceId.add(s.getId());
				}
				List<Long> parcelSubServices = parcel.getSubServices();
				//log.info("generateLabel; parcelSubServices:");
				
				//parcelSubServices.forEach(s-> log.info("{}", s));
				parcelSubServices.removeAll(subserviceId);
				for (Long serviceid : parcelSubServices) {
					Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
					subServiceList.add(service);
				}
			}
		}
		List<String> subServiceCode = new ArrayList<>();
		subServiceList.forEach(subService -> {
			subServiceCode.add(subService.getServiceCode());
		});

		String serviceCode = String.join(",", subServiceCode);

		if (parcel.isToPay() == true) {
			ServiceName = "To Pay";
			labelCode = "T";
			AmountCollection = parcel.getInvoiceBreakup().getPayableAmnt();
			dataToShow = ServiceName + ", Amount to be collected - Rs " + AmountCollection;

		} else if (parcel.isCod() == true) {
			ServiceName = "COD";
			labelCode = "C";
			AmountCollection = parcel.getParcelDeclerationValue();
			dataToShow = ServiceName + ", Amount to be collected - Rs " + AmountCollection;

		} else if (parcel.isCod() == false && parcel.isToPay() == false) {
			ServiceName = parcel.getInvoiceBreakup().getName().toUpperCase();
			labelCode = "P";
			dataToShow = ServiceName;
		}

		// String outputFileName =
		// "label/label_"+parcel.getId()+"_"+parcel.getSenderAddress().getPostalCode()+".pdf";
		// ByteArrayOutputStream outt = new ByteArrayOutputStream();
		PDDocument document = new PDDocument();
		PDRectangle pdRect = new PDRectangle(0, 0, 288, 432);
		PDPage page = new PDPage(pdRect);

		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream cos = new PDPageContentStream(document, page);
		float margin = 14.4f;

		// starting y position is whole page height subtracted by top and bottom margin
		float yStartNewPage = page.getMediaBox().getHeight() - (1 * margin);
		// we want table across whole page width (subtracted by left and right margin
		// ofcourse)
		// float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

		boolean drawContent = true;
		float bottomMargin = 0;
		float width = 259.2f;
		float yPosition = 0;

		java.util.Date d = parcel.getCreatedDate();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = d.toInstant();
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
		LocalDate estimateDate = localDate
				.plusDays(parcel.getRateCalculation().getRateServiceCategory().getExpectedDelivery());
		String shipDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String estimatedDate = estimateDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, width, margin, document, page, true,
				drawContent);

		// the parameter is the row height
		Row<PDPage> headerRow = table.createRow(60);
		// the first parameter is the cell width

		// logo
		Image image = new Image(ImageIO.read(labelLogo.getInputStream()));
		float imageWidth = 50;
		image = image.scaleByWidth(imageWidth);
		image = image.scaleByHeight(imageWidth);



		Cell<PDPage> cell = headerRow.createImageCell(16, image, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
		cell = headerRow.createCell(25, "<b>INDIA<br>POSTAGE</b><br>");
		cell.setFontSize(9);
		cell.setRightBorderStyle(null);
		cell.setValign(VerticalAlignment.TOP);
		cell.setLineSpacing(1.2f);
		//cell.setBottomBorderStyle(null);

		// cell = headerRow.createCell(25, "<br>CRGWGT: "
		// 		+ parcel.getParcelDeadWeight() + "GM" + "<br>DWGT(gms):" +
		// 		parcel.getParcelDeadWeight() + "/" + parcel.getParcelVolumeWeight() + "<br>LBH(cms): "
		// 		+ parcel.getParcelLength() +
		// 		"×" + parcel.getParcelWidth() + "×" + parcel.getParcelHeight());

		// cell.setRightBorderStyle(null);
		// // cell.setAlign(HorizontalAlignment);
		// cell.setValign(VerticalAlignment.BOTTOM);
		// // cell.setLineSpacing(1.2f);
		// // cell = row.createCell(100, parcel.getService().toUpperCase()+" - FOUR DAYS");
		// // cell.setAlign(HorizontalAlignment.CENTER);
		// cell.setFontSize(6);
		// cell.setTopPadding(3);
		// cell.setBottomPadding(1);

		// cell.setRightBorderStyle(null);
		// cell.setValign(VerticalAlignment.MIDDLE);
		// cell.setLineSpacing(1.2f);
		// cell.setBottomBorderStyle(null);

		cell = headerRow.createCell(59,
				"SHIP DATE: " + shipDate+"<br>CRGWGT(gms): "
						+ parcel.getParcelDeadWeight()  + "<br>DWGT(gms): " +
						parcel.getParcelDeadWeight() + "<br>LBH(cms): "
						+ parcel.getParcelLength() +
						"×" + parcel.getParcelWidth() + "×" + parcel.getParcelHeight()+"<br>PARCEL-COUNT: "+parcel.getParcelCount());
		// cell.setFontSize(8);
		cell.setLeftPadding(15);
		//cell.setRightPadding(10);
		cell.setValign(VerticalAlignment.TOP);
		cell.setLineSpacing(1.5f);
		//cell.setTopPadding(9);
		//cell.setBottomPadding(1);
		//cell.setBottomBorderStyle(null);

		// cell = headerRow.createCell(38, "<br>CRGWGT: "
		// + parcel.getParcelDeadWeight() + "GM"+"<br>DWGT(gms):"+
		// parcel.getParcelDeadWeight()+"/"+parcel.getParcelVolumeWeight()+"<br>LBH(cms):
		// "+parcel.getParcelLength()+
		// "×"+parcel.getParcelWidth()+"×"+parcel.getParcelHeight());
		// cell.setRightBorderStyle(null);
		// cell.setValign(VerticalAlignment.MIDDLE);
		// cell.setLineSpacing(1.2f);
		// // cell = row.createCell(100, parcel.getService().toUpperCase()+" - FOUR
		// DAYS");
		// //cell.setAlign(HorizontalAlignment.CENTER);
		// cell.setFontSize(9);
		// //cell.setTopPadding(3);
		// cell.setBottomPadding(1);

		// cell.setBottomPadding(50);

		// cell = headerRow.createCell(46,
		// "<b>" + parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) :
		// (""))
		// + "<b><br><br><b>"+ parcel.getClient().getLicenseNumber() + "</b>");

		// cell.setValign(VerticalAlignment.MIDDLE);
		// cell.setTopPadding(-6);

		table.addHeaderRow(headerRow);

		Row<PDPage> row1 = table.createRow(7);
		cell = row1.createCell(16, "");
		cell.setBottomBorderStyle(null);
		cell = row1.createCell(84, "Tracking Number");
		cell.setFontSize(8);
		cell.setAlign(HorizontalAlignment.CENTER);
		cell.setBottomBorderStyle(null);

		Row<PDPage> row = table.createRow(44);
		cell = row.createCell(16, labelCode);
		cell.setTopPadding(-2);
		cell.setAlign(HorizontalAlignment.CENTER);
		cell.setFontSize(35);
		// Open output file//

		Image barcode39 = new Image(generateCode128BarcodeImage(parcel.getTrackId()));
		float barcodeWidth39 = 300f;
		float barcodeHeight39 = 400f;
		barcode39.scaleByWidth(barcodeWidth39);
		barcode39.scaleByHeight(barcodeHeight39);

		// row = table.createRow(20);

		cell = row.createImageCell(84, barcode39);
		cell.setAlign(HorizontalAlignment.CENTER);
		cell.setTopPadding(-5);

		// priority
		row = table.createRow(8);
		cell = row.createCell(100, "<b>" + dataToShow + "</b>" + "<b>" +(subServiceCode.contains("IN") ? " (INSURED)" : "")+ "</b>");
		// cell = row.createCell(100, parcel.getService().toUpperCase()+" - FOUR DAYS");
		cell.setAlign(HorizontalAlignment.CENTER);
		cell.setFontSize(9);
		cell.setTopPadding(3);
		cell.setBottomPadding(1);

		// label uid

		row = table.createRow(6);
		// cell = row.createCell(50, "123456789123456789-00");
		cell = row.createCell(50, parcel.getLabelCode() + String.format("-%02d", parcel.getPrintCount())+"<br><br>"+ "<b>License Number:</b> " +parcel.getClient().getLicenseNumber());
		cell.setFontSize(7);
		cell.setBottomBorderStyle(null);
		cell.setRightBorderStyle(null);
		cell.setAlign(HorizontalAlignment.LEFT);

		//Ecom no and bnpl code
		if ((parcel.getClient().getCustomerCode() != null && !parcel.getClient().getCustomerCode().trim().equals(""))
                || (parcel.getEcommerceReferenceNo() != null && !parcel.getEcommerceReferenceNo().trim().equals(""))) {
            cell = row.createCell(50,
                    (parcel.getClient().getCustomerCode() != null
                            ? ("<b>BNPL Code:</b> " + parcel.getClient().getCustomerCode() + "<br><br>"
                                    + (parcel.getEcommerceReferenceNo() != null
                                            && !parcel.getEcommerceReferenceNo().trim().equals("")
                                                    ? "<b>Ecom No:</b> " + parcel.getEcommerceReferenceNo()
                                                    : " "))
                            : "<b>Ecom No:</b> " + parcel.getEcommerceReferenceNo()));
        }
        else {
            cell = row.createCell(50, "");
        }
        cell.setFontSize(7);
        cell.setBottomBorderStyle(null);
        cell.setLeftBorderStyle(null);
        cell.setAlign(HorizontalAlignment.LEFT);



		//Expected Delivery Date
		// cell = row.createCell(50,
		// 		"Estimated Delivery Date: " + estimatedDate );
		// cell.setFontSize(7);
		// cell.setBottomBorderStyle(null);
		// cell.setLeftBorderStyle(null);
		// cell.setAlign(HorizontalAlignment.RIGHT);

		// license number
		// row = table.createRow(20);
		// cell = row.createCell(60,
		// 		"License Number: " + parcel.getClient().getLicenseNumber());
		// cell.setFontSize(7);
		// //cell.setRightPadding(10);
		// cell.setTopPadding(-3);
		// cell.setBottomPadding(2);
		// cell.setRightBorderStyle(null);
		// cell.setLineSpacing(1.3f);
		// cell.setBottomBorderStyle(null);


		// cell = row.createCell(40,
		// 		 "BNPL Code: " + " 253568912");
		// cell.setFontSize(7);
		// //cell.setRightPadding(10);
		// cell.setTopPadding(-3);
		// cell.setBottomPadding(2);
		// //cell.setRightBorderStyle(null);
		// //cell.setLeftBorderStyle(null);
		// cell.setLineSpacing(1.3f);
		// cell.setBottomBorderStyle(null);


		// cell = row.createCell(50,
		// 		  "Ecom No.:" + "11421144512457896451");
		// cell.setFontSize(7);
		// cell.setTopPadding(-3);
		// cell.setBottomPadding(2);
		// cell.setBottomBorderStyle(null);
		// cell.setLeftBorderStyle(null);
		// cell.setAlign(HorizontalAlignment.RIGHT);

		// to newly
		// added possition

		row = table.createRow(50);
		cell = row.createCell(100, parcel.printToAddress());
		cell.setFontSize(10);
		cell.setTopBorderStyle(null);
		// cell.setLeftPadding(18);
		cell.setRightPadding(10);
		cell.setBottomBorderStyle(null);
		cell.setLineSpacing(1.3f);

		// shiped
		row = table.createRow(30);
		cell = row.createCell(100, parcel.printShippedAddress());
		cell.setFontSize(7);
		// cell.setRightPadding(10);
		cell.setLeftPadding(18);
		cell.setBottomBorderStyle(null);
		cell.setLineSpacing(1.3f);
		// cell.setBottomPadding(50);

		// to( previous position)
		// changed

		// row = table.createRow(10);
		// cell = row.createCell(100,"<b>DeadWeight: </b>"+
		// parcel.getParcelDeadWeight()+"<b> Width: </b>"+parcel.getParcelWidth()+"<b>
		// Height: </b>"+parcel.getParcelHeight()+"<b> VolumeWeight:
		// </b>"+parcel.getParcelVolumeWeight()+"<b> Length:
		// </b>"+parcel.getParcelLength());
		// cell.setFontSize(7);
		// cell.setRightPadding(10);
		// cell.setLineSpacing(1.3f);
		// cell.setBottomBorderStyle(null);

		// parcel.getParcelDeadWeight();
		// parcel.getParcelWidth();
		// parcel.getParcelHeight();
		// parcel.getParcelVolumeWeight();
		// parcel.getParcelLength();

		// cut-mark
		Image cutReci = new Image(ImageIO.read(labelCut.getInputStream()));
		float cutReciWidth = 100;
		cutReci = cutReci.scaleByWidth(cutReciWidth);
		cutReci = cutReci.scaleByHeight(cutReciWidth);

		row = table.createRow(6);
		cell = row.createImageCell(9, cutReci);
		cell.setRightBorderStyle(null);
		cell.setBottomBorderStyle(null);
		cell = row.createCell(91, "User Recipient............................"
				+ ".........................................................." + "..........................");
		cell.setFontSize(6);
		cell.setValign(VerticalAlignment.MIDDLE);
		cell.setLeftPadding(-3);
		cell.setLeftBorderStyle(null);
		cell.setBottomBorderStyle(null);

		row = table.createRow(5);
		cell = row.createCell(50, "");
		cell.setBottomBorderStyle(null);
		cell.setRightBorderStyle(null);
		cell.setTopPadding(-5);

		cell = row.createCell(50, "Tracking Number");
		cell.setFontSize(8);
		cell.setAlign(HorizontalAlignment.RIGHT);
		cell.setValign(VerticalAlignment.BOTTOM);
		cell.setBottomBorderStyle(null);
		cell.setLeftBorderStyle(null);
		cell.setRightPadding(20);
		cell.setTopPadding(2);

		// footer-headline
		row = table.createRow(15);
		cell = row.createImageCell(15, image);
		cell.setRightBorderStyle(null);
		cell.setBottomBorderStyle(null);
		cell.setTopPadding(-7);
		cell.setBottomPadding(4);

		cell = row.createCell(45, "<b>INDIA<br>POSTAGE </b><br><br>SHIP DATE: " + shipDate + "<br>CRGWGT: "
				+ parcel.getParcelDeadWeight() + "gms");
		cell.setFontSize(8);
		cell.setRightBorderStyle(null);
		cell.setTopPadding(-5);
		cell.setBottomBorderStyle(null);
		// cell.setFont(PDType1Font.TIMES_ROMAN);
		cell.setLineSpacing(1.2f);

		// footer-barode
		cell = row.createImageCell(40, barcode39);
		cell.setAlign(HorizontalAlignment.CENTER);
		cell.setTopPadding(-5);
		cell.setBottomBorderStyle(null);
		cell.setValign(VerticalAlignment.MIDDLE);

		row = table.createRow(20);
		cell = row.createCell(50,
				"</b><br><br><b>Rs " + parcel.getInvoiceBreakup().getPayableAmnt() + "</b>");
		cell.setTopBorderStyle(null);
		cell.setRightBorderStyle(null);
		cell.setTopPadding(-3);
		//cell.setBottomPadding(5);
		//

		cell = row.createCell(50,  (parcel.getEcommerceReferenceNo() == null ||parcel.getEcommerceReferenceNo().equals("") )? " ":"<b>Ecom No.:</b> "+parcel.getEcommerceReferenceNo());
		cell.setTopBorderStyle(null);
		cell.setLeftBorderStyle(null);
		cell.setFontSize(7);
		//cell.setTopPadding(-3);
		//cell.setValign(VerticalAlignment.MIDDLE);
		//cell.setAlign(HorizontalAlignment.CENTER);
		cell.setAlign(HorizontalAlignment.LEFT);
		cell.setBottomPadding(-5);
		cell.setTopPadding(10);

		table.draw();
		cos.close();

		// Save the results and ensure that the document is properly closed:
		// COSWriter coss= new COSWriter(outt);
		// coss.write(document);
		// document.save(outputFileName);
		// coss.close();

		PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
		// PDDocument docu = PDDocument.load(new File(outputFileName));
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(document));
		try {
			job.setPrintService(ps);
			job.print();
		} catch (PrinterException e) {
			log.error("could not print, printing error:", e);
		} finally {
			document.close();
		}

		log.info("label Created  & printed successfully");
		// return new ByteArrayInputStream(outt.toByteArray());

	}

	public static BufferedImage generateCode128BarcodeImage(String trackId) {

		// Create the barcode bean
		Code39Bean code39 = new Code39Bean();

		final int dpi = 150;

		// Configure the barcode generator
		code39.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); // makes the narrow bar
		// width exactly one pixel
		code39.setWideFactor(3);
		code39.doQuietZone(false);

		// Set up the canvas provider for monochrome PNG output
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

		// Generate the barcode
		code39.generateBarcode(canvas, trackId);

		code39.generateBarcode(canvas, trackId);

		// Signal end of generation
		try {
			canvas.finish();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return canvas.getBufferedImage();
	}

}
