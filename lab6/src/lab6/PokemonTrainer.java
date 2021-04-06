package lab6;

import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import javax.vecmath.Point3d;

import javax.vecmath.*;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class PokemonTrainer extends JFrame{
	private static final long serialVersionUID = 1L;
	public Canvas3D myCanvas3D;

    public PokemonTrainer(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);

        simpUniv.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(simpUniv);
        addLight(simpUniv);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Pokemon Trainer");
        setSize(1400,800);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }

    public void createSceneGraph(SimpleUniverse su){
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        String name;
        BranchGroup trainerBranchGroup = new BranchGroup();
        addImageBackground("res/background.jpg", trainerBranchGroup);
        Background trainerBackground =  new Background();

        Scene trainerScene = null;
        try{
            trainerScene = f.load("res/pokemon_trainer.obj");
        }
        catch (Exception e){
            System.out.println("File loading failed:" + e);
        }
        Hashtable roachNamedObjects = trainerScene.getNamedObjects();
        Enumeration enumer = roachNamedObjects.keys();
        while (enumer.hasMoreElements()){
            name = (String) enumer.nextElement();
            System.out.println("Name: " + name);
        }

        Transform3D startTransformation = new Transform3D();
        startTransformation.setScale(1.0/3);
        Transform3D combinedStartTransformation = new Transform3D();
        combinedStartTransformation.mul(startTransformation);

        TransformGroup wholeCharacterAnimation = new TransformGroup(combinedStartTransformation);

        int movesCount = 100;
        int movesDuration = 1000;
        int startTime = 0;

        Appearance headApp = new Appearance();
        setToMyDefaultAppearance(headApp, new Color3f(0.5f, 0.0f, 0.0f));
        Shape3D head = (Shape3D) roachNamedObjects.get("polygon1");
        head.setAppearance(headApp);
        wholeCharacterAnimation.addChild(head.cloneTree());

        Appearance ballApp1 = new Appearance();
        setToMyDefaultAppearance(ballApp1, new Color3f(0.9f, 0.0f, 0.0f));

        Alpha ballAlpha1 = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);

        Shape3D ball1 = (Shape3D) roachNamedObjects.get("ball1");
        ball1.setAppearance(ballApp1);
        TransformGroup ballTG1 = new TransformGroup();
        ballTG1.addChild(ball1.cloneTree());

        Transform3D ballRotAxis1 = new Transform3D();
        ballRotAxis1.rotZ(Math.PI/2);

        RotationInterpolator ballrot1 = new RotationInterpolator(ballAlpha1, ballTG1, ballRotAxis1, 0.0f, (float)-Math.PI*2);
        ballrot1.setSchedulingBounds(bs);
        ballTG1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ballTG1.addChild(ballrot1);


        Appearance ballApp = new Appearance();
        setToMyDefaultAppearance(ballApp, new Color3f(0.9f, 0.0f, 0.0f));

        Alpha ballAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE, startTime, 0, movesDuration,0,0,0,0,0);

        Shape3D ball2 = (Shape3D) roachNamedObjects.get("ball2");
        ball2.setAppearance(ballApp);
        TransformGroup ballTG = new TransformGroup();
        ballTG.addChild(ball2.cloneTree());

        Transform3D ballRotAxis = new Transform3D();
        ballRotAxis.rotZ(Math.PI/2);

        RotationInterpolator ballrot = new RotationInterpolator(ballAlpha, ballTG, ballRotAxis, 0.0f, (float)Math.PI*2);
        ballrot.setSchedulingBounds(bs);
        ballTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ballTG.addChild(ballrot);

        Appearance bodyApp = new Appearance();
        setToMyDefaultAppearance(bodyApp, new Color3f(0.0f, 0.0f, 0.5f));

        TransformGroup sceneGroup = new TransformGroup();
        sceneGroup.addChild(ballTG);
        sceneGroup.addChild(ballTG1);

        TransformGroup tgBody = new TransformGroup();
        Shape3D nShape = (Shape3D) roachNamedObjects.get("polygon0");
        nShape.setAppearance(bodyApp);
        tgBody.addChild(nShape.cloneTree());
        sceneGroup.addChild(tgBody.cloneTree());

        Appearance faceApp = new Appearance();
        setToMyDefaultAppearance(faceApp, new Color3f(0.3f, 0.3f, 0.2f));

        TransformGroup tgFace1 = new TransformGroup();
        Shape3D face1Shape = (Shape3D) roachNamedObjects.get("polygon3");
        face1Shape.setAppearance(faceApp);
        tgFace1.addChild(face1Shape.cloneTree());
        sceneGroup.addChild(tgFace1.cloneTree());

        TransformGroup tgFace2 = new TransformGroup();
        Shape3D face2Shape = (Shape3D) roachNamedObjects.get("polygon4");
        face2Shape.setAppearance(faceApp);
        tgFace2.addChild(face2Shape.cloneTree());
        sceneGroup.addChild(tgFace2.cloneTree());

        TransformGroup tgFace = new TransformGroup();
        Shape3D faceShape = (Shape3D) roachNamedObjects.get("polygon2");
        faceShape.setAppearance(faceApp);
        tgFace.addChild(faceShape.cloneTree());
        sceneGroup.addChild(tgFace.cloneTree());

        Appearance bagApp = new Appearance();
        setToMyDefaultAppearance(bagApp, new Color3f(0.0f, 0.5f, 0.0f));

        TransformGroup tgBag1 = new TransformGroup();
        Shape3D bagShape1 = (Shape3D) roachNamedObjects.get("polygon6");
        bagShape1.setAppearance(bagApp);
        tgBag1.addChild(bagShape1.cloneTree());
        sceneGroup.addChild(tgBag1.cloneTree());

        TransformGroup tgBag = new TransformGroup();
        Shape3D bagShape = (Shape3D) roachNamedObjects.get("polygon7");
        bagShape.setAppearance(bagApp);
        tgBag.addChild(bagShape.cloneTree());
        sceneGroup.addChild(tgBag.cloneTree());

        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(new Vector3f(0.0f,-0.2f,-0.5f));
        TransformGroup transformGroup =
                new TransformGroup();
        transformGroup.setTransform(transform3D);

        transformGroup.addChild(wholeCharacterAnimation);

        TransformGroup bformGroup = new TransformGroup();
        bformGroup.setCapability(
                TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D rotAsix = new Transform3D();
        rotAsix.rotY(Math.PI/2);
        Transform3D b1 = new Transform3D();
        b1.rotZ(-Math.PI/6);
        b1.mul(rotAsix);
        Transform3D b12 = new Transform3D();
        b12.rotY(Math.PI/4);
        b12.mul(b1);

        RotationInterpolator interpolator =
                new RotationInterpolator(new Alpha(10,5000),bformGroup,b12,0.0f, (float)Math.PI*2);

        interpolator.setSchedulingBounds(new BoundingSphere(
                new Point3d(0.0,0.0,0.0),1.0));

        bformGroup.addChild(interpolator);
        bformGroup.addChild(transformGroup);

        trainerBranchGroup.addChild(bformGroup);
        wholeCharacterAnimation.addChild(sceneGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(120.0,250.0,100.0),Double.MAX_VALUE);
        trainerBackground.setApplicationBounds(bounds);
        trainerBranchGroup.addChild(trainerBackground);

        trainerBranchGroup.compile();
        su.addBranchGraph(trainerBranchGroup);
    }

    private void addImageBackground(String imagePath, BranchGroup root) {
        TextureLoader t = new TextureLoader(imagePath, myCanvas3D);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }

    public void addLight(SimpleUniverse su){
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
        Vector3f lightDir1 = new Vector3f(-1.0f,0.0f,-0.5f);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        su.addBranchGraph(bgLight);
    }

    public static void setToMyDefaultAppearance(Appearance app, Color3f col) {
        app.setMaterial(new Material(col, col, col, col, 150.0f));
    }

    public static void main(String[] args) {
        PokemonTrainer trainer = new PokemonTrainer();
    }

}