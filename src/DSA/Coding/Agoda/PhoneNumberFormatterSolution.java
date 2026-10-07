package DSA.Coding.Agoda;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * ============================================================================
 * QUESTION 2: Country Phone Number Formatter
 * ============================================================================
 * Given a country name and a phone number:
 *   1. Query the API at:
 *      https://jsonmock.hackerrank.com/api/countries?name=<country>
 *   2. If multiple calling codes exist, choose the code at the HIGHEST INDEX (the last one).
 *   3. Format the phone number as: "+<Calling Code> <Phone Number>" (e.g., "+1 765355443").
 *   4. Return "-1" if the country data array is empty or no valid calling code is found.
 * 
 * ============================================================================
 * APPROACH & LOGIC
 * ============================================================================
 * 1. URL Encoding:
 *    Country names can contain spaces (e.g., "United States"). Encode using URLEncoder.
 * 
 * 2. HTTP Request:
 *    Use Java's built-in `HttpURLConnection` to execute a GET request and read response body.
 * 
 * 3. JSON Parsing:
 *    Avoid external dependency issues by parsing via substring and Regex:
 *      - Verify `"data":[]` is not empty.
 *      - Locate the section `"callingCodes":[ ... ]`.
 *      - Match all quoted elements inside using regex `\"([^\"]+)\"`.
 *      - Track the last matched group (which gives the calling code at the highest index).
 * 
 * 4. Error Handling:
 *    Wrap in a try-catch block to handle non-200 responses, network timeouts, or malformed JSON,
 *    defaulting to "-1".
 * 
 * ============================================================================
 * DRY RUN: country = "Afghanistan", phoneNumber = "656445445"
 * ============================================================================
 * - URL: https://jsonmock.hackerrank.com/api/countries?name=Afghanistan
 * - Response contains:
 *     "data": [
 *       {
 *         "name": "Afghanistan",
 *         "callingCodes": ["93"],
 *         ...
 *       }
 *     ]
 * - "data":[] check: False (records exist).
 * - callingCodes substring: ["93"]
 * - Regex extracts: "93". Since it's the only one, highest index = "93".
 * - Result formatted: "+" + "93" + " " + "656445445" -> "+93 656445445"
 * ============================================================================
 */

public class PhoneNumberFormatterSolution {

    public static String getPhoneNumbers(String country, String phoneNumber) {
        try {
            // Encode the country parameter to safely handle spaces and special symbols
            String encodedCountry = URLEncoder.encode(country, StandardCharsets.UTF_8);
            String urlStr = "https://jsonmock.hackerrank.com/api/countries?name=" + encodedCountry;

            URI uri = URI.create(urlStr);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != 200) {
                return "-1";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            String response = responseBuilder.toString();

            // Check if country record was not found
            if (response.contains("\"data\":[]")) {
                return "-1";
            }

            // Locate callingCodes segment
            int codesHeaderIndex = response.indexOf("\"callingCodes\":[");
            if (codesHeaderIndex == -1) {
                return "-1";
            }

            int startIndex = codesHeaderIndex + "\"callingCodes\":[".length();
            int endIndex = response.indexOf("]", startIndex);
            if (endIndex == -1) {
                return "-1";
            }

            String callingCodesContent = response.substring(startIndex, endIndex).trim();
            if (callingCodesContent.isEmpty()) {
                return "-1";
            }

            // Extract all string values inside callingCodes; keep track of the last element
            Matcher matcher = Pattern.compile("\"([^\"]+)\"").matcher(callingCodesContent);
            String targetCallingCode = null;
            while (matcher.find()) {
                targetCallingCode = matcher.group(1);
            }

            if (targetCallingCode == null || targetCallingCode.trim().isEmpty()) {
                return "-1";
            }

            return "+" + targetCallingCode + " " + phoneNumber;

        } catch (Exception e) {
            return "-1";
        }
    }

    public static void main(String[] args) {
        System.out.println(getPhoneNumbers("Afghanistan", "656445445")); 
        // Expected output: +93 656445445
    }
}