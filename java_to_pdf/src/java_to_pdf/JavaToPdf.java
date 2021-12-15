package java_to_pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class JavaToPdf extends PdfPageEventHelper{
	
	private static Phrase header;
	private static Phrase footer;
	
	private static String imgName = "images/123123_new.jpg";
	
	private static ArrayList<ArrayList<Integer>> arrMonth = new ArrayList<ArrayList<Integer>>();

	private static int cellVerticalSize;

	public static void main(String[] args) throws DocumentException, IOException, Exception {
				
		//���� ������ �� �� �� ��
		Document document = new Document(PageSize.A4,10,10,25,25);
		
		JavaToPdf event = new JavaToPdf();
		
		//������ ���� �Է¹޾� ������ ��,
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("my_pdf.pdf"));
		
		//���� ����, �ٴڱ� ����, ������ ����, �Ӹ��� ����
		Rectangle rect = new Rectangle(90, 30, 750, 560);
		
		pdfWriter.setBoxSize("art", rect);
		
		pdfWriter.setPageEvent(event);
		
		//�ѱ� �� ��Ʈ
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
		
		//�Ӹ��� ������ ����
		header =  new Phrase("2021�� ������ȣ���",contentFontSmall);
		footer = new Phrase("�������ջ�ȸ������",contentFontSmall);

		//�� ����
		int selectMonth = 12;
		
		arrMonth = getCal(selectMonth-1);
		
		//������ ȸ��	
		document.setPageSize(PageSize.A4.rotate());
		
		//���Ͽ���
		document.open();
		
		//ǥ ��Ÿ��
		
		//Ÿ��Ʋ�ؽ�Ʈ
		Paragraph title = new Paragraph("12�� ���ö����� ��������",titleFont);
		
		//Ÿ��Ʋ ����
		title.setAlignment(Element.ALIGN_CENTER);
		
		//ǥ �ۼ�
		
		//������ �߰�
		
		//ǥ ũ��
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(85);
		
		String weekName[] = {"��","��","ȭ","��","��","��","��"};
		
		//�� ����
		for(int i = 0; i < 7; i++) {
			PdfPCell loopCell;
			if(weekName[i].equals("��")) {
				loopCell = new PdfPCell(new Paragraph(weekName[i],contentFontBlue));
			}else if(weekName[i].equals("��")) {
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

		//���� �Է�		
		
		int a = 0;
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
			// �̹��� �ֱ�
			if(a == 6) {
				for(int k = 0; k < 7; k++) {
					if(arrMonth.get(i).get(k) != null) {
						table.addCell(Image.getInstance(imgName));
					}else {
						table.addCell(new Paragraph());
					}
				}
			}
		}
		
		//ǥ ����
		document.add(new Paragraph(title));
		document.add(new Paragraph("\n",contentFont));
		document.add(table);
		
		//���ϴݱ�
		document.close();
		
		System.out.println("pdf���� ����");
		
		System.out.println(arrMonth.size());
		
		//�ش� �޷� ���
		for(int i=0; i < arrMonth.size(); i++) {
			for(int j = 0; j < arrMonth.get(i).size(); j++) {
				System.out.print(arrMonth.get(i).get(j)+" ");
			}
			System.out.println("");
		}
		
		}
	
	public static ArrayList<ArrayList<Integer>> getCal(int month) {
		
		ArrayList<ArrayList<Integer>> arrWeek = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> arrDay1 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay2 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay3 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay4 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay5 = new ArrayList<Integer>();
		ArrayList<Integer> arrDay6 = new ArrayList<Integer>();
		
		Calendar cal = Calendar.getInstance();
		
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
	 public void onStartPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, header, rect.getLeft(), rect.getTop(), 0);
	    }
	 public void onEndPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, footer, rect.getRight(), rect.getBottom(), 0);
	    }
}
