package lab4.shapes;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class CircleGenerator extends Generator {
    public static final double DEFAULT_RADIUS = 0.5d;

    private double radius = DEFAULT_RADIUS;

    public CircleGenerator setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public double getRadius() {
        return radius;
    }

    public CircleGenerator setMaxLineLength(double maxLineLength) {
        this.maxLineLength = maxLineLength;
        return this;
    }

    @Override
    public Geometry compileGeometry() {
        int numberOfSegments = (int) Math.ceil(2*Math.PI*radius / maxLineLength);
        if(numberOfSegments % 4 != 0) {
            numberOfSegments += 4 - numberOfSegments % 4;
        }

        double angleDelta = 2 * Math.PI / numberOfSegments;

        int totalNumberOfDots = 2 + numberOfSegments;

        Point3d[] coords = new Point3d[totalNumberOfDots];
        Vector3f[] normals = new Vector3f[totalNumberOfDots];

        coords[0] = new Point3d();
        normals[0] = new Vector3f(0, 0, 1);
        for(int i = 1; i < totalNumberOfDots; i++) {
            double currentAngle = (i - 1) * angleDelta;
            coords[i] = new Point3d(
                    radius*Math.cos(currentAngle),
                    radius*Math.sin(currentAngle),
                    0
            );
            normals[i] = new Vector3f(0, 0, 1);
        }

        TriangleFanArray triangleFanArray = new TriangleFanArray(totalNumberOfDots, TriangleFanArray.COORDINATES | TriangleStripArray.NORMALS, new int[]{ totalNumberOfDots });
        triangleFanArray.setCoordinates(0, coords);
        triangleFanArray.setNormals(0, normals);
        return triangleFanArray;
    }
}
