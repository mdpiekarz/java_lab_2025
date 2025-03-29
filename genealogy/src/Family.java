import java.util.*;
public class Family {
    //private Map<String, Person> members;   //Zad 6
    private Map<String, List<Person>> members;

    public Family() {
        this.members = new HashMap<>();
    }

// Zad 6
//    public void add(Person person) {
//        String key = person.toString().split(",")[0];
//        members.put(key, person);
//    }

// Zad 7
//    public void add(Person... people) {
//        for (Person person : people) {
//            String key = person.toString().split(",")[0];
//            members.put(key, person);
//        }
//    }


// zad 6
//    public Person get(String key) {
//        return members.get(key);
//    }

// Zad 8
    public void add(Person... people) {
        for (Person person : people) {
            String key = person.toString().split(",")[0];
            members.putIfAbsent(key, new ArrayList<>());
            members.get(key).add(person);
            //members.get(key).sort(Comparator.naturalOrder());
        }
    }

//Zad 8
    public Person[] get(String key) {
        List<Person> persons = members.getOrDefault(key, new ArrayList<>());
        persons.sort(Comparator.naturalOrder());
        return persons.toArray(new Person[1]);
    }
    //W metodzie toArray(T[] a), jeśli przekazany argument (new Person[0]) ma rozmiar mniejszy niż liczba elementów w persons, to metoda utworzy nową tablicę odpowiedniego rozmiaru i zwróci ją.
    //W przeciwnym razie (jeśli tablica byłaby wystarczająco duża), metoda po prostu użyłaby przekazanej tablicy.
}
