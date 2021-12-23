package java_to_pdf;



import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class ReSizeImages extends JFrame{
	
	Container container;
	JButton btn;
	
	JLabel imageLabel = new JLabel("0개");
	JLabel textView = new JLabel();
	JScrollPane sp = new JScrollPane(textView);
	JLabel justText = new JLabel("선택한 파일");
	JLabel yearText = new JLabel("연도 선택");
	JLabel monthText = new JLabel("월 선택");
	String directPath = "";
	
	String[] MONTH = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	ArrayList<Integer> YEAR = new ArrayList<Integer>();
	
	JComboBox<String> combo = new JComboBox<String>(MONTH);

	ArrayList<String> htap = new ArrayList<String>();
	ArrayList<String> fileName = new ArrayList<String>();

	private String emptyImageFilePath = "etc/emptyImage.png";
	
	public ReSizeImages(){
		
		LocalDate dayNow = LocalDate.now();
		String getYear = dayNow.format(DateTimeFormatter.ofPattern("yyyy"));
		String getMonth = dayNow.format(DateTimeFormatter.ofPattern("MM"));
		System.out.println(getYear);
		System.out.println(getMonth);
		
		String[] yearList = new String[20];
		
		for(int i = 0; i < yearList.length; i++){
			
			int toInt = Integer.parseInt(dayNow.format(DateTimeFormatter.ofPattern("yyyy")))+(i-1);
			
			yearList[i] = toInt+"";
		}
		
		JComboBox<String> comboYear = new JComboBox<String>(yearList);
		
		setTitle("사진편집기");
		setSize(400,600);

		container = getContentPane();
		container.setLayout(null);
		
		imageLabel.setBounds(100,-140,200,330);
		sp.setBounds(10,50,250,420);
		textView.setBackground(new Color(255,255,255));
		textView.setOpaque(true);
		
		
		justText.setBounds(10,-140,200,330);
		combo.setBounds(300,400,60,25);
		combo.setSelectedItem(getMonth);
		monthText.setBounds(300,380,60,25);
		comboYear.setBounds(300,350,60,25);
		comboYear.setSelectedIndex(1);
		yearText.setBounds(300,330,60,25);

		container.add(imageLabel);
		container.add(sp);
		container.add(justText);
		container.add(combo);
		container.add(monthText);
		container.add(comboYear);
		container.add(yearText);
		
		JButton btn = new JButton("파일 열기");
		JButton btn2 = new JButton("사진 편집");
		btn.setBounds(10,500,150,50);
		btn2.setBounds(220,500,150,50);
		
		btn.addActionListener(new OpenActionListener());
		btn2.addActionListener(e ->{
			if(htap.size() != 0) {
				int result = JOptionPane.showConfirmDialog(this,comboYear.getSelectedItem()+"년"+combo.getSelectedItem()+"월\n 맞습니까?","확인",JOptionPane.YES_NO_OPTION);				
				if(result == JOptionPane.YES_OPTION) {
					try {
					
					JFileChooser jchooser = new JFileChooser();
					jchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jchooser.showDialog(this, null);
						
					directPath = jchooser.getSelectedFile().toString();
						
					String asdasd = htap.get(0).substring(htap.get(0).length()-3, htap.get(0).length());
						
					System.out.println("333 " + asdasd);
					System.out.println("333 "+ directPath);
											
					ResizeImage(Integer.parseInt(combo.getSelectedItem().toString()), Integer.parseInt(comboYear.getSelectedItem().toString()));
					}catch(Exception ee) {
						return;
					}
				}else {
					return;
				}	
					
			}else {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다.", "경고",JOptionPane.WARNING_MESSAGE);
				return;	
			}		
		});
		
		container.add(btn);
		container.add(btn2);
				
		setVisible(true);
		
	}
	
	class OpenActionListener implements ActionListener {
		
		JFileChooser chooser;
		
		OpenActionListener(){
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			
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
			textView.setText(path);
			imageLabel.setText(filePath.length+"개");
			
			System.out.println("etc"+filePath[0].getName());
			
			for(int i = 0; i < htap.size(); i++) {
				System.out.println(htap.get(i));
			}
			
			}
		}
	
	public void ResizeImage(int selectedMonth,int selectedYear) {
		try {
			
			int weekCount = getWeekCount(selectedMonth, selectedYear);
			System.out.println("weekCount : "+weekCount);
			int imageHeight = 0;
			if(weekCount == 6) {
				imageHeight = 555;
			}else {
				imageHeight = 700;
			}
	
			for(int i = 0; i < htap.size(); i++) {
				
				Image img = ImageIO.read(new File(htap.get(i)));
				
				Image resizeImage = img.getScaledInstance(900, imageHeight, Image.SCALE_SMOOTH);
				
				String extension = htap.get(i).substring(htap.get(i).length()-3, htap.get(i).length());
				
				BufferedImage newImage = new BufferedImage(900, imageHeight, BufferedImage.TYPE_INT_RGB);
				Graphics g = newImage.getGraphics();
	            g.drawImage(resizeImage, 0, 0, null);
	            g.dispose();
	            ImageIO.write(newImage, extension, new File(directPath+"\\"+fileName.get(i)));
	            
				
	            System.out.println(directPath + "\\" + fileName.get(i));
			}
            
			JOptionPane.showMessageDialog(null,"편집이 완료되었습니다.\n편집창을 끄셔도 됩니다.", "알림",JOptionPane.WARNING_MESSAGE);
	            
			return;	
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
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


