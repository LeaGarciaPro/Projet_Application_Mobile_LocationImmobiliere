package webServiceREST.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import webServiceREST.models.Annonce;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer>{
	
	@Query(value = "SELECT * FROM annonce", nativeQuery = true)	
	ArrayList<Annonce> findAll();

	// renvoie les annonces mises en ligne par un utilisateur dont on donne l'id
	@Query("select u from Annonce u where u.idAnnonceur.id = :idUtilisateur")
	ArrayList<Annonce> FindAnnoncesByUtilisateurId(int idUtilisateur);

	// renvoie les annonces mises en ligne par un utilisateur dont on donne l'id
	@Query("select u from Annonce u where u.idAnnonceur.id = :idUtilisateur and u.id = :idAnnonce")
	Annonce findAnnonceByTwoId(int idUtilisateur, int idAnnonce);

	@Query("select u from Annonce u where u.idAnnonce = :idAnnonce")
	Annonce findAnnonceById(int idAnnonce);
	
	

	
}