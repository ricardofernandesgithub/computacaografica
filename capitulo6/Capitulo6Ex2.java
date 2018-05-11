package capitulo6ex2;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
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
import javax.swing.JComboBox;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.vecmath.AxisAngle4f;

public class Capitulo6Ex2 extends Applet implements ActionListener {
	TransformGroup tg;
	Transform3D tr;
	BranchGroup root;
	SimpleUniverse su;
	Canvas3D cv;
	BranchGroup bg;

	ImageComponent2D image = null;
	Bounds bounds = null;
	Background background = null;
	Appearance ap;
	Appearance ap2;

	JComboBox<String> comboBoxNrMosaicos = new JComboBox<String>();
	JComboBox<String> comboBoxCor1 = new JComboBox<String>();
	JComboBox<String> comboBoxCor2 = new JComboBox<String>();

	int nrMosaicos = 9; // segundo uma direcção z ou y, o número de mosaicos total é nrMosaicos *
						// nrMosaicos;
	Color3f cor1 = new Color3f(0.07f, 0.59f, 0.54f);
	Color3f cor2 = new Color3f(Color.WHITE);

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo6Ex2(), 500, 500);
		frame.setTitle("Capítulo 6 Exercício 2 - Mosaicos");
	}

	public void init() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		cv = new Canvas3D(gc);
		setLayout(new BorderLayout());

		Panel p = new Panel();
		p.setLayout(new GridLayout(5, 3));
		add(p, BorderLayout.NORTH);
		p.add(new Panel());
		Button button = new Button("Polygon Modes: ");
		p.add(button);
		button = new Button("Line");
		p.add(button);
		button.addActionListener(this);
		button = new Button("Polygon");
		p.add(button);
		button.addActionListener(this);

		p.add(new Panel());
		button = new Button("Color Modes: ");
		p.add(button);
		button = new Button("Gouraud");
		p.add(button);
		button.addActionListener(this);
		button = new Button("Lighting");
		p.add(button);
		button.addActionListener(this);

		p.add(new Panel());

		button = new Button(" ");
		p.add(button);
		button = new Button("Number of Tiles:");
		p.add(button);
		comboBoxNrMosaicos.addItem("9");
		comboBoxNrMosaicos.addItem("25");
		comboBoxNrMosaicos.addItem("49");
		comboBoxNrMosaicos.addItem("81");
		comboBoxNrMosaicos.addItem("225");
		comboBoxNrMosaicos.addItem("625");
		comboBoxNrMosaicos.addItem("961");
		comboBoxNrMosaicos.addItem("2401");
		comboBoxNrMosaicos.addItem("4489");
		p.add(comboBoxNrMosaicos);

		p.add(new Panel());
		button = new Button("Choose Colors: ");
		p.add(button);
		comboBoxCor1.addItem("BLACK");
		comboBoxCor1.addItem("BLUE");
		comboBoxCor1.addItem("CYAN");
		comboBoxCor1.addItem("LIGHT_GRAY");
		comboBoxCor1.addItem("GRAY");
		comboBoxCor1.addItem("GREEN");
		comboBoxCor1.addItem("YELLOW");
		comboBoxCor1.addItem("MAGENTA");
		comboBoxCor1.addItem("ORANGE");
		comboBoxCor1.addItem("PINK");
		comboBoxCor1.addItem("RED");
		comboBoxCor1.addItem("WHITE");
		comboBoxCor1.setSelectedIndex(2);
		p.add(comboBoxCor1);
		comboBoxCor1.addActionListener(this);
		comboBoxCor2.addItem("BLACK");
		comboBoxCor2.addItem("BLUE");
		comboBoxCor2.addItem("CYAN");
		comboBoxCor2.addItem("LIGHT_GRAY");
		comboBoxCor2.addItem("GRAY");
		comboBoxCor2.addItem("GREEN");
		comboBoxCor2.addItem("YELLOW");
		comboBoxCor2.addItem("MAGENTA");
		comboBoxCor2.addItem("ORANGE");
		comboBoxCor2.addItem("PINK");
		comboBoxCor2.addItem("RED");
		comboBoxCor2.addItem("WHITE");
		comboBoxCor2.setSelectedIndex(11);
		p.add(comboBoxCor2);
		comboBoxCor2.addActionListener(this);

		p.add(new Panel());
		Button gfButton = new Button("Generate Floor");
		p.add(gfButton);
		gfButton.addActionListener(this);

		add(cv, "Center");
		bg = createSceneGraph();
		bg.compile();
		su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.addBranchGraph(bg);
	}

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();

		bounds = new BoundingSphere();

		background = new Background(new Color3f(1.0f, 0.84f, 0.77f));
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
		tr.set(new Vector3f(0, 0, 0), 0.35f);

		// inclinação do octaedro segundo o vector (1, 0, 0)
		tr.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(20)));

		TransformGroup tgShape = new TransformGroup(tr);
		tgShape.addChild(shape);
		BoundingSphere bounds = new BoundingSphere();
		TransformGroup spin = new TransformGroup();
		spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha alpha = new Alpha(-1, 5000);
		RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
		rotator.setSchedulingBounds(bounds);
		spin.addChild(rotator);
		spin.addChild(tgShape);
		root.addChild(spin);

		background.setCapability(Background.ALLOW_COLOR_WRITE);
		background.setApplicationBounds(bounds);
		root.addChild(background);

		// Iluminação
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

		// Instanciação dos mosaicos
		Shape3D shapeMosaicos = new Mosaicos((int) Math.sqrt(nrMosaicos), (int) Math.sqrt(nrMosaicos), cor1, cor2);
		Transform3D trMosaicos = new Transform3D();
		trMosaicos = new Transform3D();
		trMosaicos.set(new Vector3f(0.0f, -0.4f, 0.0f));
		trMosaicos.setScale(0.5);
		TransformGroup tgMosaicos = new TransformGroup(trMosaicos);
		tgMosaicos = new TransformGroup(trMosaicos);
		tgMosaicos.addChild(shapeMosaicos);
		root.addChild(tgMosaicos);

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
		} else if ("Generate Floor".equals(cmd)) {
			nrMosaicos = Integer.parseInt(comboBoxNrMosaicos.getSelectedItem().toString());
			// System.out.println(nrMosaicos);
			comboBoxCor1.getSelectedItem();
			String corSeleccionada1 = comboBoxCor1.getSelectedItem().toString();
			String corSeleccionada2 = comboBoxCor2.getSelectedItem().toString();
			switch (corSeleccionada1) {
			case "BLACK":
				cor1 = new Color3f(Color.BLACK);
				break;
			case "BLUE":
				cor1 = new Color3f(Color.BLUE);
				break;
			case "CYAN":
				cor1 = new Color3f(Color.CYAN);
				break;
			case "LIGHT_GRAY":
				cor1 = new Color3f(Color.LIGHT_GRAY);
				break;
			case "GRAY":
				cor1 = new Color3f(Color.GRAY);
				break;
			case "GREEN":
				cor1 = new Color3f(Color.GREEN);
				break;
			case "YELLOW":
				cor1 = new Color3f(Color.YELLOW);
				break;
			case "MAGENTA":
				cor1 = new Color3f(Color.MAGENTA);
				break;
			case "ORANGE":
				cor1 = new Color3f(Color.ORANGE);
				break;
			case "PINK":
				cor1 = new Color3f(Color.PINK);
				break;
			case "RED":
				cor1 = new Color3f(Color.RED);
				break;
			case "WHITE":
				cor1 = new Color3f(Color.WHITE);
				break;
			}
			switch (corSeleccionada2) {
			case "BLACK":
				cor2 = new Color3f(Color.BLACK);
				break;
			case "BLUE":
				cor2 = new Color3f(Color.BLUE);
				break;
			case "CYAN":
				cor2 = new Color3f(Color.CYAN);
				break;
			case "LIGHT_GRAY":
				cor2 = new Color3f(Color.LIGHT_GRAY);
				break;
			case "GRAY":
				cor2 = new Color3f(Color.GRAY);
				break;
			case "GREEN":
				cor2 = new Color3f(Color.GREEN);
				break;
			case "YELLOW":
				cor2 = new Color3f(Color.YELLOW);
				break;
			case "MAGENTA":
				cor2 = new Color3f(Color.MAGENTA);
				break;
			case "ORANGE":
				cor2 = new Color3f(Color.ORANGE);
				break;
			case "PINK":
				cor2 = new Color3f(Color.PINK);
				break;
			case "RED":
				cor2 = new Color3f(Color.RED);
				break;
			case "WHITE":
				cor2 = new Color3f(Color.WHITE);
				break;
			}

			su.cleanup();
			bg = createSceneGraph();
			su = new SimpleUniverse(cv);
			su.getViewingPlatform().setNominalViewingTransform();
			su.addBranchGraph(bg);
		}
	}
}
