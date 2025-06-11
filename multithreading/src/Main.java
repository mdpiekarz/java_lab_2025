public class Main {
    public static void main(String[] args) {
        ImageHandler handler = new ImageHandler();
        //handler.saveImage("obrazKopia.jpg");

        // Wczytanie obrazu
        handler.loadImage("multithreading/obraz.jpg");

        // Zwiększenie jasności obrazu o 100
        long startTimeParallel = System.currentTimeMillis();
        handler.adjustBrightness(100);
        long endTimeParallel = System.currentTimeMillis();
        System.out.println("Czas wykonania jeden wątek: " + (endTimeParallel - startTimeParallel) + " ms");

        handler.saveImage("multithreading/obrazKopia.jpg");
    }
}
