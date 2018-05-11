package capitulo8ex1;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class ElementosCimaMesa extends Group {
	public ElementosCimaMesa(Appearance ap) {
		ap.setMaterial(new Material());

		// Elementos em cima da mesa:

		// Garrafa
		Shape3D torus1 = new Torus(1.1f, 1.2f);
		Transform3D trTorus1 = new Transform3D();
		trTorus1.set(new Vector3f(-0.5f, 1f, -0.8f));
		trTorus1.setScale(0.07f);
		TransformGroup tgTorus1 = new TransformGroup(trTorus1);
		tgTorus1.addChild(torus1);
		torus1.setAppearance(ap);
		addChild(tgTorus1);

		Shape3D torus2 = new Torus(1.1f, 1.2f);
		Transform3D trTorus2 = new Transform3D();
		trTorus2.set(new Vector3f(-0.5f, 1.05f, -0.8f));
		trTorus2.setScale(0.07f);
		TransformGroup tgTorus2 = new TransformGroup(trTorus2);
		tgTorus2.addChild(torus2);
		torus2.setAppearance(ap);
		addChild(tgTorus2);

		Shape3D torus3 = new Torus(1.1f, 1.2f);
		Transform3D trTorus3 = new Transform3D();
		trTorus3.set(new Vector3f(-0.5f, 1.1f, -0.8f));
		trTorus3.setScale(0.07f);
		TransformGroup tgTorus3 = new TransformGroup(trTorus3);
		tgTorus3.addChild(torus3);
		torus3.setAppearance(ap);
		addChild(tgTorus3);

		Shape3D torus4 = new Torus(1.1f, 1.2f);
		Transform3D trTorus4 = new Transform3D();
		trTorus4.set(new Vector3f(-0.5f, 1.15f, -0.8f));
		trTorus4.setScale(0.07f);
		TransformGroup tgTorus4 = new TransformGroup(trTorus4);
		tgTorus4.addChild(torus4);
		torus4.setAppearance(ap);
		addChild(tgTorus4);

		Shape3D torus5 = new Torus(1.1f, 1.2f);
		Transform3D trTorus5 = new Transform3D();
		trTorus5.set(new Vector3f(-0.5f, 1.2f, -0.8f));
		trTorus5.setScale(0.07f);
		TransformGroup tgTorus5 = new TransformGroup(trTorus5);
		tgTorus5.addChild(torus5);
		torus5.setAppearance(ap);
		addChild(tgTorus5);

		Shape3D torus6 = new Torus(1.1f, 1.2f);
		Transform3D trTorus6 = new Transform3D();
		trTorus6.set(new Vector3f(-0.5f, 1.25f, -0.8f));
		trTorus6.setScale(0.07f);
		TransformGroup tgTorus6 = new TransformGroup(trTorus6);
		tgTorus6.addChild(torus6);
		torus6.setAppearance(ap);
		addChild(tgTorus6);

		Primitive cone3 = new Cone(0.15f, 0.3f);
		Transform3D trCone3 = new Transform3D();
		trCone3.set(new Vector3f(-0.5f, 1.45f, -0.8f));
		TransformGroup tgCone3 = new TransformGroup(trCone3);
		tgCone3.addChild(cone3);
		addChild(tgCone3);

		Primitive c2 = new Cylinder(0.06f, 0.4f);
		Transform3D trC2 = new Transform3D();
		trC2.set(new Vector3f(-0.5f, 1.45f, -0.8f));
		TransformGroup tgC2 = new TransformGroup(trC2);
		tgC2.addChild(c2);
		addChild(tgC2);

		// Copo
		Primitive cone1 = new Cone(0.2f, 0.2f);
		Transform3D trCone1 = new Transform3D();
		trCone1.set(new Vector3f(0.6f, 1.3f, -0.4f));
		trCone1.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(180)));
		TransformGroup tgCone1 = new TransformGroup(trCone1);
		tgCone1.addChild(cone1);
		addChild(tgCone1);

		Primitive cone2 = new Cone(0.2f, 0.1f);
		Transform3D trCone2 = new Transform3D();
		trCone2.set(new Vector3f(0.6f, 1f, -0.4f));
		TransformGroup tgCone2 = new TransformGroup(trCone2);
		tgCone2.addChild(cone2);
		addChild(tgCone2);

		Primitive c = new Cylinder(0.02f, 0.3f);
		Transform3D trC = new Transform3D();
		trC.set(new Vector3f(0.6f, 1.1f, -0.4f));
		TransformGroup tgC = new TransformGroup(trC);
		tgC.addChild(c);
		addChild(tgC);
	}
}