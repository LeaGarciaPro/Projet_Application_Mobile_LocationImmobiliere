package webServiceREST.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webServiceREST.models.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>{
	
	@Query(value = "SELECT * FROM utilisateur WHERE mail = :emailData", nativeQuery = true)	
	ArrayList<Utilisateur> findByEmail(@Param(value="emailData") String email);
	
	@Query("select U from Utilisateur U where U.idUtilisateur = :id")	
	Utilisateur findUtilisateurById(@Param(value="id") int id);


}
