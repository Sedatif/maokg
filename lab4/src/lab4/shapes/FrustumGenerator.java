package lab4.shapes;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

import javax.media.j3d.*;
import javax.vecmath.Point3d;

public class FrustumGenerator extends Generator {
    public static final double DEFAULT_INNER_RADIUS = 0.25d;
    public static final double DEFAULT_OUTER_RADIUS = 0.5d;
    public static final double DEFAULT_HEIGHT = 1d;

    private double innerRadius = DEFAULT_INNER_RADIUS;
    private double outerRadius = DEFAULT_OUTER_RADIUS;
    private double height = DEFAULT_HEIGHT;

    public double getInnerRadius() {
        return innerRadius;
    }

    public FrustumGenerator setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
        return this;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public FrustumGenerator setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
        return this;
    }

    public FrustumGenerator setMaxLineLength(double maxLineLength) {
        this.maxLineLength = maxLineLength;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public FrustumGenerator setHeight(double height) {
        this.height = height;
        return this;
    }

    @Override
    public Geometry compileGeometry() {
        int numberOfSegments = (int) Math.ceil(2*Math.PI*outerRadius / maxLineLength);
        if(numberOfSegments % 4 != 0) {
            numberOfSegments += 4 - numberOfSegments % 4;
        }

        double angleDelta = 2 * Math.PI / numberOfSegments;

        int totalNumberOfDots = (1 + numberOfSegments) * 2;

        Point3d[] coords = new Point3d[totalNumberOfDots];

        for(int segment = 0, i = 0; segment <= numberOfSegments; segment++) {
            double currentAngle = angleDelta * segment;
            coords[i++] = new Point3d(
                    outerRadius*Math.cos(currentAngle),
                    outerRadius*Math.sin(currentAngle),
                    height / 2
            );
            coords[i++] = new Point3d(
                    innerRadius*Math.cos(currentAngle),
                    innerRadius*Math.sin(currentAngle),
                    -height / 2
            );
        }

        TriangleStripArray triangleStripArray = new TriangleStripArray(totalNumberOfDots, TriangleStripArray.COORDINATES | TriangleStripArray.NORMALS, new int[]{ totalNumberOfDots });
        triangleStripArray.setCoordinates(0, coords);

        GeometryInfo geometryInfo = new GeometryInfo(triangleStripArray);
        NormalGenerator normalGenerator = new NormalGenerator();
        normalGenerator.generateNormals(geometryInfo);
        return geometryInfo.getGeometryArray();
    }
}