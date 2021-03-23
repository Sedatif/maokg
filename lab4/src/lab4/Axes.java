package lab4;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class Axes {

    public static Shape3D getXAxis() {
        LineArray xAxisGeometry = new LineArray(2, LineArray.COORDINATES);
        xAxisGeometry.setCoordinate(0, new Point3d(0d, 0d, 0d));
        xAxisGeometry.setCoordinate(1, new Point3d(1d, 0d, 0d));

        Appearance xAxisAppearance = new Appearance();
        xAxisAppearance.setColoringAttributes(new ColoringAttributes(new Color3f(1f, 0f, 0f), ColoringAttributes.NICEST));

        return new Shape3D(xAxisGeometry, xAxisAppearance);
    }

    public static Shape3D getYAxis() {
        LineArray yAxisGeometry = new LineArray(2, LineArray.COORDINATES);
        yAxisGeometry.setCoordinate(0, new Point3d(0d, 0d, 0d));
        yAxisGeometry.setCoordinate(1, new Point3d(0d, 1d, 0d));

        Appearance yAxisAppearance = new Appearance();
        yAxisAppearance.setColoringAttributes(new ColoringAttributes(new Color3f(0f, 1f, 0f), ColoringAttributes.NICEST));

        return new Shape3D(yAxisGeometry, yAxisAppearance);
    }

    public static Shape3D getZAxis() {
        LineArray zAxisGeometry = new LineArray(2, LineArray.COORDINATES);
        zAxisGeometry.setCoordinate(0, new Point3d(0d, 0d, 0d));
        zAxisGeometry.setCoordinate(1, new Point3d(0d, 0d, 1d));

        Appearance zAxisAppearance = new Appearance();
        zAxisAppearance.setColoringAttributes(new ColoringAttributes(new Color3f(0f, 0f, 1f), ColoringAttributes.NICEST));

        return new Shape3D(zAxisGeometry, zAxisAppearance);
    }

    public static BranchGroup getAxes() {
        BranchGroup axes = new BranchGroup();
        axes.addChild(getXAxis());
        axes.addChild(getYAxis());
        axes.addChild(getZAxis());
        return axes;
    }

}
