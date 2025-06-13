public class Main {
    public static void main(String[] args) {
        ImageHandler handler = new ImageHandler();
        handler.saveImage("multithreading/obrazKopia.jpg");

        // Wczytanie obrazu
        handler.loadImage("multithreading/obraz.jpg");

        // Zwiększenie jasności obrazu o 100
        long startTimeParallel = System.currentTimeMillis();
        handler.adjustBrightness(100);
        long endTimeParallel = System.currentTimeMillis();
        System.out.println("Czas wykonania jeden wątek: " + (endTimeParallel - startTimeParallel) + " ms");

        handler.saveImage("multithreading/obrazKopia.jpg");

        handler.loadImage("multithreading/obraz.jpg");

        // Zwiększenie jasności obrazu o 150 z wykorzystaniem wielu wątków (ilość wątków równa liczbie rdzeni)
        int numThreads = Runtime.getRuntime().availableProcessors();
        startTimeParallel = System.currentTimeMillis();
        handler.adjustBrightnessParallel(150, numThreads);
        endTimeParallel = System.currentTimeMillis();
        System.out.println("Czas wykonania z wieloma wątkami: " + (endTimeParallel - startTimeParallel) + " ms");

        handler.saveImage("multithreading/obrazKopia1.jpg");

        handler.loadImage("multithreading/obraz.jpg");

        // Zwiększenie jasności obrazu o 50 z wykorzystaniem wielu wątków (ilość wątków równa liczbie rdzeni)
        //numThreads = Runtime.getRuntime().availableProcessors();
        startTimeParallel = System.currentTimeMillis();
        handler.adjustBrightnessPoolThread(200);
        endTimeParallel = System.currentTimeMillis();
        System.out.println("Czas wykonania z wieloma wątkami w puli: " + (endTimeParallel - startTimeParallel) + " ms");

        // Zapisanie obrazu
        handler.saveImage("multithreading/obrazKopia2.jpg");

        handler.loadImage("multithreading/obraz.jpg");

        // Obliczenie histogramu czerwonego kanału
        int[] redHistogram = handler.calculateChannelHistogram(3);

        // Wyświetlenie histogramu
        if (redHistogram != null) {
            for (int i = 0; i < redHistogram.length; i++) {
                System.out.println("Wartość: " + i + ", Liczba pikseli: " + redHistogram[i]);
            }
        }

        // Przykładowy histogram (dla celów demonstracyjnych)
        int[] histogram = new int[256];
        for (int i = 0; i < 256; i++) {
            histogram[i] = (int) (Math.random() * 500); // Losowe wartości do histogramu
        }

        //ImageHandler handler = new ImageHandler();
        handler.generateHistogramImage(histogram, "multithreading/histogram.png");
    }
}
