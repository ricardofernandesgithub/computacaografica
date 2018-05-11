package capitulo8ex1;

import java.applet.Applet;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
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
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Capitulo8Ex1 extends Applet implements MouseListener {

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo8Ex1(), 600, 420);
		frame.setTitle("Capítulo 8 Exercício 1 - Views & Picking");
	}

	PickCanvas pc;
	boolean luzCenaLigada = true;
	PointLight pl1 = new PointLight();
	AmbientLight al = new AmbientLight();

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
		BranchGroup bgView = createView(cv, new Point3d(-1.5d, 0.5d, 1.5d), new Point3d(0d, 0d, 0d),
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
		Background bk = new Background(1f, 1f, 1f); // r = g = b => tons cinzentos
		bk.setApplicationBounds(bounds);
		root.addChild(bk);

		// Aparência
		Appearance ap = new Appearance();
		ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		ap.setPolygonAttributes(new PolygonAttributes(2, 0, 0.0f));
		ap.setMaterial(new Material());

		// Objectos
		Transform3D tr = new Transform3D();
		TransformGroup tg = new TransformGroup(tr);
		tr.set(new Vector3f(-0.7f, -1.7f, 0.0f), 0.35f);
		tg = new TransformGroup(tr);
		tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.addChild(new Mesa(ap));
		tg.addChild(new ElementosCimaMesa(ap));
		tg.setName("mesa");
		root.addChild(tg);

		Transform3D tr1 = new Transform3D();
		TransformGroup tg1 = new TransformGroup(tr1);
		tr1.set(new Vector3f(-0.7f, -1.7f, 0.0f), 0.35f);
		tg1 = new TransformGroup(tr1);
		tg1.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg1.addChild(new Candeeiro(ap));
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
		tg1.addChild(new Candeeiro(ap));
		tg1.setName("candeeiro");
		root.addChild(tg1);

		Transform3D tr2 = new Transform3D();
		TransformGroup tg2 = new TransformGroup(tr2);
		tr2.set(new Vector3f(-0.7f, -1.7f, 0.0f), 0.35f);
		tg2 = new TransformGroup(tr2);
		tg2.addChild(new Cadeira(ap));
		root.addChild(tg2);
		tr2.set(new Vector3f(-0.7f, -1.7f, -2.2f), 0.35f);
		tr2.setRotation(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(180)));
		tg2 = new TransformGroup(tr2);
		tg2.addChild(new Cadeira(ap));
		root.addChild(tg2);

		// Iluminação
		al = new AmbientLight(true, new Color3f(1f, 0f, 0f));
		al.setInfluencingBounds(bounds);
		root.addChild(al);
		al.setCapability(AmbientLight.ALLOW_STATE_WRITE);
		al.setCapability(AmbientLight.ALLOW_STATE_READ);

		pl1 = new PointLight(true, new Color3f(0.79f, 0.64f, 0.15f), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
		pl1.setInfluencingBounds(bounds);
		root.addChild(pl1);
		pl1.setCapability(PointLight.ALLOW_STATE_WRITE);
		pl1.setCapability(PointLight.ALLOW_STATE_READ);

		return root;
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
