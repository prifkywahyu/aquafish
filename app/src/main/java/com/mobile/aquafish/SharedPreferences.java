package com.mobile.aquafish;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferences {

    private static final String AQUAFISH_APP = "aquafishApp";
    static final String AQUA_CODE = "aquaCode";
    static final String AQUA_NAME = "nameUser";

    private android.content.SharedPreferences preferences;
    private android.content.SharedPreferences.Editor editor;

    public SharedPreferences(Context context) {
        preferences = context.getSharedPreferences(AQUAFISH_APP, MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public void deleteValue() {
        editor.clear();
        editor.commit();
    }

    public void saveStringCode(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void saveStringName(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getAquaCode() {
        return preferences.getString(AQUA_CODE, "");
    }

    public String getAquaName() {
        return preferences.getString(AQUA_NAME, "");
    }
}
