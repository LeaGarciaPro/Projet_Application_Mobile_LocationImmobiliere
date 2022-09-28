package com.example.projetlocationimm.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Conversation implements Parcelable {

    private int idConversation;
    private Utilisateur idUtilisateur1;
    private Utilisateur idUtilisateur2;
    private Annonce idAnnonce;
    private List<Message> listeMessages;

    public Conversation(int idConversation, Utilisateur idUtilisateur1, Utilisateur idUtilisateur2, Annonce idAnnonce, List<Message> listeMessages) {
        this.idConversation = idConversation;
        this.idUtilisateur1 = idUtilisateur1;
        this.idUtilisateur2 = idUtilisateur2;
        this.idAnnonce = idAnnonce;
        this.listeMessages = listeMessages;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public Utilisateur getIdUtilisateur1() {
        return idUtilisateur1;
    }

    public void setIdUtilisateur1(Utilisateur idUtilisateur1) {
        this.idUtilisateur1 = idUtilisateur1;
    }

    public Utilisateur getIdUtilisateur2() {
        return idUtilisateur2;
    }

    public void setIdUtilisateur2(Utilisateur idUtilisateur2) {
        this.idUtilisateur2 = idUtilisateur2;
    }

    public Annonce getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(Annonce idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public List<Message> getListeMessages() {
        return listeMessages;
    }

    public void setListeMessages(List<Message> listeMessages) {
        this.listeMessages = listeMessages;
    }

    public static Creator<Conversation> getCREATOR() {
        return CREATOR;
    }

    protected Conversation(Parcel in) {
        idConversation = in.readInt();
        idUtilisateur1 = in.readParcelable(Utilisateur.class.getClassLoader());
        idUtilisateur2 = in.readParcelable(Utilisateur.class.getClassLoader());
        idAnnonce = in.readParcelable(Annonce.class.getClassLoader());
        listeMessages = in.createTypedArrayList(Message.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idConversation);
        dest.writeParcelable(idUtilisateur1, flags);
        dest.writeParcelable(idUtilisateur2, flags);
        dest.writeParcelable(idAnnonce, flags);
        dest.writeTypedList(listeMessages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Conversation> CREATOR = new Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel in) {
            return new Conversation(in);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}


