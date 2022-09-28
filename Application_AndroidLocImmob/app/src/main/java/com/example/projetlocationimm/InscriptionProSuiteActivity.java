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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

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

public class InscriptionProSuiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_pro_suite);

        Button button_inscrip_pro = findViewById(R.id.button_s_inscrire_pro);

        button_inscrip_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On récupère le type d'abonnement voulu
                ChipGroup chipGroup = findViewById(R.id.chipGroup);
                boolean abonnement = false;
                String typeAbonnement = "";
                for (int i = 0; i < chipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) chipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        if (!(chip.getText().equals("Ne pas s'abonner"))) {
                            abonnement = true;
                            typeAbonnement = String.valueOf(chip.getText());
                        }
                    }
                }

                Intent intent = getIntent();
                boolean abonnement1 = abonnement;
                String typeAbonnement1 = typeAbonnement;

                final String URL = "http://10.0.2.2:8080/inscriptionprofessionnel";
                Map params = new HashMap();
                params.put("nom", intent.getStringExtra("nom"));
                params.put("prenom", intent.getStringExtra("prenom"));
                params.put("tel", intent.getStringExtra("tel"));
                params.put("email", intent.getStringExtra("email"));
                params.put("mdp", intent.getStringExtra("mdp"));
                params.put("ville", intent.getStringExtra("ville"));
                params.put("code", intent.getStringExtra("code"));
                params.put("abonnement", abonnement1);
                params.put("typeAbonnement", typeAbonnement1);

                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response", response.toString());
                                Toast toast = Toast.makeText(getApplicationContext(), "Inscription réussie !", Toast.LENGTH_SHORT);
                                toast.show();
                                //On demande de se connecter pour des raisons de sécurité !
                                Intent connexion = new Intent(getApplicationContext(), ConnexionActivity.class);
                                startActivity(connexion);
                                overridePendingTransition(0, 0);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });

                ApplicationController.getInstance().addToRequestQueue(req);
            }
        });

        //Flèche retour barre navigation
        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(view -> {
            Intent lancement = new Intent(getApplicationContext(), InscriptionProActivity.class);
            startActivity(lancement);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}