package com.sophie.magiworldandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MagiWorldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new HomepageFragment());
        fragmentTransaction.commit();

        Button quit = findViewById(R.id.leave);
        quit.setVisibility(View.GONE);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, new HomepageFragment());
                fm.commit();
            }
        });

        // TODO : Permettre de revenir à l'écran précédent
        // TODO : créer tous les strings

    }
}
