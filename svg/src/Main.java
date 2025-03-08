public class Main {

    public static void main(String args[]){
        //System.out.println("Witaj Świecie!");
        // Tworzenie obiektu Point i ustawienie współrzędnych
        Point point = new Point(3.5, 7.2);

        // Wyświetlenie informacji o punkcie
        System.out.println(point);
        System.out.println(point.toSvg());
        point.translate(2,1);
        System.out.println("After translate: " + point);
        Point newPoint = point.translated(3,0);
        System.out.println("point after translated: " + point);
        System.out.println("newPoint after translated: " + newPoint);
        //wywołanie łańcuchowe metod
        System.out.println(point.translated(1,1).toSvg());

        // Tworzenie i testowanie odcinka
        Segment segment = new Segment(point, newPoint);
        System.out.println(segment.toString());
        System.out.println("Segment length: " + segment.length());
        point.translate(-2,0);
        System.out.println("Segment length after change point: " + segment.length());

        // Tworzenie obiektów Point
        Point point1 = new Point(3.5, 7.2);
        Point point2 = new Point(5.0, 10.0);
        Point point3 = new Point(1.0, 1.0);

        // Tworzenie tablicy Segmentów
        Segment[] segments = {
                new Segment(point1, point2),
                new Segment(point2, point3),
                new Segment(point1, point3)
        };

        // Znajdowanie i wyświetlanie najdłuższego segmentu
        Segment longest = Segment.findLongestSegment(segments);
        System.out.println("Longest segment length: " + longest.length());
    }
}
