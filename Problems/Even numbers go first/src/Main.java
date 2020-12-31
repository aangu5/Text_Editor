import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int total = scanner.nextInt();
        Deque<Integer> queue = new ArrayDeque<>();
        while (scanner.hasNextInt()) {
            int temp = scanner.nextInt();
            if (temp % 2 == 0) {
                queue.offerFirst(temp);
            } else {
                queue.offerLast(temp);
            }
        }
        queue.forEach(System.out::println);
    }
}