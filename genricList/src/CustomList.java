import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CustomList<T> extends AbstractList<T> {

    private class Node {
        T value;
        Node next;

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node head = null, tail = null;

    public void addLast(T value) {
        Node node = new Node(value, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
    }


    public void addFirst(T value) {
//        Node node = new Node(value, null);
//        if (head == null) {
//            head = node;
//            tail = node;
//        } else {
//            node.next = head;
//            head = node;
//        }
        Node node= new Node(value,head);
        if(head==null){
            tail=node;
        }
        head=node;
    }

    public T getFirst() {
        return (head==null)?null:head.value;
    }

    public T getLast() {
        return (head==null)?null:tail.value;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        } else if (head == tail) {
            Node temp = head;
            head = null;
            tail = null;
            return temp.value;
        } else {
            Node temp = head;
            head = head.next;
            return temp.value;
        }
    }

    public T removeLast() {
        if (head == null) {
            throw new NoSuchElementException();
        } else if (head == tail) {
            Node temp = head;
            head = null;
            tail = null;
            return temp.value;
        } else {
            Node temp = head;
//            for(;temp.next != tail;temp = temp.next); dziala, ale to jest brzydko
            while (temp.next != tail) {
                temp = temp.next;
            }
            temp.next = null;

            Node temp2 = tail;
            tail = temp;

            return temp2.value;
        }
    }

    //Zad 2

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
    }

    @Override
    public int size() {
        if (head == null) {
            return 0;
        }
        Node node = head;
        int size = 1;
        while (node != tail) {
            size++;
            node = node.next;
        }
        return size;
    }

    @Override
    public T get(int index) {
        if (head == null){ throw new NoSuchElementException();}
        Node node = head;
        for (int i = 1; i <index; i++) {
            node = node.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
        }
        return node.value;
    }

    //Zad 3

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node node = head;
            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                Node temp = node;
                node = node.next;
                return temp.value;
            }
        };
    }

    @Override
    public Stream<T> stream() {
        Stream.Builder<T> builder = Stream.builder();
        for (var i: this){
            builder.accept(i);
        }
        return builder.build();
    }

    //Zad 4
    public static <T> CustomList<T> filterByClass(CustomList<T> list, Class<?> clazz) {
        CustomList<T> filteredList = new CustomList<>();
        for (T item : list) {
            if (item.getClass().equals(clazz))
            {
                filteredList.addLast(item);
            }
        }
        return filteredList;
    }

    public static <T> CustomList<T> filterByAssignableClass(CustomList<T> list, Class<?> clazz) {
        CustomList<T> filteredList = new CustomList<>();
        for (T item : list) {
            if (clazz.isInstance(item))
            {
                filteredList.addLast(item);
            }
        }
        return filteredList;
    }

    //Zad 5

    public static <T> Predicate<T> isInOpenRange(T lowerBound, T upperBound) {
        // Metodę compareTo mżemy wywołać na rzecz obiektu typu Comparable<T>. Należy więc value, które jest
        // typu T (o którym nic nie wiemy) rzutować na Comparable<T> aby jawnie powiedzieć kompilatorowi,
        // że value to Comparable<T>
        return value -> (lowerBound == null || ((Comparable<T>) value).compareTo(lowerBound) > 0) &&
                (upperBound == null || ((Comparable<T>) value).compareTo(upperBound) < 0);
    }

    public static <T> long countElementsInRange(CustomList<T> list, T lowerBound, T upperBound, Predicate<T> predicate) {
        long count = 0;
        for (T item : list) {
            if (predicate.test(item)) {
                count++;
            }
        }
        return count;
    }

    //Zad 6
    public static <T> Comparator<CustomList<T>> compareBySize() {
        return Comparator.comparingInt(customList -> customList.size());
    }

    // Użycie komparatora do porównania CustomList liczb pod względem sumy ich elementów
    public static Comparator<CustomList<? extends Number>> compareBySum() {
        return Comparator.comparingDouble(customList ->
                customList.stream()
                        .mapToDouble(Number::doubleValue)
                        .sum());
    }

}

//    public static <T> List<T> filterByClass(List<? extends T> list, Class<?> clazz) {
//        List<T> filteredList = new ArrayList<>();
//        for (T item : list) {
//            if (clazz.isInstance(item)) {
//                filteredList.add(item);
//            }
//        }
//        return filteredList;
//    }

//    public Collection<T> toList() {
//        List<T> list = new ArrayList<>();
//        for (T item : this) {
//            list.add(item);
//        }
//        return list;
//    }
