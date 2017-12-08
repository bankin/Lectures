package com.masdefect.repository;

import com.masdefect.domain.entities.Anomaly;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnomalyRepository extends JpaRepository<Anomaly, Long> {

    @Query(value = "SELECT a FROM Anomaly AS a ORDER BY SIZE(a.victims) DESC")
    List<Anomaly> findMostAffecting(Pageable pageable);
}
