package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetlocationimm.models.Annonce;
import com.example.projetlocationimm.models.Utilisateur;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class VisualiserAnnonceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser_annonce);

        Context contexte = this;
        Intent intent = getIntent();

        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent.hasExtra("retourfav")){
                    Intent lancement = new Intent(getApplicationContext(), FavorisActivity.class);
                    startActivity(lancement);
                    overridePendingTransition(0, 0);
                    finish();
                } else{
                    Intent lancement = new Intent(getApplicationContext(), AccueilActivity.class);
                    startActivity(lancement);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });



        Annonce annonce = (Annonce) getIntent().getParcelableExtra("annonceSelectionnee");

        TextView titre = findViewById(R.id.titre);
        titre.setText(annonce.getTitre());

        TextView prix = findViewById(R.id.prix);
        prix.setText(annonce.getPrix() + "€");

        TextView ville = findViewById(R.id.ville);
        ville.setText(annonce.getVille());

        TextView typeLogement = findViewById(R.id.typeLogement);
        typeLogement.setText(annonce.getTypeLogement());

        TextView typeLocation = findViewById(R.id.typeLocation);
        typeLocation.setText("Location : " + annonce.getTypeLocation());

        TextView surface = findViewById(R.id.surface);
        surface.setText(annonce.getSurface() + "m²");

        TextView nbPieces = findViewById(R.id.nbPieces);
        if (annonce.getNbPieces() == 1) {
            nbPieces.setText(annonce.getNbPieces() + " pièce");
        } else {
            nbPieces.setText(annonce.getNbPieces() + " pièces");
        }

        TextView nbChambres = findViewById(R.id.nbChambres);
        if (annonce.getNbChambres() == 1) {
            nbChambres.setText(annonce.getNbChambres() + " chambre");
        } else {
            nbChambres.setText(annonce.getNbChambres() + " chambres");
        }

        TextView caracteristiques = findViewById(R.id.carac);
        String carac = "";
        if (annonce.isAscenseur()){
            carac+= "Ascenseur\n";
        }
        if (annonce.isBaignoire()){
            carac+= "Baignoire\n";
        }
        if (annonce.isBalconTerasse()){
            carac+= "Balcon/Terrase\n";
        }
        if (annonce.isMeuble()){
            carac+= "Meublé\n";
        }
        if(annonce.isCaveBox()){
            carac+= "Cave/Box";
        }
        caracteristiques.setText(carac);


        TextView description = findViewById(R.id.textView_description);
        description.setText(annonce.getDescription());

        TextView textViewAnnonceur = findViewById(R.id.textView_annonceur);
        Utilisateur annonceur = annonce.getIdAnnonceur();
        String nom = annonceur.getNom();
        String prenom = annonceur.getPrenom();
        String nomcomplet = prenom + " " + nom;
        textViewAnnonceur.setText(nomcomplet);


        //A GARDER
        Button button_contact_annonceur = findViewById(R.id.button_contact_annonceur);
        button_contact_annonceur.setOnClickListener(view -> {
            String type = ConnexionActivity.getType(getApplicationContext());
            if (type.equals("part") || type.equals("pro")) {
                Intent contacter = new Intent(getApplicationContext(), ConversationActivity.class);
                contacter.putExtra("annonce", annonce);
                startActivity(contacter);
                overridePendingTransition(0, 0);
            } else {
                Intent impossible = new Intent(getApplicationContext(), LancementActivity.class);
                startActivity(impossible);
                overridePendingTransition(0, 0);
            }
        });


    }
}