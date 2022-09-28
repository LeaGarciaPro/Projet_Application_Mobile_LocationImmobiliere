package webServiceREST.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webServiceREST.models.Annonce;
import webServiceREST.models.Conversation;
import webServiceREST.models.Favori;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

	@Query("select m from Conversation m where (m.idUtilisateur1.id = :idUtilisateur) OR (m.idUtilisateur2.id = :idUtilisateur)")
	ArrayList<Conversation> FindConversationByUtilisateurId(int idUtilisateur);

	@Query("select u from Conversation u where u.idConversation = :idConv")
	Conversation findConversationById(int idConv);

	@Query("select u from Conversation u where (u.idUtilisateur1.idUtilisateur = :idUtilisateur1 and u.idUtilisateur2.idUtilisateur = :idUtilisateur2 and u.idAnnonce.idAnnonce = :idAnnonce) OR (u.idUtilisateur1.idUtilisateur = :idUtilisateur2 and u.idUtilisateur2.idUtilisateur = :idUtilisateur1 and u.idAnnonce.idAnnonce = :idAnnonce)")
	Conversation findConversationByTwoIdAndAnnonce(@Param("idUtilisateur1") int idUtilisateur1,
			@Param("idUtilisateur2") int idUtilisateur2, @Param("idAnnonce") int idAnnonce);

	// renvoie les conversations associées à une annonce dont on donne l'id
	@Query("select u from Conversation u where u.idConversation = :id")
	ArrayList<Conversation> FindConversationByAnnonceId(@Param("id") int id);
}
