package capitulo4ex2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Capitulo4Ex2 extends JApplet implements ActionListener {
	private Container cp;
	private PrinterJob pj;
	private MyPanel2D painter;
	private JButton button;

	public Capitulo4Ex2() {
		cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		button = new JButton("Imprimir Imagem");
		cp.add(button, BorderLayout.BEFORE_FIRST_LINE);
		button.addActionListener(this);

		painter = new MyPanel2D();
		cp.add(painter, BorderLayout.CENTER);

		pj = PrinterJob.getPrinterJob();
		pj.setPrintable(painter);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 4 Exercício 2 - Paint");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Capitulo4Ex2();
		applet.init();

		frame.getContentPane().add(applet);
		frame.pack();
	}

	public void init() {
		JPanel panel = new MyPanel2D();
		super.getContentPane().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}
}

class MyPanel2D extends JPanel implements MouseListener, MouseMotionListener, Printable {

	int x, y;
	BufferedImage bi = new BufferedImage(400, 400, 1);
	WritableRaster raster = bi.getRaster();
	Graphics2D g2 = (Graphics2D) bi.getGraphics();
	int pixelBranco[] = { 255, 255, 255 };
	int pixelPreto[] = { 0, 0, 0 };
	int botaoRato = 1; // 1 corresponde ao botão esquerdo e 3 ao direito

	public MyPanel2D() {
		setPreferredSize(new Dimension(400, 400));
		// setBackground(Color.WHITE);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 400, 400);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		mudarPixel(g);
	}

	private void mudarPixel(Graphics g) {
		if (botaoRato == 1) {
			raster.setPixel(x, y, pixelPreto);
		} else if (botaoRato == 3) {
			raster.setPixel(x, y, pixelBranco);
		}
		g.drawImage(bi, 0, 0, null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		switch (pageIndex) {
		case 0:
			paintComponent(graphics);
			break;
		default:
			return NO_SUCH_PAGE;
		}
		return PAGE_EXISTS;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		botaoRato = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
