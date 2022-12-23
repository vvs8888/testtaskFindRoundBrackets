import java.util.Stack;

public class FindValidBrackets {

    private static final String sequence = "())(()())(()";

    public static void main(String[] args) {
        Stack<Character> stack = new Stack<>();
        int level = 0;

        for (char c: sequence.toCharArray()) { // Отсечем все непарные закрывающие скобки
            if (c == '(') {
                stack.push(c);
                level++;
            } else if (c == ')' && level > 0) {
                stack.push(c);
                level--;
            }
        }

        StringBuilder result = new StringBuilder();
        level = 0;
        while (!stack.isEmpty()) {  // Отсечем все непарные открывающие скобки
            char c = stack.pop();
            if (c == ')') {
                result.append(c);
                level++;
            } else if(level > 0) {
                result.append(c);
                level--;
            }
        }

        System.out.printf("%d - %s", result.length(), result.reverse());
    }
}