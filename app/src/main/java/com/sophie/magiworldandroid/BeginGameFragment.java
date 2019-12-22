package com.sophie.magiworldandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sophie.magiworldandroid.model.Character;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by SOPHIE on 11/12/2019.
 */
public class BeginGameFragment extends Fragment implements View.OnClickListener {
    private List<Character> player = CharacterSelectionFragment.getPlayers();
    private Character player1 = player.get(0);
    private Character player2 = player.get(1);
    private Character currentPlayer = player1;
    private Character rival = player2;

    private Button basicAttack, specialAttack, restartBtn, homeBtn, quit;
    private TextView playersTurn, playerLevels1, playerLevels2, winnerLoser;
    private AlertDialog.Builder displayAttack;
    private View restartLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.begin_game, container, false);

        playersTurn = view.findViewById(R.id.players_turn);
        playerLevels1 = view.findViewById(R.id.player1_levels);
        playerLevels2 = view.findViewById(R.id.player2_levels);

        basicAttack = view.findViewById(R.id.basic_attack);
        specialAttack = view.findViewById(R.id.special_attack);

        restartLayout = view.findViewById(R.id.layout_restart);
        restartBtn = view.findViewById(R.id.restart_btn);
        winnerLoser = view.findViewById(R.id.winner_loser);
        homeBtn = view.findViewById(R.id.home_btn);

        quit = container.findViewById(R.id.leave);

        basicAttack.setOnClickListener(this);
        specialAttack.setOnClickListener(this);

        playersTurn.setText(MessageFormat.format("{0}{1}", player1.getPlayersName(),
                getString(R.string.players_turn)));

        setMessage();

        // TODO : Gerer le backstack avec une activité
        // TODO : Afficher l'historique du combat et enlever les boites de dialogues.

        // TODO : https://developer.android.com/guide/topics/resources/string-resource#escaping_quotes

        return view;
    }

    private void setMessage() {
        // To not display negative numbers
        if (player1.getLife() < 0)
            player1.setLifeAtZero();
        if (player2.getLife() < 0)
            player2.setLifeAtZero();

        // display each players levels
        playerLevels1.setText(MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{10}",
                player1.getCharactersName(),
                getString(R.string.bracket), player1.getPlayersName(),
                getString(R.string.player_life), player1.getLife(),
                getString(R.string.player_strength), currentPlayer.getStrength(),
                getString(R.string.player_agility), player1.getAgility(), getString(R.string.player_intelligence), player1.getIntelligence()));

        playerLevels2.setText(MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{10}",
                player2.getCharactersName(),
                getString(R.string.bracket), player2.getPlayersName(),
                getString(R.string.player_life), player2.getLife(),
                getString(R.string.player_strength), player2.getStrength(),
                getString(R.string.player_agility), player2.getAgility(), getString(R.string.player_intelligence), player2.getIntelligence()));
    }

    @Override
    public void onClick(View v) {
        playerAttack(currentPlayer, rival, v);
    }

    private void playerAttack(Character mainPlayer, Character opponent, View attack) {
        Character temp;
        displayAttack = new AlertDialog.Builder(getContext());

        if (attack.getId() == R.id.basic_attack) {
            mainPlayer.basicAttack(opponent);
            displayAttack.setMessage(mainPlayer.basicAttackDamage(opponent));
        } else {
            mainPlayer.specialAttack(opponent);
            displayAttack.setMessage(mainPlayer.specialAttackDamage(opponent));
        }

        displayAttack();

        // To update the display of each player's levels of life, strength etc...
        setMessage();

        if (mainPlayer.getLife() <= 0 || opponent.getLife() <= 0) {
            endOfGame();
        }

        // To switch players after each attack
        if (currentPlayer == player1) {
            playersTurn.setText(MessageFormat.format("{0}{1}", player2.getPlayersName(),
                    getString(R.string.players_turn)));

            temp = currentPlayer;
            currentPlayer = rival;
            rival = temp;
        } else {
            playersTurn.setText(MessageFormat.format("{0}{1}", player1.getPlayersName(),
                    getString(R.string.players_turn)));

            temp = currentPlayer;
            currentPlayer = rival;
            rival = temp;
        }
    }

    private void endOfGame() {
        basicAttack.setVisibility(View.GONE);
        specialAttack.setVisibility(View.GONE);
        playersTurn.setVisibility(View.GONE);


        restartLayout.setVisibility(View.VISIBLE);
        quit.setVisibility(View.GONE);

        if (player1.getLife() == 0)
            winnerLoser.setText(MessageFormat.format("{0}{1}{2}{3}{4}",player1.getPlayersName(),
                    getString(R.string.loser), getString(R.string.congrats), player2.getPlayersName(),
                    getString(R.string.winner)));
        else
            winnerLoser.setText(MessageFormat.format("{0}{1}{2}{3}{4}",player2.getPlayersName(),
                    getString(R.string.loser), getString(R.string.congrats), player1.getPlayersName(),
                    getString(R.string.winner)));

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, new CharacterSelectionFragment());
                fm.commit();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, new HomepageFragment());
                fm.commit();
            }
        });

    }

    private void displayAttack() {
        displayAttack.setTitle("ATTAQUE !");
        displayAttack.setPositiveButton("SUIVANT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayAttack.show();
    }

    public List<Character> getPlayers() {
        return player;
    }
}
