package java_to_pdf;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReSizeImge {

	public static void main(String[] args) {
		try {
			Image img = ImageIO.read(new File("images/123123.jpg"));
			
			Image resizeImage = img.getScaledInstance(900, 700, Image.SCALE_SMOOTH);
			
			BufferedImage newImage = new BufferedImage(900, 700, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.getGraphics();
            g.drawImage(resizeImage, 0, 0, null);
            g.dispose();
            ImageIO.write(newImage, "jpg", new File("images/123123_new.jpg"));
			
            System.out.println("¼º°ø");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
