package lab4.robot;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class Robot extends TransformGroup {
	public Robot(double size) {
        double delta = size / 10;
        TransformGroup group = new TransformGroup();

        Head head = new Head(delta * 3);
        Transform3D headTransform = new Transform3D();
        headTransform.setTranslation(new Vector3d(0, 0, 11 * delta / 4));
        head.setTransform(headTransform);
        group.addChild(head);

        Arm rightArm = new Arm(4 * delta);
        Transform3D rightArmTransform = new Transform3D();
        rightArmTransform.rotZ(-Math.PI / 2);
        rightArmTransform.setTranslation(new Vector3d(0, -3 * delta * 0.95, delta));
        rightArm.setTransform(rightArmTransform);
        group.addChild(rightArm);

        Arm leftArm = new Arm(4 * delta);
        Transform3D leftArmTransform = new Transform3D();
        leftArmTransform.rotZ(Math.PI / 2);
        leftArmTransform.setTranslation(new Vector3d(0, 3 * delta * 0.95, delta));
        leftArm.setTransform(leftArmTransform);
        group.addChild(leftArm);

        Leg rightLeg = new Leg(4 * delta);
        Transform3D rightLegTransform = new Transform3D();
        rightLegTransform.setTranslation(new Vector3d(0, -3 * delta / 4, -3 * delta));
        rightLeg.setTransform(rightLegTransform);
        group.addChild(rightLeg);

        Leg leftLeg = new Leg(4 * delta);
        Transform3D leftLegTransform = new Transform3D();
        leftLegTransform.setTranslation(new Vector3d(0, 3 * delta / 4, -3 * delta));
        leftLeg.setTransform(leftLegTransform);
        group.addChild(leftLeg);

        Body body = new Body(3 * delta);
        Transform3D bodyTransform = new Transform3D();
        bodyTransform.setTranslation(new Vector3d(0, 0, delta / 2));
        body.setTransform(bodyTransform);
        group.addChild(body);

        addChild(group);
    }

}
