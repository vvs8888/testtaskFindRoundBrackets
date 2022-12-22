import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindValidRoundBrackets {
    private static final Pattern pattern = Pattern.compile("[()]*");

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            String sequence;
            Matcher matcher;

            do {
                System.out.print("Введите последовательность из круглых скобок для поиска валидных: ");
                sequence = scanner.nextLine();
                sequence = sequence.replaceAll("\\s+", "").trim(); // уберем лишние пробелы

                matcher = pattern.matcher(sequence);
                if (!matcher.matches()) {// проверим, что строка состоит только из круглых скобок
                    System.out.println("Введённая строка имеет символы отличные от '(' и ')'.");
                }
            } while (!matcher.matches());

            String mode;
            do {
                System.out.print("Выберите вариант работы (first - первая последовательность, all - все последовательности): ");
                mode = scanner.nextLine();
            } while (!"first".equalsIgnoreCase(mode) && !"all".equalsIgnoreCase(mode));

            findValidBrackets(sequence, mode);
        }
    }

    private static void findValidBrackets(String sequence, String mode) {

        final int firstValidBracketPosition = sequence.indexOf("("); // если ни одной открывающей - дальше не ищем

        if (firstValidBracketPosition == -1) {
            System.out.println("0");
            return;
        }

        // если есть открывающая скобка, тогда ищем по алгоритму
        printResult( fillResult( fillStack(sequence, mode, firstValidBracketPosition) ), mode );
    }

    private static Stack<Character> fillStack(String sequence, String mode, int firstValidBracketPosition) {

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
                } else {// если нужна только первая, то после лишней закрывающей ничего не ищем
                    break;
                }
            } else {
                stack.push(c);
            }
        }

        return stack;
    }

    private static String fillResult(Stack<Character> stack) {

        StringBuilder result = new StringBuilder();
        int level = 0;

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
                } else {
                    result.append(' '); // меняем некорректную скобку на пробел для сплита
                }
            }
        }

        return result.reverse().toString().replaceAll("\\s+", " ").trim(); // перевернём строку и удалим лишние пробелы
    }

    private static void printResult(String sequence, String mode) {

        String[] words = sequence.split(" "); // разделим на последовательности

        for (String word : words) {
            if (word.length() == 0) {
                System.out.println("0");
            }
            else {
                System.out.printf("%d - %s%n", word.length(), word);
            }
            if ("first".equalsIgnoreCase(mode)) {
                break;
            }
        }
    }
}