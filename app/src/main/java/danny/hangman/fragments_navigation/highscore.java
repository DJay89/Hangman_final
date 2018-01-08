package danny.hangman.fragments_navigation;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import danny.hangman.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class highscore extends Fragment {


    // Listview
    ListView listView;

    // Shared preferences - Local data
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);

        // Access shared preferences data saved under Highscore.
        prefs = this.getActivity().getSharedPreferences("Highscore", Context.MODE_PRIVATE);

        String[] scoreboard = {"Highscore 1", "Highscore 2", "Highscore 3", "Highscore 4", "Highscore 5", "Highscore 6", "Highscore 7", "Highscore 8", "Highscore 9", "Highscore 10"};

        // Add the Highscore to the list
        for (int i = 1; i <= scoreboard.length; i++) {
            String tmp = "Highscore" + i;
            int score = prefs.getInt(tmp, 0);

            if (score != 0) {
                scoreboard[i - 1] = scoreboard[i-1] + " : " + score;
            }
        }

        listView = (ListView) view.findViewById(R.id.Scoreboard);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                scoreboard
        );

        listView.setAdapter(listViewAdapter);

        return view;

    }
}
