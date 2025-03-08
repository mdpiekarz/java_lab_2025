import java.util.Locale;

public class Point {
    // Publiczne pola zmiennoprzecinkowe
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Konstruktor domyślny
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(){
        this.x=0.;
        this.y=0.;
    }

    // Metoda toString() zwracająca informacje o współrzędnych
    @Override
    public String toString() {
        return "Point(x=" + x + ", y=" + y + ")";
    }

    // Metoda toSvg() zwracająca informacje o punkcie w formacie SVG
    public String toSvg() {
        //return "<circle cx='" + x + "' cy='" + y + "' r='5' fill='black' />";
        return String.format(Locale.US, "<circle cx='%.2f' cy='%.2f' r='5' fill='black' />", x, y);
    }

    // Metoda translate() przesuwająca punkt o dx i dy
    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    // Metoda translated() tworząca nowy punkt przesunięty o dx i dy
    public Point translated(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }


//    // Metoda główna do testowania klasy
//    public static void main(String[] args) {
//        // Tworzenie obiektu Point i ustawienie współrzędnych
//        Point point = new Point(3.5, 7.2);
//
//        // Wyświetlenie informacji o punkcie
//        System.out.println(point);
//    }
}
