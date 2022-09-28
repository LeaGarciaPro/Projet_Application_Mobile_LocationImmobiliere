package com.example.projetlocationimm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.ApplicationController;
import com.example.projetlocationimm.ConnexionActivity;
import com.example.projetlocationimm.ConversationActivity;
import com.example.projetlocationimm.LancementActivity;
import com.example.projetlocationimm.R;
import com.example.projetlocationimm.VisualiserAnnonceActivity;
import com.example.projetlocationimm.models.Annonce;
import com.example.projetlocationimm.models.Conversation;
import com.example.projetlocationimm.models.Message;
import com.example.projetlocationimm.models.Utilisateur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagerieAdapter extends ArrayAdapter<Conversation> {

    Activity context;
    private ArrayList<Conversation> conversations;

    public MessagerieAdapter(Context context, ArrayList<Conversation> conversations) {
        super(context, 0, conversations);
        this.context = (Activity) context;
        this.conversations = conversations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Conversation conversation = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout_messagerie, parent, false);
        }

        TextView contact = (TextView) convertView.findViewById(R.id.contact);
        TextView message = (TextView) convertView.findViewById(R.id.message_recu);
        FloatingActionButton button_conversation = convertView.findViewById(R.id.button_conversation);
        button_conversation.setTag(position);

        if(conversation.getIdUtilisateur1().getIdUtilisateur() == Integer.parseInt(ConnexionActivity.getId(context))){
            contact.setText(conversation.getIdUtilisateur2().getPrenom()+" "+conversation.getIdUtilisateur2().getNom());
        }else{
            contact.setText(conversation.getIdUtilisateur1().getPrenom()+" "+conversation.getIdUtilisateur1().getNom());
        }
        message.setText(conversation.getListeMessages().get(conversation.getListeMessages().size()-1).getContenuMessage());
        if(conversation.getListeMessages().get(conversation.getListeMessages().size()-1).getIdUtilisateur1().getIdUtilisateur() == Integer.parseInt(ConnexionActivity.getId(context))) {
            button_conversation.setImageDrawable(context.getResources().getDrawable(R.drawable.icone_message));
            //TODO changer couleur boutton
        }
        else if(!conversation.getListeMessages().get(conversation.getListeMessages().size()-1).isMessageSeen()
        && conversation.getListeMessages().get(conversation.getListeMessages().size()-1).getIdUtilisateur1().getIdUtilisateur() != Integer.parseInt(ConnexionActivity.getId(context))){
            button_conversation.setImageDrawable(context.getResources().getDrawable(R.drawable.icone_message_recu));
        }

        button_conversation.setOnClickListener(view -> {
            Intent conversationClick = new Intent(getContext(), ConversationActivity.class);
            conversationClick.putExtra("conversationSelectionnee", conversation);
            context.startActivity(conversationClick);
            context.overridePendingTransition(0,0);
            if(conversation.getListeMessages().get(conversation.getListeMessages().size() - 1).getIdUtilisateur1().getIdUtilisateur() != Integer.parseInt(ConnexionActivity.getId(context))) {
                conversation.getListeMessages().get(conversation.getListeMessages().size() - 1).setMessageSeen(true);
            }
            //context.finish();
        });

        return convertView;
    }
}