import java.util.Scanner;
import java.util.Stack;

public class FindValidRoundBrackets {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            String sequence;
            do {
                System.out.print("Введите последовательность из круглых скобок для поиска валидных: ");
                sequence = scanner.nextLine();
                sequence = sequence.replaceAll("\\s+", "").trim(); // уберем лишние пробелы

                if (!sequence.matches("[()]*")) // проверим, что строка состоит только из круглых скобок
                    System.out.println("Введённая строка имеет символы отличные от '(' и ')'.");
            } while (!sequence.matches("[()]*"));

            String mode;
            do {
                System.out.print("Выберите вариант работы (first - первая последовательность, all - все последовательности): ");
                mode = scanner.nextLine();
            } while (!"first".equalsIgnoreCase(mode) && !"all".equalsIgnoreCase(mode));

            FindValidBrackets(sequence, mode);
        }
    }

    public static void FindValidBrackets(String sequence, String mode) {

        final int firstValidBracketPosition = sequence.indexOf("("); // если ни одной открывающей - дальше не ищем
        if (firstValidBracketPosition == -1) {
            System.out.println("0");
            return;
        }

        Stack<Character> stack = new Stack<>();
        int level = 0; // открывающая скобка - увеличиваем, закрывающая - уменьшаем

        for (int i = firstValidBracketPosition; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (c == '(') {
                stack.push(c);
                level++;
            } else if (--level < 0) {
                if ("all".equalsIgnoreCase(mode)) {
                    stack.push(' '); // меняем некорректную скобку на пробел для сплита
                    level++; // восстанавливаем level для дальнейшего поиска
                } else // если нужна только первая, то после лишней закрывающей ничего не ищем
                    break;
            } else {
                stack.push(c);
            }
        }

        StringBuilder result = new StringBuilder();
        level = 0;
        while (!stack.isEmpty()) { // просмотрим строку в обратную сторону
            char c = stack.pop();
            if (c == ' ') {
                result.append(c);
            } else if (c == ')') {
                result.append(c);
                level++;
            } else {
                if (level > 0) {
                    result.append(c);
                    level--;
                } else
                    result.append(' '); // меняем некорректную скобку на пробел для сплита
            }
        }

        // перевернём строку, удалим лишние пробелы и разделим на последовательности
        String[] words = result.reverse().toString().replaceAll("\\s+", " ").trim().split(" ");
        for (String word : words) {
            if (word.length() == 0)
                System.out.println("0");
            else
                System.out.printf("%d - %s%n", word.length(), word);
            if ("first".equalsIgnoreCase(mode))
                break;
        }
    }
}