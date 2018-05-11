package capitulo4ex3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import capitulo4ex3.Arrow;

public class Capitulo4Ex3 extends JApplet {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Capítulo 4 Exercício 3 - Jogo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JApplet applet = new Capitulo4Ex3();
		applet.init();

		frame.getContentPane().add(applet);
		frame.pack();
	}

	public void init() {
		JPanel panel = new MyPanel2D();
		getContentPane().add(panel);
	}

}

class MyPanel2D extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	Shape player = null;
	Shape p1O1 = null; // parte 1 do obstáculo 1
	Shape p2O1 = null;// parte 2 do obstáculo 1
	int angObstaculo1 = 0;

	Arrow setaInicio = new Arrow(0, 370, 30, 30);
	Arrow setaFim = new Arrow(370, 0, 30, 30);

	Shape obstaculo2 = null;
	Shape obstaculo3 = null;
	Shape obstaculo4 = null;
	Shape obstaculo5 = null;

	Shape parede1 = null;
	Shape parede2 = null;
	Shape parede3 = null;
	Shape parede4 = null;

	int incremento2 = 0;
	int incremento3 = 0;

	int x = 400;
	int y = 400;

	// variáveis para transformações
	int tx = 15;
	int ty = 350;
	int vx, vy = 0;

	boolean flagDesce2 = true;
	boolean flagDesce3 = false;
	boolean flagFim = false;

	boolean cursorSobrePlayer = false;
	int cursorX;
	int cursorY;

	Font fim = new Font("Consolas", Font.BOLD, 38);

	AffineTransform at = new AffineTransform();

	public MyPanel2D() {
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);

		Thread thread = new Thread(this);
		thread.start();

		addKeyListener(this);
		setFocusable(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		player = new Ellipse2D.Float(-15, -15, 30, 30);
		p1O1 = new Rectangle2D.Float(-100, -10, 200, 20);
		p2O1 = new Rectangle2D.Float(-10, -100, 20, 200);
		obstaculo4 = new Rectangle2D.Float(50, 50, 30, 30);

		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

		// Seta de início e fim
		g2.setColor(Color.BLUE);
		g2.fill(setaInicio);
		g2.fill(setaFim);

		if (flagFim) {
			// Fim
			g2.setFont(fim);
			g2.setColor(Color.ORANGE);
			g2.drawString("Fim !", 170, 190);
		}

		// Paredes
		g2.setColor(Color.GREEN);
		parede1 = new Rectangle(0, 205, 360, 15);
		parede2 = new Rectangle(50, 355, 100, 15);
		parede3 = new Rectangle(150, 255, 15, 115);
		parede4 = new Rectangle(70, 120, 330, 15);
		g2.fill(parede1);
		g2.fill(parede2);
		g2.fill(parede3);
		g2.fill(parede4);

		// Obstáculo 1
		g2.setColor(Color.RED);
		at.setToTranslation(55, 290);
		at.rotate(Math.toRadians(angObstaculo1));
		at.scale(0.5, 0.5);
		p1O1 = at.createTransformedShape(p1O1);
		p2O1 = at.createTransformedShape(p2O1);
		g2.fill(p1O1);
		g2.fill(p2O1);

		// Obstáculo 2 e 3
		g2.setColor(Color.PINK);
		obstaculo2 = new Rectangle2D.Float(220, 220 + incremento2, 20, 45);
		obstaculo3 = new Rectangle2D.Float(330, 355 + incremento3, 20, 45);
		g2.fill(obstaculo2);
		g2.fill(obstaculo3);

		// Obstáculo 4 e 5
		g2.setColor(Color.MAGENTA);
		at.setToTranslation(40, 30);
		at.rotate(Math.toRadians(angObstaculo1 * 3));
		obstaculo4 = at.createTransformedShape(obstaculo4);
		g2.fill(obstaculo4);

		g2.setColor(Color.CYAN);
		at.setToTranslation(220, -2);
		at.rotate(Math.toRadians(angObstaculo1 * -3));
		obstaculo5 = at.createTransformedShape(obstaculo4);
		g2.fill(obstaculo5);

		// Player
		g2.setColor(Color.YELLOW);
		at.setToTranslation(tx, ty);
		player = at.createTransformedShape(player);
		g2.fill(player);

		tx = tx + vx;
		ty = ty + vy;
	}

	@Override
	public void run() {
		while (true) {
			// alteração do modelo
			angObstaculo1 += 1 % 360; // angulo entre 0 e 360 graus

			if (flagDesce2 && incremento2 >= 0 && incremento2 < 135) {
				incremento2 += 3;
			} else if (incremento2 == 135) {
				flagDesce2 = false;
				incremento2 = 130;
			} else if (!flagDesce2 && incremento2 >= 0 && incremento2 < 135) {
				incremento2 -= 5;
			} else {
				flagDesce2 = true;
				incremento2 = 0;
			}

			if (!flagDesce3 && incremento3 <= 0 && incremento3 > -135) {
				incremento3 -= 5;
			} else if (incremento3 == -135) {
				flagDesce3 = true;
				incremento3 = -130;
			} else if (flagDesce3 && incremento3 <= 0 && incremento3 > -135) {
				incremento3 += 5;
			} else {
				flagDesce3 = false;
				incremento3 = 0;
			}

			// Reset. Círculo volta à seta de início
			if (p1O1 != null && p2O1 != null) {
				if (p1O1.contains(tx, ty) || p2O1.contains(tx, ty) || parede1.contains(tx, ty)
						|| parede2.contains(tx, ty) || parede3.contains(tx, ty) || parede4.contains(tx, ty)
						|| obstaculo2.contains(tx, ty) || obstaculo3.contains(tx, ty) || obstaculo4.contains(tx, ty)
						|| obstaculo5.contains(tx, ty)) {
					tx = 15;
					ty = 350;
					cursorSobrePlayer = false;
				}
			}

			if (setaFim.contains(tx, ty)) {
				tx = 385;
				ty = 0;
				flagFim = true;
				vx = vy = 0;
				// x = 100;
				// y = 190;
			}

			// renderização do modelo
			repaint();

			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			vx = -3;
			vy = 0;
			break;
		case KeyEvent.VK_RIGHT:
			vx = 3;
			vy = 0;
			break;
		case KeyEvent.VK_DOWN:
			vx = 0;
			vy = 3;
			break;
		case KeyEvent.VK_UP:
			vx = 0;
			vy = -3;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		vx = 0;
		vy = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		// System.out.println(x + ", " + y);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (player.contains(e.getX(), e.getY())) {
			cursorX = e.getX();
			cursorY = e.getY();
			cursorSobrePlayer = true;
		} else {
			cursorSobrePlayer = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		vx = 0;
		vy = 0;
		cursorSobrePlayer = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (cursorSobrePlayer) {
			vx = (e.getX() - cursorX);
			vy = (e.getY() - cursorY);
			cursorX = e.getX();
			cursorY = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
