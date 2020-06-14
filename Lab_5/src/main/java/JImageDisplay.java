import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class JImageDisplay extends JComponent{

	private BufferedImage buf_image;

	public JImageDisplay(int height, int width) {
		buf_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(buf_image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	public void clearImage() {
		for(int y = 0; y < buf_image.getHeight(); y++) {
			for(int x = 0; x < buf_image.getWidth(); x++) {
				buf_image.setRGB(x, y, 0);
			}
		}
	}

	public void drawPixel(int x, int y, int rgbColor) {
		buf_image.setRGB(x, y, rgbColor);
	}

	public BufferedImage getImage(){
		return buf_image;
	}

}
