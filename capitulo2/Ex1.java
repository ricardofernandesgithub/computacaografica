package capitulo2Ex1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Limonada
public class Ex1 extends JApplet {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 2 Exercício 1 - Limonada");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Ex1();
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

	int x, y;

	public MyPanel2D() {
		setPreferredSize(new Dimension(400, 400)); // Definir dimensões do JPanel
		setBackground(Color.WHITE);
		addMouseListener(this); // Implementação do mouse listener
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// rodela de limão
		g2.setColor(Color.GREEN);
		g2.drawOval(50, 90, 75, 75);
		g2.drawOval(51, 91, 74, 74);
		g2.setColor(Color.YELLOW);
		g2.drawOval(52, 92, 73, 73);
		g2.drawOval(52, 92, 72, 72);
		g2.drawOval(53, 93, 71, 71);
		g2.drawLine(86, 124, 62, 117);
		g2.drawLine(86, 124, 63, 113);
		g2.drawLine(86, 124, 64, 109);
		g2.drawLine(86, 124, 67, 105);
		g2.drawLine(86, 124, 70, 102);
		g2.drawLine(91, 122, 85, 100);
		g2.drawLine(91, 122, 87, 97);
		g2.drawLine(91, 122, 92, 97);
		g2.drawLine(91, 122, 96, 98);
		g2.drawLine(91, 122, 101, 99);
		g2.drawLine(97, 123, 110, 108);
		g2.drawLine(97, 123, 113, 111);
		g2.drawLine(97, 123, 115, 116);
		g2.drawLine(97, 123, 117, 121);
		g2.drawLine(97, 123, 119, 125);
		g2.drawLine(81, 131, 59, 129);
		g2.drawLine(81, 131, 59, 133);
		g2.drawLine(81, 131, 60, 137);
		g2.drawLine(81, 131, 60, 143);
		g2.drawLine(81, 131, 61, 147);
		g2.drawLine(86, 135, 72, 152);
		g2.drawLine(86, 135, 77, 154);
		g2.drawLine(86, 135, 82, 156);
		g2.drawLine(86, 135, 86, 159);
		g2.drawLine(86, 135, 90, 160);
		g2.drawLine(95, 132, 100, 156);
		g2.drawLine(95, 132, 105, 155);
		g2.drawLine(95, 132, 110, 151);
		g2.drawLine(95, 132, 113, 148);
		g2.drawLine(95, 132, 117, 142);
		// superfície lateral do copo:
		g2.setColor(Color.BLACK);
		g2.drawLine(97, 140, 126, 364);
		g2.drawLine(281, 140, 252, 364);
		// curvatura superior do copo:
		g2.drawArc(97, 125, 184, 30, 0, 180);
		g2.drawArc(97, 125, 184, 30, 0, -180);
		// curvatura inferior do copo:
		g2.drawArc(126, 349, 126, 30, 0, 180);
		g2.drawArc(126, 349, 126, 30, 0, -180);
		// nível da limonada
		g2.setColor(Color.YELLOW);
		g2.drawArc(105, 187, 168, 30, 0, 180);
		g2.drawArc(105, 187, 168, 30, 0, -180);
		// cubo de gelo
		g2.setColor(new Color(0, 100, 255));
		g2.drawRect(156, 235, 20, 20);
		g2.drawLine(156, 235, 159, 232);
		g2.drawLine(176, 235, 179, 232);
		g2.drawLine(159, 232, 179, 232);
		g2.drawLine(176, 255, 179, 252);
		g2.drawLine(179, 232, 179, 252);
		// palha
		g2.setColor(Color.RED);
		g2.drawLine(242, 107, 142, 364);
		g2.drawLine(245, 111, 146, 364);
		g2.drawLine(294, 85, 242, 107);
		g2.drawLine(296, 89, 245, 110);
		g2.drawLine(294, 85, 296, 89);
		g2.drawLine(294, 85, 296, 89);
		g2.drawLine(142, 364, 146, 364);
		// açúcar
		g2.setColor(Color.BLACK);
		g2.drawOval(175, 368, 2, 2);
		g2.drawOval(178, 359, 2, 2);
		g2.drawOval(195, 366, 2, 2);
		g2.drawOval(218, 371, 2, 2);
		g2.drawOval(188, 361, 2, 2);
		g2.drawOval(199, 369, 2, 2);
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
