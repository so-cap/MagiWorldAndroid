package com.sophie.magiworldandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sophie.magiworldandroid.model.Character;
import com.sophie.magiworldandroid.model.Warrior;
import com.sophie.magiworldandroid.model.Sorcerer;
import com.sophie.magiworldandroid.model.Ranger;

import java.util.ArrayList;
import java.util.List;

import static com.sophie.magiworldandroid.R.*;

/**
 * Created by SOPHIE on 16/12/2019.
 */
public class SecondCharacterSelectionActivity extends AppCompatActivity implements View.OnClickListener {
    // static variables for further use in BeginGame class
    private static Character player2;
    private EditText vLevel, vStrength, vAgility, vIntelligence, vPlayersName;
    private int level, life, strength, agility, intelligence;
    private String playersName;
    private TextView vLife;
    private TextView playersTurn;
    private Button nextBtn;
    private AlertDialog.Builder popUp;
    private ImageView warrior, ranger, sorcerer;
    String character = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.character_selection);

        vLevel = findViewById(id.level);
        vLife = findViewById(id.life);
        vStrength = findViewById(id.strength);
        vAgility = findViewById(id.agility);
        vIntelligence = findViewById(id.intelligence);
        vPlayersName = findViewById(id.name);

        playersTurn = findViewById(id.players_turn);
        nextBtn = findViewById(id.next_and_start);
        nextBtn.setOnClickListener(this);

        warrior = findViewById(id.warrior);
        ranger = findViewById(id.ranger);
        sorcerer = findViewById(id.sorcerer);

        warrior.setOnClickListener(this);
        ranger.setOnClickListener(this);
        sorcerer.setOnClickListener(this);

        playersTurn.setText(getString(string.player_creation, 2));

        popUp = new AlertDialog.Builder(this);

        //TODO : remplacer les erreurs Toast par des setError()

        vLevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (vLevel.getText().toString().isEmpty())
                    level = 0;
                else
                    level = Integer.parseInt(vLevel.getText().toString());

                life = level * 5;
                vLife.setText(getString(string.life_display, life));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (level > 100) {
                    vLevel.setError(getString(string.inferiorto100));
                }
                // if I put "level == 0" the error would display before writting a number as
                // level is initialized at 0.
                if (vLevel.getText().toString().equals("0"))
                    vLevel.setError(getString(string.superiorto0));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == id.next_and_start)
            if (checkIfValid()) {
                introduction();
            }
        if (v.getId() == id.warrior) {
            character = "warrior";
            warrior.setBackgroundColor(getResources().getColor(color.lightWhite));
            ranger.setBackgroundColor(0x0);
            sorcerer.setBackgroundColor(0x0);
        }
        if (v.getId() == id.ranger) {
            character = "ranger";
            ranger.setBackgroundColor(getResources().getColor(color.lightWhite));
            warrior.setBackgroundColor(0x0);
            sorcerer.setBackgroundColor(0x0);
        }
        if (v.getId() == id.sorcerer) {
            character = "sorcerer";
            sorcerer.setBackgroundColor(getResources().getColor(color.lightWhite));
            ranger.setBackgroundColor(0x0);
            warrior.setBackgroundColor(0x0);
        }
    }

    private boolean checkIfValid() {

        // To check if all the characteristics have been written
        if (vLevel.getText().toString().isEmpty() || vStrength.getText().toString().isEmpty() ||
                vIntelligence.getText().toString().isEmpty() ||
                vAgility.getText().toString().isEmpty() || vPlayersName.getText().toString().isEmpty()) {

            if (vLevel.getText().toString().isEmpty())
                vLevel.setError("!");
            if (vStrength.getText().toString().isEmpty())
                vStrength.setError("!");
            if (vIntelligence.getText().toString().isEmpty())
                vIntelligence.setError("!");
            if (vAgility.getText().toString().isEmpty())
                vAgility.setError("!");
            if (vPlayersName.getText().toString().isEmpty())
                vPlayersName.setError("!");

            Toast.makeText(this, "Remplissez tous les champs!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (character == null) {
            Toast.makeText(this, "Choisissez votre personnage!", Toast.LENGTH_LONG).show();
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
            switch (character) {
                case "warrior":
                    player2 = new Warrior(level, strength, intelligence, agility, playersName);
                    break;
                case "ranger":
                    player2 = new Ranger(level, strength, intelligence, agility, playersName);
                    break;
                case "sorcerer":
                    player2 = new Sorcerer(level, strength, intelligence, agility, playersName);
            }
        }
        return true;
    }


    private void popUp() {
        popUp.setTitle(getString(string.aie));
        if (level == 0)
            popUp.setMessage(getString(string.superiorto0));
        popUp.setMessage(getString(string.totalunderlevel));
        popUp.setPositiveButton(getString(string.back), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        popUp.show();
    }

    private void introduction() {
        final AlertDialog.Builder popUp = new AlertDialog.Builder(this);
        popUp.setTitle(getString(string.introduction, 2));
        popUp.setMessage(player2.introduction());
        popUp.setPositiveButton(getString(string.next), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), BeginGameFragment.class);
                startActivity(intent);
            }
        });
        popUp.show();
    }

    public static List<Character> getPlayers() {
        List<Character> playersList = new ArrayList<>();
        Character player1 = CharacterSelectionActivity.getPlayer();

        playersList.add(player1);
        playersList.add(player2);
        return playersList;
    }
}
