package capitulo10ex1;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Bounds;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3f;

public class KeyControl extends Behavior {
	WakeupCondition wakeupConditionKeyCtrl = null;
	TransformGroup tg = null;
	Transform3D prevTr = null;
	Group collidingObj;
	boolean flagColisao = false;
	int keyCode = 0;

	public KeyControl(TransformGroup tgMove, Bounds bounds, Group myShape) {
		tg = tgMove;
		prevTr = new Transform3D();
		setSchedulingBounds(bounds);
		collidingObj = myShape;
	}

	public void initialize() {
		WakeupCriterion[] keyEvents = new WakeupCriterion[4];
		keyEvents[0] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		keyEvents[1] = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED);
		keyEvents[2] = new WakeupOnCollisionEntry(collidingObj, WakeupOnCollisionEntry.USE_GEOMETRY);
		keyEvents[3] = new WakeupOnCollisionExit(collidingObj, WakeupOnCollisionExit.USE_GEOMETRY);

		wakeupConditionKeyCtrl = new WakeupOr(keyEvents);
		wakeupOn(wakeupConditionKeyCtrl);
	}

	public void processStimulus(Enumeration criteria) {
		while (criteria.hasMoreElements()) {
			WakeupCriterion wakeup = (WakeupCriterion) criteria.nextElement();
			if ((wakeup instanceof WakeupOnCollisionEntry)) {
				flagColisao = true;
			} else if ((wakeup instanceof WakeupOnCollisionExit)) {
				flagColisao = false;
			} else {
				AWTEvent[] events = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
				for (int i = 0; i < events.length; i++) {
					if (events[i].getID() == KeyEvent.KEY_PRESSED) {
						keyPressed_((KeyEvent) events[i]);
					}
				}
			}
		}
		wakeupOn(wakeupConditionKeyCtrl);
	}

	private void keyPressed_(KeyEvent event) {

		if (!flagColisao) {
			keyCode = event.getKeyCode();
		}
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			if (!flagColisao) {
				rotateZ(Math.toRadians(50), tg);
				rotateY(Math.toRadians(300), Robot.tgRodaD);
				rotateY(Math.toRadians(300), Robot.tgRodaE);
			}
			if (flagColisao) {
				rotateZ(Math.toRadians(-50), tg);
				keyCode = KeyEvent.VK_RIGHT;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (!flagColisao) {
				rotateZ(Math.toRadians(-50), tg);
				rotateY(Math.toRadians(-300), Robot.tgRodaD);
				rotateY(Math.toRadians(-300), Robot.tgRodaE);
			}
			if (flagColisao) {
				rotateZ(Math.toRadians(50), tg);
				keyCode = KeyEvent.VK_LEFT;
			}
			break;
		case KeyEvent.VK_UP:
			if (!flagColisao) {
				move(new Vector3f(0f, 0.01f, 0f));
				rotateY(Math.toRadians(300), Robot.tgRodaD);
				rotateY(Math.toRadians(300), Robot.tgRodaE);
			}
			if (flagColisao) {
				move(new Vector3f(0f, -0.015f, 0f));
				keyCode = KeyEvent.VK_DOWN;
				;
			}
			break;
		case KeyEvent.VK_DOWN:
			if (!flagColisao) {
				move(new Vector3f(0f, -0.01f, 0f));
				rotateY(Math.toRadians(-300), Robot.tgRodaD);
				rotateY(Math.toRadians(-300), Robot.tgRodaE);
			}
			if (flagColisao) {
				move(new Vector3f(0f, 0.015f, 0f));
				keyCode = KeyEvent.VK_UP;
			}
			break;
		}
	}

	private void move(Vector3f vector) {
		tg.getTransform(prevTr);
		Transform3D newTr = new Transform3D();
		newTr.setTranslation(vector);
		prevTr.mul(newTr);
		tg.setTransform(prevTr);
	}

	private void rotateZ(double angle, TransformGroup tg) {
		tg.getTransform(prevTr);
		Transform3D newTr = new Transform3D();
		newTr.rotZ(Math.toRadians(angle));
		prevTr.mul(newTr);
		tg.setTransform(prevTr);
	}

	private void rotateY(double angle, TransformGroup tg) {
		tg.getTransform(prevTr);
		Transform3D newTr = new Transform3D();
		newTr.rotY(Math.toRadians(angle));
		prevTr.mul(newTr);
		tg.setTransform(prevTr);
	}
}
