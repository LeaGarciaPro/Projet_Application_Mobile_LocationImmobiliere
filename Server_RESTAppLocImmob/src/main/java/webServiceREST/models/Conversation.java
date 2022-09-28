package webServiceREST.models;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Conversation")
public class Conversation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idConversation")
	private int idConversation;

	@ManyToOne
	@JoinColumn(name = "idUtilisateur1", nullable = false)
	private Utilisateur idUtilisateur1;

	@ManyToOne
	@JoinColumn(name = "idUtilisateur2", nullable = false)
	private Utilisateur idUtilisateur2;

	@ManyToOne
	@JoinColumn(name = "idAnnonce", nullable = false)
	private Annonce idAnnonce;

	@OneToMany
    private List<Message> listeMessages;

	public Conversation() {}
	
	public Conversation(Utilisateur idUtilisateur1, Utilisateur idUtilisateur2, Annonce idAnnonce,
			List<Message> listeMessages) {
		super();
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

	@Override
	public String toString() {
		return "Conversation [idConversation=" + idConversation + ", idUtilisateur1=" + idUtilisateur1
				+ ", idUtilisateur2=" + idUtilisateur2 + ", idAnnonce=" + idAnnonce + ", listeMessages=" + listeMessages
				+ "]";
	}
	
	
	
}
