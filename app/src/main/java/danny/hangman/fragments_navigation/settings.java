package danny.hangman.fragments_navigation;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import danny.hangman.Galgelogik;
import danny.hangman.MainActivity;
import danny.hangman.R;

import static danny.hangman.MainActivity.spil;

/**
 * A simple {@link Fragment} subclass.
 */
public class settings extends Fragment implements View.OnClickListener {

    private TextView tvSettings;

    private CheckBox dr_words;
    private CheckBox show_words;
    private CheckBox select_word;

    private Button save;

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    private Spinner spinner;

    public settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        tvSettings = (TextView) view.findViewById(R.id.tvSettings);

        dr_words = (CheckBox) view.findViewById(R.id.checkbox_01);
        show_words = (CheckBox) view.findViewById(R.id.checkbox_02);

        select_word = (CheckBox) view.findViewById(R.id.checkbox_03);

        // Spinner
        spinner = (Spinner) view.findViewById(R.id.spinner01);
        List<String> list = spil.getMuligeOrd();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        save = (Button) view.findViewById(R.id.button01);
        save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        boolean checked_dr_words = false;
        boolean checked_show_words = false;
        boolean checked_select_word = false;

        // Access shared preferences data saved under settings.
        prefs = this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();

        // Save settings for the checked ones
        if (dr_words.isChecked()) {
            checked_dr_words = true;
            // Save setting
            prefsEditor.putInt("DR", 1);
            prefsEditor.apply();

            //Get words immediately
            ((MainActivity)getActivity()).WordsFromDR();
        }

        if (show_words.isChecked()) {
            checked_show_words = true;
            // Save setting
            prefsEditor.putInt("ShowWord", 1);
            prefsEditor.apply();
        }

        if (select_word.isChecked()) {
            checked_select_word = true;
            spinner.getSelectedItem();
            prefsEditor.putString("SelectWord", spinner.getSelectedItem().toString());
            prefsEditor.apply();
        }

        // Reset settings for the unchecked ones
        if (!checked_dr_words) {
            // Save setting
            prefsEditor.putInt("DR", 0);
            prefsEditor.apply();

            // Reset Galgelogik
            spil = new Galgelogik();
        }
        if (!checked_show_words) {
            // Save setting
            prefsEditor.putInt("ShowWord", 0);
            prefsEditor.apply();
        }
        if(!checked_select_word) {
            prefsEditor.putString("SelectWord", "DEFAULT");
            prefsEditor.apply();
        }

        tvSettings.setText("Settings has been saved");

        System.out.println(prefs.getString("SelectWord", "DEFAULT"));

    }

    // add items into spinner dynamically
    public void addWordsOnSpinner(View v) {


    }
}
