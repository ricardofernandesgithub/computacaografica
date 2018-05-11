package capitulo9ex1;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointLight;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Capitulo9Ex1 extends Applet implements MouseListener {

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo9Ex1(), 600, 420);
		frame.setTitle("Capítulo 9 Exercício 1 - Materiais, Texturas");
	}

	PickCanvas pc;
	Appearance ap, ap1, ap2, ap3;
	boolean luzCenaLigada = true;
	PointLight pl1 = new PointLight();
	AmbientLight al = new AmbientLight();
	SpotLight sl1 = new SpotLight();

	public void init() {
		// Layout para gerar 2 views
		setLayout(new GridLayout(1, 2));
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();

		// view esquerda: standard
		Canvas3D cv = new Canvas3D(gc);
		add(cv);
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();

		cv.addMouseListener(this);

		// view direita
		cv = new Canvas3D(gc);
		add(cv);
		BranchGroup bgView = createView(cv, new Point3d(-1.5d, 0.5d, 1.5d), new Point3d(-0.5d, 0d, 0d),
				new Vector3d(0d, 1d, 0d));
		su.addBranchGraph(bgView);

		cv.addMouseListener(this);

		BranchGroup bg = createSceneGraph();
		bg.compile();
		pc = new PickCanvas(cv, bg);
		pc.setMode(PickTool.GEOMETRY); // determina qual a ferramenta de picking a ser usada
		su.addBranchGraph(bg);
	}

	private BranchGroup createView(Canvas3D cv, Point3d eye, Point3d center, Vector3d vup) {
		View view = new View();
		view.setProjectionPolicy(View.PARALLEL_PROJECTION);
		ViewPlatform vp = new ViewPlatform();
		view.addCanvas3D(cv);
		view.attachViewPlatform(vp);
		view.setPhysicalBody(new PhysicalBody());
		view.setPhysicalEnvironment(new PhysicalEnvironment());
		Transform3D trans = new Transform3D();
		trans.lookAt(eye, center, vup);
		trans.invert();
		TransformGroup tg = new TransformGroup(trans);
		tg.addChild(vp);
		BranchGroup bgView = new BranchGroup();
		bgView.addChild(tg);
		return bgView;
	}

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();

		// Bounds
		BoundingSphere bounds = new BoundingSphere();
		Background bk = new Background(1f, 1f, 1f);
		bk.setApplicationBounds(bounds);
		root.addChild(bk);

		// Iluminação
		sl1 = new SpotLight(new Color3f(Color.WHITE), new Point3f(0.7f, 1.0f, -1.75f), new Point3f(1f, 0f, 0f),
				new Vector3f(-0.7f, -0.7f, 0.7f), (float) (Math.PI / 12.0), 0f);
		sl1.setCapability(PointLight.ALLOW_STATE_WRITE | PointLight.ALLOW_STATE_WRITE);
		sl1.setInfluencingBounds(bounds);
		root.addChild(sl1);

		al = new AmbientLight(true, new Color3f(Color.WHITE));
		al.setInfluencingBounds(bounds);
		root.addChild(al);
		al.setCapability(AmbientLight.ALLOW_STATE_WRITE);
		al.setCapability(AmbientLight.ALLOW_STATE_READ);

		pl1 = new PointLight(true, new Color3f(Color.WHITE), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
		pl1.setInfluencingBounds(bounds);
		root.addChild(pl1);
		pl1.setCapability(PointLight.ALLOW_STATE_WRITE);
		pl1.setCapability(PointLight.ALLOW_STATE_READ);

		// Materiais
		Material garrafa = new Material(); // verde esmeralda
		garrafa.setAmbientColor(0.0215f, 0.1745f, 0.0215f);
		garrafa.setDiffuseColor(0.07568f, 0.61424f, 0.07568f);
		garrafa.setSpecularColor(0.633f, 0.727811f, 0.633f);
		garrafa.setShininess(76.8f);

		Material abatJourMat = new Material(); // cobre
		abatJourMat.setAmbientColor(0.2295f, 0.08825f, 0.0275f);
		abatJourMat.setDiffuseColor(0.5508f, 0.2118f, 0.066f);
		abatJourMat.setSpecularColor(0.580594f, 0.223257f, 0.0695701f);
		abatJourMat.setShininess(51.2f);

		Material lampada = new Material(); // borracha azul-ciano
		lampada.setAmbientColor(0.0f, 0.05f, 0.05f);
		lampada.setDiffuseColor(0.4f, 0.5f, 0.5f);
		lampada.setSpecularColor(0.04f, 0.7f, 0.7f);
		lampada.setShininess(10.0f);

		// Objectos
		Transform3D tr = new Transform3D();
		TransformGroup tg = new TransformGroup(tr);
		tr.set(new Vector3f(-0.7f, -1.7f, 0.0f), 0.35f);
		tg = new TransformGroup(tr);
		tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// Aparência
		ap = new Appearance();
		ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		ap = createTextureAppearance("textures/woodTable.jpg");
		tg.addChild(new Mesa(ap));
		ap2 = new Appearance();
		ap2.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		ap2.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		ap2.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		tg.addChild(new ElementosCimaMesa(ap2, garrafa));
		tg.setName("mesa");
		root.addChild(tg);

		Transform3D tr1 = new Transform3D();
		TransformGroup tg1 = new TransformGroup(tr1);
		tr1.set(new Vector3f(-0.7f, -1.7f, 0.0f), 0.35f);
		tg1 = new TransformGroup(tr1);
		tg1.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		ap1 = new Appearance();
		ap1.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		ap1.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		ap1.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		tg1.addChild(new Candeeiro(ap1, abatJourMat, lampada));
		tg1.setName("candeeiro");
		root.addChild(tg1);
		tg1.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg1.addChild(new ElementosCandeeiro(ap));
		tg1.setName("candeeiro");
		tr1.set(new Vector3f(-0.7f, -1.7f, -2.2f), 0.35f);
		tr1.setRotation(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(180)));
		tg1 = new TransformGroup(tr1);
		tg1.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg1.addChild(new Candeeiro(ap1, abatJourMat, lampada));
		tg1.setName("candeeiro");
		root.addChild(tg1);

		Transform3D tr2 = new Transform3D();
		TransformGroup tg2 = new TransformGroup(tr2);
		tr2.set(new Vector3f(-0.7f, -1.7f, 0.0f), 0.35f);
		tg2 = new TransformGroup(tr2);
		ap3 = new Appearance();
		ap3.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		ap3.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		ap3.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		ap3.setMaterial(new Material());
		ap3 = createTextureAppearance("textures/wood2.jpg");
		tg2.addChild(new Cadeira(ap3));
		root.addChild(tg2);
		tr2.set(new Vector3f(-0.7f, -1.7f, -2.2f), 0.35f);
		tr2.setRotation(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(180)));
		tg2 = new TransformGroup(tr2);
		tg2.addChild(new Cadeira(ap3));
		root.addChild(tg2);

		return root;
	}

	Appearance createTextureAppearance(String ficheiroTextura) {
		ap = new Appearance();
		URL filename = getClass().getClassLoader().getResource(ficheiroTextura);
		TextureLoader loader = new TextureLoader(filename, this);
		ImageComponent2D image = loader.getImage();
		if (image == null) {
			System.out.println("can't find texture file.");
		}
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		ap.setTexture(texture);
		return ap;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		pc.setShapeLocation(e);
		PickResult result = pc.pickClosest();
		if (result != null) {
			String nomeObjecto = "";
			try {
				nomeObjecto = result.getNode(PickResult.TRANSFORM_GROUP).getName();
			} catch (NullPointerException e1) {
			}
			if (nomeObjecto.equals("candeeiro")) {
				luzCenaLigada = (!luzCenaLigada);
				al.setEnable(luzCenaLigada);
				pl1.setEnable(luzCenaLigada);
				// System.out.println("candeeiro");
			} else if (nomeObjecto.equals("mesa")) {
				TransformGroup tg = (TransformGroup) result.getNode(PickResult.TRANSFORM_GROUP);
				Transform3D tr = new Transform3D();
				tg.getTransform(tr);
				Transform3D rot = new Transform3D();
				rot.rotY(Math.PI / 3);
				tr.mul(rot);
				tg.setTransform(tr);
				// System.out.println("mesa");
				// System.out.println(result.toString());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
