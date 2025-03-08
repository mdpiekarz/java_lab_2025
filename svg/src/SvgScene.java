import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class SvgScene {
    // Prywatna tablica 3 referencji do obiektów Polygon
    private Polygon[] polygons = new Polygon[3];
    private int index = 0;

    // Funkcja addPolygon() dodająca obiekt Polygon do tablicy
    public void addPolygon(Polygon polygon) {
        polygons[index] = polygon;
        index = (index + 1) % polygons.length; // Nadpisywanie od początku po zapełnieniu
    }

    // Metoda toSvg() generująca scenę SVG z wszystkimi wielokątami
    public String toSvg(int width, int hight) {
        StringBuilder sb = new StringBuilder(String.format(Locale.US,"<svg xmlns='http://www.w3.org/2000/svg' width='%d' height='%d'>\n",width,hight));
        for (Polygon polygon : polygons) {
            if (polygon != null) {
                sb.append(polygon.toSvg()).append("\n");
            }
        }
        sb.append("</svg>");
        return sb.toString();
    }

    public void save(String filePath) {

        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Polygon polygon : polygons) {
            if (polygon != null) {
                BoundingBox box = polygon.boundingBox();
                maxX = Math.max(maxX, box.x() + box.width());
                maxY = Math.max(maxY, box.y()+ box.height());
            }
        }

        int width = (int) Math.ceil(maxX);
        int height = (int) Math.ceil(maxY);

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

        Polygon polygon1 = new Polygon(points1);
        Polygon polygon2 = new Polygon(points2);
        Polygon polygon3 = new Polygon(points3);
        Polygon polygon4 = new Polygon(points4);

        SvgScene scene = new SvgScene();
        scene.addPolygon(polygon1);
        scene.addPolygon(polygon2);
        scene.addPolygon(polygon3);
        scene.addPolygon(polygon4); // Nadpisze polygon1

        System.out.println(scene.toSvg(500,500));

        BoundingBox box = polygon1.boundingBox();
        System.out.println("BoundingBox: " + box);

        scene.save("scene.svg");
    }
}
