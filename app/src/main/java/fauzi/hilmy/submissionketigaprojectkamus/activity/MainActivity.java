package fauzi.hilmy.submissionketigaprojectkamus.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;

import fauzi.hilmy.submissionketigaprojectkamus.R;
import fauzi.hilmy.submissionketigaprojectkamus.adapter.KamusAdapter;
import fauzi.hilmy.submissionketigaprojectkamus.helper.KamusHelper;
import fauzi.hilmy.submissionketigaprojectkamus.model.KamusModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    SearchView searchView;
    String language;
    private KamusHelper kamusHelper;
    private KamusAdapter kamusAdapter;
    private ArrayList<KamusModel> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerEng);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);

        kamusHelper = new KamusHelper(this);
        kamusAdapter = new KamusAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(kamusAdapter);

        language = "Eng";
        getData(language, "");
    }

    private void getData(String language, String query) {
        try {
            kamusHelper.open();
            if (query.isEmpty()) {
                arraylist = kamusHelper.getAllData(language);
            } else {
                arraylist = kamusHelper.getDataByName(query, language);
            }

            String title = null;
            String hint = null;
            if (language == "Eng") {
                title = getResources().getString(R.string.eng_to_ind);
                hint = getResources().getString(R.string.search_word);
            } else {
                title = getResources().getString(R.string.ind_to_eng);
                hint = getResources().getString(R.string.cari_kata);
            }

            getSupportActionBar().setTitle(title);
            searchView.setQueryHint(hint);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            kamusHelper.close();
        }
        kamusAdapter.addItem(arraylist);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_eng) {
            language = "Eng";
            getData(language, "");
        } else if (id == R.id.nav_ind) {
            language = "Ind";
            getData(language, "");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getData(language, query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
//        getData(language, query);
        if (!query.equals("")) {
            recyclerView.setVisibility(View.VISIBLE);
            kamusHelper.open();
            try {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                KamusAdapter adapter = new KamusAdapter();
                adapter.addItem(kamusHelper.getDataByName(query, language));
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            kamusHelper.close();
        } else {
            recyclerView.setVisibility(View.GONE);
        }
        return true;
    }
}
