package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    public static final int KEYCUTOFF = 3;
    private static final String ALPHA3 = "alpha3";
    private ArrayList<String> countries = new ArrayList<>();
    private List<String> languages = new ArrayList<>();
    private JSONArray data;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */

    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // [Modified]
            data = jsonArray;


            for (int i = 0; i < jsonArray.length(); i++) {
                this.countries.add(jsonArray.getJSONObject(i).getString(ALPHA3));
            }

            ArrayList<String> temp = new ArrayList<>(jsonArray.getJSONObject(0).keySet());
            temp.remove("alpha2");
            temp.remove(ALPHA3);
            temp.remove("id");
            this.languages = temp;
            //this.languages = temp.subList(this.KEYCUTOFF, temp.size());

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // [Modified]
        return this.languages;
    }

    @Override
    public List<String> getCountries() {
        // [Modified]
        return this.countries;
    }

    @Override
    public String translate(String country, String language) {
        int i = 0;

        while (i < data.length()) {
            if (data.getJSONObject(i).getString(ALPHA3).equals(country)) {
                if (data.getJSONObject(i).isNull(language)) {
                    break;
                }
                return data.getJSONObject(i).getString(language);
            }

            i++;
        }

        return null;
    }
}
