package com.reportes.microreportes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reportes.microreportes.model.entity.ReporteEntity;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteEntity, Integer> {
    ReporteEntity findByIdReporte(int idReporte);
    Boolean existsByIdReporte(int idReporte);
    void deleteByIdReporte(int idReporte);
}
