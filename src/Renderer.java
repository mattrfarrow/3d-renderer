import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Renderer extends Application {

    record Point3D(double x, double y, double z){}
    record Point2D(double x, double y){}
    record Triangle3D(Point3D a, Point3D b, Point3D c, Color color){}
    record Triangle2D(Point2D a, Point2D b, Point2D c, Color color){}

    private long prevNanos = 0;

    boolean goNorth = false;
    boolean goSouth = false;
    boolean goWest = false;
    boolean goEast = false;
    boolean goUp = false;
    boolean goDown = false;

    private Point3D viewPoint = new Point3D(((double)width)/2, ((double)height)/2, 0);

    private static final int height = 800;
    private static final int width = 800;

    private static final double pixelsPerXDegree = width / 70.0;
    private static final double pixelsPerYDegree = height / 70.0;

    List<Triangle3D> triangles = Shapes.getTriangles();


    public static void main(String[] args) {
        launch(args);
    }

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
                render(now, graphics, canvas);
            }
        };

        Group group = new Group(canvas);
        Scene scene = new Scene(group, width, height);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> goNorth = true;
                case DOWN -> goSouth = true;
                case LEFT -> goWest = true;
                case RIGHT -> goEast = true;
                case A -> goUp = true;
                case Z -> goDown = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP -> goNorth = false;
                case DOWN -> goSouth = false;
                case LEFT -> goWest = false;
                case RIGHT -> goEast = false;
                case A -> goUp = false;
                case Z -> goDown = false;
            }
        });
        stage.setScene(scene);
        stage.show();

        timer.start();
        timeline.play();
    }

    private void render(long now, GraphicsContext graphics, Canvas canvas) {
        if (prevNanos == 0) {
            prevNanos = now;
            return;
        }
        long deltaNanos = now - prevNanos;
        prevNanos = now;
        double deltaSec  = deltaNanos / 1.0e9;

        int xOffset = 0;
        if(goWest) {
            xOffset = -200;
        } else if((goEast)) {
            xOffset = 200;
        }

        int yOffset = 0;
        if(goDown) {
            yOffset = 200;
        } else if((goUp)) {
            yOffset = -200;
        }

        int zOffset = 0;
        if(goNorth) {
            zOffset = 200;
        } else if(goSouth) {
            zOffset = -200;
        }

        viewPoint = new Point3D(
                viewPoint.x + (deltaSec * xOffset),
                viewPoint.y + (deltaSec * yOffset),
                viewPoint.z + (deltaSec * zOffset));

        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        triangles.sort(new Comparator<>() {
            @Override
            public int compare(Triangle3D t1, Triangle3D t2) {
                return -Double.compare(maxDistanceToTriangle(t1), maxDistanceToTriangle(t2));
            }

            private double maxDistanceToTriangle(Triangle3D t1) {
                return max(distance(viewPoint, t1.a), min(distance(viewPoint, t1.b), distance(viewPoint, t1.c)));
            }

            private double distance(Point3D a, Point3D b) {
                return Math.sqrt( Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2) + Math.pow(a.z-b.z, 2) );
            }
        });

        for(Triangle3D triangle : triangles) {
            drawTriangle(triangle, graphics, viewPoint);
        }
    }

    private static void drawTriangle(Triangle3D t, GraphicsContext graphics_context, Point3D viewPoint) {
        var t2d = triangle3Dto2D(t, viewPoint);

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
        var tanYAngle = (p.y - viewPoint.y) / (p.z - viewPoint.z);
        var yAngle = Math.toDegrees(Math.atan(tanYAngle));

        var x = width/2 + (pixelsPerXDegree * xAngle);
        var y = height/2 + (pixelsPerYDegree * yAngle);

        return new Point2D(x, y);
    }

}
