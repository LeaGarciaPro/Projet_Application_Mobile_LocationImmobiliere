package webServiceREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webServiceREST.models.Alerte;

@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Integer>{

}
