package com.example.projetlocationimm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.adapters.AnnonceAccueilAdapter;
import com.example.projetlocationimm.adapters.MessagerieAdapter;
import com.example.projetlocationimm.models.Annonce;
import com.example.projetlocationimm.models.Conversation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context contexte = this;
        if (!(ConnexionActivity.isLogged(contexte))) {

            setContentView(R.layout.activity_messages_empty);

            bottomNavigationView = findViewById(R.id.bottom_nav_bar_2);
            bottomNavigationView.setSelectedItemId(R.id.page_messages);
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

            AppBarLayout topAppBar2 = findViewById(R.id.appBarLayout2);
            topAppBar2.findViewById(R.id.compte).setOnClickListener(view -> {
                Intent pas_de_compte = new Intent(getApplicationContext(), LancementActivity.class);
                startActivity(pas_de_compte);
                overridePendingTransition(0, 0);
                finish();
            });

        } else {

            setContentView(R.layout.activity_messages);

            bottomNavigationView = findViewById(R.id.bottom_nav_bar);
            bottomNavigationView.setSelectedItemId(R.id.page_messages);
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

            AppBarLayout topAppBar = findViewById(R.id.appBarLayout2);
            topAppBar.findViewById(R.id.compte).setOnClickListener(view -> {
                String type = ConnexionActivity.getType(getApplicationContext());
                if (type.equals("part")) {
                    Log.i("Utilisateur connecté", "particulier");
                    Intent compte_particulier = new Intent(getApplicationContext(), CompteActivity.class);
                    startActivity(compte_particulier);
                    overridePendingTransition(0, 0);
                    finish();

                } else if (type.equals("pro")) {
                    Log.i("Utilisateur connecté", "professionnel");
                    Intent compte_pro = new Intent(getApplicationContext(), CompteProActivity.class);
                    startActivity(compte_pro);
                    overridePendingTransition(0, 0);
                    finish();

                } else {
                    Intent pas_de_compte = new Intent(getApplicationContext(), LancementActivity.class);
                    startActivity(pas_de_compte);
                    overridePendingTransition(0, 0);
                    finish();
                }
            });

            final String URL = "http://10.0.2.2:8080/conversationall/" + ConnexionActivity.getId(getApplicationContext());
            JsonArrayRequest req = new JsonArrayRequest(URL,
                    response -> {
                        Log.i("Retour serveur ", response.toString());

                        Type listType = new TypeToken<ArrayList<Conversation>>() {
                        }.getType();
                        ArrayList<Conversation> listConversations = new Gson().fromJson(String.valueOf(response), listType);

                        //On récupère la ListView
                        final ListView listView = findViewById(R.id.list_view_mesmessages);
                        listView.setDivider(null);
                        listView.setAdapter(new MessagerieAdapter(this, listConversations));
                    },
                    error -> Log.i("Erreur serveur ", error.getMessage()));

            ApplicationController.getInstance().addToRequestQueue(req);
        }
    }

}