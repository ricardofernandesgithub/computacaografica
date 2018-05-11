package capitulo7ex1;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Capitulo7Ex1 extends Applet {

	public static void main(String[] args) {
		Frame frame = new MainFrame(new Capitulo7Ex1(), 480, 420);
		frame.setTitle("Capítulo 7 Exercício 1 - Mobiliário");
	}

	public void init() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		BranchGroup bg = createSceneGraph();
		bg.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.addBranchGraph(bg);
	}

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();

		BoundingSphere bounds = new BoundingSphere();

		// Rotação dos objectos
		TransformGroup spin = new TransformGroup();
		spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha alpha = new Alpha(-1, 10000);
		RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
		rotator.setSchedulingBounds(bounds);
		spin.addChild(rotator);

		// Aparência
		Appearance ap = new Appearance();
		ap.setMaterial(new Material());

		// Objectos
		Transform3D tr = new Transform3D();
		tr.set(new Vector3f(-0.7f, -1.7f, 0.0f), 0.25f);
		TransformGroup tg = new TransformGroup(tr);
		spin.addChild(tg);
		tg.addChild(Candeeiro(ap));
		tg.addChild(Cadeira(ap));
		tg.addChild(Mesa(ap));
		tg.addChild(ElementosCandeeiro(ap));

		tr.set(new Vector3f(-0.7f, -1.7f, -2.2f), 0.25f);
		tr.setRotation(new AxisAngle4f(0f, 1f, 0f, (float) Math.toRadians(180)));
		tg = new TransformGroup(tr);
		spin.addChild(tg);
		tg.addChild(Cadeira(ap));
		tg.addChild(Candeeiro(ap));

		// Iluminação dos objectos
		AmbientLight aLight = new AmbientLight(true, new Color3f(1f, 0f, 0f));
		PointLight pLight = new PointLight(new Color3f(0.79f, 0.64f, 0.15f), new Point3f(3f, 3f, 3f),
				new Point3f(1f, 0f, 0f));

		// Background
		Background bk = new Background(1f, 1f, 1f);

		// Bounds
		aLight.setInfluencingBounds(bounds);
		root.addChild(aLight);
		pLight.setInfluencingBounds(bounds);
		root.addChild(pLight);
		bk.setApplicationBounds(bounds);
		root.addChild(bk);

		root.addChild(spin);

		return root;
	}

	BranchGroup Cadeira(Appearance ap) {
		BranchGroup bg = new BranchGroup();

		// Cadeira
		Primitive assento = new Box(0.5f, 0.05f, 0.5f, ap); // Box(largura, altura, profundidade, aparência)
		Transform3D trAssento = new Transform3D();
		trAssento.set(new Vector3f(0f, 0.45f, 0f));
		TransformGroup tgAssento = new TransformGroup(trAssento);
		tgAssento.addChild(assento);
		bg.addChild(tgAssento);

		Primitive encosto1 = new Box(0.07f, 0.05f, 0.5f, ap);
		Transform3D trEncosto1 = new Transform3D();
		trEncosto1.set(new Vector3f(0.43f, 0.9f, 0.55f));
		trEncosto1.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto1 = new TransformGroup(trEncosto1);
		tgEncosto1.addChild(encosto1);
		bg.addChild(tgEncosto1);

		Primitive encosto2 = new Box(0.07f, 0.05f, 0.5f, ap);
		Transform3D trEncosto2 = new Transform3D();
		trEncosto2.set(new Vector3f(-0.43f, 0.9f, 0.55f));
		trEncosto2.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto2 = new TransformGroup(trEncosto2);
		tgEncosto2.addChild(encosto2);
		bg.addChild(tgEncosto2);

		Primitive encosto3 = new Box(0.17f, 0.05f, 0.5f, ap);
		Transform3D trEncosto3 = new Transform3D();
		trEncosto3.set(new Vector3f(0f, 0.9f, 0.55f));
		trEncosto3.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto3 = new TransformGroup(trEncosto3);
		tgEncosto3.addChild(encosto3);
		bg.addChild(tgEncosto3);

		Primitive encosto4 = new Box(0.5f, 0.05f, 0.07f, ap);
		Transform3D trEncosto4 = new Transform3D();
		trEncosto4.set(new Vector3f(0f, 1.45f, 0.65f));
		trEncosto4.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto4 = new TransformGroup(trEncosto4);
		tgEncosto4.addChild(encosto4);
		bg.addChild(tgEncosto4);

		Primitive perna1 = new Cylinder(0.07f, 0.85f);
		Transform3D trPerna1 = new Transform3D();
		trPerna1.set(new Vector3f(-0.4f, 0f, 0.4f));
		TransformGroup tgPerna1 = new TransformGroup(trPerna1);
		tgPerna1.addChild(perna1);
		bg.addChild(tgPerna1);

		Primitive perna2 = new Cylinder(0.07f, 0.85f);
		Transform3D trPerna2 = new Transform3D();
		trPerna2.set(new Vector3f(0.4f, 0f, 0.4f));
		TransformGroup tgPerna2 = new TransformGroup(trPerna2);
		tgPerna2.addChild(perna2);
		bg.addChild(tgPerna2);

		Primitive perna3 = new Cylinder(0.07f, 0.85f);
		Transform3D trPerna3 = new Transform3D();
		trPerna3.set(new Vector3f(0.4f, 0f, -0.4f));
		TransformGroup tgPerna3 = new TransformGroup(trPerna3);
		tgPerna3.addChild(perna3);
		bg.addChild(tgPerna3);

		Primitive perna4 = new Cylinder(0.07f, 0.85f);
		Transform3D trPerna4 = new Transform3D();
		trPerna4.set(new Vector3f(-0.4f, 0f, -0.4f));
		TransformGroup tgPerna4 = new TransformGroup(trPerna4);
		tgPerna4.addChild(perna4);
		bg.addChild(tgPerna4);

		Primitive perna5 = new Cylinder(0.05f, 0.85f);
		Transform3D trPerna5 = new Transform3D();
		trPerna5.set(new Vector3f(-0.4f, 0f, 0f));
		trPerna5.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(90)));
		TransformGroup tgPerna5 = new TransformGroup(trPerna5);
		tgPerna5.addChild(perna5);
		bg.addChild(tgPerna5);

		Primitive perna6 = new Cylinder(0.05f, 0.85f);
		Transform3D trPerna6 = new Transform3D();
		trPerna6.set(new Vector3f(0.4f, 0f, 0f));
		trPerna6.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(90)));
		TransformGroup tgPerna6 = new TransformGroup(trPerna6);
		tgPerna6.addChild(perna6);
		bg.addChild(tgPerna6);

		Primitive perna7 = new Cylinder(0.05f, 0.85f);
		Transform3D trPerna7 = new Transform3D();
		trPerna7.set(new Vector3f(0f, 0.1f, -0.4f));
		trPerna7.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(90)));
		trPerna7.setRotation(new AxisAngle4f(0, 0, 1, (float) Math.toRadians(90)));
		TransformGroup tgPerna7 = new TransformGroup(trPerna7);
		tgPerna7.addChild(perna7);
		bg.addChild(tgPerna7);

		return bg;
	}

	BranchGroup Mesa(Appearance ap) {
		BranchGroup bg = new BranchGroup();

		// Mesa
		Primitive tampo = new Box(1f, 0.07f, 1f, ap);
		Transform3D trTampo = new Transform3D();
		trTampo.set(new Vector3f(0f, 0.9f, -1.1f));
		TransformGroup tgTampo = new TransformGroup(trTampo);
		tgTampo.addChild(tampo);
		bg.addChild(tgTampo);

		Primitive perna8 = new Box(0.1f, 0.65f, 0.1f, ap);
		Transform3D trPerna8 = new Transform3D();
		trPerna8.set(new Vector3f(0.8f, 0.2f, -0.3f));
		TransformGroup tgPerna8 = new TransformGroup(trPerna8);
		tgPerna8.addChild(perna8);
		bg.addChild(tgPerna8);

		Primitive perna9 = new Box(0.1f, 0.65f, 0.1f, ap);
		Transform3D trPerna9 = new Transform3D();
		trPerna9.set(new Vector3f(0.8f, 0.2f, -1.9f));
		TransformGroup tgPerna9 = new TransformGroup(trPerna9);
		tgPerna9.addChild(perna9);
		bg.addChild(tgPerna9);

		Primitive perna10 = new Box(0.1f, 0.65f, 0.1f, ap);
		Transform3D trPerna10 = new Transform3D();
		trPerna10.set(new Vector3f(-0.8f, 0.2f, -1.9f));
		TransformGroup tgPerna10 = new TransformGroup(trPerna10);
		tgPerna10.addChild(perna10);
		bg.addChild(tgPerna10);

		Primitive perna11 = new Box(0.1f, 0.65f, 0.1f, ap);
		Transform3D trPerna11 = new Transform3D();
		trPerna11.set(new Vector3f(-0.8f, 0.2f, -0.3f));
		TransformGroup tgPerna11 = new TransformGroup(trPerna11);
		tgPerna11.addChild(perna11);
		bg.addChild(tgPerna11);

		return bg;
	}

	Geometry abatJour() {

		int n = 80; // number of sections
		int totalVertices = 4 * n; // total number of unique vertices
		int totalIndices = 4 * 4 * n; // total number of indices

		// The geometry is constructed with quadrilateres
		IndexedQuadArray qa = new IndexedQuadArray(totalVertices, IndexedQuadArray.COORDINATES, totalIndices);

		// Transformation to generate the unique vertices
		Transform3D trans = new Transform3D();
		trans.rotY(2 * Math.PI / n); // Rotation around Y axis

		// 4 initial vertices
		Point3f[] pts = { new Point3f(1.2f, 0f, 0f), new Point3f(1.3f, 0f, 0f), new Point3f(0.9f, 1.3f, 0f),
				new Point3f(0.8f, 1.3f, 0f) };

		// Generate all the unique vertices, from the 4 initial vertices
		int index = 0;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < 4; i++) {
				// qa.setCoordinate(j * 4 + i, pts[i]);
				qa.setCoordinate(index++, pts[i]);

				// After the vertex 'i' is added, the vertex is transformed to
				// generate the correspondnet next one
				trans.transform(pts[i]);
			}
		}

		int quadIndex = 0;
		// Initial set of indexes to define the faces
		int a = 0;
		int b = 1;
		int c = 2;
		int d = 3;

		//
		int e, f, g, h;
		for (int j = 0; j < n; j++) {

			e = (a + 4) % totalVertices;
			f = (b + 4) % totalVertices;
			g = (c + 4) % totalVertices;
			h = (d + 4) % totalVertices;

			// left face
			qa.setCoordinateIndex(quadIndex++, a);
			qa.setCoordinateIndex(quadIndex++, d);
			qa.setCoordinateIndex(quadIndex++, h);
			qa.setCoordinateIndex(quadIndex++, e);

			// right face
			qa.setCoordinateIndex(quadIndex++, b);
			qa.setCoordinateIndex(quadIndex++, f);
			qa.setCoordinateIndex(quadIndex++, g);
			qa.setCoordinateIndex(quadIndex++, c);

			// top face
			qa.setCoordinateIndex(quadIndex++, c);
			qa.setCoordinateIndex(quadIndex++, g);
			qa.setCoordinateIndex(quadIndex++, h);
			qa.setCoordinateIndex(quadIndex++, d);

			// bottom face
			qa.setCoordinateIndex(quadIndex++, a);
			qa.setCoordinateIndex(quadIndex++, e);
			qa.setCoordinateIndex(quadIndex++, f);
			qa.setCoordinateIndex(quadIndex++, b);

			a = e;
			b = f;
			c = g;
			d = h;
		}

		/*
		 * int quadIndex = 0; for (int j = 0; j < n; j++) { for (int i = 0; i < 4; i++)
		 * { int j1 = (j + 1) % n; int i1 = (i + 1) % 4;
		 * qa.setCoordinateIndex(quadIndex++, j * 4 + i);
		 * qa.setCoordinateIndex(quadIndex++, j * 4 + i1);
		 * qa.setCoordinateIndex(quadIndex++, (j1) * 4 + i1);
		 * qa.setCoordinateIndex(quadIndex++, (j1) * 4 + i); } }
		 */

		// Transform the QuadArrayGeometry into GeometryInfo to be able to generate the
		// normals.
		GeometryInfo gi = new GeometryInfo(qa);
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);

		return gi.getGeometryArray();
	}

	BranchGroup Candeeiro(Appearance ap) {
		BranchGroup bg = new BranchGroup();

		Geometry abatJour = abatJour();
		Transform3D trAbatJour = new Transform3D();

		trAbatJour.set(new Vector3f(0f, 3.3f, -0.7f));
		trAbatJour.setScale(0.25f);
		TransformGroup tgAbatJour = new TransformGroup(trAbatJour);
		ap.setMaterial(new Material());
		PolygonAttributes pa = new PolygonAttributes(PolygonAttributes.POLYGON_FILL, 0, 0);
		ap.setPolygonAttributes(pa);
		Shape3D shape = new Shape3D(abatJour, ap);
		tgAbatJour.addChild(shape);
		bg.addChild(tgAbatJour);

		// Lâmpada
		Primitive lampada = new Sphere(0.115f);
		Transform3D trLampada = new Transform3D();
		trLampada.set(new Vector3f(0f, 3.3f, -0.7f));
		TransformGroup tgLampada = new TransformGroup(trLampada);
		tgLampada.addChild(lampada);
		bg.addChild(tgLampada);

		return bg;
	}

	BranchGroup ElementosCandeeiro(Appearance ap) {
		BranchGroup bg = new BranchGroup();

		// Elementos do candeeiro do tecto
		Primitive topo = new Cylinder(0.5f, 0.05f);
		Transform3D trTopo = new Transform3D();
		trTopo.set(new Vector3f(0f, 4.3f, -1.1f));
		TransformGroup tgTopo = new TransformGroup(trTopo);
		tgTopo.addChild(topo);
		bg.addChild(tgTopo);

		Primitive meio = new Cylinder(0.05f, 0.75f);
		Transform3D trMeio = new Transform3D();
		trMeio.set(new Vector3f(0f, 3.95f, -1.1f));
		TransformGroup tgMeio = new TransformGroup(trMeio);
		tgMeio.addChild(meio);
		bg.addChild(tgMeio);

		Primitive fundo = new Box(0.05f, 0.04f, 0.3f, ap);
		Transform3D trFundo = new Transform3D();
		trFundo.set(new Vector3f(0f, 3.55f, -1.1f));
		TransformGroup tgFundo = new TransformGroup(trFundo);
		tgFundo.addChild(fundo);
		bg.addChild(tgFundo);

		// Elementos em cima da mesa:

		// Garrafa
		Shape3D torus1 = new Torus(1.1f, 1.2f);
		Transform3D trTorus1 = new Transform3D();
		trTorus1.set(new Vector3f(-0.5f, 1f, -0.8f));
		trTorus1.setScale(0.07f);
		TransformGroup tgTorus1 = new TransformGroup(trTorus1);
		tgTorus1.addChild(torus1);
		torus1.setAppearance(ap);
		bg.addChild(tgTorus1);

		Shape3D torus2 = new Torus(1.1f, 1.2f);
		Transform3D trTorus2 = new Transform3D();
		trTorus2.set(new Vector3f(-0.5f, 1.05f, -0.8f));
		trTorus2.setScale(0.07f);
		TransformGroup tgTorus2 = new TransformGroup(trTorus2);
		tgTorus2.addChild(torus2);
		torus2.setAppearance(ap);
		bg.addChild(tgTorus2);

		Shape3D torus3 = new Torus(1.1f, 1.2f);
		Transform3D trTorus3 = new Transform3D();
		trTorus3.set(new Vector3f(-0.5f, 1.1f, -0.8f));
		trTorus3.setScale(0.07f);
		TransformGroup tgTorus3 = new TransformGroup(trTorus3);
		tgTorus3.addChild(torus3);
		torus3.setAppearance(ap);
		bg.addChild(tgTorus3);

		Shape3D torus4 = new Torus(1.1f, 1.2f);
		Transform3D trTorus4 = new Transform3D();
		trTorus4.set(new Vector3f(-0.5f, 1.15f, -0.8f));
		trTorus4.setScale(0.07f);
		TransformGroup tgTorus4 = new TransformGroup(trTorus4);
		tgTorus4.addChild(torus4);
		torus4.setAppearance(ap);
		bg.addChild(tgTorus4);

		Shape3D torus5 = new Torus(1.1f, 1.2f);
		Transform3D trTorus5 = new Transform3D();
		trTorus5.set(new Vector3f(-0.5f, 1.2f, -0.8f));
		trTorus5.setScale(0.07f);
		TransformGroup tgTorus5 = new TransformGroup(trTorus5);
		tgTorus5.addChild(torus5);
		torus5.setAppearance(ap);
		bg.addChild(tgTorus5);

		Shape3D torus6 = new Torus(1.1f, 1.2f);
		Transform3D trTorus6 = new Transform3D();
		trTorus6.set(new Vector3f(-0.5f, 1.25f, -0.8f));
		trTorus6.setScale(0.07f);
		TransformGroup tgTorus6 = new TransformGroup(trTorus6);
		tgTorus6.addChild(torus6);
		torus6.setAppearance(ap);
		bg.addChild(tgTorus6);

		Primitive cone3 = new Cone(0.15f, 0.3f);
		Transform3D trCone3 = new Transform3D();
		trCone3.set(new Vector3f(-0.5f, 1.45f, -0.8f));
		TransformGroup tgCone3 = new TransformGroup(trCone3);
		tgCone3.addChild(cone3);
		bg.addChild(tgCone3);

		Primitive c2 = new Cylinder(0.06f, 0.4f);
		Transform3D trC2 = new Transform3D();
		trC2.set(new Vector3f(-0.5f, 1.45f, -0.8f));
		TransformGroup tgC2 = new TransformGroup(trC2);
		tgC2.addChild(c2);
		bg.addChild(tgC2);

		// Copo
		Primitive cone1 = new Cone(0.2f, 0.2f);
		Transform3D trCone1 = new Transform3D();
		trCone1.set(new Vector3f(0.6f, 1.3f, -0.4f));
		trCone1.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(180)));
		TransformGroup tgCone1 = new TransformGroup(trCone1);
		tgCone1.addChild(cone1);
		bg.addChild(tgCone1);

		Primitive cone2 = new Cone(0.2f, 0.1f);
		Transform3D trCone2 = new Transform3D();
		trCone2.set(new Vector3f(0.6f, 1f, -0.4f));
		TransformGroup tgCone2 = new TransformGroup(trCone2);
		tgCone2.addChild(cone2);
		bg.addChild(tgCone2);

		Primitive c = new Cylinder(0.02f, 0.3f);
		Transform3D trC = new Transform3D();
		trC.set(new Vector3f(0.6f, 1.1f, -0.4f));
		TransformGroup tgC = new TransformGroup(trC);
		tgC.addChild(c);
		bg.addChild(tgC);

		return bg;
	}
}
