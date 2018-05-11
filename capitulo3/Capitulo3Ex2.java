package capitulo3ex2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Capitulo3Ex2 extends JApplet {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 3 Exercício 2 - Cenário de Jogo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Capitulo3Ex2();
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
		setBackground(new Color(137, 188, 245));
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(new Color(221, 226, 5));
		GeneralPath pathNave = new GeneralPath();
		pathNave.moveTo(5, 201);
		pathNave.lineTo(5, 238);
		pathNave.lineTo(50, 238);
		pathNave.closePath();
		g2.fill(pathNave);
		g2.setColor(Color.BLUE);
		GeneralPath pathJanela = new GeneralPath();
		pathJanela.moveTo(23, 215);
		pathJanela.lineTo(23, 224);
		pathJanela.lineTo(34, 224);
		pathJanela.closePath();
		g2.fill(pathJanela);
		Font pontuacao = new Font("Consolas", Font.BOLD, 22);
		g2.setFont(pontuacao);
		g2.setColor(Color.MAGENTA);
		g2.drawString("- - - - -", 50, 240);
		g2.setColor(Color.BLACK);
		Rectangle propulsor = new Rectangle(2, 210, 3, 20);
		g2.fill(propulsor);

		g2.setColor(new Color(9, 157, 43));
		GeneralPath pathMontanha2 = new GeneralPath();
		pathMontanha2.moveTo(0, 400);
		pathMontanha2.lineTo(270, 400);
		pathMontanha2.lineTo(270, 350);
		pathMontanha2.quadTo(167, 324, 121, 351);
		pathMontanha2.quadTo(57, 370, 0, 350);
		pathMontanha2.closePath();
		g2.fill(pathMontanha2);

		int[] pontosX = { 262, 177, 356 };
		int[] pontosY = { 272, 400, 400 };
		Shape montanha3 = new Polygon(pontosX, pontosY, 3);
		GradientPaint paint = new GradientPaint(184, 317, Color.WHITE, 184, 400, new Color(9, 103, 3));
		g2.setPaint(paint);
		g2.fill(montanha3);
		g2.scale(0.85, 0.85);
		g2.translate(113, 70);
		g2.fill(montanha3);
		g2.translate(-113, -70);

		GeneralPath pathMontanha1 = new GeneralPath();
		pathMontanha1.moveTo(184, 318);
		pathMontanha1.lineTo(156, 400);
		pathMontanha1.lineTo(213, 400);
		pathMontanha1.closePath();

		g2.setColor(new Color(255, 131, 6));
		GeneralPath pathInimigos = new GeneralPath();
		pathInimigos.moveTo(219, 223);
		pathInimigos.lineTo(242, 212);
		pathInimigos.lineTo(274, 228);
		pathInimigos.lineTo(274, 214);
		pathInimigos.lineTo(243, 235);
		pathInimigos.closePath();
		g2.translate(-20, 55);
		g2.fill(pathInimigos);
		g2.translate(20, -55);
		g2.translate(50, -20);
		g2.fill(pathInimigos);
		g2.translate(-50, 20);
		g2.setColor(new Color(64, 0, 128));
		g2.translate(150, 40);
		g2.fill(pathInimigos);
		g2.translate(-150, -40);

		g2.translate(210, 70);
		g2.setColor(new Color(9, 103, 3));
		g2.fill(pathMontanha1);
		g2.translate(-210, -70);
		g2.translate(225, 70);
		g2.fill(pathMontanha1);
		g2.translate(-225, -70);
		g2.translate(255, 70);
		g2.fill(pathMontanha1);
		g2.translate(-255, -70);

		g2.setColor(Color.RED);
		g2.drawString("\u2665 2", 5, 20);
		g2.drawString("0001750", 380, 20);

		g2.setColor(Color.WHITE);
		g2.translate(150, 45);
		Shape nuvem1 = new Ellipse2D.Double(0, 64, 70, 40);
		g2.fill(nuvem1);
		Shape nuvem2 = new Ellipse2D.Double(33, 55, 80, 65);
		g2.fill(nuvem2);
		Shape nuvem3 = new Ellipse2D.Double(66, 67, 70, 45);
		g2.fill(nuvem3);
		g2.translate(-150, -45);

		g2.scale(0.75, 0.75);
		g2.translate(40, 35);
		nuvem1 = new Ellipse2D.Double(0, 64, 70, 40);
		g2.fill(nuvem1);
		nuvem2 = new Ellipse2D.Double(33, 55, 80, 65);
		g2.fill(nuvem2);
		nuvem3 = new Ellipse2D.Double(66, 67, 70, 45);
		g2.fill(nuvem3);
		g2.translate(-40, -35);

		g2.setColor(Color.LIGHT_GRAY);
		g2.translate(395, 5);
		nuvem1 = new Ellipse2D.Double(0, 64, 70, 40);
		g2.fill(nuvem1);
		nuvem2 = new Ellipse2D.Double(33, 55, 80, 65);
		g2.fill(nuvem2);
		nuvem3 = new Ellipse2D.Double(66, 67, 70, 45);
		g2.fill(nuvem3);
		g2.translate(395, -5);
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
