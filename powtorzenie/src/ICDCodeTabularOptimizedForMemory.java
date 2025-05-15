import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular{
    private String path;

    public ICDCodeTabularOptimizedForMemory(String path) {
        this.path = path;
    }

    @Override
    public String getDescription(String code) {
        try {
            Optional<String> foundEntry = Files.lines(Paths.get(path))
                    .skip(87)
                    .map(String::trim)
                    .filter(line -> line.matches("[A-Z][0-9]{2}.*"))
                    .map(line -> line.split(" ", 2))
                    .filter(lineArr -> code.equals(lineArr[0]))
                    .findFirst()
                    .map(lineArr -> lineArr[1]);
            if(foundEntry.isPresent())
                return foundEntry.get();
            else throw new IndexOutOfBoundsException("No such code: "+code);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
