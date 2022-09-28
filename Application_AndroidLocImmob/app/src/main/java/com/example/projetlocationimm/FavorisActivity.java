package com.example.projetlocationimm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.projetlocationimm.adapters.AnnonceAccueilAdapter;
import com.example.projetlocationimm.adapters.AnnonceFavorisAdapter;
import com.example.projetlocationimm.models.Annonce;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapePathModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavorisActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Context contexte = this;
        if (!(ConnexionActivity.isLogged(contexte))) {

            setContentView(R.layout.activity_favoris_empty);

            bottomNavigationView = findViewById(R.id.bottom_nav_bar);
            bottomNavigationView.setSelectedItemId(R.id.page_favoris);
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

        } else {

            setContentView(R.layout.activity_favoris);

            bottomNavigationView = findViewById(R.id.bottom_nav_bar);
            bottomNavigationView.setSelectedItemId(R.id.page_favoris);
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

            //Requête pour récupérer les favoris de l'utilisateur
            final String URL = "http://10.0.2.2:8080/favoris/" + ConnexionActivity.getId(getApplicationContext());
            JsonArrayRequest req = new JsonArrayRequest(URL,
                    response -> {
                        Log.i("Retour serveur ", response.toString());

                        //Mettre dans une List, l'objet JSONArray (contenant des JSONObject annonces) récupéré
                        Type listType = new TypeToken<ArrayList<Annonce>>() {
                        }.getType();
                        ArrayList<Annonce> listeAnnoncesFavoris = new Gson().fromJson(String.valueOf(response), listType);

                        //On récupère la ListView
                        final ListView listView = findViewById(R.id.list_view_favoris);
                        listView.setDivider(null);
                        listView.setAdapter(new AnnonceFavorisAdapter(this, listeAnnoncesFavoris));

                    }, error -> Log.i("Erreur serveur ", error.getMessage()));

            ApplicationController.getInstance().addToRequestQueue(req);

        }
    }
}
