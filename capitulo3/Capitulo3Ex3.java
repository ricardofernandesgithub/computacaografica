package capitulo3ex3;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Capitulo3Ex3 extends JApplet {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 3 Exercício 3 - Flores");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Capitulo3Ex3();
		applet.init();

		frame.getContentPane().add(applet);
		frame.pack();
	}

	public void init() {
		JPanel panel = new MyPanel2D();
		getContentPane().add(panel);
	}

}

class MyPanel2D extends JPanel implements MouseListener {

	public MyPanel2D() {
		setPreferredSize(new Dimension(800, 400));
		setBackground(Color.WHITE);
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		AffineTransform tr = new AffineTransform();

		g2.setStroke(new BasicStroke(2.0f));
		AlphaComposite ac1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f); // 80% de transparência
		g2.setComposite(ac1);
		AlphaComposite ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);

		// Flor1
		Shape petala1 = new Ellipse2D.Double(0, -15, 180, 40);
		g2.translate(200, 200);
		int r = 255;
		int b = 100;
		for (int i = 0; i <= 31; ++i) {
			// GradientPaint paint = new GradientPaint(90, 0, new Color(150, 150, 150), 90,
			// 40, new Color(230, 230, 230));
			// g2.setPaint(paint);
			g2.setColor(new Color(r, 112, b));
			tr.setToRotation(Math.toRadians(18 * i));
			Shape flor = tr.createTransformedShape(petala1);
			g2.fill(flor);
			g2.setPaint(Color.DARK_GRAY);
			g2.draw(flor);
			r -= 7;
			b += 5;
		}
		int blue = 0;
		int red = 0;
		g2.translate(-200, -200);
		Shape petala2 = new Ellipse2D.Double(0, -15, 40, 10);
		g2.translate(200, 200);
		for (int i = 0; i <= 31; ++i) {
			g2.setColor(new Color(red, 128, blue));
			tr.setToRotation(Math.toRadians(18 * i));
			Shape flor = tr.createTransformedShape(petala2);
			g2.fill(flor);
			g2.setPaint(Color.BLACK);
			g2.draw(flor);
		}
		g2.translate(-200, -200);

		g2.setComposite(ac2);
		Shape centro = new Ellipse2D.Double(175, 175, 50, 50);
		g2.setColor(Color.BLACK);
		g2.draw(centro);
		g2.setColor(Color.YELLOW);
		g2.fill(centro);

		// Flor2
		AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f); // 80% de transparência
		g2.setComposite(ac3);
		GeneralPath petala5 = new GeneralPath();
		petala5.moveTo(0, -10);
		petala5.lineTo(110, 0);
		petala5.lineTo(0, 10);
		petala5.closePath();
		// g2.draw(petala5);
		g2.translate(580, 180);
		for (int i = 0; i <= 31; ++i) {
			tr.setToRotation(Math.toRadians(18 * i));
			Shape petala6 = tr.createTransformedShape(petala5);
			g2.setColor(new Color(0, 128, 0));
			g2.fill(petala6);
			g2.setPaint(Color.GREEN);
			g2.draw(petala6);
		}
		g2.translate(-580, -180);
		GeneralPath petala3 = new GeneralPath();
		petala3.moveTo(0, 0 - 10);
		petala3.lineTo(111, 8 - 10);
		petala3.lineTo(147, 20 - 10);
		petala3.lineTo(97, 17 - 10);
		petala3.lineTo(0, 20 - 10);
		petala3.closePath();
		// g2.draw(petala3);
		g2.translate(580, 180);
		g2.rotate(Math.toRadians(7));
		for (int i = 0; i <= 31; ++i) {
			tr.setToRotation(Math.toRadians(18 * i));
			Shape petala4 = tr.createTransformedShape(petala3);
			g2.setColor(new Color(255, 128, 0));
			g2.fill(petala4);
			g2.setPaint(new Color(217, 108, 0));
			g2.draw(petala4);
		}
		g2.translate(-580, -180);
		g2.translate(580, 180);
		Shape centro2 = new Rectangle(-20, -20, 40, 40);
		for (int i = 0; i <= 31; ++i) {
			tr.setToRotation(Math.toRadians(18 * i));
			Shape centro3 = tr.createTransformedShape(centro2);
			g2.setColor(Color.YELLOW);
			g2.fill(centro3);
			g2.setPaint(Color.GRAY);
			g2.draw(centro3);
		}
		g2.translate(-580, -180);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX() + ", " + e.getY());
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

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
