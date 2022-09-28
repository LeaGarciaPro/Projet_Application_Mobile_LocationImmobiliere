package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lancement = new Intent(getApplicationContext(), LancementActivity.class);
                startActivity(lancement);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Button button_s_inscrire = (Button) findViewById(R.id.button_s_inscrire);
        Button button_pro = (Button) findViewById(R.id.button_pro);

        button_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pro = new Intent(getApplicationContext(), InscriptionProActivity.class);
                startActivity(pro);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        button_s_inscrire.setOnClickListener(new View.OnClickListener() {
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

                    final String URL = "http://10.0.2.2:8080/inscriptionparticulier";
                    Map params = new HashMap();
                    params.put("nom", nom.getText().toString());
                    params.put("prenom", prenom.getText().toString());
                    params.put("tel", tel.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("mdp", mdp.getText().toString());
                    params.put("ville", ville.getText().toString());
                    params.put("code", code.getText().toString());

                    JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                            response -> {
                                Log.i("Retour serveur ", response.toString());
                                Toast toast = Toast.makeText(getApplicationContext(), "Inscription réussie !", Toast.LENGTH_SHORT);
                                toast.show();
                                //On demande de se connecter pour des raisons de sécurité !
                                Intent connexion = new Intent(getApplicationContext(), ConnexionActivity.class);
                                startActivity(connexion);
                                overridePendingTransition(0, 0);
                                finish();
                            }, error -> Log.i("Erreur serveur ", error.getMessage()));

                    ApplicationController.getInstance().addToRequestQueue(req);

                }
            }
        });

    }
}
