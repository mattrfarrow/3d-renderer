import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Shapes {

    public static List<Renderer.Triangle3D> getTriangles() {
        List<Renderer.Triangle3D> triangles = new ArrayList<>();

        triangles.addAll(buildCube(100, 300, 2000));
        triangles.addAll(buildCube(300, 300, 2000));
        triangles.addAll(buildCube(500, 300, 2000));
        triangles.addAll(buildCube(700, 300, 2000));
        triangles.addAll(buildCube(900, 300, 2000));
        triangles.addAll(buildCube(1100, 300, 2000));

        triangles.addAll(buildCube(100, 300, 1600));
        triangles.addAll(buildCube(300, 300, 1600));
        triangles.addAll(buildCube(500, 300, 1600));
        triangles.addAll(buildCube(700, 300, 1600));
        triangles.addAll(buildCube(900, 300, 1600));
        triangles.addAll(buildCube(1100, 300, 1600));

        triangles.addAll(buildCube(100, 300, 1200));
        triangles.addAll(buildCube(300, 300, 1200));
        triangles.addAll(buildCube(500, 300, 1200));
        triangles.addAll(buildCube(700, 300, 1200));
        triangles.addAll(buildCube(900, 300, 1200));
        triangles.addAll(buildCube(1100, 300, 1200));

        triangles.addAll(buildCube(100, 300, 1000));
        triangles.addAll(buildCube(300, 300, 1000));
        triangles.addAll(buildCube(500, 300, 1000));
        triangles.addAll(buildCube(700, 300, 1000));
        triangles.addAll(buildCube(900, 300, 1000));
        triangles.addAll(buildCube(1100, 300, 1000));

        triangles.addAll(buildCube(100, 300, 800));
        triangles.addAll(buildCube(300, 300, 800));
        triangles.addAll(buildCube(500, 300, 800));
        triangles.addAll(buildCube(700, 300, 800));
        triangles.addAll(buildCube(900, 300, 800));
        triangles.addAll(buildCube(1100, 300, 800));

        triangles.addAll(buildCube(100, 300, 600));
        triangles.addAll(buildCube(300, 300, 600));
        triangles.addAll(buildCube(500, 300, 600));
        triangles.addAll(buildCube(700, 300, 600));
        triangles.addAll(buildCube(900, 300, 600));
        triangles.addAll(buildCube(1100, 300, 600));

        return triangles;
    }

    private static Collection<Renderer.Triangle3D> buildCube(int x, int y, int z) {

        var l = x;
        var r = x+75;

        var t= y;
        var b = y+75;

        var fr = z;
        var ba = z+75;

        var leftTopFront = new Renderer.Point3D(l,t,fr);
        var rightTopFront = new Renderer.Point3D(r,t,fr);
        var leftBottomFront = new Renderer.Point3D(l,b,fr);
        var rightBottomFront = new Renderer.Point3D(r,b,fr);
        var leftTopBack = new Renderer.Point3D(l,t,ba);
        var rightTopBack = new Renderer.Point3D(r,t,ba);
        var leftBottomBack = new Renderer.Point3D(l,b,ba);
        var rightBottomBack = new Renderer.Point3D(r,b,ba);

        return List.of(
                //front
                new Renderer.Triangle3D(
                        leftTopFront,
                        rightTopFront,
                        leftBottomFront,
                        Color.RED),
                new Renderer.Triangle3D(
                        rightTopFront,
                        rightBottomFront,
                        leftBottomFront,
                        Color.RED),
                // left
                new Renderer.Triangle3D(
                        leftTopFront,
                        leftBottomFront,
                        leftBottomBack,
                        Color.GREEN),
                new Renderer.Triangle3D(
                        leftTopFront,
                        leftTopBack,
                        leftBottomBack,
                        Color.GREEN),
                //top
                new Renderer.Triangle3D(
                        leftTopFront,
                        rightTopFront,
                        leftTopBack,
                        Color.DARKRED),
                new Renderer.Triangle3D(
                        rightTopFront,
                        leftTopBack,
                        rightTopBack,
                        Color.DARKRED)
        );
    }
}
