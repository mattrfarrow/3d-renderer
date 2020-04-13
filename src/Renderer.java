import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Renderer extends Application {

    private long prevNanos = 0;

    record Point3D(double x, double y, double z){}
    record Point2D(double x, double y){}
    record Triangle3D(Point3D a, Point3D b, Point3D c, Color color){}
    record Triangle2D(Point2D a, Point2D b, Point2D c, Color color){}

    private Point3D viewPoint = new Point3D(100, 100, 0);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private static final int height = 800;
    private static final int width = 800;

    private static final double pixelsPerXDegree = width / 70.0;
    private static final double pixelsPerYDegree = height / 70.0;

    @Override
    public void start(Stage stage){
        stage.setTitle("creating canvas");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext graphics = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(20);
        timeline.setAutoReverse(false);
        timeline.setRate(1.0);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (prevNanos == 0) {
                    prevNanos = now;
                    return;
                }
                long deltaNanos = now - prevNanos;
                prevNanos = now;
                double deltaSec  = deltaNanos / 1.0e9;

                viewPoint = new Point3D(viewPoint.x + (deltaSec * 10), viewPoint.y - (deltaSec * 20), viewPoint.z + (deltaSec * 30) );
                System.out.println(viewPoint);

                graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                System.out.println(now / 100000000);
                for(Triangle3D triangle : Shapes.getTriangles()) {
                    drawTriangle(triangle, graphics, viewPoint);
                }
            }
        };

        Group group = new Group(canvas);
        Scene scene = new Scene(group, width, height);
        stage.setScene(scene);
        stage.show();

        timer.start();
        timeline.play();
    }

    private static void drawTriangle(Triangle3D t, GraphicsContext graphics_context, Point3D viewPoint) {
        var t2d = triangle3Dto2D(t, viewPoint);
        //System.out.println(t2d);

        graphics_context.setFill(t.color);
        graphics_context.fillPolygon(
                new double[]{t2d.a.x, t2d.b.x, t2d.c.x},
                new double[]{t2d.a.y, t2d.b.y, t2d.c.y},
                3
        );
    }

    private static Triangle2D triangle3Dto2D(Triangle3D t, Point3D viewPoint) {
        return new Triangle2D(point3Dto2D(t.a, viewPoint), point3Dto2D(t.b, viewPoint), point3Dto2D(t.c, viewPoint), t.color);
    }

    private static Point2D point3Dto2D(Point3D p, Point3D viewPoint) {
        var tanXAngle = (p.x - viewPoint.x) / (p.z - viewPoint.z);
        var xAngle = Math.toDegrees(Math.atan(tanXAngle));
        //System.out.println("xAngle: " + xAngle );
        var tanYAngle = (p.y - viewPoint.y) / (p.z - viewPoint.z);
        var yAngle = Math.toDegrees(Math.atan(tanYAngle));
        //System.out.println("yAngle: " + yAngle );

        var x = width/2 + (pixelsPerXDegree * xAngle);
        var y = height/2 + (pixelsPerYDegree * yAngle);

        //System.out.println("xPix: "+x);
        //System.out.println("yPix: "+y);

        return new Point2D(x, y);
    }

}
