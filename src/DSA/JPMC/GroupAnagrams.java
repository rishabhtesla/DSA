package DSA.JPMC; /**
 * PROBLEM: Group Anagrams
 * Given an array of strings strs, group the anagrams together. 
 * An Anagram is a word formed by rearranging the letters of a different word.
 * 
 * EXAMPLE:
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 
 * LOGIC:
 * Two words are anagrams if and only if their sorted character arrays are strictly equal.
 * "eat", "tea", "ate" all sort to exactly -> "aet".
 * We can use this sorted string as a Key in a HashMap. 
 * The Value will be a List of original strings that map to that key.
 * 
 * DRY RUN: ["eat", "tea", "tan"]
 * - "eat" -> Sorts to "aet". Map: {"aet": ["eat"]}
 * - "tea" -> Sorts to "aet". Key exists! Add to list. Map: {"aet": ["eat", "tea"]}
 * - "tan" -> Sorts to "ant". Map: {"aet": ["eat", "tea"], "ant": ["tan"]}
 * Return the values of the map as a list of lists.
 * 
 * COMPLEXITY: Time: O(N * K log K) where N is the number of strings and K is max string length 
 * (due to sorting each string). Space: O(N * K) to store the Map.
 */
import java.util.*;

public class GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        
        Map<String, List<String>> map = new HashMap<>();
        
        for (String word : strs) {
            // Convert to char array and sort it
            char[] characters = word.toCharArray();
            Arrays.sort(characters);
            
            // Rebuild sorted string to use as a HashMap key
            String sortedWord = new String(characters);
            
            // If the key doesn't exist, create an empty list for it
            if (!map.containsKey(sortedWord)) {
                map.put(sortedWord, new ArrayList<>());
            }
            
            // Add the ORIGINAL word to the appropriate group
            map.get(sortedWord).add(word);
        }
        
        // Return a list containing all the grouped lists
        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Grouped Anagrams: " + groupAnagrams(strs));
        // Output: [[eat, tea, ate], [bat], [tan, nat]] (Order of inner lists may vary)
    }
}