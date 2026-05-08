from __future__ import annotations

from fastapi import APIRouter

from app.schemas.vibration_schema import AnalyzeResponse, VibrationWindowRequest
from app.services.feature_service import calculate_features, classify_alarm_level, estimate_anomaly_score


router = APIRouter()


@router.post("/analyze", response_model=AnalyzeResponse)
def analyze(request: VibrationWindowRequest) -> AnalyzeResponse:
    features, fft = calculate_features(request.values, request.samplingRate)
    anomaly_score = estimate_anomaly_score(features)
    alarm_level = classify_alarm_level(anomaly_score)

    return AnalyzeResponse(
        equipmentId=request.equipmentId,
        timestamp=request.timestamp,
        samplingRate=request.samplingRate,
        rpm=request.rpm,
        windowSize=request.windowSize,
        windowIndex=request.windowIndex,
        features=features,
        fft=fft,
        anomalyScore=anomaly_score,
        alarmLevel=alarm_level,
        prediction="not_trained",
        confidence=0.0,
        modelVersion="signal-features-v0",
    )
