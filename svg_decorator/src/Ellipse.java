import java.util.Locale;

public class Ellipse implements Shape{
    private Vec2 center;
    private double rx, ry;

    public Ellipse(Vec2 center, double rx, double ry) {
        this.center = center;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(center.x() - rx, center.y() - ry, rx * 2, ry * 2);
    }

    @Override
    public String toSvg(String param) {
        return String.format(Locale.ENGLISH,"<ellipse rx=\"%f\" ry=\"%f\" cx=\"%f\" cy=\"%f\" %s/>",
                rx,ry,center.x(),center.y(),param);
    }


}
