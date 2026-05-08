package com.example.phm.analysis.entity;

import java.time.LocalDateTime;

import com.example.phm.vibration.entity.VibrationWindow;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "analysis_result")
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vibration_window_id", nullable = false)
    private VibrationWindow vibrationWindow;

    @Column(name = "equipment_code", nullable = false, length = 50)
    private String equipmentCode;

    @Column(name = "rms")
    private Double rms;

    @Column(name = "peak_frequency")
    private Double peakFrequency;

    @Column(name = "peak_to_peak")
    private Double peakToPeak;

    @Column(name = "crest_factor")
    private Double crestFactor;

    @Column(name = "kurtosis")
    private Double kurtosis;

    @Column(name = "prediction", length = 50)
    private String prediction;

    @Column(name = "confidence")
    private Double confidence;

    @Column(name = "anomaly_score")
    private Double anomalyScore;

    @Column(name = "alarm_level", length = 20)
    private String alarmLevel;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public VibrationWindow getVibrationWindow() {
        return vibrationWindow;
    }

    public void setVibrationWindow(VibrationWindow vibrationWindow) {
        this.vibrationWindow = vibrationWindow;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public Double getRms() {
        return rms;
    }

    public void setRms(Double rms) {
        this.rms = rms;
    }

    public Double getPeakFrequency() {
        return peakFrequency;
    }

    public void setPeakFrequency(Double peakFrequency) {
        this.peakFrequency = peakFrequency;
    }

    public Double getPeakToPeak() {
        return peakToPeak;
    }

    public void setPeakToPeak(Double peakToPeak) {
        this.peakToPeak = peakToPeak;
    }

    public Double getCrestFactor() {
        return crestFactor;
    }

    public void setCrestFactor(Double crestFactor) {
        this.crestFactor = crestFactor;
    }

    public Double getKurtosis() {
        return kurtosis;
    }

    public void setKurtosis(Double kurtosis) {
        this.kurtosis = kurtosis;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Double getAnomalyScore() {
        return anomalyScore;
    }

    public void setAnomalyScore(Double anomalyScore) {
        this.anomalyScore = anomalyScore;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
