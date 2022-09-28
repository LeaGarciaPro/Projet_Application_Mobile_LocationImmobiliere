package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
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

public class ConnexionActivity extends AppCompatActivity {

    private FileUtils IOUtils;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String EMAIL = "email";
    public static final String MDP = "mot de passe";
    public static final String TYPE = "type";
    public static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //Flèche retour barre navigation
        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(view -> {
            Intent lancement = new Intent(getApplicationContext(), LancementActivity.class);
            startActivity(lancement);
            overridePendingTransition(0, 0);
            finish();
        });

        //Bouton connxion
        Button button_se_connecter = findViewById(R.id.button_se_connecter);
        button_se_connecter.setOnClickListener(view -> {
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
                //String json = new JSONObject(params).toString();

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Retour serveur ", response.toString());

                                try {
                                    if (response.getString("type").equals("1") || response.getString("type").equals("2")) {
                                        if (response.getString("type").equals("1")) {
                                            ConnexionActivity.saveData(email.getText().toString(), mdp.getText().toString(), "part", response.getString("idUtilisateurConnecte"), getApplicationContext());
                                        } else {
                                            ConnexionActivity.saveData(email.getText().toString(), mdp.getText().toString(), "pro", response.getString("idUtilisateurConnecte"), getApplicationContext());
                                        }

                                        //Dans les deux cas on lance la même activité, c'est à l'intérieur qu'on change l'affichage en fonction du type d'utilisateur
                                        Intent accueil = new Intent(getApplicationContext(), AccueilActivity.class);
                                        startActivity(accueil);
                                        overridePendingTransition(0, 0);
                                        finish();

                                        //connexion échouée
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Email et/ou mot de passe incorrects !", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, error -> Log.i("Erreur serveur ", error.getMessage()));

                ApplicationController.getInstance().addToRequestQueue(req);
            }
        });
    }

    public static void saveData(String email, String mdp, String type, String id, Context contexte) {
        SharedPreferences sharedPreferences = contexte.getSharedPreferences(ConnexionActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, email);
        editor.putString(MDP, mdp);
        editor.putString(TYPE, type);
        editor.putString(ID, id);
        editor.apply();
    }

    public static boolean isLogged(Context contexte) {
        SharedPreferences sharedPreferences = contexte.getSharedPreferences(ConnexionActivity.SHARED_PREFS, MODE_PRIVATE);
        //On récupère les données de l'utilisateur qui s'était déjà connecté
        String email = sharedPreferences.getString(ConnexionActivity.EMAIL, "");
        String mdp = sharedPreferences.getString(ConnexionActivity.MDP, "");
        String type = sharedPreferences.getString(ConnexionActivity.TYPE, "");
        if (!(email.equals("NULL")) && !(mdp.equals("NULL"))) {
            Log.i("Utilisateur connecté", "On recupère ses données que l'on pourra utiliser ensuite (getByid ;))");
            return true;
        } else {
            return false;
        }
    }

    public static void logOut(Context contexte) {
        Log.i("Demande de déconnexion", "ok!");
        SharedPreferences sharedPreferences = contexte.getSharedPreferences(ConnexionActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConnexionActivity.EMAIL, "NULL");
        editor.putString(ConnexionActivity.MDP, "NULL");
        editor.putString(ConnexionActivity.TYPE, "NULL");
        editor.apply();
    }

    public static String getEmail(Context contexte) {
        SharedPreferences sharedPreferences = contexte.getSharedPreferences(ConnexionActivity.SHARED_PREFS, MODE_PRIVATE);
        String email = sharedPreferences.getString(ConnexionActivity.EMAIL, "");
        return email;
    }

    public static String getMdp(Context contexte) {
        SharedPreferences sharedPreferences = contexte.getSharedPreferences(ConnexionActivity.SHARED_PREFS, MODE_PRIVATE);
        String mdp = sharedPreferences.getString(ConnexionActivity.MDP, "");
        return mdp;
    }

    public static String getType(Context contexte) {
        SharedPreferences sharedPreferences = contexte.getSharedPreferences(ConnexionActivity.SHARED_PREFS, MODE_PRIVATE);
        String type = sharedPreferences.getString(ConnexionActivity.TYPE, "");
        return type;
    }

    public static String getId(Context contexte) {
        SharedPreferences sharedPreferences = contexte.getSharedPreferences(ConnexionActivity.SHARED_PREFS, MODE_PRIVATE);
        String id = sharedPreferences.getString(ConnexionActivity.ID, "");
        return id;
    }

}