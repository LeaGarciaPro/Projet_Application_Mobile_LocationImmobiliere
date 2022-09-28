package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.models.Annonce;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModifierAnnonceActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_annonce);

        //Flèche retour barre navigation
        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(view -> {
            Intent retour_annonces = new Intent(getApplicationContext(), MesAnnoncesActivity.class);
            startActivity(retour_annonces);
            overridePendingTransition(0, 0);
            finish();
        });

        //Je récupère l'annonce à modifier
        Annonce monannonce = getIntent().getParcelableExtra("annonceSelectionnee");

        int idAnnonce = monannonce.getIdAnnonce();

        //J'ai les données, je remplis les champs avec les valeurs
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

        titre.setText(monannonce.getTitre());
        lieu.setText(monannonce.getVille());
        typelog.setText(monannonce.getTypeLogement());
        typeloc.setText(monannonce.getTypeLocation());
        nbpieces.setText(Integer.toString(monannonce.getNbPieces()));
        surface.setText(Integer.toString(monannonce.getSurface()));
        nbchambres.setText(Integer.toString(monannonce.getNbChambres()));
        prix.setText(Integer.toString(monannonce.getPrix()));
        etage.setText(Integer.toString(monannonce.getNumeroEtage()));
        description.setText(monannonce.getDescription());

        if(monannonce.isAscenseur()){
            //ascenseur.isChecked();
            ascenseur.setChecked(true);
        }
        if(monannonce.isCaveBox()){
            //caveBox.isChecked();
            caveBox.setChecked(true);
        }
        if(monannonce.isBalconTerasse()){
            //balconTerasse.isChecked();
            balconTerasse.setChecked(true);
        }
        if(monannonce.isBaignoire()){
            //baignoire.isChecked();
            baignoire.setChecked(true);
        }
        if(monannonce.isMeuble()){
            //meuble.isChecked();
            meuble.setChecked(true);
        }

        Button savemodif = findViewById(R.id.button_enregistrer_modif);
        savemodif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                CheckBox ascenseur = (CheckBox) findViewById(R.id.ascenseur);
                CheckBox caveBox = (CheckBox) findViewById(R.id.caveBox);
                CheckBox balconTerasse = (CheckBox) findViewById(R.id.balconTerasse);
                CheckBox baignoire = (CheckBox) findViewById(R.id.baignoire);
                CheckBox meuble = (CheckBox) findViewById(R.id.meuble);

                if (titre.getText().toString().equals("") || lieu.getText().toString().equals("")
                        || typelog.getText().toString().equals("")
                        || typeloc.getText().toString().equals("")
                        || nbpieces.getText().toString().equals("")
                        || surface.getText().toString().equals("")
                        || nbchambres.getText().toString().equals("")
                        || prix.getText().toString().equals("")
                        || etage.getText().toString().equals("")
                        || description.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Remplissez tous les champs s'il vous plaît!", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    final String URL = "http://10.0.2.2:8080/modificationannonce";

                    Map params = new HashMap();

                    params.put("titre", titre.getText().toString());
                    params.put("lieu", lieu.getText().toString());
                    params.put("typelog", typelog.getText().toString());
                    params.put("typeloc", typeloc.getText().toString());
                    params.put("nbpieces", nbpieces.getText().toString());
                    params.put("surface", surface.getText().toString());
                    params.put("nbchambres", nbchambres.getText().toString());
                    params.put("prix", prix.getText().toString());
                    params.put("etage", etage.getText().toString());
                    params.put("description", description.getText().toString());
                    params.put("ascenseur", ascenseur.isChecked());
                    params.put("caveBox", caveBox.isChecked());
                    params.put("balconTerasse", balconTerasse.isChecked());
                    params.put("baignoire", baignoire.isChecked());
                    params.put("meuble", meuble.isChecked());
                    params.put("id", Integer.toString(idAnnonce));

                    JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                            response -> {
                                Log.i("Retour serveur ", response.toString());

                                Toast toast = Toast.makeText(getApplicationContext(), "Annonce modifiée !", Toast.LENGTH_LONG);
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