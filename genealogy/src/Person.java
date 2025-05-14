import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
                if(parentsName.length>3) {
                    for (Person p : result) {
                        if ((p.imie + " " + p.nazwisko).equals(parentsName[3])) {
                            p.adopt(person);
                            p.validateParentingAge();

                        }
                        if (parentsName.length == 5 && (p.imie + " " + p.nazwisko).equals(parentsName[4])) {
                            p.adopt(person);
                            p.validateParentingAge();

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

        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(people);

    }

    public static List<Person> fromBinaryFile(String filename) throws IOException, ClassNotFoundException {
        try (
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (List<Person>) ois.readObject();
        }
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
        return imie + " " + nazwisko + ", urodzony: " + dataUrodzin + ", data śmierci: "+ dataSmierci;// +", dzieci: " + children;
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

    /*------------------lab 7------------------------------------------------------*/
    public String toPlantUMLTree() {
        String result = "@startuml\n%s\n%s\n@enduml";
        Function<String, String> objectLine = str -> String.format("object \"%s\" as %s\n",str, str.replaceAll(" ", ""));
        Function<String[], String> relationLine = str -> {return String.format("%s<--%s\n",str[0],str[1]) ;};

        StringBuilder objects = new StringBuilder();
        StringBuilder relations = new StringBuilder();
        objects.append(objectLine.apply(imie + " " + nazwisko));

        for(Person child: children) {
            objects.append(objectLine.apply(child.imie + " " + child.nazwisko));
            String[] relation = new String[]{imie+nazwisko,child.imie+child.nazwisko};
            relations.append(relationLine.apply(relation));
        }
        return String.format(result, objects, relations);
    }

    public static String toPlantUMLTree(List<Person> people) {
        String result = "@startuml\n%s\n%s\n@enduml";
        Function<String, String> objectLine = str -> String.format("object \"%s\" as %s\n", str, str.replaceAll(" ", ""));
        Function<String[], String> relationLine = pair -> String.format("%s<--%s\n", pair[0], pair[1]);

        StringBuilder objects = new StringBuilder();
        StringBuilder relations = new StringBuilder();
        Set<String> addedObjects = new HashSet<>();
        Set<String> addedRelations = new HashSet<>();

        Consumer<Person> addPerson = person -> {
            String personKey = person.getImie() + " " + person.getNazwisko();
            if (addedObjects.add(personKey)) {
                objects.append(objectLine.apply(personKey));
            }

            for (Person child : person.getChildren()) {
                String childKey = child.getImie() + " " + child.getNazwisko();
                if (addedObjects.add(childKey)) {
                    objects.append(objectLine.apply(childKey));
                }

                String[] relationPair = new String[] { person.imie+person.nazwisko, child.imie+child.nazwisko };
                String relationStr = relationLine.apply(relationPair);
                if (addedRelations.add(relationStr)) {
                    relations.append(relationStr);
                }
            }
        };

        people.forEach(addPerson);

        return String.format(result, objects, relations);
    }

    public static List<Person> filterByName(List<Person> people, String nameSubstring) {
        return people.stream()
                .filter(person -> person.nazwisko.contains(nameSubstring))
                .collect(Collectors.toList());
    }

//    public static List<Person> sortByBirth(List<Person> people) {
//        people.sort(Comparator.comparing(person -> person.dataUrodzin));
//        return people;
//    }

    public static List<Person> sortByBirth(List<Person> people) {
        return people.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<Person> sortByLifespanDesc(List<Person> people) {
        return people.stream()
                .filter(person -> person.dataSmierci != null)
                .sorted((person1, person2) -> {
                    long lifespan1 = person1.dataSmierci.toEpochDay() - person1.dataUrodzin.toEpochDay();
                    long lifespan2 = person2.dataSmierci.toEpochDay() - person2.dataUrodzin.toEpochDay();
                    return Long.compare(lifespan2, lifespan1);
                })
                .collect(Collectors.toList());
    }

    public static Person findOldestPersonAlive(List<Person> people) {
        return people.stream()
                .filter(person -> person.dataSmierci == null)
                .min(Comparator.comparing(Person::getDataUrodzin))
                .orElse(null);
    }

    //Zad 8
    public static String toPlantUMLTreeV2(List<Person> people, Function<String, String> postProcess) {
        String result = "@startuml\n%s\n%s\n@enduml";
        Function<String, String> objectLine = str -> String.format("object \"%s\" as %s", str, str.replaceAll(" ", ""));
        Function<String, String> objectLineAndPostprocess = objectLine.andThen(postProcess);
        Function<String[], String> relationLine = pair -> String.format("%s<--%s\n", pair[0], pair[1]);

        StringBuilder objects = new StringBuilder();
        StringBuilder relations = new StringBuilder();
        Set<String> addedObjects = new HashSet<>();
        Set<String> addedRelations = new HashSet<>();

        Consumer<Person> addPerson = person -> {
            String personKey = person.getImie() + " " + person.getNazwisko();
            if (addedObjects.add(personKey)) {
                objects.append(objectLineAndPostprocess.apply(personKey)+"\n");
            }

            for (Person child : person.getChildren()) {
                String childKey = child.getImie() + " " + child.getNazwisko();
                if (addedObjects.add(childKey)) {
                    objects.append(objectLineAndPostprocess.apply(childKey)+"\n");
                }

                String[] relationPair = new String[] { person.imie+person.nazwisko, child.imie+child.nazwisko };
                String relationStr = relationLine.apply(relationPair);
                if (addedRelations.add(relationStr)) {
                    relations.append(relationStr);
                }
            }
        };

        people.forEach(addPerson);

        return String.format(result, objects, relations);
    }


    //Zad 8 i 9
    public static String toPlantUMLTreeV3(List<Person> people, Predicate<Person> condition, Function<String, String> postProcess) {
        String result = "@startuml\n%s\n%s\n@enduml";
        Function<String, String> objectLine = str -> String.format("object \"%s\" as %s", str, str.replaceAll(" ", ""));
        Function<String, String> objectLineAndPostprocess = objectLine.andThen(postProcess);

        Map<Boolean, List<Person>> groupedPeople = people.stream().collect(Collectors.partitioningBy(condition));

        Set<String> objects = groupedPeople.get(true).stream()
                .map(person -> (person.imie + " "+ person.nazwisko))
                .map(objectLineAndPostprocess)
                .collect(Collectors.toSet());

        objects.addAll(groupedPeople.get(false).stream()
                .map(person -> (person.imie + " "+ person.nazwisko))
                .map(objectLine)
                .collect(Collectors.toSet())
        );

        Set<String> relations = people.stream()
                .flatMap(person -> person.children.stream()
                        .map(child -> ((child.imie + child.nazwisko) + "<--" + (person.imie + person.nazwisko))))
                .collect(Collectors.toSet());

        String objectString = String.join("\n", objects);
        String relationString = String.join("\n", relations);

        return String.format(result, objectString, relationString);
    }

    //Zad 3 bez Consumer
    //    public static String toPlantUMLTree(List<Person> people) {
//        String result = "@startuml\n%s\n%s\n@enduml";
//        Function<String, String> objectLine = str -> String.format("object \"%s\" as %s\n", str, str.replaceAll(" ", ""));
//        Function<String[], String> relationLine = pair -> String.format("%s<--%s\n", pair[0].replaceAll(" ", ""), pair[1].replaceAll(" ", ""));
//
//        StringBuilder objects = new StringBuilder();
//        StringBuilder relations = new StringBuilder();
//
//        Set<String> addedObjects = new HashSet<>();
//        Set<String> addedRelations = new HashSet<>();
//
//        for (Person person : people) {
//            String parentKey = person.getImie() + " " + person.getNazwisko();
//            if (addedObjects.add(parentKey)) {
//                objects.append(objectLine.apply(parentKey));
//            }
//
//            for (Person child : person.getChildren()) {
//                String childKey = child.getImie() + " " + child.getNazwisko();
//
//                if (addedObjects.add(childKey)) {
//                    objects.append(objectLine.apply(childKey));
//                }
//
//                String[] relationPair = new String[] { parentKey, childKey };
//                String relationString = relationLine.apply(relationPair);
//                if (addedRelations.add(relationString)) {
//                    relations.append(relationString);
//                }
//            }
//        }
//
//        return String.format(result, objects, relations);
//    }



}