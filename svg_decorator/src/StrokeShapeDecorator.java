import java.util.Locale;

public class StrokeShapeDecorator extends ShapeDecorator {
    String color;
    double width;

    public StrokeShapeDecorator(Shape shape, String color, double width) {
        super(shape);
        this.color = color;
        this.width = width;
    }

    public String toSvg(String parameters){
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "stroke=\"%s\" stroke-width=\"%f\" %s ", color, width, parameters));
    }
}
