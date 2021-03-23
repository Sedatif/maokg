package lab4;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

public class VecmathHelper {
    public static Vector3d mul(Matrix3d m, Vector3d v) {
        return new Vector3d(
                m.m00*v.x+m.m01*v.y+m.m02*v.z,
                m.m10*v.x+m.m11*v.y+m.m12*v.z,
                m.m20*v.x+m.m21*v.y+m.m22*v.z
        );
    }

    public static Vector3d rotateVector(Vector3d vector, Vector3d axis, double angle) {
        final double c = Math.cos(angle);
        final double s = Math.sin(angle);
        final double x = axis.x;
        final double y = axis.y;
        final double z = axis.z;

        Matrix3d rotationMatrix = new Matrix3d(
                c+(1-c)*x*x, (1-c)*x*y-z*s, (1-c)*x*z+y*s,
                (1-c)*y*x+z*s, c+(1-c)*y*y, (1-c)*y*z-x*s,
                (1-c)*z*x-y*s, (1-c)*z*y+x*s, c+(1-c)*z*z
        );

        vector = VecmathHelper.mul(rotationMatrix, vector);
        vector.normalize();
        return vector;
    }
}
