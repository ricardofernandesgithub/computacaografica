package capitulo9ex1;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class Cadeira extends Group {
	public Cadeira(Appearance ap) {
		ap.setMaterial(new Material());

		Primitive assento = new Box(0.5f, 0.05f, 0.5f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap); // Box(largura,
																												// altura,
																												// profundidade,
																												// aparência)
		Transform3D trAssento = new Transform3D();
		trAssento.set(new Vector3f(0f, 0.45f, 0f));
		TransformGroup tgAssento = new TransformGroup(trAssento);
		tgAssento.addChild(assento);
		addChild(tgAssento);

		Primitive encosto1 = new Box(0.07f, 0.05f, 0.5f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trEncosto1 = new Transform3D();
		trEncosto1.set(new Vector3f(0.43f, 0.9f, 0.55f));
		trEncosto1.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto1 = new TransformGroup(trEncosto1);
		tgEncosto1.addChild(encosto1);
		addChild(tgEncosto1);

		Primitive encosto2 = new Box(0.07f, 0.05f, 0.5f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trEncosto2 = new Transform3D();
		trEncosto2.set(new Vector3f(-0.43f, 0.9f, 0.55f));
		trEncosto2.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto2 = new TransformGroup(trEncosto2);
		tgEncosto2.addChild(encosto2);
		addChild(tgEncosto2);

		Primitive encosto3 = new Box(0.17f, 0.05f, 0.5f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trEncosto3 = new Transform3D();
		trEncosto3.set(new Vector3f(0f, 0.9f, 0.55f));
		trEncosto3.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto3 = new TransformGroup(trEncosto3);
		tgEncosto3.addChild(encosto3);
		addChild(tgEncosto3);

		Primitive encosto4 = new Box(0.5f, 0.05f, 0.07f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trEncosto4 = new Transform3D();
		trEncosto4.set(new Vector3f(0f, 1.45f, 0.65f));
		trEncosto4.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(100)));
		TransformGroup tgEncosto4 = new TransformGroup(trEncosto4);
		tgEncosto4.addChild(encosto4);
		addChild(tgEncosto4);

		Primitive perna1 = new Cylinder(0.07f, 0.85f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap);
		Transform3D trPerna1 = new Transform3D();
		trPerna1.set(new Vector3f(-0.4f, 0f, 0.4f));
		TransformGroup tgPerna1 = new TransformGroup(trPerna1);
		tgPerna1.addChild(perna1);
		addChild(tgPerna1);

		Primitive perna2 = new Cylinder(0.07f, 0.85f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap);
		Transform3D trPerna2 = new Transform3D();
		trPerna2.set(new Vector3f(0.4f, 0f, 0.4f));
		TransformGroup tgPerna2 = new TransformGroup(trPerna2);
		tgPerna2.addChild(perna2);
		addChild(tgPerna2);

		Primitive perna3 = new Cylinder(0.07f, 0.85f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap);
		Transform3D trPerna3 = new Transform3D();
		trPerna3.set(new Vector3f(0.4f, 0f, -0.4f));
		TransformGroup tgPerna3 = new TransformGroup(trPerna3);
		tgPerna3.addChild(perna3);
		addChild(tgPerna3);

		Primitive perna4 = new Cylinder(0.07f, 0.85f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap);
		Transform3D trPerna4 = new Transform3D();
		trPerna4.set(new Vector3f(-0.4f, 0f, -0.4f));
		TransformGroup tgPerna4 = new TransformGroup(trPerna4);
		tgPerna4.addChild(perna4);
		addChild(tgPerna4);

		Primitive perna5 = new Cylinder(0.05f, 0.85f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap);
		Transform3D trPerna5 = new Transform3D();
		trPerna5.set(new Vector3f(-0.4f, 0f, 0f));
		trPerna5.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(90)));
		TransformGroup tgPerna5 = new TransformGroup(trPerna5);
		tgPerna5.addChild(perna5);
		addChild(tgPerna5);

		Primitive perna6 = new Cylinder(0.05f, 0.85f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap);
		Transform3D trPerna6 = new Transform3D();
		trPerna6.set(new Vector3f(0.4f, 0f, 0f));
		trPerna6.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(90)));
		TransformGroup tgPerna6 = new TransformGroup(trPerna6);
		tgPerna6.addChild(perna6);
		addChild(tgPerna6);

		Primitive perna7 = new Cylinder(0.05f, 0.85f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap);
		Transform3D trPerna7 = new Transform3D();
		trPerna7.set(new Vector3f(0f, 0.1f, -0.4f));
		trPerna7.setRotation(new AxisAngle4f(1, 0, 0, (float) Math.toRadians(90)));
		trPerna7.setRotation(new AxisAngle4f(0, 0, 1, (float) Math.toRadians(90)));
		TransformGroup tgPerna7 = new TransformGroup(trPerna7);
		tgPerna7.addChild(perna7);
		addChild(tgPerna7);
	}

}
