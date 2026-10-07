package DSA.Coding;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UltimateArrayMerger {

    public static void main(String[] args) {
        // --- Test Data Inputs ---
        int[] primitiveArr1 = {1, 2, 3,0,0,0};
        int[] primitiveArr2 = {4, 5, 6};
        int[] primitiveWithDupes1 = {1, 2, 3, 4};
        int[] primitiveWithDupes2 = {3, 4, 5, 6};

        String[] objectArr1 = {"Java", "Scala"};
        String[] objectArr2 = {"Python", "Go"};

        System.out.println("====== PART 1: JAVA 8 FEATURES (STREAMS API) ======");
        
        // 1. Java 8: Primitive Merge
        int[] j8PrimitiveResult = mergeJava8Primitive(primitiveArr1, primitiveArr2);
        System.out.println("1. Java 8 Primitive (int[]):    " + Arrays.toString(j8PrimitiveResult));

        // 2. Java 8: Object Array Merge
        String[] j8ObjectResult = mergeJava8Object(objectArr1, objectArr2);
        System.out.println("2. Java 8 Object (String[]):    " + Arrays.toString(j8ObjectResult));

        // 3. Java 8: Merge and Remove Duplicates
        int[] j8DistinctResult = mergeJava8Distinct(primitiveWithDupes1, primitiveWithDupes2);
        System.out.println("3. Java 8 Distinct (Unique):    " + Arrays.toString(j8DistinctResult));


        System.out.println("\n====== PART 2: TRADITIONAL APPROACHES (PRE-JAVA 8) ======");
        
        // 4. Pre-Java 8: Native System.arraycopy() [Most Efficient]
        int[] classicCopyResult = mergeClassicArrayCopy(primitiveArr1, primitiveArr2);
        System.out.println("4. Classic System.arraycopy():  " + Arrays.toString(classicCopyResult));

        // 5. Pre-Java 8: Manual For Loops
        int[] classicLoopResult = mergeClassicLoops(primitiveArr1, primitiveArr2);
        System.out.println("5. Classic Manual For Loops:    " + Arrays.toString(classicLoopResult));

        // 6. Pre-Java 8: Classic Object Array Merge
        String[] classicObjectResult = mergeClassicObject(objectArr1, objectArr2);
        System.out.println("6. Classic Object (String[]):   " + Arrays.toString(classicObjectResult));
    }

    // =========================================================================
    // SECTION 1: JAVA 8 APPROACHES
    // =========================================================================

    /**
     * Java 8: Merges primitive int arrays using IntStream.concat.
     * Fast and readable, automatically optimized for primitives.
     */
    public static int[] mergeJava8Primitive(int[] arr1, int[] arr2) {
        return IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .toArray();
    }

    /**
     * Java 8: Merges non-primitive Object arrays using Stream.concat.
     * Requires passing a generator function (String[]::new) to establish the output type.
     */
    public static String[] mergeJava8Object(String[] arr1, String[] arr2) {
        return Stream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .toArray(String[]::new);
    }

    /**
     * Java 8 Bonus: Merges two arrays and filters out any duplicate items.
     * Interjects the .distinct() operator mid-stream pipeline.
     */
    public static int[] mergeJava8Distinct(int[] arr1, int[] arr2) {
        return IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .distinct()
                .toArray();
    }

    // =========================================================================
    // SECTION 2: PRE-JAVA 8 APPROACHES
    // =========================================================================

    /**
     * Pre-Java 8: Native Memory Copy.
     * Fastest standard Java approach because it triggers a native hardware-level memory block copy.
     */
    public static int[] mergeClassicArrayCopy(int[] src1, int[] src2) {
        int[] dest = new int[src1.length + src2.length];
        
        // Copy first array to beginning of destination
        System.arraycopy(src1, 0, dest, 0, src1.length);
        // Copy second array starting right where the first array ended
        System.arraycopy(src2, 0, dest, src1.length, src2.length);
        
        return dest;
    }

    /**
     * Pre-Java 8: Basic Loop-based Logic.
     * Iterates manually through arrays, using a tracking pointer to insert values.
     */
    public static int[] mergeClassicLoops(int[] src1, int[] src2) {
        int[] dest = new int[src1.length + src2.length];
        int targetPos = 0;

        for (int i = 0; i < src1.length; i++) {
            dest[targetPos] = src1[i];
            targetPos++;
        }
        for (int i = 0; i < src2.length; i++) {
            dest[targetPos] = src2[i];
            targetPos++;
        }
        
        return dest;
    }

    /**
     * Pre-Java 8: Native copy implementation scaling directly to an Object array.
     */
    public static String[] mergeClassicObject(String[] src1, String[] src2) {
        String[] dest = new String[src1.length + src2.length];
        
        System.arraycopy(src1, 0, dest, 0, src1.length);
        System.arraycopy(src2, 0, dest, src1.length, src2.length);
        
        return dest;
    }
}