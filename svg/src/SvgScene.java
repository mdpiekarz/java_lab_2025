import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class SvgScene {
    // Prywatna tablica 3 referencji do obiektów Polygon
    private Shape[] shapes = new Shape[3];
    private int index = 0;

    // Funkcja addPolygon() dodająca obiekt Polygon do tablicy
    public void addShape(Shape shape) {
        shapes[index] = shape;
        index = (index + 1) % shapes.length; // Nadpisywanie od początku po zapełnieniu
    }

    // Metoda toSvg() generująca scenę SVG z wszystkimi wielokątami
    public String toSvg(double width, double hight) {
        StringBuilder sb = new StringBuilder(String.format(Locale.US,"<svg xmlns='http://www.w3.org/2000/svg' width='%.2f' height='%.2f'>\n",width,hight));
        for (Shape shape : shapes) {
            if (shape != null) {
                sb.append(shape.toSvg()).append("\n");
            }
        }
        sb.append("</svg>");
        return sb.toString();
    }

    public void save(String filePath) {

        double maxX = 0, maxY = 0;
        for(Shape shape: shapes) {
            if(shape == null)
                continue;
            BoundingBox shapeBB = shape.boundingBox();
            maxX = Math.max(maxX, shapeBB.x() + shapeBB.width());
            maxY = Math.max(maxY, shapeBB.y() + shapeBB.height());
        }

        double width = maxX;
        double height = maxY;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(toSvg(width, height));
        } catch (IOException e) {
            System.err.println("Error writing SVG file: " + e.getMessage());
        }
    }


    // Przykładowa metoda main do testowania
    public static void main(String[] args) {
        Point[] points1 = {new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10)};
        Point[] points2 = {new Point(20, 20), new Point(30, 20), new Point(30, 30), new Point(20, 30)};
        Point[] points3 = {new Point(40, 40), new Point(50, 40), new Point(50, 50), new Point(40, 50)};
        Point[] points4 = {new Point(110, 60), new Point(70, 60), new Point(70, 70), new Point(60, 70)};

        Polygon polygon1 = new Polygon(points1,null);
        Polygon polygon2 = new Polygon(points2,null);
        Polygon polygon3 = new Polygon(points3,null);
        Polygon polygon4 = new Polygon(points4,null);

        Ellipse ellipse = new Ellipse(new Point(3, 6),2,5,null);

        SvgScene scene = new SvgScene();
        scene.addShape(polygon1);
        scene.addShape(polygon2);
        scene.addShape(polygon3);
        scene.addShape(ellipse); // Nadpisze polygon1

        System.out.println(scene.toSvg(500,500));

        BoundingBox box = polygon1.boundingBox();
        System.out.println("BoundingBox: " + box);

        scene.save("scene.svg");
    }
}
