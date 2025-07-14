package org.example.thalassa;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
* Needed before executing the testcase : keytool -import -trustcacerts -file ./digitalworlds-top.pem -alias myRootCA -keystore "$JAVA_HOME/lib/security/cacerts"
**/
@EAT({"modules/testcases/jdkAll/DigitalWorlds/thalassa/src/main/java"})
public class PageAccessTest {

    private String fetchHtmlContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    @Test
    void testHtmlBaseThalassaAttributeExists() {
        try {
            String PAGE_URL = "https://www.digitalworlds.top/thalassa"; 
            String htmlContent = fetchHtmlContent(PAGE_URL);
            System.out.println(htmlContent);
            Pattern pattern = Pattern.compile("<base href=\"/thalassa/\">", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(htmlContent);
            assertTrue(matcher.find(), "HTML tag should have a valid 'thalassa' attribute for accessibility.");
        } catch (IOException e) {
            fail("Failed to fetch or parse HTML content: " + e.getMessage());
        }
    }

    @Test
    void testPageHasTitleHeading() {
        try {
            String PAGE_URL = "https://www.digitalworlds.top/thalassa"; 
            String htmlContent = fetchHtmlContent(PAGE_URL);
            System.out.println(htmlContent);
            // Very basic check for presence of an H1 tag. Doesn't check if it's the *first* heading.
            Pattern h1Pattern = Pattern.compile("<title[^>]*>app</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher h1Matcher = h1Pattern.matcher(htmlContent);
            assertTrue(h1Matcher.find(), "Page should contain at least one <title> heading for proper structure.");
        } catch (IOException e) {
            fail("Failed to fetch or parse HTML content: " + e.getMessage());
        }
    }


}
