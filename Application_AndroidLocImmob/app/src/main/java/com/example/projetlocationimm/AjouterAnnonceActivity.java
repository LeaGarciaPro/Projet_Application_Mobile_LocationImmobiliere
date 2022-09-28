package com.example.projetlocationimm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AjouterAnnonceActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 100;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Context contexte = this;
        //Connexion ou non de l'utilisateur
        if (!(ConnexionActivity.isLogged(contexte))) {

            //layout utilisateur non connecté
            setContentView(R.layout.activity_ajouter_empty);

            //Barre de navigation du bas
            bottomNavigationView = findViewById(R.id.bottom_nav_bar);
            bottomNavigationView.setSelectedItemId(R.id.page_ajouter);
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

            //Bouton profil en haut à droite
            AppBarLayout topAppBar = findViewById(R.id.appBarLayout2);
            topAppBar.findViewById(R.id.compte).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent pas_de_compte = new Intent(getApplicationContext(), LancementActivity.class);
                    startActivity(pas_de_compte);
                    overridePendingTransition(0, 0);
                    finish();
                }
            });

            TextView textView_s_inscrire = findViewById(R.id.textView_s_inscrire);
            textView_s_inscrire.setOnClickListener(view -> {
                Intent inscription = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(inscription);
            });

            Button button_se_connecter3 = findViewById(R.id.button_se_connecter3);
            button_se_connecter3.setOnClickListener(view -> {

                EditText email = findViewById(R.id.connexion_email);
                EditText mdp = findViewById(R.id.connexion_mdp);

                if (email.getText().toString().equals("") || mdp.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Remplissez tous les champs s'il vous plaît!", Toast.LENGTH_LONG);
                    toast.show();
                } else {

                    final String URL = "http://10.0.2.2:8080/connexion";
                    Map params = new HashMap();

                    params.put("email", email.getText().toString());
                    params.put("mdp", mdp.getText().toString());
                    String json = new JSONObject(params).toString();

                    JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                            response -> {
                                Log.i("Retour serveur ", response.toString());

                                try {
                                    if (response.getString("type").equals("1") || response.getString("type").equals("2")) {

                                        if (response.getString("type").equals("1")) {
                                            ConnexionActivity.saveData(email.getText().toString(), mdp.getText().toString(), "part", response.getString("idUtilisateurConnecte"), getApplicationContext());
                                        } else {
                                            ConnexionActivity.saveData(email.getText().toString(), mdp.getText().toString(), "pro", response.getString("idUtilisateurConnecte"), getApplicationContext());
                                        }
                                        Intent accueil = new Intent(getApplicationContext(), AccueilActivity.class);
                                        startActivity(accueil);
                                        overridePendingTransition(0, 0);
                                        finish();

                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Email et/ou mot de passe incorrects !", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, error -> Log.i("Erreur serveur ", error.getMessage()));

                    ApplicationController.getInstance().addToRequestQueue(req);
                }
            });
        } else {

            //layout utilisateur connecté
            setContentView(R.layout.activity_ajouter_annonce);

            //Barre de navigation du bas
            bottomNavigationView = findViewById(R.id.bottom_nav_bar);
            bottomNavigationView.setSelectedItemId(R.id.page_ajouter);
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

            //Récupérer les informations
            Button button_enregistrer = (Button) findViewById(R.id.button_enregistrer);

            button_enregistrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //TODO est-ce qu'on met pas une classe côté mobile là ?

                    Map params = new HashMap();
                    TextInputEditText titre = (TextInputEditText) findViewById(R.id.textFieldTitre);
                    TextInputEditText lieu = (TextInputEditText) findViewById(R.id.textFieldLieu);
                    TextInputEditText typelog = (TextInputEditText) findViewById(R.id.textFieldTypeLog);
                    TextInputEditText typeloc = (TextInputEditText) findViewById(R.id.textFieldTypeLoc);
                    TextInputEditText nbpieces = (TextInputEditText) findViewById(R.id.textFieldPieces);
                    TextInputEditText surface = (TextInputEditText) findViewById(R.id.textFieldSurface);
                    TextInputEditText nbchambres = (TextInputEditText) findViewById(R.id.textFieldChambres);
                    TextInputEditText prix = (TextInputEditText) findViewById(R.id.textFieldPrix);
                    TextInputEditText etage = (TextInputEditText) findViewById(R.id.textFieldEtage);
                    TextInputEditText description = (TextInputEditText) findViewById(R.id.textFieldDescription);
                    CheckBox ascenseur = findViewById(R.id.ascenseur);
                    CheckBox caveBox = findViewById(R.id.caveBox);
                    CheckBox balconTerasse = findViewById(R.id.balconTerasse);
                    CheckBox baignoire = findViewById(R.id.baignoire);
                    CheckBox meuble = findViewById(R.id.meuble);
                    params.put("titre", titre.getText().toString());
                    params.put("ville", lieu.getText().toString());
                    params.put("typelog", typelog.getText().toString());
                    params.put("typeloc", typeloc.getText().toString());
                    params.put("nbpieces", nbpieces.getText().toString());
                    params.put("surface", surface.getText().toString());
                    params.put("nbchambres", nbchambres.getText().toString());
                    params.put("prix", prix.getText().toString());
                    params.put("etage", etage.getText().toString());
                    params.put("description", description.getText().toString());
                    params.put("email", ConnexionActivity.getEmail(getApplicationContext()));
                    params.put("mdp", ConnexionActivity.getMdp(getApplicationContext()));
                    params.put("ascenseur", ascenseur.isChecked());
                    params.put("caveBox", caveBox.isChecked());
                    params.put("balconTerasse", balconTerasse.isChecked());
                    params.put("baignoire", baignoire.isChecked());
                    params.put("meuble", meuble.isChecked());
                    String json = new JSONObject(params).toString();

                    //Si certains champs sont vides on n'envoie pas de requête au serveur
                    if (titre.getText().toString().equals("") ||
                            lieu.getText().toString().equals("") ||
                            typelog.getText().toString().equals("") ||
                            typeloc.getText().toString().equals("") ||
                            nbpieces.getText().toString().equals("") ||
                            surface.getText().toString().equals("") ||
                            nbchambres.getText().toString().equals("") ||
                            prix.getText().toString().equals("") ||
                            etage.getText().toString().equals("") ||
                            description.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Remplissez tous les champs s'il vous plaît!", Toast.LENGTH_LONG);
                        toast.show();
                    } else {

                        final String URL = "http://10.0.2.2:8080/ajoutannonce";
                        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                                response -> {
                                    Log.i("Retour serveur ", response.toString());

                                    Toast toast = Toast.makeText(getApplicationContext(), "Annonce ajoutée !", Toast.LENGTH_LONG);
                                    toast.show();
                                    Intent mesAnnonces = new Intent(getApplicationContext(), MesAnnoncesActivity.class);
                                    startActivity(mesAnnonces);
                                    overridePendingTransition(0, 0);
                                    finish();

                                }, error -> Log.i("Erreur serveur ", error.getMessage()));

                        ApplicationController.getInstance().addToRequestQueue(req);
                    }
                }
            });
        }
    }
}