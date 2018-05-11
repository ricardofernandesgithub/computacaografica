package capitulo4ex1;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class Capitulo4Ex1 extends JApplet implements ActionListener {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 4 Exercício 1 - Processamento Imagem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Capitulo4Ex1();
		applet.init();

		frame.getContentPane().add(applet);
		frame.pack();
	}

	ImagePanel imgSrc, imgDst;
	public static int limiar = 123;
	JSlider jSliderLimiar = new javax.swing.JSlider();
	JLabel jLabelLimiar = new javax.swing.JLabel();
	Container cp = getContentPane();

	public Capitulo4Ex1() {

		// Menu Bar
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);

		// Menu Processing
		JMenu menu = new JMenu("Processamento");

		JMenuItem mi = new JMenuItem("Binarização");
		mi.addActionListener(this);
		menu.add(mi);

		mi = new JMenuItem("Emboss");
		mi.addActionListener(this);
		menu.add(mi);

		mi = new JMenuItem("Espelho Vertical");
		mi.addActionListener(this);
		menu.add(mi);

		mi = new JMenuItem("Espelho Horizontal");
		mi.addActionListener(this);
		menu.add(mi);

		mb.add(menu);

		// Slider
		jLabelLimiar.setText("Limiar Binarização");
		jSliderLimiar.setMajorTickSpacing(50);
		jSliderLimiar.setMaximum(255);
		jSliderLimiar.setMinimum(0);
		jSliderLimiar.setMinorTickSpacing(20);
		jSliderLimiar.setPaintLabels(true);
		jSliderLimiar.setPaintTicks(true);
		jSliderLimiar.setVisible(true);
		jSliderLimiar.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderLimiarStateChanged(evt);
			}

			public void jSliderLimiarStateChanged(ChangeEvent evt) {
				limiar = jSliderLimiar.getValue();
				imgDst.setImage(Binarization(imgSrc.getImage()));
			}
		});

		// Panels
		cp.setLayout(new FlowLayout(0, 0, 0));
		imgSrc = new ImagePanel();
		imgDst = new ImagePanel();
		cp.add(imgSrc);
		cp.add(imgDst);
		cp.add(jLabelLimiar);
		cp.add(jSliderLimiar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		processing(cmd);
	}

	void processing(String cmd) {

		BufferedImageOp op = null;
		BufferedImage bi = null;

		if (cmd.equals("Emboss")) {
			float[] data = { -2f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 2f };
			Kernel k = new Kernel(3, 3, data);
			op = new ConvolveOp(k);
		} else if (cmd.equals("Binarização")) {
			imgDst.setImage(Binarization(imgSrc.getImage()));
		} else if (cmd.equals("Espelho Vertical")) {
			imgDst.setImage(espelhoVertical(imgSrc.getImage()));
		} else if (cmd.equals("Espelho Horizontal")) {
			imgDst.setImage(espelhoHorizontal(imgSrc.getImage()));
		}

		if (op != null) {
			bi = op.filter(imgSrc.getImage(), null);
			imgDst.setImage(bi);
		}
	}

	public BufferedImage Binarization(BufferedImage imgIn) {
		BufferedImage imgOut = new BufferedImage(imgIn.getWidth(), imgIn.getHeight(), imgIn.getType());

		WritableRaster rasterImgIn = imgIn.getRaster();
		WritableRaster rasterImgOut = imgOut.getRaster();
		// System.out.println(limiar);
		int rgba[] = new int[4];
		for (int x = 0; x < imgIn.getWidth(); x++) {
			for (int y = 0; y < imgIn.getHeight(); y++) {
				rasterImgIn.getPixel(x, y, rgba);
				int gray = (int) ((rgba[0] + rgba[1] + rgba[2]) / 3f);
				if (gray <= limiar) {
					rgba[0] = rgba[1] = rgba[2] = 0;
					rasterImgOut.setPixel(x, y, rgba);
				} else {
					rgba[0] = rgba[1] = rgba[2] = 255;
					rasterImgOut.setPixel(x, y, rgba);
				}
			}
		}
		return imgOut;
	}

	BufferedImage espelhoVertical(BufferedImage imgSrc) {
		AffineTransform at = new AffineTransform();
		at.concatenate(AffineTransform.getScaleInstance(1, -1));
		at.concatenate(AffineTransform.getTranslateInstance(0, -imgSrc.getHeight()));
		return criarTransformada(imgSrc, at);
	}

	BufferedImage espelhoHorizontal(BufferedImage imgSrc) {
		AffineTransform at = new AffineTransform();
		at.concatenate(AffineTransform.getScaleInstance(-1, 1));
		at.concatenate(AffineTransform.getTranslateInstance(-imgSrc.getWidth(), 0));
		return criarTransformada(imgSrc, at);
	}

	private static BufferedImage criarTransformada(BufferedImage bi, AffineTransform at) {
		BufferedImage newImage = new BufferedImage(bi.getWidth(), bi.getHeight(), 2);

		Graphics2D g = newImage.createGraphics();
		g.transform(at);
		g.drawImage(bi, 0, 0, null);
		g.dispose();
		return newImage;
	}

}

class ImagePanel extends JPanel {

	BufferedImage image = null;

	public ImagePanel() {
		setPreferredSize(new Dimension(308, 256));
		URL url = getClass().getClassLoader().getResource("images/Penguins.jpg");
		try {
			this.image = ImageIO.read(url);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (image != null) {
			g2.drawImage(image, 0, 0, this);
		} else {
			g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	public void setImage(BufferedImage bi) {
		image = bi;
		setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
		repaint();
	}

	public BufferedImage getImage() {
		return image;
	}

}
