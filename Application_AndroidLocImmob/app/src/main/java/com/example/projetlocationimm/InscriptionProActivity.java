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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class InscriptionProActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_pro);

        Button button_particulier = (Button) findViewById(R.id.button_particulier);

        button_particulier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent particulier = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(particulier);
                overridePendingTransition(0, 0);
                finish();
            }
        });

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

        Button button_suite_inscription_pro = (Button) findViewById(R.id.button_suite_inscription_pro);
        button_suite_inscription_pro.setOnClickListener(new View.OnClickListener() {
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

                //tests champs vides et mots de passes identiques avant de passer à l'activité suivante
                if (nom.getText().toString().equals("") || prenom.getText().toString().equals("") || tel.getText().toString().equals("") || email.getText().toString().equals("") || mdp.getText().toString().equals("") || ville.getText().toString().equals("") || code.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Certains champs sont vides veuillez compléter !", Toast.LENGTH_LONG);
                    toast.show();
                } else if (!(mdp.getText().toString().equals(mdp2.getText().toString()))) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Les deux mots de passes doivent être identiques !", Toast.LENGTH_LONG);
                    toast.show();
                } else {

                    //on passe les arguments à l'activité suivante et on la lance
                    Intent suite_inscrip_pro = new Intent(getApplicationContext(), InscriptionProSuiteActivity.class);
                    suite_inscrip_pro.putExtra("nom", nom.getText().toString());
                    suite_inscrip_pro.putExtra("prenom", prenom.getText().toString());
                    suite_inscrip_pro.putExtra("tel", tel.getText().toString());
                    suite_inscrip_pro.putExtra("email", email.getText().toString());
                    suite_inscrip_pro.putExtra("mdp", mdp.getText().toString());
                    suite_inscrip_pro.putExtra("ville", ville.getText().toString());
                    suite_inscrip_pro.putExtra("code", code.getText().toString());
                    startActivity(suite_inscrip_pro);
                    overridePendingTransition(0, 0);
                    finish();

                }
            }
        });
    }
}


