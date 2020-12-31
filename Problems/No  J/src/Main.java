import java.util.*;

public class Main {

    public static void processIterator(String[] array) {
        // write your code here
        List<String> words = Arrays.asList(array);
        ListIterator<String> wordsIterator = words.listIterator();
        List<String> output = new ArrayList<>();

        while (wordsIterator.hasNext()) {
            String current = wordsIterator.next();
            if (current.startsWith("J")) {
                current = current.substring(1);
                output.add(current);
            }
        }
        Collections.reverse(output);
        output.forEach(System.out::println);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        processIterator(scanner.nextLine().split(" "));
    }
}