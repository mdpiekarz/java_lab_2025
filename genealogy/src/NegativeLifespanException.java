public class NegativeLifespanException extends Exception {
    public NegativeLifespanException(Person person) {
        super(person.getImie() + " " + person.getNazwisko() + " urodzony w  " + person.getDataUrodzin() + " i zmarł w "+ person.getDataSmierci()+
                ". Wykryto możliwą lukę czasoprzestrzenną.");
    }

}