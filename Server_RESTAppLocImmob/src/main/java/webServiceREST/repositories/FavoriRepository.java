package webServiceREST.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webServiceREST.models.Annonce;
import webServiceREST.models.Favori;
import webServiceREST.models.Utilisateur;

@Repository
public interface FavoriRepository extends JpaRepository<Favori, Integer> {

	@Query("select count(u) from Favori u where u.annonce.id = :id")
	int findNbFavoris(@Param("id") int id);

	// renvoie les favoris associée à une annonce dont on donne l'id
	@Query("select u from Favori u where u.annonce.id = :id")
	ArrayList<Favori> FindFavorisByAnnonceId(@Param("id") int id);

	// renvoie les annonces mises en favori par un utilisateur dont on donne l'id
	@Query("select u.annonce from Favori u where u.utilisateur.id = :id")
	ArrayList<Annonce> FindFavorisByUtilisateurId(@Param("id") int id);

	// renvoi le favoris associé à un certain utilisateur et une certaine annonce
	@Query("select u from Favori u where u.utilisateur.id = :idUtilisateur and u.annonce.id = :idAnnonce")
	Favori findFavorisByTwoId(@Param("idUtilisateur") int idUtilisateur, @Param("idAnnonce") int idAnnonce);

}
