package com.example.phm.analysis.controller;

import java.util.List;

import com.example.phm.analysis.dto.AnalysisResultResponse;
import com.example.phm.analysis.repository.AnalysisResultRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalysisResultController {

    private final AnalysisResultRepository analysisResultRepository;

    public AnalysisResultController(AnalysisResultRepository analysisResultRepository) {
        this.analysisResultRepository = analysisResultRepository;
    }

    @GetMapping("/api/equipments/{equipmentCode}/analysis-results")
    public List<AnalysisResultResponse> findByEquipment(
            @PathVariable String equipmentCode,
            @RequestParam(defaultValue = "100") int limit
    ) {
        int safeLimit = Math.max(1, Math.min(limit, 500));
        return analysisResultRepository.findByEquipmentCodeOrderByCreatedAtDesc(equipmentCode, PageRequest.of(0, safeLimit)).stream()
                .map(AnalysisResultResponse::from)
                .toList();
    }
}
