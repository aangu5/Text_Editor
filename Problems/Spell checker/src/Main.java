import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int dictLen = scanner.nextInt();
        Set<String> dict = new TreeSet<>();
        for (int i = 0; i < dictLen; i++) {
            dict.add(scanner.next().toLowerCase());
        }
        int wordsLen = scanner.nextInt();
        Set<String> badWords = new TreeSet<>();
        while (scanner.hasNext()) {
            String currentWord = scanner.next().toLowerCase();
            if (!dict.contains(currentWord) && !badWords.contains(currentWord)) {
                System.out.println(currentWord);
                badWords.add(currentWord);
            }
        }
    }
}