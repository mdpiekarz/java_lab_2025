public class AmbiguousPersonException extends Exception {
    public AmbiguousPersonException(String name) {
        super("Znaleziono więcej niż jedną osobę o imieniu: " + name);
    }
}
