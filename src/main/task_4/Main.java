import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        // 1. Лямбда выражение для интерфейса Printable.
        System.out.println("\n===== 1 =====\n");
        Printable printable = () -> System.out.println("Printable");
        printable.print();

        // 2. Проверка пустой строки.
        System.out.println("\n===== 2 =====\n");
        Predicate<String> notNull = str -> str != null;
        Predicate<String> notEmpty = str -> !str.isEmpty();
        Predicate<String> isValid = notNull.and(notEmpty);

        String testString1 = "string";
        String testString2 = "";
        String testString3 = null;

        List<String> stringList1 = Arrays.asList(testString1, testString2, testString3);
        stringList1.stream()
                .filter(isValid)
                .forEach(System.out::println);

        // 3. Проверка строки.
        System.out.println("\n===== 3 =====\n");
        String testString4 = "JANNA";
        String testString5 = "LANNA";
        String testString6 = "JANNI";

        Predicate<String> startsWithJorN = str -> str.startsWith("J") || str.startsWith("N");
        Predicate<String> endsWithA = str -> str.endsWith("A");
        Predicate<String> stringPredicate = startsWithJorN.and(endsWithA);

        List<String> stringList2 = Arrays.asList(testString4, testString5, testString6);
        stringList2.stream()
                .filter(stringPredicate)
                .forEach(System.out::println);

        // 4. Лямбда выражение для HeavyBox.
        System.out.println("\n===== 4 =====\n");
        HeavyBox box = new HeavyBox(52);
        Consumer<HeavyBox> shipped = newBox -> System.out
                .println("The box with weight " + newBox.getWeight() + " was shipped");
        Consumer<HeavyBox> sent = newBox -> System.out
                .println("We send a box with weight " + newBox.getWeight());
        Consumer<HeavyBox> deliverProcess = shipped.andThen(sent);

        deliverProcess.accept(box);

        // 5. Лямбда для Function.
        System.out.println("\n===== 5 =====\n");
        Function<Integer, String> numberFunction = num -> {
            if (num > 0)
                return "Positive number";
            else if (num < 0)
                return "Negative number";
            else
                return "Zero";
        };

        System.out.println(numberFunction.apply(1));
        System.out.println(numberFunction.apply(-1));
        System.out.println(numberFunction.apply(0));

        // 6. Лямбда для Supplier.
        System.out.println("\n===== 6 =====\n");
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(11);
        System.out.println("Random 0 to 10 " + randomSupplier.get());
        System.out.println("Random 0 to 10 " + randomSupplier.get());
        System.out.println("Random 0 to 10 " + randomSupplier.get());

        // Задание 1: Кастомная аннотация @DeprecatedEx
        System.out.println("\n===== 7 =====\n");
        AnnotationProcessor.processDeprecatedEx(OldSevice.class);

        // Задание 2: Кастомная сериализация в JSON с аннотацией @JsonField ***
        System.out.println("\n===== 8 =====\n");
        Account acc = new Account("Lisich");
        System.out.println(JsonSerializer.toJson(acc));
    }
}
