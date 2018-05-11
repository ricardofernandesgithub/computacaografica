package capitulo11ex1;

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

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Capitulo11Ex1 extends Applet implements KeyListener {
	BoundingSphere bounds = new BoundingSphere(new Point3d(0f, 0f, 0f), 4f);
	Background background = null;
	Transform3D locator = null;
	SimpleUniverse su = null;

	Appearance ap = new Appearance();

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo11Ex1(), 500, 500);
		frame.setTitle("Capítulo 11 Exercício 1 - Robô Bombeiro Autónomo");
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

		TextArea ta = new TextArea("", 3, 30, TextArea.SCROLLBARS_NONE);
		ta.setText(" Mover cena: Botão Direito\n");
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

		// instância do robô
		Group robot = new Robot(this);

		TransformGroup tgRobot = new TransformGroup();
		tgRobot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgRobot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgMove.addChild(tgRobot);
		tgRobot.addChild(robot);

		// configuração Alpha
		int loopCount = -1;
		int mode = Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE;

		long triggerTime = 3000;
		long phaseDelayDuration = 0;

		long increasingAlphaDuration = 11000;
		long increasingAlphaRampDuration = 1000;
		long alphaAtOneDuration = 10;

		long decreasingAlphaDuration = 11000;
		long decreasingAlphaRampDuration = 1000;
		long alphaAtZeroDuration = 10;

		Alpha alpha = new Alpha(loopCount, mode, triggerTime, phaseDelayDuration, increasingAlphaDuration,
				increasingAlphaRampDuration, alphaAtOneDuration, decreasingAlphaDuration, decreasingAlphaRampDuration,
				alphaAtZeroDuration);

		float[] knots = { 0f, 0.125f, 0.25f, 0.35f, 0.5f, 0.7f, 0.8f, 0.9f, 0.95f, 1f };
		Quat4f[] quats = new Quat4f[10];
		Point3f[] positions = new Point3f[10];

		// rotações
		quats[0] = new Quat4f();
		quats[1] = new Quat4f();
		quats[2] = new Quat4f(0f, 0f, 1f, 1f);
		quats[3] = new Quat4f(0f, 0f, 1f, 0f);
		quats[4] = new Quat4f(0f, 0f, 1f, -1f);
		quats[5] = new Quat4f(0f, 0f, 1f, 0f);
		quats[6] = new Quat4f();
		quats[7] = new Quat4f(0f, 0f, 1f, -1f);
		quats[8] = new Quat4f();
		quats[9] = new Quat4f();

		// posições
		positions[0] = new Point3f(0f, -0.25f, 0f);
		positions[1] = new Point3f(0f, 0.7f, 0f);
		positions[2] = new Point3f(-0.8f, 0.7f, 0f);
		positions[3] = new Point3f(-0.8f, 0.5f, 0f);
		positions[4] = new Point3f(-0.65f, 0.5f, 0f);
		positions[5] = new Point3f(-0.8f, 0.5f, 0f);
		positions[6] = new Point3f(-0.8f, -0.2f, 0f);
		positions[7] = new Point3f(0.8f, -0.2f, 0f);
		positions[8] = new Point3f(0.5f, -0.2f, 0f);
		positions[9] = new Point3f(0.5f, -0.05f, 0f);

		RotPosPathInterpolator rotPosPath = new RotPosPathInterpolator(alpha, tgMove, new Transform3D(), knots, quats,
				positions);
		rotPosPath.setSchedulingBounds(bounds);
		tgMove.addChild(rotPosPath);

		// Transform Group para a parte superior do robô
		TransformGroup moverCr1 = new TransformGroup();
		moverCr1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		moverCr1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D trCr1 = new Transform3D();
		trCr1.setTranslation(new Vector3f(0f, 0f, 0f));
		TransformGroup tgCr1 = new TransformGroup(trCr1);
		tgMove.addChild(tgCr1);
		tgCr1.addChild(moverCr1);
		moverCr1.addChild(Robot.tgCr1);

		int loopCount2 = -1;
		int mode2 = Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE;

		long triggerTime2 = 3000;
		long phaseDelayDuration2 = 0;

		long increasingAlphaDuration2 = 100000;
		long increasingAlphaRampDuration2 = 0;
		long alphaAtOneDuration2 = 3000;

		long decreasingAlphaDuration2 = 100;
		long decreasingAlphaRampDuration2 = 0;
		long alphaAtZeroDuration2 = 0;

		Alpha alpha4 = new Alpha(loopCount2, mode2, triggerTime2, phaseDelayDuration2, increasingAlphaDuration2,
				increasingAlphaRampDuration2, alphaAtOneDuration2, decreasingAlphaDuration2,
				decreasingAlphaRampDuration2, alphaAtZeroDuration2);
		Transform3D axisOfTransform4 = new Transform3D();
		axisOfTransform4.setRotation(new AxisAngle4d(1d, 0d, 0d, (float) Math.toRadians(90)));

		// Transform Group para a parte inferior do robô
		Transform3D trCr2 = new Transform3D();
		trCr2.setTranslation(new Vector3f(0f, 0f, 0f));
		TransformGroup tgCr2 = new TransformGroup(trCr2);
		tgMove.addChild(tgCr2);
		tgMove.addChild(Robot.tgCr2);

		RotationInterpolator rotator4 = new RotationInterpolator(alpha4, tgMove, axisOfTransform4, 0f, -180f);
		rotator4.setSchedulingBounds(bounds);
		tgMove.addChild(rotator4);

		Alpha alpha2 = new Alpha(-1, 25000);
		Transform3D eixoTransformacao = new Transform3D();

		// Transform Group para a roda direita
		TransformGroup spinRodaD = new TransformGroup();
		spinRodaD.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		spinRodaD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D trRodaD = new Transform3D();
		trRodaD.setTranslation(new Vector3f(0.1675f, 0.02f, -0.11f));
		trRodaD.setRotation(new AxisAngle4d(0d, 0d, 1d, (float) Math.toRadians(90)));
		TransformGroup tgRodaD = new TransformGroup(trRodaD);
		tgMove.addChild(tgRodaD);
		tgRodaD.addChild(spinRodaD);
		spinRodaD.addChild(Robot.tgRodaD);

		RotationInterpolator rotator2 = new RotationInterpolator(alpha2, spinRodaD, eixoTransformacao, 0f, 360f);
		rotator2.setSchedulingBounds(bounds);
		spinRodaD.addChild(rotator2);

		Alpha alpha3 = new Alpha(-1, 25000);
		Transform3D axisOfTransform3 = new Transform3D();

		// Transform Group para a roda esquerda
		TransformGroup spinRodaE = new TransformGroup();
		spinRodaE.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		spinRodaE.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D trRodaE = new Transform3D();
		trRodaE.setTranslation(new Vector3f(0.037f, 0.02f, -0.11f));
		trRodaE.setRotation(new AxisAngle4d(0d, 0d, 1d, (float) Math.toRadians(90)));
		TransformGroup tgRodaE = new TransformGroup(trRodaE);
		tgMove.addChild(tgRodaE);
		tgRodaE.addChild(spinRodaE);
		spinRodaE.addChild(Robot.tgRodaE);

		RotationInterpolator rotator3 = new RotationInterpolator(alpha3, spinRodaE, axisOfTransform3, 0f, 360f);
		rotator3.setSchedulingBounds(bounds);
		spinRodaE.addChild(rotator3);

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
