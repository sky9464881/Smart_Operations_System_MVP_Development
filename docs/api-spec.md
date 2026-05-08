# API Spec Draft

## Backend REST API

```text
GET /api/equipments
GET /api/equipments/{equipmentCode}/latest
GET /api/equipments/{equipmentCode}/analysis-results
GET /api/alarms
GET /api/dashboard/summary
```

## FastAPI

```text
GET  /health
POST /analyze
```

`POST /analyze`는 Spring Boot가 MQTT payload를 전달하는 분석 API입니다.

Request:

```json
{
  "equipmentId": "MOTOR_001",
  "timestamp": "2026-05-06T12:00:00.000Z",
  "samplingRate": 8000,
  "rpm": 1200,
  "windowSize": 2,
  "windowIndex": 0,
  "values": [-0.13646967, -0.1220464]
}
```

Response:

```json
{
  "equipmentId": "MOTOR_001",
  "timestamp": "2026-05-06T12:00:00.000Z",
  "samplingRate": 8000,
  "rpm": 1200,
  "windowSize": 2,
  "windowIndex": 0,
  "features": {
    "rms": 0.0,
    "peakFrequency": 0.0,
    "peakToPeak": 0.0,
    "crestFactor": 0.0,
    "kurtosis": 0.0
  },
  "fft": {
    "frequencyResolution": 4000.0,
    "binCount": 2,
    "frequencies": [0.0, 3.90625],
    "magnitudes": [0.0, 0.0]
  },
  "anomalyScore": 0.0,
  "alarmLevel": "normal",
  "prediction": "not_trained",
  "confidence": 0.0,
  "modelVersion": "signal-features-v0"
}
```

현재 `prediction`은 AI 모델 연결 전이므로 `not_trained`로 반환합니다.
실제 `windowSize=2048` payload를 보내면 FFT bin은 `1025`개가 반환됩니다.
