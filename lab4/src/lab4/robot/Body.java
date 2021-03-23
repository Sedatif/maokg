package lab4.robot;

import lab4.shapes.CircleGenerator;
import lab4.shapes.FrustumGenerator;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Body extends TransformGroup {
    public Body(double size) {
        TransformGroup group = new TransformGroup();

        TransformGroup upGroup = new TransformGroup();
        Shape3D up = new FrustumGenerator()
                .setHeight(size / 6)
                .setInnerRadius(size / 4)
                .setOuterRadius(size / 2)
                .compile(getAppearance());
        upGroup.addChild(up);
        Transform3D upTransform = new Transform3D();
        upTransform.rotX(Math.PI);
        upTransform.setTranslation(new Vector3f(0, 0, 5 * (float) size / 12));
        upGroup.setTransform(upTransform);
        group.addChild(upGroup);

        TransformGroup downGroup = new TransformGroup();
        Shape3D down = new FrustumGenerator()
                .setHeight(5 * size / 6)
                .setInnerRadius(size / 3)
                .setOuterRadius(size / 2)
                .compile(getAppearance());
        downGroup.addChild(down);
        Transform3D downTransform = new Transform3D();
        downTransform.setTranslation(new Vector3f(0, 0, (float) -size / 12));
        downGroup.setTransform(downTransform);
        group.addChild(downGroup);

        TransformGroup downCloseDiskGroup = new TransformGroup();
        Shape3D downCloseDisk = new CircleGenerator()
                .setRadius(size / 3)
                .compile(getAppearance());
        downCloseDiskGroup.addChild(downCloseDisk);
        Transform3D downCloseDiskTransform = new Transform3D();
        downCloseDiskTransform.rotX(Math.PI);
        downCloseDiskTransform.setTranslation(new Vector3d( 0, 0, -size / 2));
        downCloseDiskGroup.setTransform(downCloseDiskTransform);
        group.addChild(downCloseDiskGroup);

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
}
