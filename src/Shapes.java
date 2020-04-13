import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Shapes {

    public static List<Renderer.Triangle3D> getTriangles() {
        List<Renderer.Triangle3D> triangles = new ArrayList<>();


        for (var y: List.of(0, 300, 600, 900)) {
            for (var z : List.of(3800, 4400, 4000, 3600, 3200, 2800, 2400, 2000, 1600, 1200, 1000, 800, 600)) {
                for (var x : List.of(-1100, -900, -700, -500, -300, -100, 100, 300, 500, 700, 900, 1100, 1300, 1500, 1700, 1900, 2100, 2300)) {
                    triangles.addAll(buildCube(x, y, z));
                }
            }
        }

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
                        Color.DARKRED),
                // right
                new Renderer.Triangle3D(
                        rightTopFront,
                        rightBottomFront,
                        rightBottomBack,
                        Color.LIGHTGOLDENRODYELLOW),
                new Renderer.Triangle3D(
                        rightTopFront,
                        rightTopBack,
                        rightBottomBack,
                        Color.LIGHTGOLDENRODYELLOW),
                // bottom
                new Renderer.Triangle3D(
                        leftBottomFront,
                        rightBottomFront,
                        leftBottomBack,
                        Color.LIGHTSKYBLUE),
                new Renderer.Triangle3D(
                        rightBottomFront,
                        leftBottomBack,
                        rightBottomBack,
                        Color.LIGHTSKYBLUE)
        );
    }
}
