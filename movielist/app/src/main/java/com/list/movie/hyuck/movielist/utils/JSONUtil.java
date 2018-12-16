package com.list.movie.hyuck.movielist.utils;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONUtil {

    public static String readJSONFile(Context context, String JSONFileName) {
        String JSONFormatString = "";
        try {
            AssetManager assetManager = context.getAssets();
            AssetManager.AssetInputStream assetInputStream = (AssetManager.AssetInputStream) assetManager.open(JSONFileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetInputStream));

            String tempBuffer = "";
            while ((tempBuffer = bufferedReader.readLine()) != null) {
                JSONFormatString += tempBuffer;
            }

        } catch (IOException e) {
            JSONFormatString = "";
        }

        return JSONFormatString;
    }

    public static String[] extractJSONDataList(String JSONFormatString, String JSONKeys[]) {
        String extractJSONDataList[] = new String[JSONKeys.length];
        try {
            JSONObject jsonObject = new JSONObject(JSONFormatString);

            for(int i=0; i<JSONKeys.length; i++) {
                extractJSONDataList[i] = jsonObject.getString(JSONKeys[i]);
            }
        } catch (JSONException e) {
            for(int i=0; i<extractJSONDataList.length; i++) {
                extractJSONDataList[i] = "";
            }
        }

        return extractJSONDataList;
    }

}
