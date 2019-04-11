package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_NAME = "name";
    private static final String JSON_MAIN_NAME = "mainName";
    private static final String JSON_AKAS = "alsoKnownAs";
    private static final String JSON_ORIGIN = "placeOfOrigin";
    private static final String JSON_DESC = "description";
    private static final String JSON_IMG = "image";
    private static final String JSON_INGS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject parsedJson = new JSONObject(json);
            JSONObject jsonName = parsedJson.getJSONObject(JSON_NAME);

            String name = jsonName.getString(JSON_MAIN_NAME);
            List<String> alsoKnownAs =
                    convertJsonArrayToStringList(jsonName.getJSONArray(JSON_AKAS));
            String origin = parsedJson.getString(JSON_ORIGIN);
            String description = parsedJson.getString(JSON_DESC);
            String image = parsedJson.getString(JSON_IMG);
            List<String> ingredients =
                    convertJsonArrayToStringList(parsedJson.getJSONArray(JSON_INGS));

            sandwich = new Sandwich(name, alsoKnownAs, origin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    /**
     * Converts a JSONArray into a List<String> object.
     */
    private static List<String> convertJsonArrayToStringList(JSONArray jsonArray)
            throws JSONException {
        List<String> result = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(jsonArray.getString(i));
        }
        return result;
    }
}
