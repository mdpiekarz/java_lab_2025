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




}