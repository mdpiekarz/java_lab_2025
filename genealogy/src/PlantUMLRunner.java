import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlantUMLRunner {
    private static String path="";
    public static void setPath(String path) {
        PlantUMLRunner.path = path;
    }

    public static void generate(String data, String outPath, String outName) {
        try {
            new File(outPath).mkdirs();

            String path1 = outPath + "/" + outName + ".puml";
            //File file = new File(path);
            FileWriter writer = new FileWriter(path1);
            writer.write(data);
            writer.close();

            ProcessBuilder builder = new ProcessBuilder("java", "-jar", PlantUMLRunner.path, path1);
            Process process = builder.start();
            System.out.println(process.info());
            process.waitFor();
            //file.delete();
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}