package lab4.shapes;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;

abstract class Generator {
    public static final double DEFAULT_MAX_LINE_LENGTH = 0.01d;

    protected double maxLineLength = DEFAULT_MAX_LINE_LENGTH;

    public double getMaxLineLength() {
        return maxLineLength;
    }

    abstract Geometry compileGeometry();

    public Shape3D compile(Appearance appearance) {
        Shape3D shape = new Shape3D();
        shape.setGeometry(compileGeometry());
        shape.setAppearance(appearance);
        return shape;
    }
}
