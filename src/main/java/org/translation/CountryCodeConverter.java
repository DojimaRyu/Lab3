package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // [Modified]
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> codes = new ArrayList<>();

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // [Modified]
            for (String line : lines.subList(1, lines.size())) {
                String[] curr = line.split("\t+");

                String name = curr[0];
                String alpha3 = curr[2].toLowerCase();

                this.names.add(name);
                this.codes.add(alpha3);

            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // [Modified]
        int i = 0;
        String answer = null;

        while (i < this.getNumCountries()) {
            if (this.codes.get(i).equals(code.toLowerCase())) {
                answer = this.names.get(i);
                break;
            }

            i++;
        }

        return answer;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // [Modified]
        int i = 0;
        String answer = null;

        while (i < this.getNumCountries()) {
            if (this.names.get(i).equals(country)) {
                answer = this.codes.get(i);
                break;
            }

            i++;
        }

        return answer;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // [Modified]
        return this.names.size();
    }
}
