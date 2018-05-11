package capitulo6ex2;

import com.sun.j3d.utils.geometry.GeometryInfo;

import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class Mosaicos extends Shape3D {
	public Mosaicos(int m, int n, Color3f cor1, Color3f cor2) {

		// m = 21;
		float a = -3.0f;
		float b = 3.0f;
		float divX = (b - a) / m;

		// n = 21;
		float c = -3.0f;
		float d = 3.0f;
		float divZ = (d - c) / m;

		int pontosTotais = m * n * 4;

		Point3f[] pontos = new Point3f[pontosTotais];
		Color3f[] col = new Color3f[pontosTotais];

		int idx = 0;
		boolean flag = true;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				float x = a + i * divX;
				float z = c + j * divZ;
				float y = 0.0f;
				pontos[idx] = new Point3f(x, y, z);
				col[idx] = (flag ? cor1 : cor2);
				idx++;

				x = a + i * divX;
				z = c + (j + 1) * divZ;
				pontos[idx] = new Point3f(x, y, z);
				col[idx] = (flag ? cor1 : cor2);
				idx++;

				x = a + (i + 1) * divX;
				z = c + (j + 1) * divZ;
				pontos[idx] = new Point3f(x, y, z);
				col[idx] = (flag ? cor1 : cor2);
				idx++;

				x = a + (i + 1) * divX;
				z = c + j * divZ;
				pontos[idx] = new Point3f(x, y, z);
				col[idx] = (flag ? cor1 : cor2);
				idx++;

				flag = !flag;
			}
		}
		GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
		gi.setCoordinates(pontos);
		gi.setColors(col);
		setGeometry(gi.getGeometryArray());
	}

}
