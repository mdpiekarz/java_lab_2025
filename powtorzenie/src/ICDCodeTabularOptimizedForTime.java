
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular{
    Map<String, String> descriptions = new HashMap<>();

    public ICDCodeTabularOptimizedForTime(String path) {
        try {
            Files.lines(Paths.get(path))
                    .skip(87)
                    .map(String::trim)
                    .filter(line -> line.matches("[A-Z][0-9]{2}.*"))
                    .map(line -> line.split(" ", 2))
                    .forEach(line -> descriptions.put(line[0], line[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDescription(String code) throws IndexOutOfBoundsException {
        if(!descriptions.containsKey(code))
            throw new IndexOutOfBoundsException("No such code: "+code);
        return descriptions.get(code);
    }
}
