package webServiceREST.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConsulterAnnonce {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idConsultation;
	
	@Column(name="idUtilisateur")
	private int idUtilisateur;
	
	@Column(name="idAnnonce")
	private int idAnnonce;


}
