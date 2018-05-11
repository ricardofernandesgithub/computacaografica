package capitulo10ex1;

import java.applet.Applet;
import java.net.URL;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

public class Arena extends Group {

	Applet applet = new Applet();

	Appearance ap = new Appearance();

	public Arena() {
		ap.setMaterial(new Material());

		// paredes exterirores
		criarParede(1f, -0.01f, -1f, false, ap);
		criarParede(1f, 0.01f, 0.99f, false, ap);
		criarParede(1f, -1f, 0f, true, ap);
		criarParede(1f, 1f, -0.01f, true, ap);

		// quarto ilha
		criarParede(0.25f, -0.32f, 0.665f, false, ap);
		criarParede(0.25f, -0.32f, -0.025f, false, ap);
		criarParede(0.15f, -0.56f, 0.135f, true, ap);
		criarParede(0.35f, -0.08f, 0.325f, true, ap);

		// quarto canto inferior esquerdo
		criarParede(0.43f, -0.565f, -0.395f, false, ap);
		criarParede(0.185f, -0.145f, -0.58f, true, ap);

		// quarto canto inferior direito
		criarParede(0.14f, 0.85f, -0.015f, false, ap);
		criarParede(0.505f, 0.44f, 0.475f, true, ap);

		// quarto canto superior direito
		criarParede(0.2f, 0.595f, -0.425f, false, ap);
		criarParede(0.295f, 0.404f, -0.715f, true, ap);

		// chao
		criarChao();

		// mobiliário
		ap = aplicarMaterial();
		criarMobiliario(-0.3f, 0.4f, ap );
		criarMobiliario(-0.5f, -0.7f, ap );
	}

	public void criarParede(float comprimento, float vx, float vy, boolean flagRodar, Appearance ap) {
		Primitive parede = new Box(comprimento, 0.01f, 0.2f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trParede = new Transform3D();
		trParede.set(new Vector3f(vx, vy, 0f));
		float angulo = flagRodar ? 90f : 0f;
		trParede.setRotation(new AxisAngle4d(0d, 0d, 1d, (float) Math.toRadians(angulo)));
		TransformGroup tgParede = new TransformGroup(trParede);
		tgParede.addChild(parede);
		addChild(tgParede);
	}

	public void criarChao() {
		ap = createTextureAppearance("texturas/chao.png");
		Primitive chao = new Box(1f, 0.01f, 1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trChao = new Transform3D();
		trChao.set(new Vector3f(0f, 0f, -0.185f));
		trChao.setRotation(new AxisAngle4d(1d, 0d, 0d, (float) Math.toRadians(90f)));
		TransformGroup tgChao = new TransformGroup(trChao);
		tgChao.addChild(chao);
		addChild(tgChao);

		ap = new Appearance();
		ap.setMaterial(new Material());
		Primitive chao2 = new Box(1f, 0.01f, 1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap);
		Transform3D trChao2 = new Transform3D();
		trChao2.set(new Vector3f(0f, 0f, -0.19f));
		trChao2.setRotation(new AxisAngle4d(1d, 0d, 0d, (float) Math.toRadians(90f)));
		TransformGroup tgChao2 = new TransformGroup(trChao2);
		tgChao2.addChild(chao2);
		addChild(tgChao2);
	}

	public void criarMobiliario(float vx, float vy, Appearance ap) {
		Primitive c = new Cylinder(0.05f, 0.3f, ap);
		Transform3D trC = new Transform3D();
		trC.set(new Vector3f(vx, vy, 0f));
		trC.setRotation(new AxisAngle4d(1d, 0d, 0d, (float) Math.toRadians(90f)));
		TransformGroup tgC = new TransformGroup(trC);
		tgC.addChild(c);
		addChild(tgC);
	}

	Appearance createTextureAppearance(String ficheiroTextura) {
		ap = new Appearance();
		URL filename = getClass().getClassLoader().getResource(ficheiroTextura);
		TextureLoader loader = new TextureLoader(filename, applet);
		ImageComponent2D image = loader.getImage();
		if (image == null) {
			System.out.println("can't find texture file.");
		}
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		ap.setTexture(texture);
		return ap;
	}

	Appearance aplicarMaterial() {
		Appearance ap = new Appearance();
		Material mobiliario = new Material(); // ouro
		mobiliario.setAmbientColor(0.24725f, 0.1995f, 0.0745f);
		mobiliario.setDiffuseColor(0.75164f, 0.60648f, 0.22648f);
		mobiliario.setSpecularColor(0.628281f, 0.555802f, 0.366065f);
		mobiliario.setShininess(51.2f);
		ap.setMaterial(mobiliario);
		return ap;
	}
}