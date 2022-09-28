package webServiceREST.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webServiceREST.models.Favori;
import webServiceREST.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	// Les messages entre deux utilisateurs concernant une annonce (penser à
	// demander en ordre croissant sur les id de message pour retracer la
	// conversation)
	@Query("select m from Message m where (m.idUtilisateur1.id = :idUtilisateur1 AND m.idUtilisateur2.id = :idUtilisateur2 AND m.idAnnonce.id = :idAnnonce) "
			+ "OR (m.idUtilisateur1.id = :idUtilisateur2 AND m.idUtilisateur2.id = :idUtilisateur1 AND m.idAnnonce.id = :idAnnonce) ORDER BY m.idMessage ASC")
	ArrayList<Message> findMessageByThreeIds(@Param("idUtilisateur1") int idUtilisateur1,
			@Param("idUtilisateur2") int idUtilisateur2, @Param("idAnnonce") int idAnnonce);

	// renvoie les messages associés à une annonce dont on donne l'id
	@Query("select u from Message u where u.idMessage = :id")
	ArrayList<Message> FindMessageByAnnonceId(@Param("id") int id);

}
