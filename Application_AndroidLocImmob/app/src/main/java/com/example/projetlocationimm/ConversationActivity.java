package com.example.projetlocationimm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetlocationimm.adapters.ConversationAdapter;
import com.example.projetlocationimm.models.Annonce;
import com.example.projetlocationimm.models.Conversation;
import com.example.projetlocationimm.models.Message;
import com.example.projetlocationimm.models.Utilisateur;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        //Flèche retour barre navigation
        MaterialToolbar fleche_retour = findViewById(R.id.topAppBarFleche);
        fleche_retour.setOnClickListener(view -> {
            Intent messages = new Intent(getApplicationContext(), MessagesActivity.class);
            startActivity(messages);
            overridePendingTransition(0, 0);
        });

        //Afficher la conversation
        Intent intent = getIntent();

        if (intent.hasExtra("conversationSelectionnee")) {
            Conversation conversation = getIntent().getParcelableExtra("conversationSelectionnee");

            //On affiche les coordonnées
            TextView contact = findViewById(R.id.contact);
            TextView annonce_nom = findViewById(R.id.annonce_nom);
            Utilisateur correspondant;
            Utilisateur utilisateur;

            if (conversation.getIdUtilisateur1().getIdUtilisateur() == Integer.parseInt(ConnexionActivity.getId(getApplicationContext()))) {
                contact.setText(conversation.getIdUtilisateur2().getPrenom() + " " + conversation.getIdUtilisateur2().getNom());
                correspondant = conversation.getIdUtilisateur2();
                utilisateur = conversation.getIdUtilisateur1();
            } else {
                contact.setText(conversation.getIdUtilisateur1().getPrenom() + " " + conversation.getIdUtilisateur1().getNom());
                correspondant = conversation.getIdUtilisateur1();
                utilisateur = conversation.getIdUtilisateur2();
            }
            annonce_nom.setText(conversation.getIdAnnonce().getTitre());

            final ListView listView = findViewById(R.id.conversation_list_view);
            listView.setDivider(null);
            listView.setAdapter(new ConversationAdapter(this, (ArrayList<Message>) conversation.getListeMessages()));

            FloatingActionButton button_envoyer = findViewById(R.id.button_envoyer);
            button_envoyer.setOnClickListener(view -> {
                EditText message = findViewById(R.id.message_input);
                Message nouveauMessage = new Message(utilisateur, correspondant, conversation.getIdAnnonce(), message.getText().toString(), false);
                conversation.getListeMessages().add(nouveauMessage);
                final ListView listView2 = findViewById(R.id.conversation_list_view);
                listView2.setDivider(null);
                listView2.setAdapter(new ConversationAdapter(this, (ArrayList<Message>) conversation.getListeMessages()));

                Map params = new HashMap();
                params.put("conversation", conversation.getIdConversation());
                params.put("envoyeur", utilisateur.getIdUtilisateur());
                params.put("receveur", correspondant.getIdUtilisateur());
                params.put("annonce", conversation.getIdAnnonce().getIdAnnonce());
                params.put("message", message.getText().toString());

                final String URL = "http://10.0.2.2:8080/envoimessage";
                JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                        response -> {
                            Log.i("Retour serveur ", response.toString());
                        }, error -> VolleyLog.e("Error: ", error.getMessage()));

                ApplicationController.getInstance().addToRequestQueue(req);
            });
        } else if (intent.hasExtra("annonce")) {

            //On affiche les coordonnées
            TextView contact = findViewById(R.id.contact);
            TextView annonce_nom = findViewById(R.id.annonce_nom);
            Utilisateur correspondant;
            Utilisateur utilisateur;
            Annonce annonce = getIntent().getParcelableExtra("annonce");
            contact.setText(annonce.getIdAnnonceur().getPrenom() + " " + annonce.getIdAnnonceur().getNom());
            annonce_nom.setText(annonce.getTitre());

            final String URL = "http://10.0.2.2:8080/recuperationdonnees";
            Map params = new HashMap();
            params.put("email", ConnexionActivity.getEmail(getApplicationContext()));
            params.put("mdp", ConnexionActivity.getMdp(getApplicationContext()));
            AtomicReference<Utilisateur> utilisateur2 = new AtomicReference<Utilisateur>();
            JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                    response -> {
                        Log.i("Retour serveur appuie bouton envoyer", String.valueOf(response));
                        Type type = new TypeToken<Utilisateur>() {
                        }.getType();
                        utilisateur2.set(new Gson().fromJson(String.valueOf(response), type));
                        Log.i("LEA", utilisateur2.get().getNom());
                    }, error -> VolleyLog.e("Error: ", error.getMessage()));

            ApplicationController.getInstance().addToRequestQueue(req);

            FloatingActionButton button_envoyer = findViewById(R.id.button_envoyer);
            button_envoyer.setOnClickListener(view -> {
                Utilisateur utilisateur1 = new Utilisateur();
                EditText message = findViewById(R.id.message_input);

                Message nouveauMessage = new Message(utilisateur2.get(), annonce.getIdAnnonceur(), annonce, message.getText().toString(), false);
                ArrayList<Message> nouveau = new ArrayList<>();
                nouveau.add(nouveauMessage);
                final ListView listView2 = findViewById(R.id.conversation_list_view);
                listView2.setDivider(null);
                listView2.setAdapter(new ConversationAdapter(this, nouveau));

                Map params2 = new HashMap();
                params2.put("envoyeur", ConnexionActivity.getId(getApplicationContext()));
                params2.put("receveur", annonce.getIdAnnonceur().getIdUtilisateur());
                params2.put("annonce", annonce.getIdAnnonce());
                params2.put("message", message.getText().toString());

                final String URL2 = "http://10.0.2.2:8080/envoimessagenouvelleconversation";
                JsonObjectRequest req2 = new JsonObjectRequest(URL2, new JSONObject(params2),
                        response -> {
                            Log.i("Retour serveur ", response.toString());
                        }, error -> VolleyLog.e("Error: ", error.getMessage()));

                ApplicationController.getInstance().addToRequestQueue(req2);
            });



        }


    }
}