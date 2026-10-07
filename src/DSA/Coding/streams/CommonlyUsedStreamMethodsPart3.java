package DSA.Coding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CommonlyUsedStreamMethodsPart3 {
    public static void main(String[] args) {
        
        // ====================================================================================
        // CONTEXT & SUMMARY: Commonly Used Stream Methods (Part 3)
        // 
        // This video completes the "all-in-one" revision roadmap by tracking element searchers, 
        // max/min arithmetic reduction operators, array converters, and infinite stream factories [00:00:13].
        // ====================================================================================

        List<String> nameList = Arrays.asList("Harry", "Ron", "Hermione");
        List<Integer> numList = Arrays.asList(1, 2, 3, 4);

        // ------------------------------------------------------------------------------------
        // 1. findFirst() -> Returns the absolute first element encountered inside the stream [00:00:26].
        // ------------------------------------------------------------------------------------
        System.out.println("1. findFirst():");
        String first = nameList.stream().findFirst().get(); // Returns an Optional, extract with .get() [00:00:57, 00:1:33]
        System.out.println("First element: " + first); // Output: Harry


        // ------------------------------------------------------------------------------------
        // 2. findAny() -> Returns any arbitrary element from the stream; optimized for parallel tasks [00:01:41].
        // ------------------------------------------------------------------------------------
        System.out.println("\n2. findAny():");
        String any = nameList.stream().findAny().get(); // [00:02:28]
        System.out.println("Any element: " + any);


        // ------------------------------------------------------------------------------------
        // 3. max(Comparator) -> Pulls the highest value matching the evaluation of a comparator [00:02:44].
        // ------------------------------------------------------------------------------------
        System.out.println("\n3. max():");
        int maxVal = numList.stream().max(Integer::compareTo).get(); // Evaluates against comparison indexes [00:03:33, 00:04:25]
        System.out.println("Maximum value: " + maxVal); // Output: 4


        // ------------------------------------------------------------------------------------
        // 4. min(Comparator) -> Pulls the absolute lowest value matching the comparison indexes [00:04:25].
        // ------------------------------------------------------------------------------------
        System.out.println("\n4. min():");
        int minVal = numList.stream().min(Integer::compareTo).get(); // [00:05:05]
        System.out.println("Minimum value: " + minVal); // Output: 1


        // ------------------------------------------------------------------------------------
        // 5. toArray(IntFunction) -> Accumulates stream elements into a traditional primitive/object array [00:05:17].
        // ------------------------------------------------------------------------------------
        System.out.println("\n5. toArray():");
        String[] namesArray = nameList.stream()
                .toArray(String[]::new); // Uses array constructor method reference [00:06:12]
        System.out.println("Converted Array: " + Arrays.toString(namesArray));


        // ------------------------------------------------------------------------------------
        // 6. generate(Supplier) -> Factory that generates an infinite stream of stateless values [00:07:20].
        // ------------------------------------------------------------------------------------
        System.out.println("\n6. generate() (Limited):");
        Stream.generate(Math::random) // Infinite generator engine [00:08:08]
                .limit(3)             // Binds infinite sequence loop safely to avoid runaway thread crashes [00:08:29, 00:08:54]
                .forEach(System.out::println);


        // ------------------------------------------------------------------------------------
        // 7. iterate(T seed, UnaryOperator) -> Generates infinite streams following a strict state pattern [00:09:00].
        // ------------------------------------------------------------------------------------
        System.out.println("\n7. iterate() (Generates progressive multiples of 7):");
        Stream.iterate(0, n -> n + 7) // Starts at 0, adds 7 sequentially to the previous number state [00:09:54, 00:10:06]
                .limit(5)             // Binds infinite sequence loop safely [00:10:06]
                .forEach(n -> System.out.print(n + " ")); // Output: 0 7 14 21 28 [00:11:14]
        System.out.println();


        // ------------------------------------------------------------------------------------
        // 8. Stream.of(T... values) -> Creates a custom standalone stream from sequential parameters [00:11:14].
        // ------------------------------------------------------------------------------------
        System.out.println("\n8. Stream.of():");
        Stream.of("A", "B", "C").forEach(System.out::print); // Custom value list engine [00:11:53, 00:12:31]
        System.out.println();


        // ------------------------------------------------------------------------------------
        // 9. Stream.concat(Stream, Stream) -> Appends two streams together into a single pipeline [00:12:31].
        // ------------------------------------------------------------------------------------
        System.out.println("\n9. Stream.concat():");
        Stream<String> s1 = Stream.of("Part 1 ");
        Stream<String> s2 = Stream.of("Part 2");
        Stream.concat(s1, s2).forEach(System.out::print); // [00:13:26, 00:13:46]
        System.out.println();


        // ------------------------------------------------------------------------------------
        // 10. reduce(BinaryOperator) -> Accumulates elements sequentially into a single outcome value [00:13:54].
        // ------------------------------------------------------------------------------------
        System.out.println("\n10. reduce():");
        Optional<Integer> sum = numList.stream()
                .reduce((a, b) -> a + b); // Accumulates values using addition rules [00:14:18, 00:14:58]
        System.out.println("Reduced Sum Value = " + sum.get()); // Output: 10 [00:15:14]
    }
}