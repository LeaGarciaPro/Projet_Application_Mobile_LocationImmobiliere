package com.example.projetlocationimm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CompteActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        //Bouton de déconnexion
        Button button_deconnexion = findViewById(R.id.button_deconnexion);
        button_deconnexion.setOnClickListener(view -> {
            ConnexionActivity.logOut(getApplicationContext());
            Intent retour_menu = new Intent(getApplicationContext(), LancementActivity.class);
            startActivity(retour_menu);
            overridePendingTransition(0,0);
            finish();
        });

        //Bouton de modification des données
        Button button_modif = findViewById(R.id.button_modif);
        button_modif.setOnClickListener(view -> {
            Intent modif_donnees = new Intent(getApplicationContext(), ModificationDonneesActivity.class);
            startActivity(modif_donnees);
            overridePendingTransition(0,0);
            finish();
        });

        //Bouton de modification des alertes
        Button button_gerer_alertes = findViewById(R.id.button_gerer_alertes);
        button_gerer_alertes.setOnClickListener(view -> {
            Intent modif_alertes = new Intent(getApplicationContext(), ModificationAlertesActivity.class);
            startActivity(modif_alertes);
            overridePendingTransition(0,0);
            finish();
        });

        //Flèche retour barre navigation
        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(view -> {
            Intent lancement = new Intent(getApplicationContext(), AccueilActivity.class);
            startActivity(lancement);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}