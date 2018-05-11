package capitulo8ex1;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

public class Candeeiro extends Group {
	public Candeeiro(Appearance ap) {
		ap.setMaterial(new Material());

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
		addChild(tgAbatJour);

		// Lâmpada
		Primitive lampada = new Sphere(0.115f);
		Transform3D trLampada = new Transform3D();
		trLampada.set(new Vector3f(0f, 3.3f, -0.7f));
		TransformGroup tgLampada = new TransformGroup(trLampada);
		tgLampada.addChild(lampada);
		addChild(tgLampada);
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

}
