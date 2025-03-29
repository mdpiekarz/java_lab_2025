import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

            List<Person> osoby = new ArrayList<>();
            Person jan = new Person("Jan", "Kowalski", LocalDate.of(1990, 5, 20));
            Person anna = new Person("Anna", "Nowak", LocalDate.of(1985, 10, 15));
            Person piotr = new Person("Piotr", "Zielinski", LocalDate.of(2000, 3, 8));

            osoby.add(jan);
            osoby.add(anna);
            osoby.add(piotr);

            boolean adoptionSuccess = jan.adopt(piotr);
            System.out.println("Adopcja udana: " + adoptionSuccess);

            for (Person osoba : osoby) {
                System.out.println(osoba);
            }

        Person maria = new Person("Maria", "Zielinska", LocalDate.of(2010, 6, 22));
        jan.adopt(maria);

        Person youngest = jan.getYoungestChild();
        System.out.println("Najmłodsze dziecko Jana: " + (youngest != null ? youngest : "Brak dzieci"));

        System.out.println("Dzieci Jana posortowane od najstarszego do najmłodszego:");
        for (Person child : jan.getChildren()) {
            System.out.println(child);
        }

        Family rodzina = new Family();
        rodzina.add(jan);
        rodzina.add(anna);

        //System.out.println("Wyszukana osoba: " + rodzina.get("Jan Kowalski"));

        rodzina.add(piotr,maria);
        //System.out.println("Wyszukana osoba: " + rodzina.get("Piotr Zielinski"));

        Person jan2 = new Person("Jan", "Kowalski", LocalDate.of(1985, 3, 10));
        rodzina.add(jan2);
        System.out.println("Wyszukane osoby o imieniu Jan Kowalski:");
        for (Person p : rodzina.get("Jan Kowalski")) {
            System.out.println(p);
        }

    }

}