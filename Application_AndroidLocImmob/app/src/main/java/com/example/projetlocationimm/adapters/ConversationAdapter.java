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
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.ApplicationController;
import com.example.projetlocationimm.ConnexionActivity;
import com.example.projetlocationimm.R;
import com.example.projetlocationimm.VisualiserAnnonceActivity;
import com.example.projetlocationimm.models.Annonce;
import com.example.projetlocationimm.models.Message;
import com.example.projetlocationimm.models.Utilisateur;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConversationAdapter extends ArrayAdapter<Message> {

    Activity context;
    private ArrayList<Message> messages;

    public ConversationAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
        this.context = (Activity) context;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = getItem(position);
        if (convertView == null) {
            //L'utilisateur c'est celui qui envoie!
            if(message.getIdUtilisateur1().getIdUtilisateur() == Integer.parseInt(ConnexionActivity.getId(context))){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.send_message, parent, false);
                TextView messageText = (TextView) convertView.findViewById(R.id.message_utilisateur);
                TextView nameText = (TextView) convertView.findViewById(R.id.nom_utilisateur);
                messageText.setText(message.getContenuMessage());
                nameText.setText(message.getIdUtilisateur1().getPrenom());
            }
            else{
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.receive_message, parent, false);
                TextView messageText = (TextView) convertView.findViewById(R.id.message_correspondant);
                TextView nameText = (TextView) convertView.findViewById(R.id.nom_correspondant);
                messageText.setText(message.getContenuMessage());
                nameText.setText(message.getIdUtilisateur1().getPrenom());
            }
        }
        return convertView;
    }
}