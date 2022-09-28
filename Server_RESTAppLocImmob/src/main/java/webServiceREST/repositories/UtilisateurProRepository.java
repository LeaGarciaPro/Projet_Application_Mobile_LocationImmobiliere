package webServiceREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webServiceREST.models.UtilisateurPro;

@Repository
public interface UtilisateurProRepository extends JpaRepository<UtilisateurPro, Integer>{

}
