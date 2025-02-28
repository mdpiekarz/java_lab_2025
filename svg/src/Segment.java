public class Segment {
    public Point start;
    public Point end;

//    public Segment(Point start, Point end) {
//        this.start = start;
//        this.end = end;
//    }

    //konstruktor kopiujący
    public Segment(Point start, Point end){
        this.start = new Point(start.x,start.y);
        this.end = new Point(end.x,end.y);
    }

    //Metoda zwracająca długość odcinka
    public double length() {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }

    // Metoda statyczna zwracająca najdłuższy segment z tablicy
    public static Segment findLongestSegment(Segment[] segments) {
        if (segments == null || segments.length == 0) {
            return null;
        }

        Segment longest = segments[0];
        for (Segment segment : segments) {
            if (segment.length() > longest.length()) {
                longest = segment;
            }
        }
        return longest;
    }
}
