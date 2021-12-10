package java_to_pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class JavaToPdf {
	
	private static ArrayList<ArrayList<Integer>> arrMonth = new ArrayList<ArrayList<Integer>>();

	private static int cellVerticalSize;

	public static void main(String[] args) throws DocumentException, IOException, Exception {
				
		//���� ������
		Document document = new Document(PageSize.A4,0,0,10,10);
		
		//������ ���� �Է¹޾� ������ ��,
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("my_pdf.pdf"));
		
		//�ѱ����ڵ� ��ġ ����?
		BaseFont font = BaseFont.createFont("font/yoon.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font titleFont = new Font(font, 20);
		Font contentFont = new Font(font, 10);
		
		cellVerticalSize = 1;
		
		//���Ͽ���
		document.open();
		
		//������ ȸ��
		document.setPageSize(PageSize.A4.rotate());
		document.newPage();		
		
		//ǥ ��Ÿ��
		
		//Ÿ��Ʋ�ؽ�Ʈ
		Paragraph title = new Paragraph("2021�� 12�� ����޽� ���� ��Ȳ.",titleFont);
		
		//Ÿ��Ʋ ����
		title.setAlignment(Element.ALIGN_CENTER);
		
		//ǥ �ۼ�
		
		//������ �߰�
		PdfPTable table = new PdfPTable(7);
		
		//�� �� ���� ���� (�̿�)
//		table.setWidths(new int[] {cellVerticalSize,cellVerticalSize,cellVerticalSize,cellVerticalSize,cellVerticalSize,cellVerticalSize,cellVerticalSize});
//		table.setWidths(new int[] {1,1,1,1,1,1,1});
		
		System.out.println(cellVerticalSize);
		PdfPCell titleMon = new PdfPCell(new Paragraph("��",contentFont));
		PdfPCell titleTue = new PdfPCell(new Paragraph("ȭ",contentFont));
		PdfPCell titleWen = new PdfPCell(new Paragraph("��",contentFont));
		PdfPCell titleThu = new PdfPCell(new Paragraph("��",contentFont));
		PdfPCell titleFri = new PdfPCell(new Paragraph("��",contentFont));
		PdfPCell titleSet = new PdfPCell(new Paragraph("��",contentFont));
		PdfPCell titleSun = new PdfPCell(new Paragraph("��",contentFont));
		PdfPCell titleWeek = new PdfPCell(new Paragraph("����",contentFont));

		//ǥ ����
		//���� ���
		titleMon.setHorizontalAlignment(Element.ALIGN_CENTER);
		titleTue.setHorizontalAlignment(Element.ALIGN_CENTER);
		titleWen.setHorizontalAlignment(Element.ALIGN_CENTER);
		titleThu.setHorizontalAlignment(Element.ALIGN_CENTER);
		titleFri.setHorizontalAlignment(Element.ALIGN_CENTER);
		titleSet.setHorizontalAlignment(Element.ALIGN_CENTER);
		titleSun.setHorizontalAlignment(Element.ALIGN_CENTER);
		titleWeek.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		//���� ���
		titleMon.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titleTue.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titleWen.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titleThu.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titleFri.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titleSet.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titleSun.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titleWeek.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		//ǥ ����
		table.addCell(titleSun);
		table.addCell(titleMon);
		table.addCell(titleTue);
		table.addCell(titleWen);
		table.addCell(titleThu);
		table.addCell(titleFri);
		table.addCell(titleSet);
		
		//���� �Է�		
		
		List list = new ArrayList();
		Map firstLine = new HashMap();
		firstLine.put("1","");
		firstLine.put("2","");
		firstLine.put("3","1");
		firstLine.put("4","2");
		firstLine.put("5","3");
		firstLine.put("6","4");
		firstLine.put("7","5");
		
		list.add(firstLine);
		
		for(int i = 0; i < 41; i++) {
			table.addCell(new Paragraph(i+"��°",contentFont));
		}
		table.addCell(Image.getInstance("images/20211208.png"));
		
		
		//ǥ ����
		document.add(new Paragraph(title));
		document.add(new Paragraph("\n\n"));
		document.add(table);
		
		//���ϴݱ�
		document.close();
		
		System.out.println("pdf���� ����");
		
		
		int selectMonth = 1;
		
		arrMonth = cal(selectMonth-1);
		
		System.out.println(arrMonth.size());
		
		for(int i=0; i < arrMonth.size(); i++) {
			for(int j = 0; j < 7; j++) {
				System.out.print(arrMonth.get(i).get(j)+" ");
			}
			System.out.println("");
		}
		
		}
	
	public static ArrayList<ArrayList<Integer>> cal(int month) {
		
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
		
//		cal.set(Calendar.DATE, 1);
		
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
				array.clear();
			}
			else {
				if(array.size()<7) {
					int remain = 7 - array.size();
					for(int i = 0; i < remain; i++) {
						if(kind == 0) {
							array.add(0, 0);
						}else {
							array.add(array.size(),0);
						}
					}						
				}		
			}
	}
}
