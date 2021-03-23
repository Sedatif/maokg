package lab4;

import javax.media.j3d.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import lab4.robot.Robot;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements KeyListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double movingDelta = 0.1d;
    private double angleDelta = Math.PI / 100;

    private SimpleUniverse universe;
    private Point3d eyePosition = new Point3d(2d, 0d, 1d);
    private Vector3d sightDirection = new Vector3d(-1d, 0d, 0d);
    private Vector3d upDirection = new Vector3d(0d, 0d, 1d);

    public Main() {
        super("Lab 4");
        setLayout(new BorderLayout());
        setSize(1500, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.addKeyListener(this);

        universe = new SimpleUniverse(canvas);

        universe.addBranchGraph(createSceneGraph());
        updateViewPosition();

        add(BorderLayout.CENTER, canvas);

        setVisible(true);
    }

    public BranchGroup createSceneGraph() {
        BranchGroup group = new BranchGroup();
        group.addChild(Axes.getAxes());

        group.addChild(new Robot(2));

        Color3f lightColor = new Color3f(1, 1, 1);
        BoundingSphere lightArea = new BoundingSphere(new Point3d(0, 0, 0), 100);

        Vector3f lightDirection1 = new Vector3f(-1, 1, -1);
        DirectionalLight light1 = new DirectionalLight(lightColor, lightDirection1);
        light1.setInfluencingBounds(lightArea);
        group.addChild(light1);

        Vector3f lightDirection2 = new Vector3f(-1, 0, 0);
        DirectionalLight light2 = new DirectionalLight(lightColor, lightDirection2);
        light2.setInfluencingBounds(lightArea);
        group.addChild(light2);

        return group;
    }

    private void updateViewPosition() {
        Transform3D viewingTransform = new Transform3D();

        Point3d lookAtPosition = new Point3d(
                eyePosition.x + sightDirection.x,
                eyePosition.y + sightDirection.y,
                eyePosition.z + sightDirection.z
        );

        viewingTransform.lookAt(eyePosition, lookAtPosition, upDirection);
        viewingTransform.invert();

        universe.getViewingPlatform().getViewPlatformTransform().setTransform(viewingTransform);
    }

    private void updateEyePosition(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP: {
                moveViewBySightDirection(movingDelta);
            } break;
            case KeyEvent.VK_DOWN: {
                moveViewBySightDirection(-movingDelta);
            } break;
            case KeyEvent.VK_LEFT: {
                moveViewPerpendicularToSightDirection(-movingDelta);
            } break;
            case KeyEvent.VK_RIGHT: {
                moveViewPerpendicularToSightDirection(movingDelta);
            } break;
            case KeyEvent.VK_Z: {
                moveViewByUpDirection(movingDelta);
            } break;
            case KeyEvent.VK_X: {
                moveViewByUpDirection(-movingDelta);
            } break;
            case KeyEvent.VK_W: {
                rotateViewAroundHorizon(-angleDelta);
            } break;
            case KeyEvent.VK_S: {
                rotateViewAroundHorizon(angleDelta);
            } break;
            case KeyEvent.VK_D: {
                rotateViewAroundUpDirection(-angleDelta);
            } break;
            case KeyEvent.VK_A: {
                rotateViewAroundUpDirection(angleDelta);
            } break;
            case KeyEvent.VK_Q: {
                rotateViewAroundSightDirection(-angleDelta);
            } break;
            case KeyEvent.VK_E: {
                rotateViewAroundSightDirection(angleDelta);
            } break;
        }
        updateViewPosition();
    }

    public void moveViewBySightDirection(double delta) {
        Vector3d deltaVector = new Vector3d(sightDirection);
        deltaVector.scale(delta);
        eyePosition.add(deltaVector);
    }

    public void moveViewByUpDirection(double delta) {
        Vector3d deltaVector = new Vector3d(upDirection);
        deltaVector.scale(delta);
        eyePosition.add(deltaVector);
    }

    public void moveViewPerpendicularToSightDirection(double delta) {
        Vector3d n = new Vector3d();
        n.cross(sightDirection, upDirection);
        n.normalize();
        n.scale(delta);
        eyePosition.add(n);
    }

    public void rotateViewAroundSightDirection(double angle) {
        upDirection = VecmathHelper.rotateVector(upDirection, sightDirection, angle);
    }

    public void rotateViewAroundUpDirection(double angle) {
        sightDirection = VecmathHelper.rotateVector(sightDirection, upDirection, angle);
    }

    public void rotateViewAroundHorizon(double angle) {
        Vector3d horizon = new Vector3d();
        horizon.cross(sightDirection, upDirection);

        sightDirection = VecmathHelper.rotateVector(sightDirection, horizon, angle);
        upDirection = VecmathHelper.rotateVector(upDirection, horizon, angle);
        System.out.println();
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        updateEyePosition(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
