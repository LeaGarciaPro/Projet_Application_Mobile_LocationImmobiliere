package com.example.projetlocationimm.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

    private int idMessage;
    private Utilisateur idUtilisateur1;
    private Utilisateur idUtilisateur2;
    private Annonce idAnnonce;
    private String contenuMessage;
    private boolean messageSeen;

    public Message(Utilisateur idUtilisateur1, Utilisateur idUtilisateur2, Annonce idAnnonce, String contenuMessage, boolean messageSeen) {
        this.idUtilisateur1 = idUtilisateur1;
        this.idUtilisateur2 = idUtilisateur2;
        this.idAnnonce = idAnnonce;
        this.contenuMessage = contenuMessage;
        this.messageSeen = messageSeen;
    }

    protected Message(Parcel in) {
        idMessage = in.readInt();
        idUtilisateur1 = in.readParcelable(Utilisateur.class.getClassLoader());
        idUtilisateur2 = in.readParcelable(Utilisateur.class.getClassLoader());
        idAnnonce = in.readParcelable(Annonce.class.getClassLoader());
        contenuMessage = in.readString();
        messageSeen = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idMessage);
        dest.writeParcelable(idUtilisateur1, flags);
        dest.writeParcelable(idUtilisateur2, flags);
        dest.writeParcelable(idAnnonce, flags);
        dest.writeString(contenuMessage);
        dest.writeByte((byte) (messageSeen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
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

    public String getContenuMessage() {
        return contenuMessage;
    }

    public void setContenuMessage(String contenuMessage) {
        this.contenuMessage = contenuMessage;
    }

    public boolean isMessageSeen() {
        return messageSeen;
    }

    public void setMessageSeen(boolean messageSeen) {
        this.messageSeen = messageSeen;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", idUtilisateur1=" + idUtilisateur1 +
                ", idUtilisateur2=" + idUtilisateur2 +
                ", idAnnonce=" + idAnnonce +
                ", contenuMessage='" + contenuMessage + '\'' +
                ", messageSeen=" + messageSeen +
                '}';
    }
}
