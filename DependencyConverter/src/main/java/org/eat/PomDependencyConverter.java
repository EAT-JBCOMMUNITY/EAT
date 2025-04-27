package org.eat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PomDependencyConverter {

    /**
     * Reads a file, converts each line from 'groupId:artifactId:version' format
     * to Maven XML dependency format, and writes the output to a new file.
     *
     * @param inputFilePath  The path to the input file.
     * @param outputFilePath The path to the output file.
     * @throws IOException If an error occurs during file processing.
     */
    public static void convertDependencies(String inputFilePath, String outputFilePath) throws IOException {
        // Use try-with-resources to automatically close the reader and writer.
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            List<String> mavenDependencies = new ArrayList<>();
            String line;

            // Read each line from the input file.
            while ((line = reader.readLine()) != null) {
                // Trim the line to remove leading/trailing spaces.
                line = line.trim();
                if (!line.isEmpty()) { // Skip empty lines
                    // Convert the line to a Maven dependency XML string.
                    String mavenDependency = convertLineToMavenDependency(line);
                    mavenDependencies.add(mavenDependency);
                }
            }

            // Join all the dependencies into a single string.
            String outputContent = "<project>\n" +
                    "  <modelVersion>4.0.0</modelVersion>\n" +
                    "  <groupId>com.example</groupId>\n" +  // Added groupId, artifactId, version.
                    "  <artifactId>my-app</artifactId>\n" + //  These should be replaced with actual values.
                    "  <version>1.0-SNAPSHOT</version>\n" +
                    "  <dependencies>\n" +
                    String.join("\n", mavenDependencies) + // Add the dependencies.
                    "\n  </dependencies>\n" +
                    "</project>";

            //Write the output to the output file.
            Path outputPath = Paths.get(outputFilePath);
            Files.writeString(outputPath, outputContent);

            System.out.println("Conversion complete. Output written to " + outputFilePath);
        } catch (IOException e) {
            // Handle file-related errors (e.g., file not found, read/write errors).
            System.err.println("Error processing files: " + e.getMessage());
            throw e; // Re-throw the exception so that the caller knows that an error occurred.
        }
    }

    /**
     * Converts a single line in the format 'groupId:artifactId:version' to
     * a Maven dependency XML string.
     *
     * @param line The line to convert.
     * @return A string representing the Maven dependency in XML format.
     * Returns an empty string if the input line is invalid.
     */
    public static String convertLineToMavenDependency(String line) {
        // Split the line by the delimiter ':'.
        String[] parts = line.split(":");
        // Check if the line has the correct number of parts.
        if (parts.length == 3) {
            String groupId = parts[0].trim();
            String artifactId = parts[1].trim();
            String version = parts[2].trim();
            //basic input validation
            if(groupId.isEmpty() || artifactId.isEmpty() || version.isEmpty()){
                System.err.println("Invalid input format: Empty groupId, artifactId or version in line: " + line);
                return ""; // Return empty string for invalid input
            }
            // Construct the Maven dependency XML string.
            return "    <dependency>\n" +
                    "      <groupId>" + groupId + "</groupId>\n" +
                    "      <artifactId>" + artifactId + "</artifactId>\n" +
                    "      <version>" + version + "</version>\n" +
                    "    </dependency>";
        } else {
            // Handle invalid input format.
            System.err.println("Invalid input format: Expected 'groupId:artifactId:version', but got: " + line);
            return ""; // Return an empty string for invalid input.
        }
    }

    public static void main(String[] args) {
        // Check if the required command-line arguments are provided.
   //     if (args.length != 2) {
   //         System.out.println("Usage: java PomDependencyConverter <input_file_path> <output_file_path>");
   //         return; // Exit if the arguments are not provided.
   //     }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            // Call the method to convert the dependencies.
            convertDependencies(inputFilePath, outputFilePath);
        } catch (IOException e) {
            // The error is already handled in convertDependencies,
            // but we can add more specific error handling here if needed.
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}

