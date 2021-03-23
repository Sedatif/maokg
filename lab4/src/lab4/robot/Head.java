package lab4.robot;

import com.sun.j3d.utils.geometry.Sphere;
import lab4.shapes.CubeGenerator;
import lab4.shapes.FrustumGenerator;
import lab4.shapes.HalfSphereGenerator;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Head extends TransformGroup {

    public Head(double size) {
        TransformGroup group = new TransformGroup();

        Shape3D cylinder = new FrustumGenerator()
                .setHeight(size / 2)
                .setInnerRadius(size / 4)
                .setOuterRadius(size / 4)
                .compile(getAppearance());
        group.addChild(cylinder);

        TransformGroup halfSphere1Group = new TransformGroup();
        Shape3D halfSphere1 = new HalfSphereGenerator()
                .setRadius(size / 4)
                .compile(getAppearance());
        halfSphere1Group.addChild(halfSphere1);
        Transform3D halfSphere1Transform = new Transform3D();
        halfSphere1Transform.setTranslation(new Vector3d(0, 0, size / 4));
        halfSphere1Group.setTransform(halfSphere1Transform);
        group.addChild(halfSphere1Group);

        TransformGroup halfSphere2Group = new TransformGroup();
        Shape3D halfSphere2 = new HalfSphereGenerator()
                .setRadius(size / 16)
                .compile(getAppearance());
        halfSphere2Group.addChild(halfSphere2);
        Transform3D halfSphere2Transform = new Transform3D();
        halfSphere2Transform.setScale(new Vector3d(1, 1, 0.5));
        halfSphere2Transform.setTranslation(new Vector3d(0, 0, size / 2 - size / 90));
        halfSphere2Group.setTransform(halfSphere2Transform);
        group.addChild(halfSphere2Group);

        TransformGroup antenaGroup = new TransformGroup();
        Shape3D antena = new FrustumGenerator()
                .setHeight(size / 4)
                .setInnerRadius(size / 64)
                .setOuterRadius(size / 32)
                .compile(getAppearance());
        antenaGroup.addChild(antena);
        Transform3D antenaTransform = new Transform3D();
        antenaTransform.rotX(Math.PI);
        antenaTransform.setTranslation(new Vector3d(0, 0, size / 2 + size / 8));
        antenaGroup.setTransform(antenaTransform);
        group.addChild(antenaGroup);

        TransformGroup antenaSphereGroup = new TransformGroup();
        Sphere antenaSphere = new Sphere((float) size / 32);
        antenaSphere.setAppearance(getAppearance());
        antenaSphereGroup.addChild(antenaSphere);
        Transform3D antenaSphereTransform = new Transform3D();
        antenaSphereTransform.setTranslation(new Vector3f(0, 0, 3 * (float)size / 4));
        antenaSphereGroup.setTransform(antenaSphereTransform);
        group.addChild(antenaSphereGroup);

        TransformGroup topEyesGroup = new TransformGroup();
        Shape3D topEyes = new CubeGenerator()
                .setSize(size / 4)
                .compile(getAppearance());
        topEyesGroup.addChild(topEyes);
        Transform3D topEyesTransform = new Transform3D();
        topEyesTransform.setTranslation(new Vector3f( 3 * (float) size / 16, 0, (float) size / 4));
        topEyesTransform.setScale(new Vector3d(0.75, 1, 0.02));
        topEyesGroup.setTransform(topEyesTransform);
        group.addChild(topEyesGroup);

        TransformGroup bottomEyesGroup = new TransformGroup();
        Shape3D bottomEyes = new CubeGenerator()
                .setSize(size / 4)
                .compile(getAppearance());
        bottomEyesGroup.addChild(bottomEyes);
        Transform3D bottomEyesTransform = new Transform3D();
        bottomEyesTransform.setTranslation(new Vector3f( 3 * (float) size / 16, 0, (float) size / 16));
        bottomEyesTransform.setScale(new Vector3d(0.75, 1, 0.02));
        bottomEyesGroup.setTransform(bottomEyesTransform);
        group.addChild(bottomEyesGroup);

        TransformGroup leftEyesGroup = new TransformGroup();
        Shape3D leftEyes = new CubeGenerator()
                .setSize(size / 4)
                .compile(getAppearance());
        leftEyesGroup.addChild(leftEyes);
        Transform3D leftEyesTransform = new Transform3D();
        leftEyesTransform.setTranslation(new Vector3f(
                3 * (float) size / 16,
                (float) size / 4 - 0.02f * (float) size / 4,
                5 * (float) size / 32));
        leftEyesTransform.setScale(new Vector3d(0.75, 0.02, 0.39));
        leftEyesGroup.setTransform(leftEyesTransform);
        group.addChild(leftEyesGroup);

        TransformGroup rightEyesGroup = new TransformGroup();
        Shape3D rightEyes = new CubeGenerator()
                .setSize(size / 4)
                .compile(getAppearance());
        rightEyesGroup.addChild(rightEyes);
        Transform3D rightEyesTransform = new Transform3D();
        rightEyesTransform.setTranslation(new Vector3f(
                3 * (float) size / 16,
                (float) -size / 4 + 0.02f * (float) size / 4,
                5 * (float) size / 32));
        rightEyesTransform.setScale(new Vector3d(0.75, 0.02, 0.39));
        rightEyesGroup.setTransform(rightEyesTransform);
        group.addChild(rightEyesGroup);

        Eye leftEye = new Eye(3 * (float) size / 16);
        Transform3D leftEyeTransform = new Transform3D();
        leftEyeTransform.setTranslation(new Vector3f(
                9 * (float) size / 32,
                (float) size / 8,
                5 * (float) size / 32
        ));
        leftEye.setTransform(leftEyeTransform);
        group.addChild(leftEye);

        Eye rightEye = new Eye(3 * (float) size / 16);
        Transform3D rightEyeTransform = new Transform3D();
        rightEyeTransform.setTranslation(new Vector3f(
                9 * (float) size / 32,
                (float) -size / 8,
                5 * (float) size / 32
        ));
        rightEye.setTransform(rightEyeTransform);
        group.addChild(rightEye);

        TransformGroup backEyesGroup = new TransformGroup();
        Shape3D backEyes = new CubeGenerator()
                .setSize(size / 4)
                .compile(leftEye.getPupilAppearance());
        backEyesGroup.addChild(backEyes);
        Transform3D backEyesTransform = new Transform3D();
        backEyesTransform.setTranslation(new Vector3f(
                (float) size / 4,
                0,
                5 * (float) size / 32));
        backEyesTransform.setScale(new Vector3d(0.02, 0.98, 0.39));
        backEyesGroup.setTransform(backEyesTransform);
        group.addChild(backEyesGroup);

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