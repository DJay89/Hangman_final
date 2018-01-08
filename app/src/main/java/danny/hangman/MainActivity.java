package danny.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import danny.hangman.fragments_navigation.hangman;
import danny.hangman.fragments_navigation.highscore;
import danny.hangman.fragments_navigation.settings;

public class MainActivity extends AppCompatActivity {

    // Has to be static so asynctask can get words from DR before the game starts
    public static Galgelogik spil;

    private TextView mTextMessage;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_highscore:
                    fragment = new highscore();
                    System.out.println("Highscore");
                    break;
                case R.id.navigation_hangman:
                    fragment = new hangman();
                    System.out.println("Hangman");
                    break;
                case R.id.navigation_settings:
                    fragment = new settings();
                    System.out.println("Settings");
                    break;
            }

            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
                return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare the game
        spil = new Galgelogik();

        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (prefs.getInt("DR", 0) != 0) {
            // The game is started here, so the asynctask can have time to gather all the words, before the game actually begins.
            // Get words from DR
            WordsFromDR();
        }

        Fragment fragment = new hangman();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_hangman);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    // Public method, as this is being used in settings as well
    public void WordsFromDR() {
        //AsyncTask was granted by: https://docs.google.com/document/d/1YLo9krF3pdg-IB8Wjw3ZZzr4Xo1le-nBBj7gk158muQ/edit#
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    spil.hentOrdFraDr();
                    return "Ordene blev korrekt hentet fra DR's server";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ordene blev ikke hentet korrekt: "+e;
                }
            }

        }.execute();
    }

}
