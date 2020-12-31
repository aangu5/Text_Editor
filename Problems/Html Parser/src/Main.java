import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    static String tagPattern = "<.*>(.*)<.*>";
    static String childPattern = ".+(<.*>.*<.*>).+";

    public static void main(String[] args) throws IOException {
        // put your code here
        List<String> list = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        processElement(inputString);
    }

    public static void processElement(String input) {
        Pattern pattern = Pattern.compile(tagPattern);
        Matcher matcher = pattern.matcher(input);
        if (!containsChild(input)) {
            System.out.println(matcher.group(1));
        } else {
            processChild(input);
            System.out.println(input);
        }
    }

    public static boolean containsChild(String input) {
        return input.matches(childPattern);
    }

    public static String removeTags(String input) {
        Pattern pattern = Pattern.compile(tagPattern);
        Matcher matcher = pattern.matcher(input);
        //noinspection ResultOfMethodCallIgnored
        matcher.matches();
        return matcher.group(1);
    }

    public static String processChild(String input) {
        if (containsChild(input)) {
            processChild(input);
        }
        Pattern pattern = Pattern.compile(childPattern);
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        return matcher.group(2);
    }
}