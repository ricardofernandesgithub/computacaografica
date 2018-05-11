package capitulo9ex1;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

public class Mesa extends Group {
	public Mesa(Appearance ap) {
		ap.setMaterial(new Material());

		Primitive tampo = new Box(1f, 0.07f, 1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trTampo = new Transform3D();
		trTampo.set(new Vector3f(0f, 0.9f, -1.1f));
		TransformGroup tgTampo = new TransformGroup(trTampo);
		tgTampo.addChild(tampo);
		addChild(tgTampo);

		Primitive perna8 = new Box(0.1f, 0.65f, 0.1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trPerna8 = new Transform3D();
		trPerna8.set(new Vector3f(0.8f, 0.2f, -0.3f));
		TransformGroup tgPerna8 = new TransformGroup(trPerna8);
		tgPerna8.addChild(perna8);
		addChild(tgPerna8);

		Primitive perna9 = new Box(0.1f, 0.65f, 0.1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trPerna9 = new Transform3D();
		trPerna9.set(new Vector3f(0.8f, 0.2f, -1.9f));
		TransformGroup tgPerna9 = new TransformGroup(trPerna9);
		tgPerna9.addChild(perna9);
		addChild(tgPerna9);

		Primitive perna10 = new Box(0.1f, 0.65f, 0.1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trPerna10 = new Transform3D();
		trPerna10.set(new Vector3f(-0.8f, 0.2f, -1.9f));
		TransformGroup tgPerna10 = new TransformGroup(trPerna10);
		tgPerna10.addChild(perna10);
		addChild(tgPerna10);

		Primitive perna11 = new Box(0.1f, 0.65f, 0.1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trPerna11 = new Transform3D();
		trPerna11.set(new Vector3f(-0.8f, 0.2f, -0.3f));
		TransformGroup tgPerna11 = new TransformGroup(trPerna11);
		tgPerna11.addChild(perna11);
		addChild(tgPerna11);
	}

}
