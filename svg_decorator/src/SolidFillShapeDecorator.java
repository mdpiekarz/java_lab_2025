import java.util.Locale;

public class SolidFillShapeDecorator extends ShapeDecorator{

    private String color;
    public SolidFillShapeDecorator(Shape decoratedShape, String color) {
        super(decoratedShape);
        this.color = color;
    }

    public String toSvg(String parameters){
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "fill=\"%s\" %s ", color, parameters));
    }
}
