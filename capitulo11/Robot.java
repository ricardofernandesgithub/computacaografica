package capitulo11ex2;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import java.applet.Applet;
import java.net.URL;
import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class Robot extends Group {
	private Applet applet = null;
	static TransformGroup tgRodaD = null;
	static TransformGroup tgRodaE = null;
	static TransformGroup tgCr1 = null;
	static TransformGroup tgCr2 = null;

	public Robot(Applet applet) {
		this.applet = applet;

		Appearance ap1 = createTextureAppearance("texturas/pcb.jpg");
		Appearance ap2 = createTextureAppearance("texturas/rodas.png");
		Appearance ap3 = createTextureAppearance("texturas/pcb2.jpg");

		// corpo do robot
		Box cr1 = new Box(0.05f, 0.06f, 0.05f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap1);
		Transform3D trCr1 = new Transform3D();
		trCr1.setTranslation(new Vector3f(0.1f, 0f, -0.11f));
		trCr1.setScale(1.25d);
		tgCr1 = new TransformGroup(trCr1);
		tgCr1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgCr1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgCr1.addChild(cr1);
		// addChild(tgCr1);

		Box cr2 = new Box(0.035f, 0.035f, 0.025f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, ap3);
		Transform3D trCr2 = new Transform3D();
		trCr2.setTranslation(new Vector3f(0.1f, 0.035f, -0.025f));
		trCr1.setScale(1.25d);
		tgCr2 = new TransformGroup(trCr2);
		tgCr2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgCr2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgCr2.addChild(cr2);
		// addChild(tgCr2);

		// roda esquerda e direita
		Cylinder rodaD = new Cylinder(0.06f, 0.015f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap2);
		Transform3D trRodaD = new Transform3D();
		// trRodaD.setTranslation(new Vector3f(0.17f, 0.01f, -0.112f));
		// trRodaD.setRotation(new AxisAngle4d(0d, 0d, 1d, (float) Math.toRadians(90)));
		trCr1.setScale(1.25d);
		tgRodaD = new TransformGroup(trRodaD);
		tgRodaD.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgRodaD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgRodaD.addChild(rodaD);
		// addChild(tgRodaD);

		Cylinder rodaE = new Cylinder(0.06f, 0.015f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
				ap2);
		Transform3D trRodaE = new Transform3D();
		// trRodaE.setTranslation(new Vector3f(0.03f, 0.015f, -0.112f));
		// trRodaE.setRotation(new AxisAngle4d(0d, 0d, 1d, (float) Math.toRadians(90)));
		trCr1.setScale(1.25d);
		tgRodaE = new TransformGroup(trRodaE);
		tgRodaE.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgRodaE.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgRodaE.addChild(rodaE);
		// addChild(tgRodaE);
	}

	Appearance createTextureAppearance(String ficheiroTextura) {
		Appearance ap = new Appearance();
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
}
