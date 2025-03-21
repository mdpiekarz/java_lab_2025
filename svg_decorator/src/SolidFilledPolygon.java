import java.util.Locale;

public class SolidFilledPolygon extends Polygon{
    private String color;
    public SolidFilledPolygon(String color, Vec2[] points){
        super(points);
        this.color = color;
    }

    public String toSvg(String parameters){
        return super.toSvg(String.format(Locale.ENGLISH, "fill=\"%s\" %s ", color, parameters));
    }
}