package lab4.shapes;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class HalfSphereGenerator extends Generator {
    public static final double DEFAULT_RADIUS = 0.5d;

    private double radius = DEFAULT_RADIUS;

    public HalfSphereGenerator setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public HalfSphereGenerator setMaxLineLength(double maxLineLength) {
        this.maxLineLength = maxLineLength;
        return this;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Geometry compileGeometry() {
        int numberOfSegments = (int) Math.ceil(2*Math.PI*radius / maxLineLength);
        if(numberOfSegments % 4 != 0) {
            numberOfSegments += 4 - numberOfSegments % 4;
        }
        int numberOfVerticalLevels = numberOfSegments / 4;

        double angleDelta = 2 * Math.PI / numberOfSegments;

        int dotsPerSegment = 1 + 2 * numberOfVerticalLevels;
        int totalNumberOfDots = numberOfSegments * dotsPerSegment;

        int[] stripCounts = new int[numberOfSegments];
        for (int i = 0; i < stripCounts.length; i++) stripCounts[i] = dotsPerSegment;

        Point3d[] coords = new Point3d[totalNumberOfDots];
        Vector3f[] normals = new Vector3f[totalNumberOfDots];

        for(int segment = 0, i = 0; segment < numberOfSegments; segment++) {
            coords[i] = new Point3d(0, 0, radius);
            normals[i++] = new Vector3f(coords[i-1]);
            normals[i-1].normalize();
            double currentAngle = segment * angleDelta;
            double nextAngle = (segment + 1)*angleDelta;
            for(int level = 1; level <= numberOfVerticalLevels; level++){
                double angleFromVertical = level * angleDelta;
                double r = radius * Math.sin(angleFromVertical);
                double z = radius*Math.cos(angleFromVertical);
                coords[i] = new Point3d(
                        r*Math.cos(currentAngle),
                        r*Math.sin(currentAngle),
                        z
                );
                normals[i++] = new Vector3f(coords[i-1]);
                normals[i-1].normalize();
                coords[i] = new Point3d(
                        r*Math.cos(nextAngle),
                        r*Math.sin(nextAngle),
                        z
                );
                normals[i++] = new Vector3f(coords[i-1]);
                normals[i-1].normalize();
            }
        }

        TriangleStripArray triangleStripArray = new TriangleStripArray(totalNumberOfDots, TriangleStripArray.COORDINATES | TriangleStripArray.NORMALS, stripCounts);
        triangleStripArray.setCoordinates(0, coords);
        triangleStripArray.setNormals(0, normals);
        return triangleStripArray;
    }
}
