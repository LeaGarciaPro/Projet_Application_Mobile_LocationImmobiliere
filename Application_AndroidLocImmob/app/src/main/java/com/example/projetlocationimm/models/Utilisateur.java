package com.example.projetlocationimm.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Utilisateur implements Parcelable {

    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private String telephone;
    private String ville;
    private String codePostal;
    private int idAlerte;

    public Utilisateur(int idUtilisateur, String nom, String prenom, String mail, String mdp, String telephone, String ville, String codePostal, int idAlerte) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.telephone = telephone;
        this.ville = ville;
        this.codePostal = codePostal;
        this.idAlerte = idAlerte;
    }

    protected Utilisateur(Parcel in) {
        idUtilisateur = in.readInt();
        nom = in.readString();
        prenom = in.readString();
        mail = in.readString();
        mdp = in.readString();
        telephone = in.readString();
        ville = in.readString();
        codePostal = in.readString();
        idAlerte = in.readInt();
    }

    public Utilisateur() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUtilisateur);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(mail);
        dest.writeString(mdp);
        dest.writeString(telephone);
        dest.writeString(ville);
        dest.writeString(codePostal);
        dest.writeInt(idAlerte);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public int getIdAlerte() {
        return idAlerte;
    }

    public void setIdAlerte(int idAlerte) {
        this.idAlerte = idAlerte;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", mdp='" + mdp + '\'' +
                ", telephone='" + telephone + '\'' +
                ", ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", idAlerte=" + idAlerte +
                '}';
    }
}
