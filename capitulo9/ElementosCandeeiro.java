package capitulo9ex1;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class ElementosCandeeiro extends Group {
	public ElementosCandeeiro(Appearance ap) {
		ap.setMaterial(new Material());

		// Elementos do candeeiro do tecto
		Primitive topo = new Cylinder(0.5f, 0.05f);
		Transform3D trTopo = new Transform3D();
		trTopo.set(new Vector3f(0f, 4.3f, -1.1f));
		TransformGroup tgTopo = new TransformGroup(trTopo);
		tgTopo.addChild(topo);
		addChild(tgTopo);

		Primitive meio = new Cylinder(0.05f, 0.75f);
		Transform3D trMeio = new Transform3D();
		trMeio.set(new Vector3f(0f, 3.95f, -1.1f));
		TransformGroup tgMeio = new TransformGroup(trMeio);
		tgMeio.addChild(meio);
		addChild(tgMeio);

		Primitive fundo = new Box(0.05f, 0.04f, 0.3f, ap);
		Transform3D trFundo = new Transform3D();
		trFundo.set(new Vector3f(0f, 3.55f, -1.1f));
		TransformGroup tgFundo = new TransformGroup(trFundo);
		tgFundo.addChild(fundo);
		addChild(tgFundo);
	}
}