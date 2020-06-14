import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FractalExplorer extends JFrame{

	private JImageDisplay image;
	private JButton reset;
	private  JButton save;
	private final Rectangle2D.Double rectangle;
	private FractalGenerator fractal;
	private final int size;
	private JComboBox comboBox = null;

	public FractalExplorer(int size) {
		this.size = size;

		this.rectangle = new Rectangle2D.Double(0, 0, size, size);

		createAndShowGUI(new Dimension(size, size));

		fractal = new Mandelbrot();
		fractal.getInitialRange(rectangle);

		drawFractal();
	}

	private void createAndShowGUI(Dimension dim){
		reset = new JButton("Reset Fractal");
		reset.setBackground(Color.GRAY);

		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				fractal.getInitialRange(rectangle);
				drawFractal();
			}
		});

		save = new JButton("Save Fractal");
		save.setBackground(Color.GRAY);

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser chooser = new JFileChooser();

				FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);

				chooser.showDialog(null, "Выбрать файл");

				File file = chooser.getSelectedFile();
				BufferedImage buf = image.getImage();
				try {
					ImageIO.write(buf, "PNG", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		setSize(dim.width, dim.height + reset.getHeight());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FractalGenerator[] fractals = {new Mandelbrot(), new Tricorn(), new BurningShip()};

		comboBox = new JComboBox(fractals);

		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JComboBox box = (JComboBox) actionEvent.getSource();
				fractal = (FractalGenerator) box.getSelectedItem();
				fractal.getInitialRange(rectangle);
				drawFractal();
			}
		});

		int height = dim.height - reset.getHeight();
		image = new JImageDisplay(height, dim.width);
		image.clearImage();

		image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				double xCoord = FractalGenerator.getCoord(rectangle.x, rectangle.x + rectangle.width, size,x);
				double yCoord = FractalGenerator.getCoord(rectangle.y, rectangle.y + rectangle.height, size,y);
				fractal.recenterAndZoomRange(rectangle,xCoord,yCoord,0.5);
				drawFractal();
			}
		});

		JPanel panel = new JPanel();
		panel.add(reset);
		panel.add(save);

		add(image, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		add(comboBox, BorderLayout.NORTH);

		pack ();
		setVisible (true);
		setResizable (false);
	}

	private void drawFractal() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				double xCoord = FractalGenerator.getCoord(rectangle.x, rectangle.x + rectangle.width, size, x);
				double yCoord = FractalGenerator.getCoord(rectangle.y, rectangle.y + rectangle.height, size, y);

				int iteration = fractal.numIterations(xCoord, yCoord);

				if (iteration == -1)
					image.drawPixel(x, y, 0);
				else {
					float hue = 0.5f + (float) iteration / 200f;
					int rgbColor = Color.HSBtoRGB(hue, 0.7f, 1.0f);
					image.drawPixel(x, y, rgbColor);
				}
			}
		}
		image.repaint();
	}
}
