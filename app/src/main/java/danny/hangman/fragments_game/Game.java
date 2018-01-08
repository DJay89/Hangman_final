package danny.hangman.fragments_game;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import danny.hangman.Galgelogik;
import danny.hangman.R;

import static danny.hangman.MainActivity.spil;


public class Game extends Fragment implements View.OnClickListener {

    // ImageView where the images goes
    private ImageView imageView;

    // List of images in an array
    private int[] images = new int[]{
            R.drawable.galge,
            R.drawable.forkert1,
            R.drawable.forkert2,
            R.drawable.forkert3,
            R.drawable.forkert4,
            R.drawable.forkert5,
            R.drawable.forkert6
    };

    // Edit text
    private EditText usersInput;
    // Submit
    private Button submit;
    // Word
    private TextView tvWord;
    private TextView tvWrongLetters;
    private TextView tvError;
    private TextView tvShowWord;


    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEditor;

    private Fragment frag_EndGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageView01);
        imageView.setImageDrawable(getResources().getDrawable(images[0]));

        usersInput = (EditText) view.findViewById(R.id.usersInput01);
        submit = (Button) view.findViewById(R.id.submit01);

        tvWord = (TextView) view.findViewById(R.id.tvWord01);
        tvWrongLetters = (TextView) view.findViewById(R.id.tvWrongLetters01);
        tvError = (TextView) view.findViewById(R.id.tvError01);
        tvShowWord = (TextView) view.findViewById(R.id.tvShowWord);

        submit.setOnClickListener(this);

        spil.nulstil();
        spil.logStatus();

        // Shared preferences
        prefs = this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        prefEditor = prefs.edit();

        // Start game with a specific word
        String selectedWord = prefs.getString("SelectWord", "DEFAULT");
        if (!selectedWord.equals("DEFAULT")) {
            spil.setOrdet(selectedWord);
        }
        // Start the game while seeing the word
        if (prefs.getInt("ShowWord", 0) != 0) {
            tvShowWord.setText("Word is " + spil.getOrdet());
        }


        tvWord.setText(spil.getSynligtOrd());

        return view;
    }

    @Override
    public void onClick(View v) {
        // If submit has been clicked
        if (v == submit) {
            // Make sure the input is not a number!
            if (usersInput.getText().toString().matches(".*\\d.*")) { tvError.setText("Numbers not allowed"); return; }
            // remove error text if it passed
            tvError.setText("");
            // Verify that the game is still on
            if (!spil.erSpilletSlut()) {
                String input = usersInput.getText().toString();
                spil.gætBogstav(input);
                status();
                tvWord.setText(spil.getSynligtOrd());

                // Update the incorrect guesses
                if (!spil.erSidsteBogstavKorrekt()) {
                    if (!tvWrongLetters.getText().toString().contains(input)) {
                        tvWrongLetters.setText(tvWrongLetters.getText() + " " + input);
                    }
                }
                // Remove last guessed letter from the input field
                usersInput.setText("");
            }

            // if the game has ended
            if (spil.erSpilletSlut()) {
                frag_EndGame = new EndGame();

                // Using Bundle to pass data to endgame fragment
                Bundle bundle = new Bundle();

                // if user has won the game
                if (spil.erSpilletVundet()) {
                    bundle.putBoolean("endCondition", true);            // Winning condition value 1
                }
                // if user has lost the game
                if (spil.erSpilletTabt()) {
                    bundle.putBoolean("endCondition", false);           // Losing condition value 0
                }

                bundle.putString("word", spil.getOrdet());              // Correct word passed on to endGame fragment with keyword "word"
                bundle.putInt("incorrectGuesses", spil.getAntalForkerteBogstaver());    // Number of incorrect letters passed on as well

                // set the argument for winning condition
                frag_EndGame.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_game_container, frag_EndGame);
                transaction.commit();
            }
        }
    }

    public void status() {
        int wrongGuesses = spil.getAntalForkerteBogstaver();

        switch(wrongGuesses) {
            case 0:
                imageView.setImageDrawable(getResources().getDrawable(images[0]));
                break;
            case 1:
                imageView.setImageDrawable(getResources().getDrawable(images[1]));
                break;
            case 2:
                imageView.setImageDrawable(getResources().getDrawable(images[2]));
                break;
            case 3:
                imageView.setImageDrawable(getResources().getDrawable(images[3]));
                break;
            case 4:
                imageView.setImageDrawable(getResources().getDrawable(images[4]));
                break;
            case 5:
                imageView.setImageDrawable(getResources().getDrawable(images[5]));
                break;
            case 6:
                imageView.setImageDrawable(getResources().getDrawable(images[6]));
                break;
        }
    }
}