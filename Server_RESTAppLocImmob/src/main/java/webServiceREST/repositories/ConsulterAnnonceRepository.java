package webServiceREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webServiceREST.models.ConsulterAnnonce;

@Repository
public interface ConsulterAnnonceRepository extends JpaRepository<ConsulterAnnonce, Integer>{

}
