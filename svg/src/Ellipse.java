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
        return String.format(Locale.ENGLISH, "<ellipse cx=\"%.2f\" cy=\"%.2f\" rx=\"%.2f\" ry=\"%.2f\" %s />",
                center.getX(), center.getY(), rx, ry, style.toSvg());

    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(center.getX() - rx, center.getY() - ry, rx * 2, ry * 2);
    }
}

