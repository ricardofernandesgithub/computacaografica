package capitulo6ex1;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.vecmath.AxisAngle4f;

public class Capitulo6Ex1 extends Applet implements ActionListener {
	ImageComponent2D image = null;
	Bounds bounds = null;
	Background background = null;
	Appearance ap;
	Appearance ap2;

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo6Ex1(), 400, 400);
		frame.setTitle("Capítulo 6 Exercício 1 - Octaedro");
	}

	public void init() {
		setLayout(new BorderLayout());
		Panel p = new Panel();
		p.setLayout(new GridLayout(2, 0, 1, 1));
		add(p, BorderLayout.NORTH);
		p.add(new Panel());
		p.add(new Label("Polygon Modes:"));
		Button button = new Button("Line");
		p.add(button);
		button.addActionListener(this);
		button = new Button("Polygon");
		p.add(button);
		button.addActionListener(this);
		p.add(new Panel());
		p.add(new Label("Color Modes:"));
		button = new Button("Gouraud");
		p.add(button);
		button.addActionListener(this);
		button = new Button("Lighting");
		p.add(button);
		button.addActionListener(this);

		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		add(cv, "Center");
		BranchGroup bg = createSceneGraph();
		bg.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.addBranchGraph(bg);
	}

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();

		bounds = new BoundingSphere();

		background = new Background(new Color3f(Color.WHITE));
		background.setApplicationBounds(bounds);

		ap = new Appearance();
		ap.setMaterial(new Material());
		RenderingAttributes ra = new RenderingAttributes();
		ra.setIgnoreVertexColors(true);
		ap.setRenderingAttributes(ra);
		ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		ap.setCapability(Appearance.ALLOW_MATERIAL_WRITE);

		ap2 = new Appearance();
		ap2.setMaterial(new Material());
		ap2.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		ap2.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
		ap2.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
		ap2.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
		ap2.setCapability(Appearance.ALLOW_MATERIAL_WRITE);

		Shape3D shape = new Octaedro();
		shape.setAppearance(ap);
		Transform3D tr = new Transform3D();
		tr.set(new Vector3f(0, 0, 0), 0.5f);

		// inclinação do octaedro segundo o vector (1, 0, 0)
		tr.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(20)));

		TransformGroup tgShape = new TransformGroup(tr);
		tgShape.addChild(shape);
		BoundingSphere bounds = new BoundingSphere();
		TransformGroup spin = new TransformGroup();
		spin.setCapability(spin.ALLOW_TRANSFORM_WRITE);
		Alpha alpha = new Alpha(-1, 7000);
		RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
		rotator.setSchedulingBounds(bounds);
		spin.addChild(rotator);
		spin.addChild(tgShape);
		root.addChild(spin);

		background.setCapability(Background.ALLOW_COLOR_WRITE);
		background.setApplicationBounds(bounds);
		root.addChild(background);

		AmbientLight ambLight = new AmbientLight(true, new Color3f(Color.GREEN));
		ambLight.setInfluencingBounds(bounds);
		root.addChild(ambLight);
		PointLight pointLight = new PointLight(new Color3f(Color.YELLOW), new Point3f(-15, -2, 0),
				new Point3f(1, 0, 0));
		pointLight.setInfluencingBounds(bounds);
		root.addChild(pointLight);
		PointLight pointLight2 = new PointLight(new Color3f(Color.GREEN), new Point3f(-7, -2, 2), new Point3f(1, 0, 0));
		pointLight2.setInfluencingBounds(bounds);
		root.addChild(pointLight2);

		return root;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String cmd = actionEvent.getActionCommand();
		if ("Line".equals(cmd)) {
			ap.setPolygonAttributes(new PolygonAttributes(1, 0, 0.0f));
			ap.setLineAttributes(new LineAttributes(3.0f, 0, false));
			ap2.setPolygonAttributes(
					new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0.0f));
			ap2.setLineAttributes(new LineAttributes(3.0f, 0, false));
		} else if ("Polygon".equals(cmd)) {
			ap.setPolygonAttributes(
					new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0.0f));
			ap2.setPolygonAttributes(new PolygonAttributes(2, 1, 0.0f));
		} else if ("Gouraud".equals(cmd)) {
			ColoringAttributes ca = new ColoringAttributes();
			ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
			ap.setColoringAttributes(ca);
			ap.setMaterial(null);
			RenderingAttributes ra = new RenderingAttributes();
			ra.setIgnoreVertexColors(false);
			ap.setRenderingAttributes(ra);
			ColoringAttributes ca2 = new ColoringAttributes(new Color3f(0.1f, 0.3f, 0.8f),
					ColoringAttributes.SHADE_GOURAUD);
			ap2.setColoringAttributes(ca2);
			ap2.setMaterial(null);
		} else if ("Lighting".equals(cmd)) {
			ap.setMaterial(new Material());
			RenderingAttributes ra = new RenderingAttributes();
			ra.setIgnoreVertexColors(true);
			ap.setRenderingAttributes(ra);
			ap2.setMaterial(new Material());
		}
	}
}
