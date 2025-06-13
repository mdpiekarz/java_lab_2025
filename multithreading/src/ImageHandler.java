import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageHandler {
    private BufferedImage image;

    // Metoda do wczytania obrazu z podanej ścieżki
    public void loadImage(String path) {
        try {
            File file = new File(path);
            image = ImageIO.read(file);
            System.out.println("Obraz został wczytany pomyślnie.");
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas wczytywania obrazu: " + e.getMessage());
        }
    }

    // Metoda do zapisania obrazu z pola klasy do podanej ścieżki
    public void saveImage(String path) {
        try {
            File file = new File(path);
            String format = path.substring(path.lastIndexOf('.') + 1);
            ImageIO.write(image, format, file);
            System.out.println("Obraz został zapisany pomyślnie.");
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania obrazu: " + e.getMessage());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Niepoprawna ścieżka, brak rozszerzenia obrazu.");
        }catch (IllegalArgumentException e) {
            System.out.println("Brak wczytanego obrazu do zapisania.");
        }
    }

    // Metoda do pobrania obiektu BufferedImage (jeśli potrzebne)
    public BufferedImage getImage() {
        return image;
    }

    // Metoda do zwiększenia jasności obrazu o podaną stałą
    public void adjustBrightness(int value) {
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }

        // Iteracja po pikselach obrazu i zmiana jasności
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Zmiana jasności dla każdego kanału koloru
                red = clamp(red + value);
                green = clamp(green + value);
                blue = clamp(blue + value);

                int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newRGB);
            }
        }

        //graphics.dispose();
    }

    // Metoda pomocnicza do ograniczenia wartości do zakresu 0-255
    private int clamp(int value) {
        return Math.min(Math.max(0, value), 255);
    }

    private class BrightnessAdjustmentTask extends Thread {
        private final int startY;
        private final int endY;
        private final int value;

        public BrightnessAdjustmentTask(int startY, int endY, int value) {
            this.startY = startY;
            this.endY = endY;
            this.value = value;
        }

        @Override
        public void run() {
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int alpha = (rgb >> 24) & 0xFF;
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Zmiana jasności dla każdego kanału koloru
                    red = clamp(red + value);
                    green = clamp(green + value);
                    blue = clamp(blue + value);

                    int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    image.setRGB(x, y, newRGB);
                }
            }
        }
    }

    // Metoda do zwiększenia jasności obrazu o podaną stałą z wykorzystaniem wielu wątków
    public void adjustBrightnessParallel(int value, int numThreads) {
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }

        Thread[] threads = new Thread[numThreads];
        int heightPerThread = image.getHeight() / numThreads;

        for (int i = 0; i < numThreads; i++) {
                 int threadIndex = i;
            int startY = threadIndex * heightPerThread;
            int endY = (threadIndex == numThreads - 1) ? image.getHeight() : (threadIndex + 1) * heightPerThread;
            threads[i] = new BrightnessAdjustmentTask(startY,endY,value);
            threads[i].start();
        }

        // Czekanie na zakończenie wszystkich wątków
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Wątek został przerwany: " + e.getMessage());
            }
        }
    }

    public void adjustBrightnessPoolThread(int value /*, int numThreads*/) {
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }
        int numThreads = image.getHeight();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Dzielenie obrazu na części i przetwarzanie ich w osobnych wątkach
        int heightPerThread = 1;
        for (int i = 0; i < numThreads-1; i++) {
            int startY = i;
            int endY = i + 1;
            Runnable task = new BrightnessAdjustmentTask(startY, endY, value);
            executor.execute(task);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Wątek został przerwany: " + e.getMessage());
        }
    }

    // Metoda do obliczenia histogramu wybranego kanału obrazu
    public int[] calculateChannelHistogram(int channel) {
        //(0 dla czerwonego, 1 dla zielonego, 2 dla niebieskiego)
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return null;
        }

        int[] histogram = new int[256];
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Dzielenie obrazu na części i przetwarzanie ich w osobnych wątkach
        int heightPerThread = image.getHeight() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int startY = i * heightPerThread;
            int endY = (i == numThreads - 1) ? image.getHeight() : (i + 1) * heightPerThread;
            Runnable task = new HistogramCalculationTask(channel, startY, endY, histogram);
            executor.execute(task);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Wątek został przerwany: " + e.getMessage());
        }

        return histogram;
    }

    // Klasa wewnętrzna reprezentująca zadanie obliczania histogramu dla wybranego kanału w jednym wątku
    private class HistogramCalculationTask implements Runnable {
        private final int channel;
        private final int startY;
        private final int endY;
        private final int[] histogram;

        public HistogramCalculationTask(int channel, int startY, int endY, int[] histogram) {
            this.channel = channel;
            this.startY = startY;
            this.endY = endY;
            this.histogram = histogram;
        }

        @Override
        public void run() {
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int color = (rgb >> (channel * 8)) & 0xFF; // Wybór kanału koloru
                    synchronized (histogram) {
                        histogram[color]++;
                    }
                }
            }
        }
    }

    // Metoda do generowania obrazu przedstawiającego wykres histogramu
    public void generateHistogramImage(int[] histogram, String outputPath) {
        if (histogram == null || histogram.length != 256) {
            System.out.println("Niepoprawny histogram.");
            return;
        }

        int width = 256; // Szerokość obrazu
        int height = 200; // Wysokość obrazu
        BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = histogramImage.getGraphics();

        // Wypełnienie tła na biało
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // Znalezienie maksymalnej wartości w histogramie
        int maxCount = 0;
        for (int count : histogram) {
            maxCount = Math.max(maxCount, count);
        }

        // Rysowanie słupków na podstawie histogramu
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) ((double) histogram[i] / maxCount * (height - 20)); // Wysokość słupka
            int x = i;
            int y = height - 10 - barHeight; // Górna krawędź słupka
            graphics.drawLine(x, height - 10, x, y); // Linia pionowa
        }

        // Zapisanie obrazu histogramu do pliku
        try {
            File outputFile = new File(outputPath);
            ImageIO.write(histogramImage, "png", outputFile);
            System.out.println("Obraz histogramu został wygenerowany i zapisany pomyślnie.");
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas zapisywania obrazu histogramu: " + e.getMessage());
        }
    }


    //Zad 3 z wykorzystaniem Runnable

    private class BrightnessAdjustmentTaskRunnable implements Runnable {
        private final int startY;
        private final int endY;
        private final int value;

        public BrightnessAdjustmentTaskRunnable(int startY, int endY, int value) {
            this.startY = startY;
            this.endY = endY;
            this.value = value;
        }

        @Override
        public void run() {
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int alpha = (rgb >> 24) & 0xFF;
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Zmiana jasności dla każdego kanału koloru
                    red = clamp(red + value);
                    green = clamp(green + value);
                    blue = clamp(blue + value);

                    int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    image.setRGB(x, y, newRGB);
                }
            }
        }
    }

    public void adjustBrightnessParallelRunnable(int value, int numThreads) {
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }

        Thread[] threads = new Thread[numThreads];
        int heightPerThread = image.getHeight() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int threadIndex = i;
            int startY = threadIndex * heightPerThread;
            int endY = (threadIndex == numThreads - 1) ? image.getHeight() : (threadIndex + 1) * heightPerThread;

            threads[i] = new Thread(new BrightnessAdjustmentTaskRunnable(startY, endY, value));

            //   bez definiowania prywatnej klasy wątku
//          threads[i] = new Thread(() -> {
//                for (int y = startY; y < endY; y++) {
//                    for (int x = 0; x < image.getWidth(); x++) {
//                        int rgb = image.getRGB(x, y);
//                        int alpha = (rgb >> 24) & 0xFF;
//                        int red = (rgb >> 16) & 0xFF;
//                        int green = (rgb >> 8) & 0xFF;
//                        int blue = rgb & 0xFF;
//
//                        // Zmiana jasności dla każdego kanału koloru
//                        red = clamp(red + value);
//                        green = clamp(green + value);
//                        blue = clamp(blue + value);
//
//                        int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
//                        image.setRGB(x, y, newRGB);
//                    }
//            }});

            threads[i].start();
        }

        // Czekanie na zakończenie wszystkich wątków
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Wątek został przerwany: " + e.getMessage());
            }
        }
    }


}