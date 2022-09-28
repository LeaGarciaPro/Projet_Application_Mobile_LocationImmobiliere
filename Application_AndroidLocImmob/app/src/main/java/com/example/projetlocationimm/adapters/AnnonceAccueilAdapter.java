package com.example.projetlocationimm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.ApplicationController;
import com.example.projetlocationimm.ConnexionActivity;
import com.example.projetlocationimm.LancementActivity;
import com.example.projetlocationimm.R;
import com.example.projetlocationimm.VisualiserAnnonceActivity;
import com.example.projetlocationimm.models.Annonce;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AnnonceAccueilAdapter extends ArrayAdapter<Annonce> {

    Activity context;

    public AnnonceAccueilAdapter(Context context, ArrayList<Annonce> annonces) {
        super(context, 0, annonces);
        this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Annonce annonce = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout_accueil, parent, false);
        }

        TextView titre = (TextView) convertView.findViewById(R.id.titre);
        TextView typelocation = (TextView) convertView.findViewById(R.id.typeLocation);
        TextView ville = (TextView) convertView.findViewById(R.id.ville);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView prix = (TextView) convertView.findViewById(R.id.prix);
        Button button_visualiser = convertView.findViewById(R.id.button_visualiser);
        // Cache row position inside the button using `setTag`
        button_visualiser.setTag(position);

        //Malheureusement par manque de temps, images pas liées au serveur
        ImageView image = convertView.findViewById(R.id.imageAnnonce);
        switch(annonce.getIdAnnonceur().getIdUtilisateur()){
            case 1 :image.setImageResource(R.drawable.villa1);
                break;
            case 2 :image.setImageResource(R.drawable.villa2);
                break;
            case 3 :image.setImageResource(R.drawable.villa3);
                break;
            case 4 :image.setImageResource(R.drawable.villa4);
                break;
            case 5 :image.setImageResource(R.drawable.villa5);
                break;
        }

        titre.setText(annonce.getTitre());
        typelocation.setText(annonce.getTypeLocation());
        ville.setText(annonce.getVille());
        description.setText(annonce.getDescription());
        prix.setText(annonce.getPrix() + "€");

        button_visualiser.setOnClickListener(view -> {
            Intent visualiser = new Intent(getContext(), VisualiserAnnonceActivity.class);
            visualiser.putExtra("annonceSelectionnee", annonce);
            context.startActivity(visualiser);
            context.overridePendingTransition(0, 0);
            //context.finish();
        });

        CheckBox icon_favoris = convertView.findViewById(R.id.icon_favoris);
        icon_favoris.setOnClickListener(view -> {
            if (ConnexionActivity.isLogged(getContext())) {
                Map params = new HashMap();
                params.put("idUtilisateur", ConnexionActivity.getId(context));
                params.put("idAnnonce", annonce.getIdAnnonce());
                final String URL = "http://10.0.2.2:8080/ajouterfavoris";
                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        response -> {
                            Log.i("Retour serveur ", response.toString());
                            //TODO retour

                        }, error -> Log.i("Erreur serveur ", error.getMessage()));

                ApplicationController.getInstance().addToRequestQueue(req);

            } else {
                //On le revoit à l'accueil s'il n'est pas connecté
                Intent accueil = new Intent(getContext(), LancementActivity.class);
                context.startActivity(accueil);
                context.overridePendingTransition(0, 0);
                context.finish();
            }
        });


        return convertView;
    }
}