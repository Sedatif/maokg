import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.Background;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;

public class Boat extends JFrame {
  private static final long serialVersionUID = 1L;
  private static Canvas3D canvas;
  private static SimpleUniverse universe;
  private static BranchGroup root;

  private static TransformGroup boat;

  public Boat() throws IOException {
    configureWindow();
    configureCanvas();
    configureUniverse();
    root = new BranchGroup();
    addImageBackground("res/puddle.jpg");
    addDirectionalLightToUniverse();
    addAmbientLightToUniverse();
    boat = getBoatGroup();
    root.addChild(boat);
    root.compile();
    universe.addBranchGraph(root);
  }

  private void configureWindow() {
    setTitle("Lab 5");
    setSize(1400,800);
    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void configureCanvas() {
    canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    canvas.setDoubleBufferEnable(true);
    canvas.setFocusable(true);
    getContentPane().add(canvas, BorderLayout.CENTER);
  }

  private void configureUniverse() {
    universe = new SimpleUniverse(canvas);
    universe.getViewingPlatform().setNominalViewingTransform();
  }

  private void addImageBackground(String imagePath) {
      TextureLoader t = new TextureLoader(imagePath, canvas);
      Background background = new Background(t.getScaledImage(1400, 800));
      background.setImageScaleMode(Background.SCALE_FIT_ALL);
      BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
      background.setApplicationBounds(bounds);
      root.addChild(background);
  }

  private void addDirectionalLightToUniverse() {
    BoundingSphere bounds = new BoundingSphere();
    bounds.setRadius(1000);
    DirectionalLight light = new DirectionalLight(new Color3f(1, 1, 1), new Vector3f(-1, -1, -1));
    light.setInfluencingBounds(bounds);
    root.addChild(light);
  }

  private void addAmbientLightToUniverse() {
    AmbientLight light = new AmbientLight(new Color3f(1, 1, 1));
    light.setInfluencingBounds(new BoundingSphere());
    root.addChild(light);
  }

  private void addAppearance(Shape3D shape, String path) {
    Appearance appearance = new Appearance();
    appearance.setTexture(getTexture(path));
    TextureAttributes attrs = new TextureAttributes();
    attrs.setTextureMode(TextureAttributes.COMBINE);
    appearance.setTextureAttributes(attrs);
    shape.setAppearance(appearance);
  }

  private Texture getTexture(String path) {
    TextureLoader textureLoader = new TextureLoader(path, "LUMINANCE", canvas);
    Texture texture = textureLoader.getTexture();
    texture.setBoundaryModeS(Texture.WRAP);
    texture.setBoundaryModeT(Texture.WRAP);
    texture.setBoundaryColor(new Color4f(1.0f, 1.0f, 1.0f, 1.0f));
    return texture;
  }

  private TransformGroup getBoatGroup() throws IOException {
    Shape3D shape = getModelShape3D("default", "res/Boat2.obj");

    addAppearance(shape, "res/paper2.jpg");
    Transform3D transform3D = new Transform3D();
    transform3D.setScale(new Vector3d(0.2, 0.2, 0.2));
    Transform3D rotationY = new Transform3D();
    rotationY.rotY(Math.PI / 2);
    transform3D.mul(rotationY);
    TransformGroup group = getModelGroup(shape);
    group.setTransform(transform3D);
    return group;
  }

  private TransformGroup getModelGroup(Shape3D shape) {
    TransformGroup group = new TransformGroup();
    group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    group.addChild(shape);
    return group;
  }

  private Shape3D getModelShape3D(String name, String path) throws IOException {
    Scene scene = getSceneFromFile(path);
    @SuppressWarnings("unchecked")
	Map<String, Shape3D> map = scene.getNamedObjects();
    printModelElementsList(map);
    Shape3D shape = map.get(name);
    scene.getSceneGroup().removeChild(shape);
    return shape;
  }

  private Scene getSceneFromFile(String path) throws IOException {
    ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
    file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
    return file.load(new FileReader(path));
  }

  private void printModelElementsList(Map<String, Shape3D> map) {
    for (String name : map.keySet()) {
      System.out.println("Name: " + name);
    }
  }

  public static void main(String[] args) {
    try {
      Boat window = new Boat();
      BoatAnimation boatMovement = new BoatAnimation(boat);
      canvas.addKeyListener(boatMovement);
      window.setVisible(true);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}