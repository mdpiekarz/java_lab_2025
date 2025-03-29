import java.time.LocalDate;
import java.util.*;
public class Person implements Comparable<Person>{
    private String imie;
    private String nazwisko;
    private LocalDate dataUrodzin;
    private Set<Person> children;

    public Person(String imie, String nazwisko, LocalDate dataUrodzin) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzin = dataUrodzin;
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
        return imie + " " + nazwisko + ", urodzony: " + dataUrodzin;
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