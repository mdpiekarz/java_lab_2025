public class Segment {
    private Point start;
    private Point end;

//    public Segment(Point start, Point end) {
//        this.start = start;
//        this.end = end;
//    }

    //konstruktor kopiujący
    public Segment(Point start, Point end){
        this.start = new Point(start.getX(),start.getY());
        this.end = new Point(end.getX(),end.getY());
    }

    //Metoda zwracająca długość odcinka
    public double length() {
        return Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
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

    public String toString(){
        return "Segment: ["+ start.toString()+ ", "+ end.toString()+"]";
    }
}
