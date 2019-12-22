package com.sophie.magiworldandroid;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sophie.magiworldandroid.model.Guerrier;
import com.sophie.magiworldandroid.model.Mage;
import com.sophie.magiworldandroid.model.Character;
import com.sophie.magiworldandroid.model.Rodeur;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CharacterSelectionFragment extends Fragment implements View.OnClickListener {
    // static variables for further use in BeginGame class
    private static Character[] players = new Character[2];
    private static Character currentPlayer, firstPlayer = players[0];
    private EditText vLevel, vStrength, vAgility, vIntelligence, vPlayersName;
    private int level, life, strength, agility, intelligence;
    private String playersName;
    private TextView vLife;
    private TextView playersTurn;
    private RadioGroup character;
    private Button nextBtn, quit;
    private int playerNumber = 1;
    private AlertDialog.Builder popUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_selection, container, false);

        vLevel = view.findViewById(R.id.level);
        vLife = view.findViewById(R.id.life);
        vStrength = view.findViewById(R.id.strength);
        vAgility = view.findViewById(R.id.agility);
        vIntelligence = view.findViewById(R.id.intelligence);
        vPlayersName = view.findViewById(R.id.name);

        playersTurn = view.findViewById(R.id.players_turn);
        character = view.findViewById(R.id.characters);
        nextBtn = view.findViewById(R.id.next_and_start);
        nextBtn.setOnClickListener(this);

        quit = container.findViewById(R.id.leave);
        quit.setVisibility(View.VISIBLE);

        popUp = new AlertDialog.Builder(getContext());

        //TODO : remplacer les erreurs Toast par des setError()

        vLevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (vLevel.getText().toString().isEmpty())
                    level = 0;
                else
                    level = Integer.parseInt(vLevel.getText().toString());

                life = level * 5;
                vLife.setText(MessageFormat.format("{0}{1}", getString(R.string.life_display), life));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (level > 100) {
                    Toast.makeText(getContext(), "Votre niveau doit être inférieur à 100 !", Toast.LENGTH_LONG).show();
                    vLevel.setError("Votre niveau doit être inférieur à 100 !");

                    // s'affiche à chaque début de création du joueur car level est égal à 0 par défault
                    if (level == 0)
                        Toast.makeText(getContext(), "Votre niveau doit être supérieur à 0 !", Toast.LENGTH_LONG).show();
                }
            }
        });

        vStrength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (level == 0)
                    Toast.makeText(getContext(), "Votre niveau doit être supérieur à 0 !", Toast.LENGTH_LONG).show();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //TODO : Lors de la création du personnage 2, lors du click sur retour; afficher la page de
        // création du joueur 1 préremplie
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_and_start)
            if (checkIfValid()) {
                if (playerNumber == 1) {
                    introduction();
                    // Steps for updating the page for the next player
                    playersTurn.setText("JOUEUR 2 \n Créez votre personnage !");
                    firstPlayer = currentPlayer;
                    currentPlayer = players[1];
                    playerNumber++;
                    vStrength.setText(null);
                    vLevel.setText(null);
                    vAgility.setText(null);
                    vIntelligence.setText(null);
                    vPlayersName.setText(null);
                    character.check(R.id.guerrier);
                } else {
                    introduction();
                    FragmentTransaction fm = getFragmentManager().beginTransaction();
                    fm.replace(R.id.fragment_container, new BeginGameFragment());
                    fm.commit();
                }
            }
    }

    private boolean checkIfValid() {

        // To check if all the characteristics have been written
        if (vLevel.getText().toString().isEmpty() || vStrength.getText().toString().isEmpty() ||
                vIntelligence.getText().toString().isEmpty() ||
                vAgility.getText().toString().isEmpty() || vPlayersName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Remplissez tous les champs!", Toast.LENGTH_LONG).show();
            return false;
        }

        // Gets the int & String values from the views.
        strength = Integer.parseInt(vStrength.getText().toString());
        intelligence = Integer.parseInt(vIntelligence.getText().toString());
        agility = Integer.parseInt(vAgility.getText().toString());
        playersName = vPlayersName.getText().toString().trim();

        if ((strength + intelligence + agility) != level || level == 0) {
            popUp();
            return false;
        } else {
            switch (character.getCheckedRadioButtonId()) {
                case R.id.guerrier:
                    currentPlayer = new Guerrier(level, strength, intelligence, agility, playersName);
                    break;
                case R.id.rodeur:
                    currentPlayer = new Rodeur(level, strength, intelligence, agility, playersName);
                    break;
                case R.id.mage:
                    currentPlayer = new Mage(level, strength, intelligence, agility, playersName);
            }
        }
        return true;
    }


    private void popUp() {
        popUp.setTitle("Aïe !");
        if (level == 0)
            popUp.setMessage("Choississez un niveau supérieur à 0 !");
        popUp.setMessage("La somme de votre force, intelligence et agilité doit être égale à votre niveau !");
        popUp.setPositiveButton("RETOUR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        popUp.show();
    }

    private void introduction() {
        final AlertDialog.Builder popUp = new AlertDialog.Builder(getContext());
        popUp.setTitle("Introduction du joueur " + playerNumber + "...");
        popUp.setMessage(currentPlayer.introduction());
        popUp.setPositiveButton("SUIVANT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //TODO : Acceder à la page suivante ici
            }
        });
        popUp.show();
    }

    public static List<Character> getPlayers() {
        List<Character> playersList = new ArrayList<>();
        playersList.add(firstPlayer);
        playersList.add(currentPlayer);
        return playersList;
    }
}
