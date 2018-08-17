package fauzi.hilmy.submissionketigaprojectkamus.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import fauzi.hilmy.submissionketigaprojectkamus.R;

public class KamusPreference {
    private SharedPreferences preferences;
    private Context context;

    public KamusPreference(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.apply();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.app_first_run);
        return preferences.getBoolean(key, true);
    }
}

