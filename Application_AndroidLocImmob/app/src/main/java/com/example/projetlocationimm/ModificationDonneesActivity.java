package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;

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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ModificationDonneesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_donnees);

        //On récupère toutes les données de l'utilisateur pour pouvoir les afficher

        EditText email = findViewById(R.id.connexion_email);
        EditText mdp = findViewById(R.id.connexion_mdp);

        final String URL = "http://10.0.2.2:8080/recuperationdonnees";
        Map params = new HashMap();
        params.put("email", ConnexionActivity.getEmail(getApplicationContext()));
        params.put("mdp", ConnexionActivity.getMdp(getApplicationContext()));

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());

                        //J'ai les données, je remplis les champs avec les valeurs
                        TextInputEditText nom = (TextInputEditText) findViewById(R.id.field_nom);
                        TextInputEditText prenom = (TextInputEditText) findViewById(R.id.field_prenom);
                        TextInputEditText tel = (TextInputEditText) findViewById(R.id.field_telephone);
                        TextInputEditText email2 = (TextInputEditText) findViewById(R.id.field_email);
                        TextInputEditText mdp3 = (TextInputEditText) findViewById(R.id.field_mdp);
                        TextInputEditText mdp2 = (TextInputEditText) findViewById(R.id.field_mdp2);
                        TextInputEditText ville = (TextInputEditText) findViewById(R.id.field_ville);
                        TextInputEditText code = (TextInputEditText) findViewById(R.id.field_codePostal);
                        try {
                            nom.setText(response.getString("nom"));
                            prenom.setText(response.getString("prenom"));
                            tel.setText(response.getString("telephone"));
                            email2.setText(response.getString("mail"));
                            mdp3.setText(response.getString("mdp"));
                            mdp2.setText(response.getString("mdp"));
                            ville.setText(response.getString("ville"));
                            code.setText(response.getString("codePostal"));
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        ApplicationController.getInstance().addToRequestQueue(req);

        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                }
            }
        });

        Button modifier = findViewById(R.id.button_modifier);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextInputEditText nom = (TextInputEditText) findViewById(R.id.field_nom);
                TextInputEditText prenom = (TextInputEditText) findViewById(R.id.field_prenom);
                TextInputEditText tel = (TextInputEditText) findViewById(R.id.field_telephone);
                TextInputEditText email = (TextInputEditText) findViewById(R.id.field_email);
                TextInputEditText mdp = (TextInputEditText) findViewById(R.id.field_mdp);
                TextInputEditText mdp2 = (TextInputEditText) findViewById(R.id.field_mdp2);
                TextInputEditText ville = (TextInputEditText) findViewById(R.id.field_ville);
                TextInputEditText code = (TextInputEditText) findViewById(R.id.field_codePostal);

                if (nom.getText().toString().equals("") || prenom.getText().toString().equals("") || tel.getText().toString().equals("") || email.getText().toString().equals("") || mdp.getText().toString().equals("") || ville.getText().toString().equals("") || code.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Remplissez tous les champs s'il vous plaît!", Toast.LENGTH_LONG);
                    toast.show();
                } else if (!(mdp.getText().toString().equals(mdp2.getText().toString()))) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Les deux mots de passe doivent être identiques", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    final String URL = "http://10.0.2.2:8080/modificationdonnees";
                    Map params = new HashMap();

                    params.put("nom", nom.getText().toString());
                    params.put("prenom", prenom.getText().toString());
                    params.put("tel", tel.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("mdp", mdp.getText().toString());
                    params.put("ville", ville.getText().toString());
                    params.put("code", code.getText().toString());

                    JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.i("Response", response.toString());
                                    Toast toast = Toast.makeText(getApplicationContext(), "Vos données ont été modifiées !", Toast.LENGTH_LONG);
                                    toast.show();
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
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    });

                    ApplicationController.getInstance().addToRequestQueue(req);

                }
            }
        });
    }
}