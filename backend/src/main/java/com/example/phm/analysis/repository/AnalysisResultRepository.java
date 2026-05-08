package com.example.phm.analysis.repository;

import java.util.List;
import java.util.Optional;

import com.example.phm.analysis.entity.AnalysisResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long> {

    Optional<AnalysisResult> findTopByEquipmentCodeOrderByCreatedAtDesc(String equipmentCode);

    @EntityGraph(attributePaths = "vibrationWindow")
    List<AnalysisResult> findTop100ByEquipmentCodeOrderByCreatedAtDesc(String equipmentCode);

    @EntityGraph(attributePaths = "vibrationWindow")
    List<AnalysisResult> findByEquipmentCodeOrderByCreatedAtDesc(String equipmentCode, Pageable pageable);

    List<AnalysisResult> findTop100ByOrderByCreatedAtDesc();
}
