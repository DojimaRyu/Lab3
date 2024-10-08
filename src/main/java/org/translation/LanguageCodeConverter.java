package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    // [Modified]
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> codes = new ArrayList<>();

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // [Modified]
            for (String line : lines.subList(1, lines.size())) {
                String[] curr = line.split("\t+");
                String name = curr[0];
                String code = curr[1].toLowerCase();

                this.names.add(name);
                this.codes.add(code);

            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // [Modified]
        int i = 0;
        String answer = null;
        while (i < getNumLanguages()) {
            if (this.codes.get(i).equals(code)) {
                answer = this.names.get(i);
            }

            i++;
        }

        return answer;
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // [Modified]
        int i = 0;
        String answer = null;
        while (i < getNumLanguages()) {
            if (this.names.get(i).equals(language)) {
                answer = this.codes.get(i);
            }

            i++;
        }

        return answer;
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // [Modified]
        return this.names.size();
    }
}
