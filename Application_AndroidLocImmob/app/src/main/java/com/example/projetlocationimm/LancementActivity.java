package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LancementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancement);

        if (ConnexionActivity.isLogged(getApplicationContext())) {
            //si il est connecté on lance directement l'activité accueil
            Intent accueil = new Intent(getApplicationContext(), AccueilActivity.class);
            startActivity(accueil);

            //sinon on lance l'application dès le départ
        } else {

            Button button_connexion = (Button) findViewById(R.id.button_connexion);

            button_connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent connexion = new Intent(getApplicationContext(), ConnexionActivity.class);
                    startActivity(connexion);
                    //finish();
                }
            });

            Button button_inscription = (Button) findViewById(R.id.button_inscription);

            button_inscription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inscription = new Intent(getApplicationContext(), InscriptionActivity.class);
                    startActivity(inscription);
                    //finish();
                }
            });

            TextView textView_continuer = (TextView) findViewById(R.id.textView_continuer);

            textView_continuer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent accueil = new Intent(getApplicationContext(), AccueilActivity.class);
                    startActivity(accueil);
                    //finish();
                }
            });
        }
    }
}