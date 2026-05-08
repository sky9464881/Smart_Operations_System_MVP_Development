package com.example.phm.analysis.dto;

import java.time.LocalDateTime;

import com.example.phm.analysis.entity.AnalysisResult;

public record AnalysisResultResponse(
        Long id,
        Long vibrationWindowId,
        String equipmentCode,
        LocalDateTime measuredAt,
        Double rms,
        Double peakFrequency,
        Double peakToPeak,
        Double crestFactor,
        Double kurtosis,
        String prediction,
        Double confidence,
        Double anomalyScore,
        String alarmLevel,
        LocalDateTime createdAt
) {

    public static AnalysisResultResponse from(AnalysisResult analysisResult) {
        return new AnalysisResultResponse(
                analysisResult.getId(),
                analysisResult.getVibrationWindow().getId(),
                analysisResult.getEquipmentCode(),
                analysisResult.getVibrationWindow().getMeasuredAt(),
                analysisResult.getRms(),
                analysisResult.getPeakFrequency(),
                analysisResult.getPeakToPeak(),
                analysisResult.getCrestFactor(),
                analysisResult.getKurtosis(),
                analysisResult.getPrediction(),
                analysisResult.getConfidence(),
                analysisResult.getAnomalyScore(),
                analysisResult.getAlarmLevel(),
                analysisResult.getCreatedAt()
        );
    }
}
