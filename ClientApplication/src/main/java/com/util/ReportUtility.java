package com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportUtility {

	private static Logger log = LoggerFactory.getLogger(ReportUtility.class);
	private static final String SEPARATOR = "~";
	private static final String baseDirectoryFolderPath = "/";

	public static InputStream createZip(List<String> line) {
		System.out.println("inside createZip");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream inputStream = null;
		try {
			line.remove(0);
			ZipOutputStream zipOS = new ZipOutputStream(baos);
			line.stream().forEach(l -> {
				try {
					System.out.println("image path:" + baseDirectoryFolderPath + l);
					writeToZipFile(baseDirectoryFolderPath + l, zipOS);
				} catch (IOException e) {
					log.error("Error occurred:: ",e);
				}
			});
			zipOS.close();
			zipOS.flush();
			inputStream = new ByteArrayInputStream(baos.toByteArray());
		} catch (FileNotFoundException e) {
			log.error("Error occurred:: ",e);
		} catch (IOException e) {
			log.error("Error occurred:: ",e);
		}

		return inputStream;
	}
	public static void writeToZipFile(String path, ZipOutputStream zipStream)
			throws FileNotFoundException, IOException {
		File aFile = new File(path);
		FileInputStream fis = new FileInputStream(aFile);
		ZipEntry zipEntry = new ZipEntry(aFile.getName());
		zipStream.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipStream.write(bytes, 0, length);
		}
		zipStream.closeEntry();
		fis.close();
	}

	public File getCSVFile(List<String> list, String fileName){
		File file = null;
		if(list != null){
			file = new File(fileName);
			try {
				file.createNewFile();
				FileUtils.writeLines(file, list);
			} catch (IOException e) {
				log.error("error, ", e);
			}
		}

		return file;
	}
	/*
	 * In List<String> actually string is columns separated by ~
	 */
	public static File getExcelFile(List<String> list, String fileName){

		File f = null;

		if(list != null){

			try{
				f = new File(fileName);
				//f.deleteOnExit();//to test it locally, on prod we will delete this
				f.createNewFile();
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet(fileName);

				for(int i=0; i< list.size();i++){
					Row row = sheet.createRow(i);
					Object [] objArr = list.get(i).split(SEPARATOR);
					int cellnum = 0;

					if(i ==0){
						for (Object obj : objArr)
						{
							CellStyle style = workbook.createCellStyle();
							style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
							style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
							style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							Font font = workbook.createFont();
							font.setColor(IndexedColors.WHITE.getIndex());
							font.setBold(true);
							style.setFont(font);
							Cell cell = row.createCell(cellnum++);
							cell.setCellValue((String)obj);
							cell.setCellStyle(style);

						}

					}else{
						for (Object obj : objArr)
						{
							Cell cell = row.createCell(cellnum++);
							if(obj instanceof String)
								cell.setCellValue((String)obj);
							else if(obj instanceof Integer)
								cell.setCellValue((Integer)obj);

						}

					}

				}
				FileOutputStream out = new FileOutputStream(f);
				workbook.write(out);
				workbook.close();
				out.close();

			}catch(Exception e){
				log.error("error, ", e);
			}
		}

		return f;

	}

	public static void main(String[] args) throws IOException {

		List<String> list = new ArrayList<String>();
		list.add("sdsd~sdsd");
		list.add("ddddddd~ppppppp");
		System.out.println("file will be created on path:"+System.getProperty("user.dir"));
		File f = getExcelFile(list, "faltu.xls");

	}

}
