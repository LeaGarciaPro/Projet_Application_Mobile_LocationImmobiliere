package webServiceREST.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Alerte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAlerte;
	
	@Column(name="typeLogement")
	private String typeLogement;
	
	@Column(name="prixMax")
	private int prixMax;
	
	@Column(name="ville")
	private String ville;
	
	@Column(name="typeLocation")
	private String typeLocation;

	public Alerte(int idAlerte, String typeLogement, int prixMax, String ville, String typeLocation) {
		super();
		this.idAlerte = idAlerte;
		this.typeLogement = typeLogement;
		this.prixMax = prixMax;
		this.ville = ville;
		this.typeLocation = typeLocation;
	}

	public int getIdAlerte() {
		return idAlerte;
	}

	public void setIdAlerte(int idAlerte) {
		this.idAlerte = idAlerte;
	}

	public String getTypeLogement() {
		return typeLogement;
	}

	public void setTypeLogement(String typeLogement) {
		this.typeLogement = typeLogement;
	}

	public int getPrixMax() {
		return prixMax;
	}

	public void setPrixMax(int prixMax) {
		this.prixMax = prixMax;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTypeLocation() {
		return typeLocation;
	}

	public void setTypeLocation(String typeLocation) {
		this.typeLocation = typeLocation;
	}


}
