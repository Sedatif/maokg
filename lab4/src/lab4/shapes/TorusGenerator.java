package lab4.shapes;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class TorusGenerator extends Generator {
    public static final double DEFAULT_INNER_RADIUS = 0.25d;
    public static final double DEFAULT_OUTER_RADIUS = 0.5d;
    public static final Point3d DEFAULT_CENTER = new Point3d(0d, 0d, 0d);
    public static final double DEFAULT_MAX_LINE_LENGTH = 0.01d;

    private double innerRadius = DEFAULT_INNER_RADIUS;
    private double outerRadius = DEFAULT_OUTER_RADIUS;
    private Point3d center = DEFAULT_CENTER;
    private double maxLineLength = DEFAULT_MAX_LINE_LENGTH;

    public double getInnerRadius() {
        return innerRadius;
    }

    public TorusGenerator setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
        return this;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public TorusGenerator setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
        return this;
    }

    public Point3d getCenter() {
        return center;
    }

    public TorusGenerator setCenter(Point3d center) {
        this.center = center;
        return this;
    }

    public double getMaxLineLength() {
        return maxLineLength;
    }

    public TorusGenerator setMaxLineLength(double maxLineLength) {
        this.maxLineLength = maxLineLength;
        return this;
    }

    @Override
    public Geometry compileGeometry() {
        int numberOfSegments = (int) Math.ceil(2*Math.PI*outerRadius / maxLineLength);
        if(numberOfSegments % 4 != 0) {
            numberOfSegments += 4 - numberOfSegments % 4;
        }

        double segmentRadius = (outerRadius - innerRadius) / 2d;

        int numberOfPolygonsInSegment = (int) Math.ceil(2*Math.PI*segmentRadius / maxLineLength);
        if(numberOfPolygonsInSegment % 4 != 0) {
            numberOfPolygonsInSegment += 4 - numberOfPolygonsInSegment % 4;
        }

        double angleDelta = 2 * Math.PI / numberOfSegments;
        double verticalAngleDelta = 2 * Math.PI / numberOfPolygonsInSegment;

        int dotsPerSegment = (numberOfPolygonsInSegment + 1) * 2;
        int totalNumberOfDots = dotsPerSegment * numberOfSegments;

        int[] stripCounts = new int[numberOfSegments];
        for (int i = 0; i < stripCounts.length; i++) stripCounts[i] = dotsPerSegment;

        Point3d[] coords = new Point3d[totalNumberOfDots];
        Vector3f[] normals = new Vector3f[totalNumberOfDots];

        for(int segment = 0, i = 0; segment < numberOfSegments; segment++){
            double currentAngle = angleDelta * segment;
            Point3d currentCenter = new Point3d(
                    center.x + (innerRadius + segmentRadius)*Math.cos(currentAngle),
                    center.y + (innerRadius + segmentRadius)*Math.sin(currentAngle),
                    center.z
            );
            double nextAngle = angleDelta * (segment + 1);
            Point3d nextCenter = new Point3d(
                    center.x + (innerRadius + segmentRadius)*Math.cos(nextAngle),
                    center.y + (innerRadius + segmentRadius)*Math.sin(nextAngle),
                    center.z
            );
            for(int level = 0; level <= numberOfPolygonsInSegment; level++){
                double currentVerticalAngle = verticalAngleDelta * level;
                double z = center.z + segmentRadius * Math.sin(currentVerticalAngle);
                Vector3d normal = new Vector3d();
                coords[i] = new Point3d(
                        center.x + (innerRadius + segmentRadius - segmentRadius*Math.cos(currentVerticalAngle))*Math.cos(currentAngle),
                        center.y + (innerRadius + segmentRadius - segmentRadius*Math.cos(currentVerticalAngle))*Math.sin(currentAngle),
                        z
                );
                normal.sub(coords[i], currentCenter);
                normal.normalize();
                normals[i++] = new Vector3f(normal);
                coords[i] = new Point3d(
                        center.x + (innerRadius + segmentRadius - segmentRadius*Math.cos(currentVerticalAngle))*Math.cos(nextAngle),
                        center.y + (innerRadius + segmentRadius - segmentRadius*Math.cos(currentVerticalAngle))*Math.sin(nextAngle),
                        z
                );
                normal.sub(coords[i], nextCenter);
                normal.normalize();
                normals[i++] = new Vector3f(normal);
            }
        }

        TriangleStripArray triangleStripArray = new TriangleStripArray(totalNumberOfDots, TriangleStripArray.COORDINATES | TriangleStripArray.NORMALS, stripCounts);
        triangleStripArray.setCoordinates(0, coords);
        triangleStripArray.setNormals(0, normals);
        return triangleStripArray;
    }
}
