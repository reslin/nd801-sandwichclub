package com.example.android.sandwichclub.utils;

import android.text.TextUtils;

import com.example.android.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Sandwich sandwich = null;

        try {
            //level 0
            JSONObject rootJsonObject = new JSONObject(json);

            //level 1-0
            JSONObject nameJsonObject = rootJsonObject.getJSONObject("name");
            String mainName = nameJsonObject.getString("mainName");
            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            //level 1-1
            String placeOfOrigin = rootJsonObject.getString("placeOfOrigin");

            //level 1-2
            String description = rootJsonObject.getString("description");

            //level 1-3
            String image = rootJsonObject.getString("image");

            //level 1-4
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsJsonArray = rootJsonObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
