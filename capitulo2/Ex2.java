package capitulo2Ex2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.javafx.geom.Arc2D;

public class Ex2 extends JApplet {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 2 Exercício 2 - Palhaço");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Ex2();
		applet.init();

		frame.getContentPane().add(applet);
		frame.pack(); // Ajuste automático de tamanho da JFrame de acordo com os componentes
						// utilizados

	}

	public void init() {
		JPanel panel = new MyPanel2D();
		getContentPane().add(panel);
	}

}

class MyPanel2D extends JPanel implements MouseListener {

	int x = 200, y = 200;

	public MyPanel2D() {
		setPreferredSize(new Dimension(400, 400)); // Definir dimensões do JPanel
		setBackground(Color.WHITE);
		addMouseListener(this); // Implementação do mouse listener
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Shape contornoCara = new Ellipse2D.Double(x - 100, y - 100, 200, 200);
		g2.draw(contornoCara);
		Area areaContornoCara = new Area(contornoCara);

		Shape olhoDireitoA = new Ellipse2D.Double(x - 70, y - 75, 45, 60);
		g2.draw(olhoDireitoA);
		Shape olhoDireitoB = new Ellipse2D.Double(x - 55, y - 32, 15, 15);
		g2.fill(olhoDireitoB);
		Shape olhoEsquerdoA = new Ellipse2D.Double(x + 20, y - 75, 45, 60);
		g2.draw(olhoEsquerdoA);
		Shape olhoEsquerdoB = new Ellipse2D.Double(x + 35, y - 32, 15, 15);
		g2.fill(olhoEsquerdoB);

		GeneralPath pathLaco = new GeneralPath();
		pathLaco.moveTo(x - 72, y + 70);
		pathLaco.lineTo(x - 72, y + 118);
		pathLaco.lineTo(x + 70, y + 70);
		pathLaco.lineTo(x + 70, y + 118);
		pathLaco.closePath();
		// g2.draw(pathLaco);
		g2.setColor(Color.GREEN);
		g2.fill(pathLaco);
		Shape detalheLaco = new Ellipse2D.Double(x - 16, y + 80, 30, 30);
		g2.fill(detalheLaco);

		g2.setColor(Color.RED);
		Shape nariz = new Ellipse2D.Double(x - 33, y - 10, 65, 65);
		g2.fill(nariz);
		Area areaNariz = new Area(nariz);
				
		Area areaAuxiliar1 = new Area(new Rectangle(x - 200, y - 200, 400, 240));
		// g2.draw(areaAuxiliar1);
		g2.setColor(Color.BLUE);
		Shape boca = new Ellipse2D.Double(x - 72, y - 27, 150, 100);
		//g2.setStroke(new BasicStroke(7.5f));
		Area areaBoca = new Area(boca);
		areaBoca.subtract(areaAuxiliar1);
		areaBoca.subtract(areaNariz);
		g2.draw(areaBoca);
		
		g2.setColor(Color.ORANGE);
		GeneralPath pathChapeu = new GeneralPath();
		pathChapeu.moveTo(x - 97, y -42);
		pathChapeu.lineTo(x - 28, y -100);
		pathChapeu.lineTo(x -129, y -136);
		pathChapeu.closePath();
		g2.fill(pathChapeu);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		repaint();
		System.out.println("x = " + e.getX() + ", y = " + e.getY()); // Mostra as coordenadas do ponto onde o cursor
																		// clicou
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
