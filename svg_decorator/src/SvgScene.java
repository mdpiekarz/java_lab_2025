import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class SvgScene {
    private int index = 0;
    private Shape[] shapes = new Shape[30];

    public void addShape(Shape shape) {
        shapes[(index++) % shapes.length] = shape;
    }

    private BoundingBox sceneBox() {
        double maxX = 0, maxY = 0;
        for(Shape shape: shapes) {
            if(shape == null)
                continue;
            BoundingBox shapeBB = shape.boundingBox();
            maxX = Math.max(maxX, shapeBB.x() + shapeBB.width());
            maxY = Math.max(maxY, shapeBB.y() + shapeBB.height());
        }
        return new BoundingBox(0, 0, maxX, maxY);
    }

    public String toSvg()
    {
        BoundingBox boundingBox = this.sceneBox();
        String result = String.format(Locale.ENGLISH,
                "<svg width=\"%f\" height=\"%f\" xmlns=\"http://www.w3.org/2000/svg\">",
                boundingBox.width(), boundingBox.height());
        for(var shape : shapes) {
            if(shape ==  null)
                continue;
            result += "\n\t" + shape.toSvg("");
        }
        result += "\n</svg>";
        return result;
    }

    public void save(String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(toSvg());
        writer.close();
    }
}
