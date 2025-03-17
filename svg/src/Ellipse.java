import java.util.Locale;

public class Ellipse extends Shape {
    private Point center;
    private double rx, ry;

    public Ellipse(Point center, double rx, double ry, Style style) {
        super(style);
        this.center = center;
        this.rx = rx;
        this.ry = ry;
        if (style == null)this.style = new Style("none", "black", 1.0);
    }

    @Override
    public String toSvg() {
        return String.format(Locale.ENGLISH, "<ellipse cx=\"%f\" cy=\"%f\" rx=\"%f\" ry=\"%f\" style=\"%s\" />",
                center.getX(), center.getY(), rx, ry, style.toSvg());

    }

    @Override
    public BoundingBox boundingBox() {
        double x = center.getX() - rx;
        double y = center.getY() - ry;
        double width = 2 * rx;
        double height = 2 * ry;
        return new BoundingBox(x, y, width, height);
    }
}

