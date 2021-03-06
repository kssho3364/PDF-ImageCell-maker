package java_to_pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class JavaToPdf extends PdfPageEventHelper{
	
	private static Phrase header;
	private static Phrase footer;
	private static Calendar cal = Calendar.getInstance();
	private static int imageHeight = 700;
	
	private String emptyImagePath555 = "etc/emptyImage555.png";
	private String emptyImagePath700 = "etc/emptyImage700.png";
		
	private static ArrayList<ArrayList<Integer>> arrMonth = new ArrayList<ArrayList<Integer>>();
	ArrayList<String> fileName = new ArrayList<String>();

	public JavaToPdf(ArrayList<String> fileList, String filePath, String getTitle, 
			String getHeader, String getFooter, String getFileName, String selectedYear, 
			String selectedMonth) throws DocumentException, IOException, Exception {
		
		int weekCount = getWeekCount(Integer.parseInt(selectedMonth), Integer.parseInt(selectedYear));
		
		if(weekCount == 6) {
			imageHeight = 555;
		}else {
			imageHeight = 700;
		}
		
		//파일이름추출
		for(int a = 0; a < fileList.size(); a++) {
			String strFile = fileList.get(a).substring(fileList.get(a).length()-7, fileList.get(a).length()-5);
			strFile = strFile.replaceAll("[^0-9]","");			
			strFile = strFile.replaceAll(" ","");
			strFile = String.format("%02d", Integer.parseInt(strFile));
			System.out.println("strFile "+strFile);
			fileName.add(strFile);
		}
//		Collections.sort(fileName);
		for(int a = 0; a < fileName.size();a++) {
			System.out.println("@@@@@@@@@@@@@@@@@@ : "+fileName.get(a)); 
		}
		//용지 사이즈 좌 우 상 하
		Document document = new Document(PageSize.A4,10,10,25,25);
		
		//저장경로 추후 입력받아 수정할 것,
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filePath+"\\"+getFileName+".pdf"));
		
		MyHeaderFooter event = new MyHeaderFooter();
		
		//왼쪽 여백, 바닥글 여백, 오른쪽 여백, 머리글 여백
		Rectangle rect = new Rectangle(90, 30, 750, 560);
		
		pdfWriter.setBoxSize("art", rect);
		
		pdfWriter.setPageEvent(event);
		
		//한글 및 폰트
		BaseFont font310 = BaseFont.createFont("font/yoon_320.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		BaseFont font330 = BaseFont.createFont("font/yoon_330.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font titleFont = new Font(font310, 20);
		Font contentFont = new Font(font330, 10);
		Font contentFontRed = new Font(font330, 10);
		contentFontRed.setColor(255, 0, 0);
		Font contentFontBlue = new Font(font330, 10);
		contentFontBlue.setColor(0, 0, 255);
		Font contentFontSmall = new Font(font310,9);
		Font contentFontSmallRed = new Font(font310,9);
		contentFontSmallRed.setColor(255,0,0);
		Font contentFontSmallBlue = new Font(font310,9);
		contentFontSmallBlue.setColor(0,0,255);
		
		BaseColor baseColor = new BaseColor(244,221,237);
		
		//머리말 꼬리말 설정
		header =  new Phrase(getHeader,contentFontSmall);
		footer = new Phrase(getFooter,contentFontSmall);

		//월 선택
		
		arrMonth = getCal(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth)-1);
		
		//페이지 회전	
		document.setPageSize(PageSize.A4.rotate());
		
		//파일열기
		document.open();
		
		//표 스타일
		
		//타이틀텍스트
		Paragraph title = new Paragraph(getTitle,titleFont);
		
		//타이틀 정렬
		title.setAlignment(Element.ALIGN_CENTER);
		
		//표 작성
		
		//테이플 추가
		
		//표 크기
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(85);
		
		String weekName[] = {"일","월","화","수","목","금","토"};
		
		//셀 요일
		for(int i = 0; i < 7; i++) {
			PdfPCell loopCell;
			if(weekName[i].equals("토")) {
				loopCell = new PdfPCell(new Paragraph(weekName[i],contentFontBlue));
			}else if(weekName[i].equals("일")) {
				loopCell = new PdfPCell(new Paragraph(weekName[i],contentFontRed));
				
			}else {
				loopCell = new PdfPCell(new Paragraph(weekName[i],contentFont));
			}
			loopCell.setPaddingTop(0);
			loopCell.setPaddingBottom(3);
			loopCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			loopCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			loopCell.setBackgroundColor(baseColor);
			table.addCell(loopCell);
		}

		//내용 입력		
		
		int a = 0;
		int countName = 0;
		
		try {
			
		for(int i = 0; i < arrMonth.size(); i++) {
			for(int j = 0; j < 7; j++) {
				if(arrMonth.get(i).get(0) != null || arrMonth.get(i).get(6) != null) {
					if(arrMonth.get(i).get(j) == null) {
						PdfPCell cell = new PdfPCell(new Paragraph(""));
						cell.setBackgroundColor(baseColor);
						table.addCell(cell);
					}else {
						PdfPCell cell;
						
						if(j == 0){
							cell = new PdfPCell(new Paragraph(arrMonth.get(i).get(j)+"",contentFontSmallRed));
							
						}else if(j == 6) {
							cell = new PdfPCell(new Paragraph(arrMonth.get(i).get(j)+"",contentFontSmallBlue));
							
						}else {
							cell = new PdfPCell(new Paragraph(arrMonth.get(i).get(j)+"",contentFontSmall));
						}
						cell.setPaddingBottom(3);
						cell.setPaddingTop(0);
						cell.setBackgroundColor(baseColor);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						
						table.addCell(cell);
					}
					a = j;
				}
				else {
					a = 0;
				}
			}
			// 이미지 넣기 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			if(a == 6) {
				for(int k = 0; k < arrMonth.get(i).size(); k++) {
					if(countName < fileList.size()) {

						if(arrMonth.get(i).get(k) != null) {
							String formatDate = String.format("%02d", arrMonth.get(i).get(k));
							System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrr"+formatDate);
							
							if(fileName.get(countName).equals(formatDate)) {
							table.addCell(Image.getInstance(fileList.get(countName)));	
							System.out.println("fileList "+fileList.get(countName));
							countName++;
							}else {
								if(imageHeight == 555) {
									table.addCell(Image.getInstance(emptyImagePath555));
								}else {
									table.addCell(Image.getInstance(emptyImagePath700));
								}
							}
							System.out.println("formatData "+formatDate);
							System.out.println(countName);
						}else {
							if(imageHeight == 555) {
								table.addCell(Image.getInstance(emptyImagePath555));
							}else {
								table.addCell(Image.getInstance(emptyImagePath700));
							}
						}
					}else {
						if(imageHeight == 555) {
							table.addCell(Image.getInstance(emptyImagePath555));
						}else {
							table.addCell(Image.getInstance(emptyImagePath700));
						}
					}
				}
				System.out.println("");
			}
		}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"오류발생", "알림",JOptionPane.WARNING_MESSAGE);
		}
		
		//표 삽입
		document.add(new Paragraph(title));
		document.add(new Paragraph("\n",contentFont));
		document.add(table);
		
		//파일닫기
		document.close();
		
		JOptionPane.showMessageDialog(null,"파일생성 완료!", "알림",JOptionPane.WARNING_MESSAGE);
		
		System.out.println("pdf생성 성공");
		
		System.out.println("arrMonth.size "+arrMonth.size());
		
		//해당 달력 출력
		for(int i=0; i < arrMonth.size(); i++) {
			for(int j = 0; j < arrMonth.get(i).size(); j++) {
				System.out.print(arrMonth.get(i).get(j)+" ");
			}
			System.out.println("");
		}
		
		}
	
	public static ArrayList<ArrayList<Integer>> getCal(int year,int month) {
		
		ArrayList<ArrayList<Integer>> arrWeek = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> arrDay1 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay2 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay3 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay4 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay5 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay6 = new ArrayList<Integer>();
		
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		
		int maxDay1 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for(int i = 1; i <= maxDay1; i++) {
			cal.set(Calendar.DATE, i);
			
			int weekBefore = cal.get(Calendar.WEEK_OF_MONTH);
			
			switch(weekBefore) {
			case 1:
				arrDay1.add(i);
				break;
			case 2:
				arrDay2.add(i);
				break;
			case 3:
				arrDay3.add(i);
				break;
			case 4:
				arrDay4.add(i);
				break;
			case 5:
				arrDay5.add(i);
				break;
			case 6:
				arrDay6.add(i);
				break;
			}
		}
		
		setArray(arrDay1,0);
		setArray(arrDay5,1);
		setArray(arrDay6,1);
		
		arrWeek.add(arrDay1);
		arrWeek.add(arrDay2);
		arrWeek.add(arrDay3);
		arrWeek.add(arrDay4);
		arrWeek.add(arrDay5);
		arrWeek.add(arrDay6);
	   
	    return arrWeek;
	}
	// 날짜 리스트 셋
	public static void setArray(ArrayList<Integer> array, int kind) {
		
			if(array.size()==0) {
				for(int i= 0; i<7; i++) {
					array.add(0,null);
				}	
			}
			else {
				if(array.size()<7) {
					int remain = 7 - array.size();
					for(int i = 0; i < remain; i++) {
						if(kind == 0) {
							array.add(0, null);
						}else {
							array.add(array.size(),null);
						}
					}						
				}		
			}
	}
	
	
	class MyHeaderFooter extends PdfPageEventHelper {
		
		public void onStartPage(PdfWriter writer,Document document) {
			Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, header, rect.getLeft(), rect.getTop(), 0);
	    	}
		public void onEndPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, footer, rect.getRight(), rect.getBottom(), 0);
	    }
	}	 
	
	private static int getWeekCount(int selectedMonth, int selectedYear) {
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, selectedYear);
		cal.set(Calendar.MONTH, selectedMonth-1);
		
		int returnCount = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
	
		return returnCount;
	}
}
