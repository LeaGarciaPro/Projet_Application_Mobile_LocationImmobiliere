package webServiceREST.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Favori")
public class Favori {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFavori")
	private int idFavori;

	@ManyToOne
	@JoinColumn(name = "idAnnonce", nullable = false)
	private Annonce annonce;

	@ManyToOne
	@JoinColumn(name = "idUtilisateur", nullable = false)
	private Utilisateur utilisateur;

	public Favori() {
	}

	public Favori(Annonce annonce, Utilisateur utilisateur) {
		super();
		this.annonce = annonce;
		this.utilisateur = utilisateur;
	}

	public int getIdFavori() {
		return idFavori;
	}

	public void setIdFavori(int idFavori) {
		this.idFavori = idFavori;
	}

	public Annonce getAnnonce() {
		return annonce;
	}

	public void setAnnonce(Annonce annonce) {
		this.annonce = annonce;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public String toString() {
		return "Favori [idFavori=" + idFavori + ", annonce=" + annonce + ", utilisateur=" + utilisateur + "]";
	}
	
	
	
	
	
	
	
}
