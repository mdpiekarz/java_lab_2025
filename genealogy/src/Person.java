import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Person implements Comparable<Person>{
    private String imie;
    private String nazwisko;
    private LocalDate dataUrodzin;

    private LocalDate dataSmierci;
    private Set<Person> children;

    public Person(String imie, String nazwisko, LocalDate dataUrodzin, LocalDate dataSmierci) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzin = dataUrodzin;
        this.dataSmierci = dataSmierci;
        this.children = new HashSet<>();
    }

    public boolean adopt(Person child) {
        return children.add(child);
    }

//    public Person getYoungestChild() {
//        return children.stream()
//                .min((c1, c2) -> c2.dataUrodzin.compareTo(c1.dataUrodzin))
//                .orElse(null);
//    }

    public Person getYoungestChild() {
        Person youngest = null;
//        if (children.isEmpty()) {
//            return null;
//        }
//        for (Person child : children) {
//            if (youngest == null || child.compareTo(youngest) > 0 /* child.dataUrodzin.isAfter(youngest.dataUrodzin)*/) {
//                youngest = child;
//            }
//        }

        return Collections.max(children,Comparator.naturalOrder());
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko + ", urodzony: " + dataUrodzin + ", zmarł: " + dataSmierci;
    }

    @Override
    public int compareTo(Person other) {
        //zero dla równych, ujemna dla daty wcześniejszej, dodatnia dla daty późniejszej
        return this.dataUrodzin.compareTo(other.dataUrodzin);
    }

    public List<Person> getChildren() {
        List<Person> sortedChildren = new ArrayList<>(children);
        Collections.sort(sortedChildren);
        return sortedChildren;
    }

    //lab6-start-------------------------------------------------------

    public static Person fromCsvLine(String line) {
        String[] fields = line.split(",");
        String[] imieNazwisko = fields[0].split(" ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String birthString = fields[1];
        String deathString = fields[2];
        LocalDate birth = null, death = null;
        if(!birthString.isEmpty())
            birth = LocalDate.parse(birthString, formatter);
        if(!deathString.isEmpty())
            death = LocalDate.parse(deathString, formatter);

        return new Person(imieNazwisko[0], imieNazwisko[1], birth, death);
    }
    public static List<Person> fromCsv(String path) throws IOException {
        List<Person> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Person person;
            String line;
            br.readLine();
            String[] parentsName;
            while ((line = br.readLine()) != null) {
                person = Person.fromCsvLine(line);
                person.validateLifespan();
                result.add(person);
                //br.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Plik " + path + " nie istnieje.");
        } catch (NegativeLifespanException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    public String getImie() {
        return imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public LocalDate getDataUrodzin() {
        return dataUrodzin;
    }
    public LocalDate getDataSmierci() {
        return dataSmierci;
    }
    private void validateLifespan() throws NegativeLifespanException {
        if(this.dataSmierci != null && this.dataUrodzin.isAfter(this.dataSmierci))
            throw new NegativeLifespanException(this);
    }
}