package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName= "", placeOfOrigin="", description="", image="";
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
        Sandwich sandwich= null;
        String formatedString = json.replaceAll("\\\\" , "" );
        Log.i("JSon data : ", formatedString);
        try {
            JSONObject rootObj = new JSONObject(formatedString);
            JSONObject mainObj = rootObj.getJSONObject("name");
            mainName = mainObj.getString("mainName");
            if(!rootObj.isNull("alsoKnownAs")){
                JSONArray knownAsJsonArray = rootObj.getJSONArray("alsoKnownAs");
                for(int i = 0; i<knownAsJsonArray.length(); i++){
                    alsoKnownAs.add(knownAsJsonArray.getString(i));
                }
            }else{
                alsoKnownAs = null;
            }

            if(!rootObj.isNull("placeOfOrigin")){
                placeOfOrigin =  rootObj.getString("placeOfOrigin");
            }else{
                placeOfOrigin="";
            }
            if(!rootObj.isNull("description")) description = rootObj.getString("description"); else description = "";

            if(!rootObj.isNull("image")){
                image = rootObj.getString("image");
            }else{
                image = "";
            }

            if(!rootObj.isNull("ingredients")){
                JSONArray ingredientJsonArray = rootObj.getJSONArray("ingredients");
                for(int i = 0 ; i< ingredientJsonArray.length(); i++){
                    ingredientsList.add(ingredientJsonArray.getString(i));
                }
            }else{
                ingredientsList = null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredientsList);
        return sandwich;
    }
}
