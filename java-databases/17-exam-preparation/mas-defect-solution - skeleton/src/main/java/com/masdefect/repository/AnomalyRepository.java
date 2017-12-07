package com.masdefect.repository;

import com.masdefect.domain.entities.Anomaly;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyRepository extends CrudRepository<Anomaly, Long> {
}
