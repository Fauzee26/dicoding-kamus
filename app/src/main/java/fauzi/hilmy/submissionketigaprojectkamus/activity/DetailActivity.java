package fauzi.hilmy.submissionketigaprojectkamus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import fauzi.hilmy.submissionketigaprojectkamus.R;

public class DetailActivity extends AppCompatActivity {

    public static String ITEM_WORD = "kata";
    public static String ITEM_MEANING = "arti";
    TextView tvWord, tvMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle(getIntent().getStringExtra(ITEM_WORD));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvWord = (TextView) findViewById(R.id.tvDetailWord);
        tvMeaning = (TextView) findViewById(R.id.tvDetailMeaning);
        tvWord.setText(getIntent().getStringExtra(ITEM_WORD));
        tvMeaning.setText(getIntent().getStringExtra(ITEM_MEANING));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
