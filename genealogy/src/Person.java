import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Person implements Comparable<Person>, Serializable{
    private String imie;
    private String nazwisko;
    private LocalDate dataUrodzin, dataSmierci;
    private Set<Person> children;

    public Person(String imie, String nazwisko, LocalDate dataUrodzin, LocalDate dataSmierci) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzin = dataUrodzin;
        this.dataSmierci=dataSmierci;
        this.children = new HashSet<>();
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
                person.validateAmbiguity(result);
                result.add(person);
                //br.close();
                //dodanie dziecka do rodziców
                parentsName = line.split(","); //skladowe 3 i 4
                if (parentsName.length > 3) {
                    if (parentsName[3] != "") {
                        for (Person p : result) {
                            if (parentsName.length > 3 && (p.imie + " " + p.nazwisko).equals(parentsName[3])) {
                                p.adopt(person);
                                p.validateParentingAge();
                            }
                        }
                    }
                    if (parentsName.length ==5) {
                        for (Person p : result) {
                            if ((p.imie + " " + p.nazwisko).equals(parentsName[4])) {
                                p.adopt(person);
                                p.validateParentingAge();
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Plik " + path + " nie istnieje.");
        } catch (NegativeLifespanException | AmbiguousPersonException | ParentingAgeException e) {
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

    private void validateAmbiguity(List<Person> peopleSoFar) throws AmbiguousPersonException {
        for(Person person: peopleSoFar)
            if((person.imie + " " + person.nazwisko).equals(this.imie + " " + this.nazwisko))
                throw new AmbiguousPersonException(this.imie + " " + this.nazwisko);
    }

    private void validateParentingAge() throws ParentingAgeException {
        for(Person child: children)
            if (this.dataUrodzin.isAfter(child.dataUrodzin.minusYears(15)) || ( this.dataSmierci != null && child.dataUrodzin.isAfter(this.dataSmierci)))
                throw new ParentingAgeException(child, this);
    }

    public static void toBinaryFile(List<Person> people, String filename) throws IOException {
        try (//try-with-resources" - konstrukcja, która automatycznie zamyka strumienie po zakończeniu bloku try,
             // strumienie muszą implementować interfejs AutoCloseable
                FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(people);
        }
    }

    public static List<Person> fromBinaryFile(String filename) throws IOException, ClassNotFoundException {
        try (//try-with-resources" - konstrukcja, która automatycznie zamyka strumienie po zakończeniu bloku try,
             // strumienie muszą implementować interfejs AutoCloseable
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (List<Person>) ois.readObject();
        }

        //wersja z try-finally
//        FileInputStream fis = null;
//        ObjectInputStream ois = null;
//        try {
//            fis = new FileInputStream(filename);
//            ois = new ObjectInputStream(fis);
//            return (List<Person>) ois.readObject();
//        } finally {
//            if (ois != null) {
//                try {
//                    ois.close();
//                } catch (IOException e) {
//                    // opcjonalnie obsługa błędu przy zamykaniu
//                }
//            }
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    // opcjonalnie obsługa błędu przy zamykaniu
//                }
//            }
//        }
    }

    //lab6-end-----------------------------------------------------------

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
//            if (youngest == null || /*child.compareTo(youngest) > 0*/ child.dataUrodzin.isAfter(youngest.dataUrodzin)) {
//                youngest = child;
//            }
//        }

        return Collections.max(children,Comparator.naturalOrder());
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko + ", urodzony: " + dataUrodzin + " dzieci: " + children;
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
}