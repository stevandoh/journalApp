package com.stevandoh.journalapp.journalapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesFavorites implements Favorites {
    private final SharedPreferences pref;

    public SharedPreferencesFavorites(Context context) {
        pref = context.getSharedPreferences("favorites.xml", Context.MODE_PRIVATE);
    }

    public boolean get(int id) {
        return pref.getBoolean( String.valueOf( id), false);
    }

    public void put(int id, boolean favorite) {
        SharedPreferences.Editor editor = pref.edit();
        if (favorite) {
            editor.putBoolean(String.valueOf( id), true);
        } else {
            editor.remove(String.valueOf( id));
        }
        editor.apply();
    }

    public boolean toggle(int id) {
        boolean favorite = get(id);
        put(id, !favorite);
        return !favorite;
    }
}
