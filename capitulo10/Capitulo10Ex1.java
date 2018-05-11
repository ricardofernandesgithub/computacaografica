package capitulo10ex1;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.media.j3d.PointLight;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

public class Capitulo10Ex1 extends Applet implements KeyListener {
	BoundingSphere bounds = new BoundingSphere(new Point3d(0f, 0f, 0f), 4f);
	Background background = null;
	Transform3D locator = null;
	SimpleUniverse su = null;

	Appearance ap = new Appearance();

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo10Ex1(), 500, 500);
		frame.setTitle("Capítulo 10 Exercício 1 - Robô Bombeiro");
		frame.setFocusable(true);
		frame.setFocusableWindowState(true);
	}

	public void init() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		cv.setFocusable(true);
		cv.addKeyListener(this);

		TextArea ta = new TextArea("", 5, 30, TextArea.SCROLLBARS_NONE);
		ta.setText(" Mover robô: Cima, Baixo\n");
		ta.append(" Rodar robô: Esquerda, Direita\n");
		ta.append(" Mover cena: Botão Direito\n");
		ta.append(" Rodar cena: Botão Esquerdo\n");
		ta.append(" Zoom: Botão Scroll\n");
		ta.append(" Reset: Barra Espaços");
		ta.setEditable(false);
		add(ta, BorderLayout.SOUTH);

		BranchGroup bg = createSceneGraph();
		bg.compile();
		su = new SimpleUniverse(cv);

		locator = new Transform3D();
		locator.lookAt(new Point3d(0d, 1d, 7d), new Point3d(0d, 0d, 0d), new Vector3d(0d, 1d, 0d));
		locator.invert();
		su.getViewingPlatform().getViewPlatformTransform().setTransform(locator);

		OrbitBehavior orbit = new OrbitBehavior(cv);
		orbit.setSchedulingBounds(new BoundingSphere());
		su.getViewingPlatform().setViewPlatformBehavior(orbit);

		su.addBranchGraph(bg);
	}

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();

		// Bounds
		Background bk = new Background(0f, 0f, 0f);
		bk = carregarImagemBackground("texturas/bk.png");
		bk.setApplicationBounds(bounds);
		root.addChild(bk);

		// Iluminação
		AmbientLight al = new AmbientLight(new Color3f(Color.WHITE));
		al.setInfluencingBounds(bounds);
		root.addChild(al);
		al.setCapability(AmbientLight.ALLOW_STATE_WRITE);
		al.setCapability(AmbientLight.ALLOW_STATE_READ);

		PointLight pl1 = new PointLight(new Color3f(Color.WHITE), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
		pl1.setInfluencingBounds(bounds);
		root.addChild(pl1);
		pl1.setCapability(PointLight.ALLOW_STATE_WRITE);
		pl1.setCapability(PointLight.ALLOW_STATE_READ);

		// instância da arena
		Group arena = new Arena();
		root.addChild(arena);

		TransformGroup tgMove = new TransformGroup();
		tgMove.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgMove.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(tgMove);

		// instância do robot
		Group robot = new Robot(this);
		TransformGroup tgRobot = new TransformGroup();

		tgRobot.addChild(robot);
		tgMove.addChild(tgRobot);

		// behavior personalizado
		KeyControl kc = new KeyControl(tgMove, bounds, robot);
		root.addChild(kc);

		return root;
	}

	private Background carregarImagemBackground(String filePath) {
		URL url = getClass().getClassLoader().getResource(filePath);

		TextureLoader loader = new TextureLoader(url, this);
		Texture texture = loader.getTexture();

		Appearance ap = new Appearance();
		ap.setTexture(texture);
		Sphere sphere = new Sphere(1.0f,
				Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD | Sphere.GENERATE_TEXTURE_COORDS, 120, ap);
		BranchGroup bg = new BranchGroup();
		bg.addChild(sphere);

		Background background = new Background(bg);

		return background;
	}

	public void keyPressed(KeyEvent e) {
		// default view
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			locator = new Transform3D();
			locator.lookAt(new Point3d(0d, 1d, 7d), new Point3d(0d, 0d, 0d), new Vector3d(0d, 1d, 0d));
			locator.invert();
			su.getViewingPlatform().getViewPlatformTransform().setTransform(locator);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
