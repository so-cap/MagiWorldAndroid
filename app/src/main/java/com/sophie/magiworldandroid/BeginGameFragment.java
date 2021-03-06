package com.sophie.magiworldandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sophie.magiworldandroid.model.Character;

import java.util.ArrayList;
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
    private TextView playersTurn, playerLevels1, playerLevels2, winnerLoser;
    private View restartLayout;

    RecyclerView recyclerView;
    private List<String> mNumberOfTurn = new ArrayList<>();
    private List<String> mAttackDamage = new ArrayList<>();
    int i;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_game);

        playersTurn = findViewById(R.id.players_turn);
        playerLevels1 = findViewById(R.id.player1_levels);
        playerLevels2 = findViewById(R.id.player2_levels);

        basicAttack = findViewById(R.id.basic_attack);
        specialAttack = findViewById(R.id.special_attack);

        restartLayout = findViewById(R.id.layout_restart);
        restartBtn = findViewById(R.id.restart_btn);
        winnerLoser = findViewById(R.id.winner_loser);
        homeBtn = findViewById(R.id.home_btn);

        basicAttack.setOnClickListener(this);
        specialAttack.setOnClickListener(this);

        playersTurn.setText(getString(R.string.players_turn, player1.getPlayersName()));

        setMessage();

        initRecyclerView();
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

        mNumberOfTurn.add("TOUR " + ++i);

        if (attack.getId() == R.id.basic_attack) {
            mainPlayer.basicAttack(opponent);
            mAttackDamage.add(mainPlayer.basicAttackDamage(opponent));
        } else {
            mainPlayer.specialAttack(opponent);
            mAttackDamage.add(mainPlayer.specialAttackDamage(opponent));
        }

        // To update the display of each player's levels of life, strength etc...
        setMessage();
        initList(mNumberOfTurn, mAttackDamage);

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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        initList(mNumberOfTurn, mAttackDamage);
    }

    private void initList(List<String> numberOfTurn, List<String> attackDamage) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(numberOfTurn, attackDamage);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder popUp = new AlertDialog.Builder(this, R.style.MyDialogTheme);

        popUp.setTitle("PAUSE");
        popUp.setNegativeButton("Quitter la partie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                home();
            }
        });

        popUp.setPositiveButton("Reprendre !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        popUp.show();
    }

    private void home(){
        Intent intent = new Intent(getApplicationContext(), MagiWorldActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
