import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextAnalyzer {

    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);
        
        System.out.println("--- Java Text File Analyzer ---");
        System.out.print("Enter the path to your .txt file: ");
        String filePath = consoleScanner.nextLine();
        
        analyzeFile(filePath);
        
        consoleScanner.close();
    }

    public static void analyzeFile(String path) {
        StringBuilder content = new StringBuilder();
        int lineCount = 0;

        // 1. Reading the File
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
                lineCount++;
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read file. Please check the path.");
            return;
        }

        String fullText = content.toString().trim();

        if (fullText.isEmpty()) {
            System.out.println("The file is empty.");
            return;
        }

        // 2. Calculating Statistics
        int wordCount = countWords(fullText);
        int sentenceCount = countSentences(fullText);
        int charCount = fullText.replaceAll("\\s", "").length();
        Map.Entry<String, Integer> mostFrequent = findMostFrequentWord(fullText);

        // 3. Displaying Results
        System.out.println("\n--- Analysis Results ---");
        System.out.println("Total Lines:     " + lineCount);
        System.out.println("Total Sentences: " + sentenceCount);
        System.out.println("Total Words:     " + wordCount);
        System.out.println("Total Characters (no spaces): " + charCount);
        
        if (mostFrequent != null) {
            System.out.println("Most Frequent Word: '" + mostFrequent.getKey() + 
                               "' (appeared " + mostFrequent.getValue() + " times)");
        }
        System.out.println("-------------------------");
    }

    private static int countWords(String text) {
        // Splitting by one or more whitespace characters
        String[] words = text.split("\\s+");
        return words.length;
    }

    private static int countSentences(String text) {
        // Splitting by punctuation: . ! or ?
        String[] sentences = text.split("[.!?]+");
        return sentences.length;
    }

    private static Map.Entry<String, Integer> findMostFrequentWord(String text) {
        // Clean text: lowercase and remove non-alphabetic characters
        String cleanText = text.toLowerCase().replaceAll("[^a-z ]", "");
        String[] words = cleanText.split("\\s+");
        
        Map<String, Integer> wordMap = new HashMap<>();

        for (String word : words) {
            if (word.length() > 1) { // Ignore single letters like 'a' or 'i' if desired
                wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
            }
        }

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }
}
