package lab4.robot;

import lab4.shapes.CircleGenerator;
import lab4.shapes.FrustumGenerator;
import lab4.shapes.HalfSphereGenerator;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Leg extends TransformGroup {
    public Leg(double size) {
        double delta = size / 8;

        TransformGroup group = new TransformGroup();

        TransformGroup legBackgroundGroup = new TransformGroup();
        Shape3D legBackground = new FrustumGenerator()
                .setHeight(13 * delta / 2)
                .setInnerRadius(3 * delta / 8 * 0.98)
                .setOuterRadius(3 * delta / 8 * 0.98)
                .compile(getDarkAppearance());
        legBackgroundGroup.addChild(legBackground);
        Transform3D legBackgroundTransform  = new Transform3D();
        legBackgroundTransform.setTranslation(new Vector3d(0, 0, 3 * delta / 4));
        legBackgroundGroup.setTransform(legBackgroundTransform);
        group.addChild(legBackgroundGroup);

        for(int i = 0; i < 7; i++) {
            TransformGroup legPartGroup = new TransformGroup();
            Shape3D legPart = new FrustumGenerator()
                    .setHeight(delta * 0.98)
                    .setInnerRadius(3 * delta / 8)
                    .setOuterRadius(3 * delta / 8)
                    .compile(getAppearance());
            legPartGroup.addChild(legPart);
            Transform3D legPartTransform = new Transform3D();
            legPartTransform.setTranslation(new Vector3d( 0, 0, (3.5 - i) * delta));
            legPartGroup.setTransform(legPartTransform);
            group.addChild(legPartGroup);

            TransformGroup legPartCloseDisk1Group = new TransformGroup();
            Shape3D legPartCloseDisk1 = new CircleGenerator()
                    .setRadius(3 * delta / 8)
                    .compile(getAppearance());
            legPartCloseDisk1Group.addChild(legPartCloseDisk1);
            Transform3D legPartCloseDisk1Transform = new Transform3D();
            legPartCloseDisk1Transform.setTranslation(new Vector3d( 0, 0, (3.5 - i + 0.49) * delta));
            legPartCloseDisk1Group.setTransform(legPartCloseDisk1Transform);
            group.addChild(legPartCloseDisk1Group);

            TransformGroup legPartCloseDisk2Group = new TransformGroup();
            Shape3D legPartCloseDisk2 = new CircleGenerator()
                    .setRadius(3 * delta / 8)
                    .compile(getAppearance());
            legPartCloseDisk2Group.addChild(legPartCloseDisk2);
            Transform3D legPartCloseDisk2Transform = new Transform3D();
            legPartCloseDisk2Transform.rotX(Math.PI);
            legPartCloseDisk2Transform.setTranslation(new Vector3d( 0, 0, (3.5 - i - 0.49) * delta));
            legPartCloseDisk2Group.setTransform(legPartCloseDisk2Transform);
            group.addChild(legPartCloseDisk2Group);
        }

        TransformGroup footGroup = new TransformGroup();
        Shape3D foot = new HalfSphereGenerator()
                .setRadius(delta * 9 / 8)
                .compile(getAppearance());
        footGroup.addChild(foot);
        Transform3D footTransform = new Transform3D();
        footTransform.setTranslation(new Vector3d(0, 0, -delta * 4));
        footGroup.setTransform(footTransform);
        group.addChild(footGroup);

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