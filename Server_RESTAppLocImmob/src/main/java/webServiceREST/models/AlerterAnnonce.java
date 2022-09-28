package webServiceREST.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AlerterAnnonce {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAlerterAnnonce;
	
	@Column(name="idAlerte")
	private int idAlerte;
	
	@Column(name="idAnnonce")
	private int idAnnonce;

}
