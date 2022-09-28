package com.example.projetlocationimm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.ApplicationController;
import com.example.projetlocationimm.ConnexionActivity;
import com.example.projetlocationimm.LancementActivity;
import com.example.projetlocationimm.ModifierAnnonceActivity;
import com.example.projetlocationimm.R;
import com.example.projetlocationimm.VisualiserAnnonceActivity;
import com.example.projetlocationimm.models.Annonce;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnnonceFavorisAdapter extends ArrayAdapter<Annonce> {

    Activity context;
    private ArrayList<Annonce> annonces;

    public AnnonceFavorisAdapter(Context context, ArrayList<Annonce> annonces) {
        super(context, 0, annonces);
        this.context = (Activity) context;
        this.annonces = annonces;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Annonce annonce = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout_favoris, parent, false);
        }

        TextView titre = (TextView) convertView.findViewById(R.id.titre);
        TextView ville = (TextView) convertView.findViewById(R.id.ville);
        TextView prix = (TextView) convertView.findViewById(R.id.prix);
        Button button_retirer = convertView.findViewById(R.id.button_retirer);
        Button button_visualiser = convertView.findViewById(R.id.button_visualiser);
        button_retirer.setTag(position);
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
        ville.setText(annonce.getVille());
        prix.setText(annonce.getPrix() + "€");

        View finalConvertView = convertView;
        button_retirer.setOnClickListener(view -> {
            Map params = new HashMap();
            params.put("idUtilisateur", ConnexionActivity.getId(context));
            params.put("idAnnonce", annonce.getIdAnnonce());
            final String URL = "http://10.0.2.2:8080/supprimerfavori";
            JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                    response -> {
                        Log.i("Retour serveur ", response.toString());

                    }, error -> Log.i("Erreur serveur ", error.getMessage()));

            ApplicationController.getInstance().addToRequestQueue(req);
            //TODO supprimer de view
            annonces.remove(annonce);
            notifyDataSetChanged();

        });

        button_visualiser.setOnClickListener(view -> {
            Intent modifier = new Intent(getContext(), VisualiserAnnonceActivity.class);
            modifier.putExtra("annonceSelectionnee", annonce);
            modifier.putExtra("retourfav", "retourfav");
            context.startActivity(modifier);
            context.overridePendingTransition(0,0);
            //context.finish();
        });

        return convertView;
    }
}