package capitulo3ex1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Capitulo3Ex1 extends JApplet {

    public static void main(String s[]) {
        JFrame frame = new JFrame();
        frame.setTitle("Capítulo 3 Exercício 1 - Gráfico de Barras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JApplet applet = new Capitulo3Ex1();
        applet.init();
        frame.getContentPane().add(applet);
        frame.pack();
        frame.setVisible(true);
    }

    public void init() {
        JPanel panel = new MyPanel();
        getContentPane().add(panel);
    }
}

class MyPanel extends JPanel {

    String[] rotulos = {"1º Trim.", "2º Trim.", "3º Trim.", "4º Trim."};
    float[] serie1 = {89.3f, 134.5f, 76.2f, 93.9f}; // download
    float[] serie2 = {50.3f, 34.5f, 46.2f, 23.5f}; // upload

    public MyPanel() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GraficoBarras(g2, 50, 100, 400, 200, rotulos, "Tranferência Anual Dados", serie1, serie2);

    }

    public void GraficoBarras(Graphics2D g2, int x, int y, int largura, int altura, String rotulo[], String titulo,
            float S1[], float S2[]) {

        Font fonteEixoY = new Font("Arial", Font.PLAIN, 12);

        int margemH = 100;
        int margemV = 60;
        int xG = x + margemH / 2;
        int yG = y + margemV / 2;
        int lG = largura - margemH;
        int aG = altura - margemV;

        g2.setColor(Color.BLACK);
        //g2.drawRect(x, y, largura, altura);

        // Painel do gráfico
        //g2.setColor(Color.LIGHT_GRAY);
        g2.setColor(Color.BLACK);
        //g2.drawRect(xG, yG, lG, aG);

        // Carregamento do padrão de fundo
        BufferedImage img = null;
        URL url = getClass().getClassLoader().getResource("images/textura.png");
        try {
            img = ImageIO.read(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        TexturePaint textura = new TexturePaint(img, new Rectangle2D.Float(0, 0, img.getWidth() / 2, img.getHeight() / 2));
        g2.setPaint(textura);
        g2.fillRect(xG, yG, lG, aG);

        // Eixo y
        g2.setFont(fonteEixoY);
        FontRenderContext frc = g2.getFontRenderContext();

        int d = 5; // número de divisões da escala
        int incLinha = aG / d;
        int max = (int) Math.ceil(getMax(S1));

        while (max % d != 0) {
            max++;
        }
        int incEscala = max / d;

        g2.setColor(Color.BLACK);
        g2.drawLine(xG, yG, xG, yG + aG);

        for (int i = 0; i <= d; i++) {
            int yi = yG + aG - incLinha * i;
            g2.drawLine(xG, yi, xG - 3, yi);
            String ei = Integer.toString(i * incEscala);

            // Desenho do traço interrompido
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 1));
            g2.setColor(Color.GRAY);
            g2.drawLine(xG, yi, xG + lG, yi);

            // g2.drawString(ei, xG - 10, yi);
            int lTexto = (int) fonteEixoY.getStringBounds(ei, frc).getWidth();
            int aTexto = (int) fonteEixoY.getStringBounds(ei, frc).getHeight();
            g2.setColor(Color.BLACK);
            g2.drawString(ei, xG - 5 - lTexto, yi + aTexto / 2);
        }
        g2.setStroke(new BasicStroke()); // reset stroke

        // Legendas
        g2.drawString("Download", 170, 340);
        g2.drawString("Upload", 360, 340);
        GradientPaint paint = new GradientPaint(110, 330, Color.GREEN, 150, 330, Color.BLUE);
        g2.setPaint(paint);
        g2.fillRect(110, 330, 40, 10);
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 1));
        g2.setColor(Color.RED);
        g2.drawLine(300, 335, 335, 335);
        g2.fillRect(314, 332, 6, 6);
        g2.setStroke(new BasicStroke());

        // Eixo x
        Font fonteEixoX = new Font("Arial", Font.ITALIC, 12);
        g2.setFont(fonteEixoX);
        frc = g2.getFontRenderContext();

        g2.setColor(Color.BLACK);
        g2.drawLine(xG, yG + aG, xG + lG, yG + aG);
        incLinha = lG / S1.length;

        for (int i = 0; i < S1.length; i++) {
            int xi = xG + incLinha * i;
            g2.drawLine(xi, yG + aG, xi, yG + aG + 5);
            int lTexto = (int) fonteEixoY.getStringBounds(rotulos[i], frc).getWidth();
            int aTexto = (int) fonteEixoY.getStringBounds(rotulos[i], frc).getHeight();
            g2.drawString(rotulos[i], xi + lTexto / 2 - 5, yG + aG + aTexto + 5);
        }

        // Desenho das barras e linhas
        int lBarra = 30; // lBarra = incLinha * 80%
        float fEscala = aG / (float) max;
        GeneralPath path = new GeneralPath();
        for (int i = 0; i < S1.length; i++) {

            int aBarra = (int) (S1[i] * fEscala);
            int aPonto = (int) (S2[i] * fEscala);
            int yi = yG + aG - aBarra;
            int yPonto = yG + aG - aPonto;
            int xi = xG + i * incLinha + incLinha / 2 - lBarra / 2;

            paint = new GradientPaint(250, 100, Color.GREEN, 250, 400, Color.BLUE);
            g2.setPaint(paint);

            //g2.setColor(Color.GREEN);
            g2.fillRect(xi, yi, lBarra, aBarra);

            //linhas do gráfico
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2, new float[]{5}, 1));
            if (i == 0) {
                path.moveTo(xi + 15, yPonto);
            } else {
                path.lineTo(xi + 15, yPonto);
            }
            g2.draw(path);
            g2.fillRect(xi + 12, yPonto - 3, 6, 6);
        }

        // Legendas
        Font fonteTitulo = new Font("Consolas", Font.BOLD, 20);
        g2.setFont(fonteTitulo);
        g2.setColor(Color.BLUE);
        //int lTexto = (int) fonteTitulo.getStringBounds(titulo, frc).getWidth();
        g2.drawString(titulo, xG + 20, yG - 15);
        fonteTitulo = new Font("Consolas", Font.BOLD, 14);
        g2.setFont(fonteTitulo);
        g2.drawString("Trimestres", margemH + lG / 2 - 30, 310);
        g2.rotate(-Math.PI / 2, margemH - 30, margemV + 150);
        g2.drawString("GB", margemH - 30, margemV + 150);
    }

    public float getMax(float v[]) {
        float max = 0f;
        for (int i = 0; i < v.length; i++) {
            if (v[i] > max) {
                max = v[i];
            }
        }
        return max;
    }

}
