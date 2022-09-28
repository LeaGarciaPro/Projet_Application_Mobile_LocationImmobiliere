package webServiceREST.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UtilisateurPro extends Utilisateur {
	
	@Column(name="abonnement")
	private boolean abonnement;
	
	@Column(name="typeAbonnement")
	private String typeAbonnement;
	
	public UtilisateurPro() {};

	public UtilisateurPro(String nom, String prenom, String mail, String mdp, String telephone,
			String ville, String codePostal, int idAlerte, boolean abonnement,
			String typeAbonnement) {
		super(nom, prenom, mail, mdp, telephone, ville, codePostal, idAlerte);
		this.abonnement = abonnement;
		this.typeAbonnement = typeAbonnement;
	}

	public boolean isAbonnement() {
		return abonnement;
	}

	public void setAbonnement(boolean abonnement) {
		this.abonnement = abonnement;
	}

	public String getTypeAbonnement() {
		return typeAbonnement;
	}

	public void setTypeAbonnement(String typeAbonnement) {
		this.typeAbonnement = typeAbonnement;
	}
	
}
