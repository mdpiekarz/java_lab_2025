import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

            List<Person> osoby;
//            Person jan = new Person("Jan", "Kowalski", LocalDate.of(1990, 5, 20),null);
//            Person anna = new Person("Anna", "Nowak", LocalDate.of(1985, 10, 15),null);
//            Person piotr = new Person("Piotr", "Zielinski", LocalDate.of(2000, 3, 8),null);
//
//            osoby.add(jan);
//            osoby.add(anna);
//            osoby.add(piotr);
//
//            boolean adoptionSuccess = jan.adopt(piotr);
//            System.out.println("Adopcja udana: " + adoptionSuccess);
//
//            for (Person osoba : osoby) {
//                System.out.println(osoba);
//            }
//
//        Person maria = new Person("Maria", "Zielinska", LocalDate.of(2010, 6, 22),null);
//        jan.adopt(maria);
//
//        Person youngest = jan.getYoungestChild();
//        System.out.println("Najmłodsze dziecko Jana: " + (youngest != null ? youngest : "Brak dzieci"));
//
//        System.out.println("Dzieci Jana posortowane od najstarszego do najmłodszego:");
//        for (Person child : jan.getChildren()) {
//            System.out.println(child);
//        }
//
//        Family rodzina = new Family();
//        rodzina.add(jan);
//        rodzina.add(anna);
//
//        //System.out.println("Wyszukana osoba: " + rodzina.get("Jan Kowalski"));
//
//        rodzina.add(piotr,maria);
//        //System.out.println("Wyszukana osoba: " + rodzina.get("Piotr Zielinski"));
//
//        Person jan2 = new Person("Jan", "Kowalski", LocalDate.of(1985, 3, 10),null);
//        rodzina.add(jan2);
//        System.out.println("Wyszukane osoby o imieniu Jan Kowalski:");
//        for (Person p : rodzina.get("Jan Kowalski")) {
//            System.out.println(p);
//        }

        //lab6-start-------------------------------------------------------------------------
        //System.out.println(Person.fromCsvLine("Marek Kowalski,15.05.1899,25.06.1957,,"));

        osoby=Person.fromCsv("family.csv");

        //System.out.println(osoby);

//        Person.toBinaryFile(osoby, "family.bin");
//        List<Person> loadPeople = Person.fromBinaryFile("family.bin");
//        for(Person person: loadPeople)
//            System.out.println(person);

//_____________lab 7_____________________________________
        PlantUMLRunner.setPath("plantuml-1.2025.2.jar");
//        PlantUMLRunner.generate(osoby.get(0).toPlantUMLTree(), "out_uml", "family");
//        PlantUMLRunner.generate(Person.toPlantUMLTree(osoby), "out_uml", "allFamily");
//__________________lab 9 _____________________________________
        System.out.println(Person.filterByName(osoby, "Nowak"));
        System.out.println(Person.sortByBirth(osoby));
        System.out.println("zmarli:" + Person.sortByLifespanDesc(osoby));
        //osoby = Person.sortByLifespanDesc(osoby);
        System.out.println("Najstarsza żyjąca: " +Person.findOldestPersonAlive(osoby));

        PlantUMLRunner.generate(Person.toPlantUMLTreeV2(osoby,
                    str -> str + " #6BBF59"
            ), "out_uml", "familyColor");

        PlantUMLRunner.generate(Person.toPlantUMLTreeV3(osoby,
                    person -> person == Person.findOldestPersonAlive(osoby),
                    str -> str + " #6BBF59"
            ), "out_uml", "familyAlive");
    }
}