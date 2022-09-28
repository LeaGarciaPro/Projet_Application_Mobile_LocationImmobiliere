package webServiceREST.models;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMessage")
	private int idMessage;

	// Ceci est celui qui envoie
	@ManyToOne
	@JoinColumn(name = "idUtilisateur1", nullable = false)
	private Utilisateur idUtilisateur1;

	// Ceci est celui qui reçoit
	@ManyToOne
	@JoinColumn(name = "idUtilisateur2", nullable = false)
	private Utilisateur idUtilisateur2;

	@ManyToOne
	@JoinColumn(name = "idAnnonce", nullable = true)
	private Annonce idAnnonce;

	@JoinColumn(name = "contenuMessage", nullable = false)
	private String contenuMessage;

	@Column(name = "messageSeen")
	private boolean messageSeen;
	
	public Message() {}

	public Message(Utilisateur idUtilisateur1, Utilisateur idUtilisateur2, Annonce idAnnonce,
			String contenuMessage, boolean messageSeen) {
		super();
		this.idUtilisateur1 = idUtilisateur1;
		this.idUtilisateur2 = idUtilisateur2;
		this.idAnnonce = idAnnonce;
		this.contenuMessage = contenuMessage;
		this.messageSeen = messageSeen;
	}

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
		return "Message [idMessage=" + idMessage + ", idUtilisateur1=" + idUtilisateur1 + ", idUtilisateur2="
				+ idUtilisateur2 + ", idAnnonce=" + idAnnonce + ", contenuMessage=" + contenuMessage + ", messageSeen="
				+ messageSeen + "]";
	}	
	
	
}