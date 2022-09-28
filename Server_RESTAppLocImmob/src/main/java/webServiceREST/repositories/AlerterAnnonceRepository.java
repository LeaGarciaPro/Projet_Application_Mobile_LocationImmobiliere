package webServiceREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webServiceREST.models.AlerterAnnonce;

@Repository
public interface AlerterAnnonceRepository extends JpaRepository<AlerterAnnonce, Integer>{

}
