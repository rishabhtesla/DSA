package DSA.Coding.Agoda;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * ============================================================================
 * QUESTION 1: COUNTRY POPULATION DENSITY API
 * ============================================================================
 * Problem Statement:
 * Query an API for country information and calculate population density[cite: 1].
 * Make a request to https://jsonmock.hackerrank.com/api/countries?name={name}
 * where {name} is the country you are searching for[cite: 1].
 *
 * The API returns a JSON object where the 'data' field contains either:
 *   - An empty array if the country is not found[cite: 1].
 *   - An array with a single object containing country details[cite: 1].
 *
 * When a country is found, the relevant properties are:
 *   - population: the number of people in the country[cite: 1].
 *   - area: the land area of the country[cite: 1].
 *
 * Formula:
 *   populationDensity = population / area[cite: 1]
 *
 * Return:
 *   - Round the result to the nearest integer[cite: 1, 4].
 *   - If the country is not found (empty data array), return -1[cite: 1, 4].
 *
 * Constraints:
 *   - There will be either 1 or 0 records in data[cite: 4].
 * ============================================================================
 */

class CountryDensityResult {

    /*
     * ------------------------------------------------------------------------
     * LOGIC EXPLANATION:
     * ------------------------------------------------------------------------
     * 1. URL Encoding:
     *    Countries with spaces (e.g., "Sri Lanka") must be percent-encoded to 
     *    prevent malformed URI exceptions during HTTP request building.
     *
     * 2. HTTP Request:
     *    Uses Java 11+ java.net.http.HttpClient directly to avoid depending on 
     *    external JSON libraries (like Jackson or Gson), ensuring seamless 
     *    compilation inside HackerRank's default sandbox.
     *
     * 3. Response Parsing via Regex:
     *    - Locates the "data" JSON key and checks the enclosing array [ ... ].
     *    - If empty (i.e. "[]"), returns -1 immediately[cite: 1, 4].
     *    - Extracts the values of "population" and "area" via regular expressions.
     *
     * 4. Calculation & Rounding:
     *    - Performs division using floating-point types: (double) population / (double) area.
     *    - Uses Math.round() to round to the nearest whole integer[cite: 1, 4].
     *
     * ------------------------------------------------------------------------
     * DRY RUN:
     * ------------------------------------------------------------------------
     * Input: name = "india"
     *
     * Step 1: Encoded URI -> "https://jsonmock.hackerrank.com/api/countries?name=india"
     * Step 2: HTTP GET call executes and returns response body.
     * Step 3: Check "data" field:
     *         dataContent contains country object -> not empty.
     * Step 4: Regex extracts:
     *         population = 1275840000.0[cite: 3]
     *         area = 3287590.0[cite: 3]
     * Step 5: Division:
     *         1275840000.0 / 3287590.0 = 388.07758...[cite: 3]
     * Step 6: Math.round(388.07758...) = 388[cite: 3]
     * Return: 388[cite: 3]
     *
     * Edge Case (Country Not Found):
     * Input: name = "notacountry"
     * Step 1: API returns { "page": 1, "data": [] }
     * Step 2: Content inside [ ... ] is empty.
     * Return: -1[cite: 1, 4]
     * ------------------------------------------------------------------------
     */
    public static int getPopulationDensity(String name) {
        if (name == null || name.trim().isEmpty()) {
            return -1;
        }

        try {
            String encodedName = URLEncoder.encode(name.trim(), StandardCharsets.UTF_8);
            String url = "https://jsonmock.hackerrank.com/api/countries?name=" + encodedName;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // Locate "data" array
            int dataIndex = json.indexOf("\"data\"");
            if (dataIndex == -1) {
                return -1;
            }

            int openBracket = json.indexOf('[', dataIndex);
            int closeBracket = json.lastIndexOf(']');
            if (openBracket == -1 || closeBracket == -1 || openBracket >= closeBracket) {
                return -1;
            }

            String dataContent = json.substring(openBracket + 1, closeBracket).trim();
            if (dataContent.isEmpty()) {
                return -1;
            }

            // Extract numeric values for population and area
            Pattern popPattern = Pattern.compile("\"population\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)");
            Pattern areaPattern = Pattern.compile("\"area\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)");

            Matcher popMatcher = popPattern.matcher(dataContent);
            Matcher areaMatcher = areaPattern.matcher(dataContent);

            if (popMatcher.find() && areaMatcher.find()) {
                double population = Double.parseDouble(popMatcher.group(1));
                double area = Double.parseDouble(areaMatcher.group(1));

                if (area <= 0) {
                    return -1;
                }

                return (int) Math.round(population / area);
            }

            return -1;
        } catch (Exception e) {
            return -1;
        }
    }
}

public class CountryPopulationDensitySolution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String outputPath = System.getenv("OUTPUT_PATH");
        BufferedWriter bufferedWriter;
        if (outputPath != null) {
            bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
        } else {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        String name = bufferedReader.readLine();

        int result = CountryDensityResult.getPopulationDensity(name);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}