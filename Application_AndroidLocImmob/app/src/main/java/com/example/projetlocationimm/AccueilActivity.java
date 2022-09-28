package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.projetlocationimm.adapters.AnnonceAccueilAdapter;
import com.example.projetlocationimm.models.Annonce;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AccueilActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Context contexte = this;

        //Barre de navigation du bas
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.page_accueil);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.page_accueil:
                    Intent accueil = new Intent(getApplicationContext(), AccueilActivity.class);
                    startActivity(accueil);
                    overridePendingTransition(0, 0);
                    finish();

                    break;
                case R.id.page_favoris:
                    Intent favoris = new Intent(getApplicationContext(), FavorisActivity.class);
                    startActivity(favoris);
                    overridePendingTransition(0, 0);
                    finish();

                    break;
                case R.id.page_ajouter:
                    Intent ajouterAnnonce = new Intent(getApplicationContext(), AjouterAnnonceActivity.class);
                    startActivity(ajouterAnnonce);
                    overridePendingTransition(0, 0);
                    finish();

                    break;
                case R.id.page_annonces:
                    Intent mesAnnonces = new Intent(getApplicationContext(), MesAnnoncesActivity.class);
                    startActivity(mesAnnonces);
                    overridePendingTransition(0, 0);
                    finish();

                    break;
                case R.id.page_messages:
                    Intent messages = new Intent(getApplicationContext(), MessagesActivity.class);
                    startActivity(messages);
                    overridePendingTransition(0, 0);
                    finish();

                    break;
            }
            return true;
        });

        //Bouton profil en haut à gauche
        AppBarLayout topAppBar = findViewById(R.id.appBarLayout2);
        topAppBar.findViewById(R.id.compte).setOnClickListener(view -> {
            String type = ConnexionActivity.getType(getApplicationContext());
            if (type.equals("part")) {
                Log.i("Utilisateur connecté", "particulier");
                Intent compte_particulier = new Intent(getApplicationContext(), CompteActivity.class);
                startActivity(compte_particulier);
                overridePendingTransition(0, 0);
                //finish();

            } else if (type.equals("pro")) {
                Log.i("Utilisateur connecté", "professionnel");
                Intent compte_pro = new Intent(getApplicationContext(), CompteProActivity.class);
                startActivity(compte_pro);
                overridePendingTransition(0, 0);
                //finish();

            } else {
                Intent pas_de_compte = new Intent(getApplicationContext(), LancementActivity.class);
                startActivity(pas_de_compte);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        //Requête pour récupérer les dernières annonces postées
        final String URL = "http://10.0.2.2:8080/dernieresannonces";
        JsonArrayRequest req = new JsonArrayRequest(URL,
                response -> {
                    Log.i("Retour serveur ", response.toString());

                    //Mettre dans une List, l'objet JSONArray (contenant des JSONObject annonces) récupéré
                    Type listType = new TypeToken<ArrayList<Annonce>>() {
                    }.getType();
                    ArrayList<Annonce> listeAnnoncesAccueil = new Gson().fromJson(String.valueOf(response), listType);

                    //On récupère la ListView
                    final ListView listView = findViewById(R.id.list_view_accueil);
                    listView.setDivider(null);
                    listView.setAdapter(new AnnonceAccueilAdapter(this, listeAnnoncesAccueil));

                }, error -> Log.i("Erreur serveur ", error.getMessage()));

        ApplicationController.getInstance().addToRequestQueue(req);

        FloatingActionButton button_recherche_rapide = findViewById(R.id.button_recherche_rapide);
        button_recherche_rapide.setOnClickListener(view ->
        {
            TextInputEditText recherche = (TextInputEditText) findViewById(R.id.recherche_rapide);
            final String URL2 = "http://10.0.2.2:8080/recherche/"+recherche.getText().toString();
            JsonArrayRequest req2 = new JsonArrayRequest(URL2,
                    response -> {
                        Log.i("Retour serveur ", response.toString());

                        //Mettre dans une List, l'objet JSONArray (contenant des JSONObject annonces) récupéré
                        Type listType = new TypeToken<ArrayList<Annonce>>() {
                        }.getType();
                        ArrayList<Annonce> listeAnnoncesRechercheRapide = new Gson().fromJson(String.valueOf(response), listType);

                        //On récupère la ListView, on remplace les annonces rapides par le résultat
                        final ListView listView = findViewById(R.id.list_view_accueil);
                        listView.setDivider(null);
                        listView.setAdapter(new AnnonceAccueilAdapter(this, listeAnnoncesRechercheRapide));

                    }, error -> Log.i("Erreur serveur ", error.getMessage()));

            ApplicationController.getInstance().addToRequestQueue(req2);
        });

        //TODO ajouter bouton flottant pour aller dans recherche poussée
        FloatingActionButton button_recherche = findViewById(R.id.button_recherche);
        button_recherche.setOnClickListener(view ->
        {
            Intent recherche = new Intent(getApplicationContext(), RechercheActivity.class);
            startActivity(recherche);
            overridePendingTransition(0,0);
            finish();
        });
    }
}