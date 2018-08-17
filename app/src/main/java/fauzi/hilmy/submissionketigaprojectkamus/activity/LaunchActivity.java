package fauzi.hilmy.submissionketigaprojectkamus.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fauzi.hilmy.submissionketigaprojectkamus.helper.KamusHelper;
import fauzi.hilmy.submissionketigaprojectkamus.model.KamusModel;
import fauzi.hilmy.submissionketigaprojectkamus.pref.KamusPreference;
import fauzi.hilmy.submissionketigaprojectkamus.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launch);
        AVLoadingIndicatorView avi = findViewById(R.id.avi);
        avi.setIndicator("BallPulseSyncIndicator");

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        KamusPreference kamusPreference;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(LaunchActivity.this);
            kamusPreference = new KamusPreference(LaunchActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = kamusPreference.getFirstRun();
            if (firstRun) {
                ArrayList<KamusModel> dictionaryModelsEngToInd = preLoadRaw("Eng");
                ArrayList<KamusModel> dictionaryModelsIndToEng = preLoadRaw("Ind");

                kamusHelper.open();
                kamusHelper.beginTransaction();
                try {
                    for (KamusModel model : dictionaryModelsEngToInd) {
                        kamusHelper.insertTransactionEng(model);
                    }
                    for (KamusModel model : dictionaryModelsIndToEng) {
                        kamusHelper.insertTransactionInd(model);
                    }
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusHelper.endTransaction();
                kamusHelper.close();
                kamusPreference.setFirstRun(false);
//                publishProgress((int) maxprogress);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(String selection) {
        ArrayList<KamusModel> dictionaryModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_dict;

            if (selection.equalsIgnoreCase("Eng")) {
                raw_dict = res.openRawResource(R.raw.english_indonesia);
            } else if (selection.equalsIgnoreCase("Ind")) {
                raw_dict = res.openRawResource(R.raw.indonesia_english);
            } else {
                raw_dict = null;
            }

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel dictionaryModel;

                dictionaryModel = new KamusModel(splitstr[0], splitstr[1]);
                dictionaryModels.add(dictionaryModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryModels;
    }
}
