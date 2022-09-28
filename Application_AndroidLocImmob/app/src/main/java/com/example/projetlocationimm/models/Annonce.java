package com.example.projetlocationimm.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Annonce implements Parcelable {

    private int idAnnonce;
    private String titre;
    private String ville;
    private String typeLogement;
    private String typeLocation;
    private int surface;
    private int prix;
    private int nbPieces;
    private int nbChambres;
    private int numeroEtage;
    private boolean ascenseur;
    private boolean caveBox;
    private boolean balconTerasse;
    private boolean baignoire;
    private boolean meuble;
    private String description;
    private boolean boost;
    private Utilisateur idAnnonceur;

    public Annonce(int idAnnonce, String titre, String ville, String typeLogement, String typeLocation, int surface, int prix, int nbPieces, int nbChambres, int numeroEtage, boolean ascenseur, boolean caveBox, boolean balconTerasse, boolean baignoire, boolean meuble, String description, boolean boost, Utilisateur idAnnonceur) {
        this.idAnnonce = idAnnonce;
        this.titre = titre;
        this.ville = ville;
        this.typeLogement = typeLogement;
        this.typeLocation = typeLocation;
        this.surface = surface;
        this.prix = prix;
        this.nbPieces = nbPieces;
        this.nbChambres = nbChambres;
        this.numeroEtage = numeroEtage;
        this.ascenseur = ascenseur;
        this.caveBox = caveBox;
        this.balconTerasse = balconTerasse;
        this.baignoire = baignoire;
        this.meuble = meuble;
        this.description = description;
        this.boost = boost;
        this.idAnnonceur = idAnnonceur;
    }

    protected Annonce(Parcel in) {
        idAnnonce = in.readInt();
        titre = in.readString();
        ville = in.readString();
        typeLogement = in.readString();
        typeLocation = in.readString();
        surface = in.readInt();
        prix = in.readInt();
        nbPieces = in.readInt();
        nbChambres = in.readInt();
        numeroEtage = in.readInt();
        ascenseur = in.readByte() != 0;
        caveBox = in.readByte() != 0;
        balconTerasse = in.readByte() != 0;
        baignoire = in.readByte() != 0;
        meuble = in.readByte() != 0;
        description = in.readString();
        boost = in.readByte() != 0;
        idAnnonceur = in.readParcelable(Utilisateur.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAnnonce);
        dest.writeString(titre);
        dest.writeString(ville);
        dest.writeString(typeLogement);
        dest.writeString(typeLocation);
        dest.writeInt(surface);
        dest.writeInt(prix);
        dest.writeInt(nbPieces);
        dest.writeInt(nbChambres);
        dest.writeInt(numeroEtage);
        dest.writeByte((byte) (ascenseur ? 1 : 0));
        dest.writeByte((byte) (caveBox ? 1 : 0));
        dest.writeByte((byte) (balconTerasse ? 1 : 0));
        dest.writeByte((byte) (baignoire ? 1 : 0));
        dest.writeByte((byte) (meuble ? 1 : 0));
        dest.writeString(description);
        dest.writeByte((byte) (boost ? 1 : 0));
        dest.writeParcelable(idAnnonceur, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Annonce> CREATOR = new Creator<Annonce>() {
        @Override
        public Annonce createFromParcel(Parcel in) {
            return new Annonce(in);
        }

        @Override
        public Annonce[] newArray(int size) {
            return new Annonce[size];
        }
    };

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTypeLogement() {
        return typeLogement;
    }

    public void setTypeLogement(String typeLogement) {
        this.typeLogement = typeLogement;
    }

    public String getTypeLocation() {
        return typeLocation;
    }

    public void setTypeLocation(String typeLocation) {
        this.typeLocation = typeLocation;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getNbPieces() {
        return nbPieces;
    }

    public void setNbPieces(int nbPieces) {
        this.nbPieces = nbPieces;
    }

    public int getNbChambres() {
        return nbChambres;
    }

    public void setNbChambres(int nbChambres) {
        this.nbChambres = nbChambres;
    }

    public int getNumeroEtage() {
        return numeroEtage;
    }

    public void setNumeroEtage(int numeroEtage) {
        this.numeroEtage = numeroEtage;
    }

    public boolean isAscenseur() {
        return ascenseur;
    }

    public void setAscenseur(boolean ascenseur) {
        this.ascenseur = ascenseur;
    }

    public boolean isCaveBox() {
        return caveBox;
    }

    public void setCaveBox(boolean caveBox) {
        this.caveBox = caveBox;
    }

    public boolean isBalconTerasse() {
        return balconTerasse;
    }

    public void setBalconTerasse(boolean balconTerasse) {
        this.balconTerasse = balconTerasse;
    }

    public boolean isBaignoire() {
        return baignoire;
    }

    public void setBaignoire(boolean baignoire) {
        this.baignoire = baignoire;
    }

    public boolean isMeuble() {
        return meuble;
    }

    public void setMeuble(boolean meuble) {
        this.meuble = meuble;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBoost() {
        return boost;
    }

    public void setBoost(boolean boost) {
        this.boost = boost;
    }

    public Utilisateur getIdAnnonceur() {
        return idAnnonceur;
    }

    public void setIdAnnonceur(Utilisateur idAnnonceur) {
        this.idAnnonceur = idAnnonceur;
    }
}
