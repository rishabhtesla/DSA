package DSA.Coding.streams;

import java.util.stream.Collectors;

public class RemoveDuplicatesIntStream {
    public static void main(String[] args) {
        String input = "dacefda"; // Example string with repeating 'd' and 'a' [00:00:22]
        
        // 1. input.chars() creates an IntStream of character code points [00:00:50]
        // 2. .distinct() filters out duplicate integer values [00:01:01]
        // 3. .mapToObj(c -> (char) c) converts the primitive ints back into Character objects [00:01:44]
        // 4. .map(String::valueOf) turns each Character into a String
        // 5. .collect(Collectors.joining()) merges them back into the final string
        String result = input.chars()
                .distinct()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .collect(Collectors.joining());

        // Output: dacefg (duplicates 'd' and 'a' removed in order) [00:02:07]
        System.out.println(result); 
    }
}