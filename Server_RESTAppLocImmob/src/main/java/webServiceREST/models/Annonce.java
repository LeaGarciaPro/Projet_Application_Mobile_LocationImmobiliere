package webServiceREST.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Annonce")
public class Annonce {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAnnonce")
	private int idAnnonce;

	@Column(name = "titre")
	private String titre;

	@Column(name = "ville")
	private String ville;

	@Column(name = "typeLogement")
	private String typeLogement;

	@Column(name = "typeLocation")
	private String typeLocation;

	@Column(name = "surface")
	private int surface;

	@Column(name = "prix")
	private int prix;

	@Column(name = "nbPieces")
	private int nbPieces;

	@Column(name = "nbChambres")
	private int nbChambres;

	@Column(name = "numeroEtage")
	private int numeroEtage;

	@Column(name = "ascenseur")
	private boolean ascenseur;

	@Column(name = "caveBox")
	private boolean caveBox;

	@Column(name = "balconTerasse")
	private boolean balconTerasse;

	@Column(name = "baignoire")
	private boolean baignoire;

	@Column(name = "meuble")
	private boolean meuble;

	@Column(name = "description")
	private String description;

	@Column(name = "boost")
	private boolean boost;

	@ManyToOne
	@JoinColumn(name = "idAnnonceur", nullable = false)
	private Utilisateur idAnnonceur;

	public Annonce() {
	}

	public Annonce(String titre, String ville, String typeLogement, String typeLocation, int surface,
			int prix, int nbPieces, int nbChambres, int numeroEtage, boolean ascenseur, boolean caveBox,
			boolean balconTerasse, boolean baignoire, boolean meuble, String description, boolean boost,
			Utilisateur idAnnonceur) {
		super();
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