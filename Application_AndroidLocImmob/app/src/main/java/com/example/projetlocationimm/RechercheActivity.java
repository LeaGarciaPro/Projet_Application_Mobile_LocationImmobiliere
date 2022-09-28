package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.models.Annonce;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RechercheActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        //Flèche retour barre navigation
        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(view -> {
            Intent retour_accueil = new Intent(getApplicationContext(), AccueilActivity.class);
            startActivity(retour_accueil);
            overridePendingTransition(0, 0);
            finish();
        });

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

        Button button_recherche = (Button) findViewById(R.id.button_recherche_approfondi);
        button_recherche.setOnClickListener(view -> {

            TextInputEditText lieu = (TextInputEditText) findViewById(R.id.textFieldLieuRecherche);
            TextInputEditText typelog = (TextInputEditText) findViewById(R.id.textFieldTypeLogRecherche);
            TextInputEditText typeloc = (TextInputEditText) findViewById(R.id.textFieldTypeLocRecherche);
            TextInputEditText nbpieces = (TextInputEditText) findViewById(R.id.textFieldPiecesRecherche);
            TextInputEditText surface = (TextInputEditText) findViewById(R.id.textFieldSurfaceRecherche);
            TextInputEditText nbchambres = (TextInputEditText) findViewById(R.id.textFieldChambresRecherche);
            TextInputEditText prix = (TextInputEditText) findViewById(R.id.textFieldPrixRecherche);

            //Si certains champs sont vides on n'envoient pas de requête au serveur
            if (lieu.getText().toString().equals("") ||
                    typelog.getText().toString().equals("") ||
                    typeloc.getText().toString().equals("") ||
                    nbpieces.getText().toString().equals("") ||
                    surface.getText().toString().equals("") ||
                    nbchambres.getText().toString().equals("") ||
                    prix.getText().toString().equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), "Remplissez tous les champs s'il vous plaît!", Toast.LENGTH_LONG);
                toast.show();

            } else {

                final String URL = "http://10.0.2.2:8080/rechercher/annonces/" + lieu.getText().toString() + ":" + typelog.getText().toString() + ":" + typeloc.getText().toString() + ":" + nbpieces.getText().toString() + ":" + surface.getText().toString() + ":" + nbchambres.getText().toString() + ":" + prix.getText().toString();
                JsonArrayRequest req = new JsonArrayRequest(URL,
                        response -> {
                            Log.i("Retour serveur ", response.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Annonces trouvées !", Toast.LENGTH_LONG);
                            toast.show();

                            //Mettre dans une List, l'objet JSONArray (contenant des JSONObject annonces) récupéré
                            Type listType = new TypeToken<ArrayList<Annonce>>() {
                            }.getType();
                            ArrayList<Annonce> listeAnnoncesRecherche = new Gson().fromJson(String.valueOf(response), listType);

                            //Aller sur la page qui donne le résultat de la recherche
                            Intent resultats = new Intent(getApplicationContext(), ResultatRechercheActivity.class);
                            //Envoyer la liste d'annonce dans ResultatRechercheActivity
                            resultats.putParcelableArrayListExtra("Liste annonces", listeAnnoncesRecherche);
                            startActivity(resultats);
                            overridePendingTransition(0, 0);

                        }, error -> Log.i("Erreur serveur ", error.getMessage()));

                ApplicationController.getInstance().addToRequestQueue(req);
            }
        });
    }
}