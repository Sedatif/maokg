package lab4.robot;

import com.sun.j3d.utils.geometry.Sphere;
import lab4.shapes.CircleGenerator;
import lab4.shapes.FrustumGenerator;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Arm extends TransformGroup {
    public Arm(double size) {
        double delta = size / 8;

        TransformGroup group = new TransformGroup();

        TransformGroup sholderGroup = new TransformGroup();
        Sphere sholder = new Sphere(3 * (float) delta / 4);
        sholder.setAppearance(getAppearance());
        sholderGroup.addChild(sholder);
        Transform3D sholderTransform = new Transform3D();
        sholderTransform.setTranslation(new Vector3f((float) -delta * 3, 0, 0));
        sholderGroup.setTransform(sholderTransform);
        group.addChild(sholderGroup);

        TransformGroup armBackgroundGroup = new TransformGroup();
        Shape3D armBackground = new FrustumGenerator()
                .setHeight(delta * 5)
                .setInnerRadius(3 * delta / 8 * 0.98)
                .setOuterRadius(3 * delta / 8 * 0.98)
                .compile(getDarkAppearance());
        armBackgroundGroup.addChild(armBackground);
        Transform3D armBackgroundTransform  = new Transform3D();
        armBackgroundTransform.rotY(Math.PI / 2);
        armBackgroundGroup.setTransform(armBackgroundTransform);
        group.addChild(armBackgroundGroup);

        for(int i = 0; i < 5; i++) {
            TransformGroup armPartGroup = new TransformGroup();
            Shape3D armPart = new FrustumGenerator()
                    .setHeight(delta * 0.98)
                    .setInnerRadius(3 * delta / 8)
                    .setOuterRadius(3 * delta / 8)
                    .compile(getAppearance());
            armPartGroup.addChild(armPart);
            Transform3D armPartTransform = new Transform3D();
            armPartTransform.rotY(Math.PI / 2);
            armPartTransform.setTranslation(new Vector3f( (float) delta * (2 - i), 0, 0));
            armPartGroup.setTransform(armPartTransform);
            group.addChild(armPartGroup);

            TransformGroup armPartCloseDisk1Group = new TransformGroup();
            Shape3D armPartCloseDisk1 = new CircleGenerator()
                    .setRadius(3 * delta / 8)
                    .compile(getAppearance());
            armPartCloseDisk1Group.addChild(armPartCloseDisk1);
            Transform3D armPartCloseDisk1Transform = new Transform3D();
            armPartCloseDisk1Transform.rotY(Math.PI / 2);
            armPartCloseDisk1Transform.setTranslation(new Vector3d((float) delta * (2 - i + 0.49), 0, 0));
            armPartCloseDisk1Group.setTransform(armPartCloseDisk1Transform);
            group.addChild(armPartCloseDisk1Group);

            TransformGroup armPartCloseDisk2Group = new TransformGroup();
            Shape3D armPartCloseDisk2 = new CircleGenerator()
                    .setRadius(3 * delta / 8)
                    .compile(getAppearance());
            armPartCloseDisk2Group.addChild(armPartCloseDisk2);
            Transform3D armPartCloseDisk2Transform = new Transform3D();
            armPartCloseDisk2Transform.rotY(-Math.PI / 2);
            armPartCloseDisk2Transform.setTranslation(new Vector3d((float) delta * (2 - i - 0.49), 0, 0));
            armPartCloseDisk2Group.setTransform(armPartCloseDisk2Transform);
            group.addChild(armPartCloseDisk2Group);
        }

        TransformGroup handGroup = new TransformGroup();
        Shape3D hand = new FrustumGenerator()
                .setHeight(3 * delta / 4)
                .setInnerRadius(3 * delta / 8)
                .setOuterRadius(9 * delta / 16)
                .compile(getAppearance());
        handGroup.addChild(hand);
        Transform3D handTransform = new Transform3D();
        handTransform.rotY(Math.PI / 2);
        handTransform.setTranslation(new Vector3f(23 * (float) delta / 8, 0, 0));
        handGroup.setTransform(handTransform);
        group.addChild(handGroup);

        TransformGroup handCloseDisk1Group = new TransformGroup();
        Shape3D handCloseDisk1 = new CircleGenerator()
                .setRadius(3 * delta / 8)
                .compile(getAppearance());
        handCloseDisk1Group.addChild(handCloseDisk1);
        Transform3D handCloseDisk1Transform = new Transform3D();
        handCloseDisk1Transform.rotY(-Math.PI / 2);
        handCloseDisk1Transform.setTranslation(new Vector3d(20 * (float) delta / 8, 0, 0));
        handCloseDisk1Group.setTransform(handCloseDisk1Transform);
        group.addChild(handCloseDisk1Group);

        TransformGroup handCloseDisk2Group = new TransformGroup();
        Shape3D handCloseDisk2 = new CircleGenerator()
                .setRadius(9 * delta / 16)
                .compile(getAppearance());
        handCloseDisk2Group.addChild(handCloseDisk2);
        Transform3D handCloseDisk2Transform = new Transform3D();
        handCloseDisk2Transform.rotY(Math.PI / 2);
        handCloseDisk2Transform.setTranslation(new Vector3d(13 * (float) delta / 4, 0, 0));
        handCloseDisk2Group.setTransform(handCloseDisk2Transform);
        group.addChild(handCloseDisk2Group);

        for(int i = 0; i < 3; i++) {
            TransformGroup fingerGroup = new TransformGroup();
            Shape3D finger = new FrustumGenerator()
                    .setHeight(delta / 2)
                    .setInnerRadius(3 * delta / 16)
                    .setOuterRadius(3 * delta / 16)
                    .compile(getAppearance());
            fingerGroup.addChild(finger);
            Transform3D fingerTransform = new Transform3D();
            fingerTransform.rotY(Math.PI / 2);
            fingerTransform.setTranslation(new Vector3d(
                    14 * delta / 4,
                    3 * delta / 8 * Math.cos(Math.PI / 6 + i * 2 * Math.PI / 3),
                    3 * delta / 8 * Math.sin(Math.PI / 6 + i * 2 * Math.PI / 3)
            ));
            fingerGroup.setTransform(fingerTransform);
            group.addChild(fingerGroup);

            TransformGroup fingerCloseGroup = new TransformGroup();
            Sphere fingerClose = new Sphere(3 * (float) delta / 16);
            fingerClose.setAppearance(getAppearance());
            fingerCloseGroup.addChild(fingerClose);
            Transform3D fingerCloseTransform = new Transform3D();
            fingerCloseTransform.setTranslation(new Vector3d(
                    15 * delta / 4,
                    3 * delta / 8 * Math.cos(Math.PI / 6 + i * 2 * Math.PI / 3),
                    3 * delta / 8 * Math.sin(Math.PI / 6 + i * 2 * Math.PI / 3)
            ));
            fingerCloseGroup.setTransform(fingerCloseTransform);
            group.addChild(fingerCloseGroup);
        }

        addChild(group);
    }

    public Appearance getAppearance() {
        Appearance appearance = new Appearance();
        appearance.setMaterial(
                new Material(
                        new Color3f(0.4453f, 0.4453f, 0.4453f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0.4453f, 0.4453f, 0.4453f),
                        new Color3f(1f, 1f, 1f),
                        70f
                )
        );
        return appearance;
    }

    public Appearance getDarkAppearance() {
        Appearance appearance = new Appearance();
        appearance.setMaterial(
                new Material(
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0f, 0f, 0f),
                        new Color3f(0f, 0f, 0f),
                        70f
                )
        );
        return appearance;
    }
}
