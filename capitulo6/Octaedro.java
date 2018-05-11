package capitulo6ex2;

import java.awt.Color;

import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class Octaedro extends Shape3D {

	public Octaedro() {
		GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);

		// int[] nrVerticesPorFaixa = { 3, 3, 3, 3, 3, 3, 3, 3 }; // cada face do
		// octaedro é uma faixa

		// Coordenadas dos vértices
		Point3f[] vertices = { new Point3f(0, 0, 1), new Point3f(-1, 0, 0), new Point3f(0, -1, 0), new Point3f(1, 0, 0),
				new Point3f(0, 1, 0), new Point3f(0, 0, -1) };
		gi.setCoordinates(vertices);

		// Indíces
		int[] indices = { 4, 3, 0, 4, 0, 1, 4, 1, 5, 4, 5, 3, 0, 3, 2, 0, 2, 1, 1, 2, 5, 5, 2, 3 };
		gi.setCoordinateIndices(indices);

		// gi.setStripCounts(nrVerticesPorFaixa);

		Color3f[] cores = { new Color3f(Color.RED), new Color3f(Color.CYAN), new Color3f(Color.BLACK),
				new Color3f(Color.BLUE), new Color3f(Color.YELLOW), new Color3f(Color.MAGENTA), new Color3f(Color.BLUE),
				new Color3f(Color.ORANGE) };
		gi.setColors(cores);
		gi.setColorIndices(indices);

		// Gerar normais
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);
		setGeometry(gi.getGeometryArray());
	}

}
