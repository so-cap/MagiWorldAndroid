package com.sophie.magiworldandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.sophie.magiworldandroid.model.Character;

import java.util.List;

/**
 * Created by SOPHIE on 11/12/2019.
 */
public class BeginGameFragment extends AppCompatActivity implements View.OnClickListener {
    private List<Character> player = SecondCharacterSelectionActivity.getPlayers();
    private Character player1 = player.get(0);
    private Character player2 = player.get(1);
    private Character currentPlayer = player1;
    private Character rival = player2;

    private Button basicAttack, specialAttack, restartBtn, homeBtn;
    private TextView playersTurn, playerLevels1, playerLevels2, winnerLoser, attackHistory;
    private View restartLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_game);

        playersTurn = findViewById(R.id.players_turn);
        playerLevels1 = findViewById(R.id.player1_levels);
        playerLevels2 = findViewById(R.id.player2_levels);

        basicAttack = findViewById(R.id.basic_attack);
        specialAttack = findViewById(R.id.special_attack);

        attackHistory = findViewById(R.id.attack_history);

        restartLayout = findViewById(R.id.layout_restart);
        restartBtn = findViewById(R.id.restart_btn);
        winnerLoser = findViewById(R.id.winner_loser);
        homeBtn = findViewById(R.id.home_btn);

        basicAttack.setOnClickListener(this);
        specialAttack.setOnClickListener(this);

        playersTurn.setText(getString(R.string.players_turn, player1.getPlayersName()));

        setMessage();
    }

    private void setMessage() {
        // To not display negative numbers
        if (player1.getLife() < 0)
            player1.setLifeAtZero();
        if (player2.getLife() < 0)
            player2.setLifeAtZero();

        // display each players levels
        playerLevels1.setText(getString(R.string.player_levels,
                player1.getCharactersName(), player1.getPlayersName(), player1.getLife(), player2.getStrength(),
                player1.getAgility(), player1.getIntelligence()));

        playerLevels2.setText(getString(R.string.player_levels,
                player2.getCharactersName(), player2.getPlayersName(), player2.getLife(), player2.getStrength(),
                player2.getAgility(), player2.getIntelligence()));
    }

    @Override
    public void onClick(View v) {
        playerAttack(currentPlayer, rival, v);
    }

    private void playerAttack(Character mainPlayer, Character opponent, View attack) {
        Character temp;

        if (attack.getId() == R.id.basic_attack) {
            mainPlayer.basicAttack(opponent);
            displayAttack(mainPlayer.basicAttackDamage(opponent));
        } else {
            mainPlayer.specialAttack(opponent);
            displayAttack(mainPlayer.specialAttackDamage(opponent));
        }

        // To update the display of each player's levels of life, strength etc...
        setMessage();

        if (mainPlayer.getLife() <= 0 || opponent.getLife() <= 0) {
            endOfGame();
        }

        // To switch players after each attack
        if (currentPlayer == player1) {
            playersTurn.setText(getString(R.string.players_turn, player2.getPlayersName()));

            temp = currentPlayer;
            currentPlayer = rival;
            rival = temp;
        } else {
            playersTurn.setText(getString(R.string.players_turn, player1.getPlayersName()));

            temp = currentPlayer;
            currentPlayer = rival;
            rival = temp;
        }
    }

    private void endOfGame() {

        basicAttack.setVisibility(View.INVISIBLE);
        specialAttack.setVisibility(View.INVISIBLE);
        playersTurn.setVisibility(View.INVISIBLE);


        restartLayout.setVisibility(View.VISIBLE);

        if (player1.getLife() == 0)
            winnerLoser.setText(getString(R.string.display_winnerloser, player1.getPlayersName(),
                    player2.getPlayersName()));
        else
            winnerLoser.setText(getString(R.string.display_winnerloser, player2.getPlayersName(),
                    player1.getPlayersName()));

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CharacterSelectionActivity.class);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MagiWorldActivity.class);
                startActivity(intent);
            }
        });

    }

    private void displayAttack(String attackMessage) {
        attackHistory.setText(getString(R.string.display_attacks, attackMessage, attackHistory.getText().toString()));
    }
}
