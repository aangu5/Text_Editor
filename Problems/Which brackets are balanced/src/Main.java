import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> valid = new LinkedHashMap<>();
        valid.put('(', ')');
        valid.put('{', '}');
        valid.put('[', ']');

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            if (current == '(' || current == '{' || current == '[') {
                stack.push(current);
            } else {
                char check = 0;
                try {
                    check = stack.pop();
                } catch (EmptyStackException e) {
                    System.out.println("false");
                    System.exit(0);
                }
                if (!(valid.get(check) == current)) {
                    System.out.println("false");
                    System.exit(0);
                }
            }
        }
        System.out.println(stack.isEmpty());
    }
}