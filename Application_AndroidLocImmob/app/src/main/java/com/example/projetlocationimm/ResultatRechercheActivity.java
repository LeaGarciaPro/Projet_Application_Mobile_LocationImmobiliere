package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.projetlocationimm.adapters.AnnonceAccueilAdapter;
import com.example.projetlocationimm.adapters.AnnonceFavorisAdapter;
import com.example.projetlocationimm.models.Annonce;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class ResultatRechercheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_recherche);

        //Flèche retour barre navigation
        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(view -> {
            Intent retour_recherche = new Intent(getApplicationContext(), RechercheActivity.class);
            startActivity(retour_recherche);
            overridePendingTransition(0,0);
            finish();
        });

        ArrayList<Annonce> resultats_recherche;
        resultats_recherche = getIntent().getParcelableArrayListExtra("Liste annonces");

        //System.out.println(resultats_recherche);

        //On récupère la ListView
        final ListView listView = findViewById(R.id.list_view_recherche);
        listView.setDivider(null);
        listView.setAdapter(new AnnonceAccueilAdapter(this, resultats_recherche));

    }
}