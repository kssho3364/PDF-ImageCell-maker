package java_to_pdf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.DocumentException;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReSizeImge extends JFrame {

	Container container;
	JTextField inputTitle, inputHeader,  inputFooter, inputFileName;
	JLabel titleLabel, headerLabel, footerLabel, fileNameLabel, fileListLabel, textLabel1, pathLabel, monthLabel, yearLabel, fileCountLabel;
	JButton selectPic_bt, reSizeIamge_bt, selectPath_bt, create_bt;
	JScrollPane sp;
	
	String[] MONTH = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	ArrayList<Integer> YEAR = new ArrayList<Integer>();
	ArrayList<String> htap = new ArrayList<String>(); // 파일들의 경로가 담겨있는 리스트
	ArrayList<String> fileName = new ArrayList<String>();
	
	int isClickCombo = 0;
	int isClickComboYear = 0;
	String directPath = "";
	
	JComboBox<String> combo = new JComboBox<String>(MONTH);
	
	public static void main(String[] args) {
		
		new ReSizeImge();
	}
	
	public ReSizeImge(){
		
		LocalDate dateNow = LocalDate.now();
		String yearNow = dateNow.format(DateTimeFormatter.ofPattern("yyyy"));
		String monthNow = dateNow.format(DateTimeFormatter.ofPattern("MM"));
		String[] yearList = new String[20];
		
		for(int i = 0; i < yearList.length; i++){
			
			int toInt = Integer.parseInt(yearNow)+(i-1);
			
			yearList[i] = toInt+"";
		}
		
		JComboBox<String> comboYear = new JComboBox<String>(yearList);
		
		setTitle("PDF 생성기");
		setSize(400,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = getContentPane();
		container.setLayout(null);
		
		titleLabel = new JLabel("제목");
		headerLabel = new JLabel("머릿말");
		footerLabel = new JLabel("꼬릿말");
		fileNameLabel = new JLabel("파일명");
		fileListLabel = new JLabel("선택한 파일이 없습니다.");
		textLabel1 = new JLabel("선택된 파일");
		pathLabel = new JLabel("경로를 선택해주세요.");
		yearLabel = new JLabel("년도 선택");
		monthLabel = new JLabel("월 선택");
		fileCountLabel = new JLabel("0개");
		
		inputTitle = new JTextField(40);
		inputHeader = new JTextField();
		inputFooter = new JTextField();
		inputFileName = new JTextField();
		
		reSizeIamge_bt = new JButton("사진편집기");
		selectPic_bt = new JButton("사진 선택");
		selectPath_bt = new JButton("저장경로 선택");
		create_bt = new JButton("PDF생성");	
		
		
		// x좌표, y좌표, 가로크기, 세로크기
		titleLabel.setBounds(180,40,50,25);
		headerLabel.setBounds(180,80,50,25);
		footerLabel.setBounds(180,120,50,25);
		fileNameLabel.setBounds(180,160,50,25);
		textLabel1.setBounds(10,10,100,25);
		pathLabel.setBounds(230,360,140,25);
		yearLabel.setBounds(250,400,55,25);
		monthLabel.setBounds(250,440,55,25);
		fileCountLabel.setBounds(80,10,50,25);
		
		pathLabel.setBorder(new TitledBorder(new LineBorder(Color.black,1)));
		
		fileListLabel.setBackground(new Color(255,255,255));
		fileListLabel.setOpaque(true);
		
		pathLabel.setBackground(new Color(255,255,255));
		pathLabel.setOpaque(true);
		
		inputTitle.setBounds(230,40,140,25);
		inputHeader.setBounds(230,80,140,25);
		inputFooter.setBounds(230,120,140,25);
		inputFileName.setBounds(230,160,140,25);
		
		reSizeIamge_bt.setBounds(230,240,140,25);
		selectPic_bt.setBounds(230,280,140,25);
		selectPath_bt.setBounds(230,320,140,25);
		create_bt.setBounds(190,490,180,50);
		
		combo.setBounds(310,440,60,25);
		comboYear.setBounds(310,400,60,25);
		
		combo.setSelectedItem(monthNow);
		comboYear.setSelectedItem(yearNow);
		
		sp = new JScrollPane(fileListLabel);
		sp.setBounds(10,40,160,500);

		
		container.add(titleLabel);
		container.add(headerLabel);
		container.add(footerLabel);
		container.add(fileNameLabel);
		container.add(sp);
		container.add(textLabel1);
		container.add(pathLabel);
		container.add(yearLabel);
		container.add(monthLabel);
		container.add(fileCountLabel);
		
		container.add(inputTitle);
		container.add(inputHeader);
		container.add(inputFooter);
		container.add(inputFileName);
		
		container.add(reSizeIamge_bt);
		container.add(selectPic_bt);
		container.add(selectPath_bt);
		container.add(create_bt);
		
		container.add(combo);
		container.add(comboYear);
		
		setVisible(true);
		
		reSizeIamge_bt.addActionListener(e ->{
			new ReSizeImages();
		});
		
		selectPic_bt.addActionListener(e ->{
			JFileChooser chooser = new JFileChooser();
			
			htap.clear();
			fileName.clear();
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG","jpg","png");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(true);
			int ret = chooser.showOpenDialog(null);
			if(ret != JFileChooser.APPROVE_OPTION) {
			return;	
			}
			
			
			File[] filePath = chooser.getSelectedFiles();
			String path= "";

			for(int i = 0; i < filePath.length; i++) {
				if(i==0){
					path = "<HTML>" + (i+1) + ". " + filePath[i].getName()+"<br>";
				}else {
					path = path + (i+1) + ". " +filePath[i].getName()+"<br>";
				}
				fileName.add(filePath[i].getName());
				htap.add(filePath[i].toString());
			}
			path = path+"</HTML>";
			
			fileListLabel.setText(path);
			fileCountLabel.setText(filePath.length+"개");
			
			System.out.println("etc"+filePath[0].getName());
			
			for(int i = 0; i < htap.size(); i++) {
				System.out.println(htap.get(i));
			}
		});
		
		selectPath_bt.addActionListener(e ->{
			try {
				isClickCombo = 0;
				JFileChooser jchooser = new JFileChooser();
				jchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jchooser.showDialog(this, null);
					
				directPath = jchooser.getSelectedFile().toString();

				pathLabel.setText(directPath);
										
			}catch(Exception ee) {
				return;
			}
		});
		
		combo.addActionListener(e ->{
			isClickCombo = 1;
		});
		
		comboYear.addActionListener(e ->{
			isClickComboYear = 1;
		});
		
		create_bt.addActionListener(e ->{
			try {
				new JavaToPdf(htap,directPath,inputTitle.getText(),inputHeader.getText(),inputFooter.getText()
						, inputFileName.getText(),comboYear.getSelectedItem().toString(),combo.getSelectedItem().toString());
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	

}
