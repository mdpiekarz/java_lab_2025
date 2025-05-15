import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        CustomList<Integer> list = new CustomList<>();
        list.addLast(11);
        list.addLast(3);
        list.addLast(7);
        System.out.println(list.size());
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        //System.out.println(list.get(60));

        list.stream()
                .map(o -> o + "_")
               .forEach(System.out::println);

        // Tworzymy listę obiektów różnych klas
        CustomList<Object> mixedList = new CustomList<>();
        mixedList.addLast("Hello");
        mixedList.addLast(42);
        mixedList.addLast(3.14);
        mixedList.addLast("World");
        mixedList.addLast(true);

        //CustomList<Object> filteredList = CustomList.filterByClass(mixedList, String.class);
        //Number
        CustomList<Object> filteredList = CustomList.filterByAssignableClass(mixedList, Number.class);
        System.out.println("Lista po filtracji:");
        for (var value : filteredList)
            System.out.println(value);

//        // Przykładowe granice przedziału
//        Integer lowerBound = 0;
//        Integer upperBound = 10;
//
//        // Wywołanie metody isInOpenRange, aby utworzyć predykat dla określonego przedziału
//        Predicate<Integer> inRangePredicate = CustomList.isInOpenRange(lowerBound, upperBound);
//
//        // Wywołanie metody countElementsInRange, aby zliczyć elementy w określonym przedziale
//        long count = CustomList.countElementsInRange(list, lowerBound, upperBound, inRangePredicate);
//        System.out.println("Liczba elementów w przedziale: " + count);
//
//        CustomList<Integer> list1 = new CustomList<>();
//        list1.add(1);
//        list1.add(70);
//        CustomList<Integer> list2 = new CustomList<>();
//        list2.add(1);
//        list2.add(2);
//        list2.add(3);
//
//        // Porównanie CustomList pod względem liczby elementów
//        Comparator<CustomList<Integer>> sizeComparator = CustomList.compareBySize();
//        System.out.println("Porównanie CustomList pod względem liczby elementów:");
//        System.out.println(sizeComparator.compare(list1, list2));
//
//        // Porównanie CustomList liczb pod względem sumy ich elementów
//        Comparator<CustomList<? extends Number>> sumComparator = CustomList.compareBySum();
//        System.out.println("\nPorównanie CustomList liczb pod względem sumy:");
//        System.out.println(sumComparator.compare(list1, list2));
    }
}