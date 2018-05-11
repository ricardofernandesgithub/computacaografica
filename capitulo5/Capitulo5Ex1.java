package capitulo5ex1;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Capitulo5Ex1 extends Applet {

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo5Ex1(), 400, 400);
		frame.setTitle("Capítulo 5 Exercício 1 - Cubo Colorido");
	}

	Background background = null;
	Bounds bounds = null;
	ImageComponent2D image = null;

	public void init() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		cv.addMouseListener(new MouseAdapter() {
			int contador = 1;
			int r = 255, g = 255, b = 255;

			// change background color on mouse click
			public void mouseClicked(MouseEvent ev) {
				if (background.getImage() == null)
					background.setImage(image);
				else {
					background.setImage(null);
					if (contador == 1) {
						r = 255;
						g = 0;
						b = 0;
					} else if (contador == 2) {
						r = 0;
						g = 255;
						b = 0;
					} else if (contador == 3) {
						r = 0;
						g = 0;
						b = 255;
						contador = 0;
					}
					contador++;
					background.setColor(r, g, b);
				}
				//System.out.println(contador);
			}
		});
		BranchGroup bg = createSceneGraph();
		bg.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.addBranchGraph(bg);
	}

	private BranchGroup createSceneGraph() {

		BranchGroup root = new BranchGroup();
		root.addChild(createColorCube());

		bounds = new BoundingSphere();

		background = new Background(new Color3f(Color.BLUE));
		background.setApplicationBounds(bounds);

		// carregar imagem de fundo
		URL url = getClass().getClassLoader().getResource("images/grid.jpg");
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(url);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		image = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, bi);
		background.setImage(image);

		// set capability bit to allow color and image change
		background.setCapability(Background.ALLOW_COLOR_WRITE);
		background.setCapability(Background.ALLOW_IMAGE_READ);
		background.setCapability(Background.ALLOW_IMAGE_WRITE);

		root.addChild(background);
		return root;
	}

	private BranchGroup createColorCube() {
		BranchGroup root = new BranchGroup();

		ColorCube cube = new ColorCube(0.25); // o parâmetro diz respeito à escala com a qual o cubo será renderizado

		TransformGroup tg = new TransformGroup();
		tg.addChild(cube);

		// alínea c)
		Transform3D tr = new Transform3D();
		tr.setRotation(new AxisAngle4d(1, 1, 1, Math.toRadians(45)));
		tg.setTransform(tr);
		root.addChild(tg);

		return root;
	}
}
