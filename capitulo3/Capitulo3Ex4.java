package capitulo3ex4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.scene.shape.Ellipse;

public class Capitulo3Ex4 extends JApplet {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 3 Exercício 4 - Glyphs");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Capitulo3Ex4();
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
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setStroke(new BasicStroke(1.5f));
		Area areaCircularExt = new Area(new Ellipse2D.Float(0, 0, 400, 400));
		Area areaCircularInt = new Area(new Ellipse2D.Float(10, 10, 380, 380));
		// g2.fill(areaCircularExt);
		// g2.setColor(Color.WHITE);
		// g2.fill(areaCircularInt);
		// g2.setColor(Color.YELLOW);
		// g2.draw(areaCircularExt);
		// g2.draw(areaCircularInt);
		areaCircularExt.subtract(areaCircularInt);
		GradientPaint paint = new GradientPaint(-0, 200, Color.YELLOW, 600, 200, Color.BLUE);
		g2.setPaint(paint);
		g2.fill(areaCircularExt);
		// g2.setColor(Color.BLUE);
		// g2.draw(areaCircularExt);

		Font fonte = new Font("Times New Roman", Font.PLAIN, 200);
		g2.setFont(fonte);
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector glyph = fonte.createGlyphVector(frc, "R");
		Shape r = glyph.getOutline(125, 245);
		// g2.draw(r);
		// g2.rotate(Math.toRadians(-35));
		glyph = fonte.createGlyphVector(frc, "F");
		Shape f = glyph.getOutline(195, 330);
		// g2.draw(f);

		// g2.rotate(Math.toRadians(35));
		g2.translate(-15, -25);
		Area areaR = new Area(r);
		Area areaF = new Area(f);
		areaR.add(areaF);
		g2.setColor(Color.MAGENTA);
		g2.setStroke(new BasicStroke(4.0f));
		g2.draw(areaR);
		paint = new GradientPaint(100, 100, Color.ORANGE, 400, 400, Color.GREEN);
		g2.setPaint(paint);
		g2.fill(areaR);
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
