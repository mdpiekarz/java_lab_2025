import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Polygon triangle = new Polygon(new Vec2[]{
                new Vec2(0, 0),
                new Vec2(300, 0),
                new Vec2(150, 250)
        });
        Polygon rectangle = new Polygon(new Vec2[]{
                new Vec2(350, 0),
                new Vec2(750, 0),
                new Vec2(750, 200),
                new Vec2(350, 200)
        });
        Polygon pentagon = new Polygon(new Vec2[]{
                new Vec2(0, 260),
                new Vec2(100, 460),
                new Vec2(300, 560),
                new Vec2(500, 460),
                new Vec2(600, 260)
        });
        Ellipse ellipse = new Ellipse(new Vec2(500, 700), 400, 100);

        Shape sFRectangle = new SolidFilledPolygon("red", new Vec2[]{
                new Vec2(1,1),
                new Vec2(1,60),
                new Vec2(60,60),
                new Vec2(60,1)});

        Shape sFSRectangle = new SolidFillShapeDecorator(rectangle,"red");
        Shape sFSEllipse = new SolidFillShapeDecorator(ellipse,"red");
        Shape sSSRectangle = new StrokeShapeDecorator(sFSRectangle, "blue", 6);
        Shape sSSEllipse = new StrokeShapeDecorator(sFSEllipse, "black",4);

        Shape ellipseTransform = new TransformationDecorator.Builder()
                .translate(new Vec2(-100,-200))
                .rotate(new Vec2(400,500),30)
                //.scale(new Vec2(-20,0))
                .build(sSSEllipse);

        SvgScene scene = new SvgScene();
        scene.addShape(triangle);
        scene.addShape(sSSRectangle);
        scene.addShape(pentagon);
        scene.addShape(ellipseTransform);
        scene.addShape(sFRectangle);
        scene.save("result.svg");
    }
}
