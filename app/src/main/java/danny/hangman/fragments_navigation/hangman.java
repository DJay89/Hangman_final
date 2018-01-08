package danny.hangman.fragments_navigation;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import danny.hangman.GameActivity;
import danny.hangman.MainActivity;
import danny.hangman.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class hangman extends Fragment implements View.OnClickListener {

    private Button button_StartGame;
    private TextView textView_WinStreak;


    public hangman() {
        // Required empty public constructor
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hangman, container, false);

        // Show users current Win streak
        // Shared preferences - Local data
        SharedPreferences prefs;
        prefs = this.getActivity().getSharedPreferences("Highscore", Context.MODE_PRIVATE);
        int winStreak = prefs.getInt("winStreak", 0);   // Get the winStreak if there is any. otherwise set it to 0

        textView_WinStreak = view.findViewById(R.id.streak);

        if (winStreak != 0) {
            textView_WinStreak.setText("Win Streak: " + winStreak);
        }else { textView_WinStreak.setText(""); }

        button_StartGame = (Button) view.findViewById(R.id.button01);
        button_StartGame.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button01:
                Intent hangman = new Intent(getActivity(), GameActivity.class);
                startActivity(hangman);
                break;
            default:
                break;
        }
    }
}
