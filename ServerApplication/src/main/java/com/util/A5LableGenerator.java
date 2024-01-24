package com.util;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
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
public class A5LableGenerator {

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	private Logger log = LoggerFactory.getLogger(A5LableGenerator.class);

        @Value("${labelimages.logo}")
        private Resource labelLogo;

        @Value("${labelimages.cut}")
        private Resource labelCut;

	public void generateLabel(Parcel parcel) throws Exception{

		 String parentService="";
	    	String parentServiceCode="";
	    	String ServiceName="";
	    	String labelCode="";
	    	double AmountCollection=0.0f;
	    	String dataToShow="";
	    	
	    	parentService=parcel.getInvoiceBreakup().getName();
			Long parentServiceId=parcel.getService();
		    Services services=postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);	
		    parentServiceCode=services.getServiceCode();


		/*
		 * parentService=parcel.getInvoiceBreakup().getName();
		 * 
		 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
		 * parentServiceCode="GE"; } else
		 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
		 * parentServiceCode="UP"; } else
		 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
		 * parentServiceCode="IC"; }
		 */
		List<Services> subServiceList = new ArrayList<>();

		if(!(parcel.getSubServices() == null))
			subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(), Status.ACTIVE);

			List<String> subServiceCode = new ArrayList<>();
			subServiceList.forEach(subService -> {
				subServiceCode.add(subService.getServiceCode());
			});

			String serviceCode = String.join(",", subServiceCode);

			if(parcel.isToPay()==true)
			{
				ServiceName="To Pay";
				labelCode="T";
				AmountCollection=parcel.getInvoiceBreakup().getPayableAmnt();
				dataToShow=ServiceName+", Amount to be collected - Rs "+AmountCollection;
			}
			else if(parcel.isCod()==true)
			{
				ServiceName="COD";
				labelCode="C";
				AmountCollection=parcel.getParcelDeclerationValue();
				dataToShow=ServiceName+", Amount to be collected - Rs "+AmountCollection;

			}
			else if(parcel.isCod()==false && parcel.isToPay()==false)
			{
				ServiceName=parcel.getInvoiceBreakup().getName().toUpperCase();
				labelCode="P";
				dataToShow=ServiceName;
           }


		//String outputFileName = "label/label_"+parcel.getId()+"_"+parcel.getSenderAddress().getPostalCode()+".pdf";
		//ByteArrayOutputStream outt = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();


      // PDRectangle pdRect=new PDRectangle(0,0,288,432);
	    PDPage page = new PDPage();
        page = createPage(PDRectangle.A5);
        writeTextToPage(document, page, "A5");


		// Start a new content stream which will "hold" the to be created content
     //PDPageContentStream cos = new PDPageContentStream(document, page);
        float margin = 13.9f;

        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = page.getBBox().getHeight()-(1*margin);
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        //float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float bottomMargin = 0;
        float width=page.getBBox().getWidth()-(2* margin);
        float yPosition = 0;
        final int dpi = 300;

        java.util.Date d = parcel.getCreatedDate();
        ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = d.toInstant();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        LocalDate estimateDate = localDate.plusDays(parcel.getRateCalculation().getRateServiceCategory().getExpectedDelivery());
        String shipDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String estimatedDate = estimateDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        BaseTable table = new BaseTable(yPosition, yStartNewPage,
        bottomMargin,width, margin, document, page, true, drawContent);

        // the parameter is the row height
        Row<PDPage> headerRow = table.createRow(65);
        // the first parameter is the cell width

        //logo
        Image image = new Image(ImageIO.read(labelLogo.getInputStream()));
        float imageWidth = 50;
        image = image.scaleByWidth(imageWidth);
        image =image.scaleByHeight(imageWidth);

        Cell<PDPage> cell = headerRow.createImageCell(16 , image);

        cell = headerRow.createCell(42,"<b>BANGLADESH<br>POSTAGE </b><br><br>SHIP DATE: "+shipDate+"<br>ACTWGT: "+parcel.getParcelDeadWeight()+"GM");
        cell.setFontSize(12);
        cell.setRightBorderStyle(null);
        cell.setValign(VerticalAlignment.MIDDLE);
        cell.setLineSpacing(1.2f);

        cell= headerRow.createCell(42,"<b>"+parentServiceCode+(!(serviceCode.isBlank())?("/"+serviceCode):(""))+"<b><br><br><b>Rs "+parcel.getInvoiceBreakup().getPayableAmnt()+"</b>");
        cell.setFontSize(12);
        cell.setLeftPadding(38);
		cell.setTopPadding(-6);
          cell.setValign(VerticalAlignment.MIDDLE);

        table.addHeaderRow(headerRow);

        Row<PDPage> row1 = table.createRow(25);
        cell = row1.createCell(16, "");
        cell.setBottomBorderStyle(null);
        cell = row1.createCell(84, "Tracking Number");
        cell.setFontSize(12);
        cell.setAlign(HorizontalAlignment.CENTER);
        cell.setBottomBorderStyle(null);

        Row<PDPage> row = table.createRow(55);
        cell = row.createCell(16,labelCode);
        cell.setTopPadding(6);
        cell.setAlign(HorizontalAlignment.CENTER);
        cell.setFontSize(50);

		Code39Bean bean39 = new Code39Bean();
		bean39.setModuleWidth(UnitConv.in2mm(3.5f/ dpi));

		bean39.doQuietZone(false);

		//Open output file//
//		String outputFile = "barcode/";
//		String barcodefilename="barcode_"+parcel.getId()+"_"+parcel.getSenderAddress().getPostalCode()+".jpg";
//
//		FileOutputStream out = new FileOutputStream(outputFile+barcodefilename);
//
//
//		FileInputStream fin=new FileInputStream(outputFile+barcodefilename);
		//Set up the canvas provider for monochrome PNG output
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);


          //Generate the barcode
          bean39.generateBarcode(canvas, parcel.getTrackId());

          //Signal end of generation
          canvas.finish();

          Image barcode = new Image(canvas.getBufferedImage());
          float barcodeWidth = 360;
          barcode = barcode.scaleByWidth(barcodeWidth);
          barcode =barcode.scaleByHeight(barcodeWidth);

          //row = table.createRow(20);

          cell = row.createImageCell(84, barcode);
          cell.setAlign(HorizontalAlignment.CENTER);
          cell.setTopPadding(-5);

        //priority
        row = table.createRow(20);
        cell = row.createCell(100,"<b>"+dataToShow+"</b>");
        cell.setAlign(HorizontalAlignment.CENTER);
        cell.setFontSize(12);
        cell.setTopPadding(3);
        cell.setBottomPadding(1);

        //label uid
        row = table.createRow(25);
        cell = row.createCell(50,parcel.getLabelCode()+String.format("-%02d", parcel.getPrintCount()));
        cell.setFontSize(10);
        cell.setBottomBorderStyle(null);
        cell.setRightBorderStyle(null);

        //Expected Delivery Date9
        cell = row.createCell(50, "Estimated Delivery Date: "+estimatedDate);
        cell.setFontSize(10);
        cell.setBottomBorderStyle(null);
        cell.setLeftBorderStyle(null);
        cell.setAlign(HorizontalAlignment.RIGHT);


        //shiped
        row = table.createRow(35);
        cell = row.createCell(100,parcel.printShippedAddress());
        cell.setFontSize(10);
        cell.setRightPadding(10);
        cell.setBottomBorderStyle(null);
        cell.setLineSpacing(1.3f);

        //to
        row = table.createRow(115);
        cell = row.createCell(100, parcel.printToAddress());
        cell.setFontSize(11);
        cell.setFont(PDType1Font.TIMES_BOLD_ITALIC);
        cell.setTopBorderStyle(null);
        cell.setLeftPadding(18);
        cell.setRightPadding(10);
        cell.setBottomBorderStyle(null);
        cell.setLineSpacing(1.3f);

        //cut-mark
        Image cutReci = new Image(ImageIO.read(labelCut.getInputStream()));
        float cutReciWidth = 100;
        cutReci = cutReci.scaleByWidth(cutReciWidth);
        cutReci =cutReci.scaleByHeight(cutReciWidth);

        row = table.createRow(20);
        cell = row.createImageCell(9, cutReci);
        cell.setRightBorderStyle(null);
        cell.setBottomBorderStyle(null);
        cell = row.createCell(91, "User Recipient............................"
        		+ ".........................................................."
        		+ "..............................");
        cell.setFontSize(9);
        cell.setValign(VerticalAlignment.MIDDLE);
        cell.setLeftPadding(-3);
        cell.setLeftBorderStyle(null);
        cell.setBottomBorderStyle(null);

        row = table.createRow(20);
        cell = row.createCell(50, "");
        cell.setBottomBorderStyle(null);
        cell.setRightBorderStyle(null);

        cell.setTopPadding(-5);

        cell = row.createCell(50, "Tracking Number");
        cell.setFontSize(10);
        cell.setAlign(HorizontalAlignment.RIGHT);
        cell.setValign(VerticalAlignment.BOTTOM);
        cell.setBottomBorderStyle(null);
        cell.setLeftBorderStyle(null);
        cell.setRightPadding(25);;
        cell.setTopPadding(1);

        //footer-headline
        
        row = table.createRow(20);
		cell = row.createImageCell(15, image);
		cell.setRightBorderStyle(null);
		cell.setBottomBorderStyle(null);
		cell.setTopPadding(-7);
		cell.setBottomPadding(4);
		
        cell = row.createCell(45,"<b>BANGLADESH<br>POSTAGE </b><br><br>SHIP DATE: "+shipDate+"<br>ACTWGT: "+parcel.getParcelDeadWeight()+"GM");
        cell.setFontSize(12);
        cell.setRightBorderStyle(null);
        cell.setTopPadding(-5);
        cell.setBottomBorderStyle(null);
        //cell.setFont(PDType1Font.TIMES_ROMAN);
        cell.setLineSpacing(1.1f);

        //footer-barode
        cell = row.createImageCell(40, barcode);
        cell.setAlign(HorizontalAlignment.CENTER);
        cell.setTopPadding(-5);
        cell.setBottomBorderStyle(null);
        cell.setValign(VerticalAlignment.MIDDLE);

        row = table.createRow(60);
        cell = row.createCell(45, "<b>"+parentServiceCode+(!(serviceCode.isBlank())?("/"+serviceCode):(""))+"<b><br><br><b>Rs "+parcel.getInvoiceBreakup().getPayableAmnt()+"</b>");
        cell.setTopBorderStyle(null);
        cell.setRightBorderStyle(null);
        cell.setFontSize(11);
        cell.setTopPadding(2);

        cell = row.createCell(55, "To Track Please Visit : track24.net");
        cell.setFontSize(10);
        cell.setTopBorderStyle(null);
        cell.setLeftBorderStyle(null);
        cell.setTopPadding(-3);
        cell.setLeftPadding(21);
        cell.setValign(VerticalAlignment.MIDDLE);

        table.draw();


        // Save the results and ensure that the document is properly closed:
//        COSWriter coss= new COSWriter(outt);
//        coss.write(document);
//        document.save(outputFileName);
//        coss.close();


        PrintService ps=PrintServiceLookup.lookupDefaultPrintService();
        //PDDocument docu = PDDocument.load(new File(outputFileName));
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.LANDSCAPE);
       // PageFormat pf = job.defaultPage();
        //pf.setOrientation(PageFormat.LANDSCAPE);
       // Paper paper= new Paper();
       // paper.setSize(page.getBBox().getWidth()-(2* margin),  page.getBBox().getHeight()-(1*margin));
        try {
			job.setPrintService(ps);
			job.print(aset);
		} catch (PrinterException e) {
			log.error("could not print, printing error:", e);
		} finally {
                document.close();
        }

        log.info("label Created  & printed successfully");
		//return new ByteArrayInputStream(outt.toByteArray());

    }

	private static PDPage createPage(PDRectangle size) {
	    return new PDPage(size);
 }
	 private static void writeTextToPage(PDDocument document, PDPage page, String text) throws Exception {
		    int textPosition = (int)(page.getBBox().getHeight());
		   PDPageContentStream stream = new PDPageContentStream(document, page);
		  // stream.transform(Matrix.getRotateInstance(Math.toRadians(90), 0, 0));

		    stream.beginText();
		    stream.setFont(PDType1Font.COURIER, 15);
		    stream.newLineAtOffset(10, textPosition);
		    stream.showText(text);
		    stream.endText();
		    stream.close();
	 }
}