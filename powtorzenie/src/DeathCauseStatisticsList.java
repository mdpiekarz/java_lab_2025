import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DeathCauseStatisticsList {
    List<DeathCauseStatistic> causeStats = new ArrayList<>();
    public void repopulate(String path) {
        causeStats.clear();
        try {
            Files.lines(Paths.get(path))
                    .skip(2)
                    .map(DeathCauseStatistic::fromCsvLine)
                    .forEach(statistic -> causeStats.add(statistic));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DeathCauseStatistic get(int index) {
        return causeStats.get(index);
    }
    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n) {
        List<DeathCauseStatistic> values = new ArrayList<>(causeStats);
        //Collections.sort(values, Comparator.comparingInt((DeathCauseStatistic a) -> a.getDeaths(age).deathCount).reversed());
        values.sort(Comparator.comparingInt((DeathCauseStatistic a) -> a.getDeaths(age).deathCount).reversed());
        return values.subList(0,n);
    }
}
