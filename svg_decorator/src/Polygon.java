import java.util.Locale;

public class Polygon implements Shape{
    private Vec2[] points;
    public Polygon(Vec2[] points) {
        this.points = new Vec2[points.length];
        for(int i =0; i < points.length; i++)
        {
            this.points[i] = new Vec2(points[i]);
        }
    }

    @Override
    public BoundingBox boundingBox() {
        double xMin = this.points[0].x();
        double xMax = this.points[0].x();
        double yMin = this.points[0].y();
        double yMax = this.points[0].y();

        for (int i = 1; i < points.length; i++) {
            xMin = Math.min(xMin, points[i].x());
            xMax = Math.max(xMax, points[i].x());
            yMin = Math.min(yMin, points[i].y());
            yMax = Math.max(yMax, points[i].y());
        }
        return new BoundingBox(xMin, yMin, xMax - xMin, yMax - yMin);
    }

    public String toSvg(String param)    {
        String pointsString = "";
        for(Vec2 point : points) {
            pointsString += point.x() + "," + point.y() + " ";
        }
        return String.format(Locale.ENGLISH, "<polygon points=\"%s\" %s/>", pointsString, param);
    }
}
