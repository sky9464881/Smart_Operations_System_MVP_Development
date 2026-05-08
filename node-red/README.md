# Node-RED

Node-RED는 실제 센서 대신 JSONL vibration window를 한 줄씩 MQTT로 발행하는 DAS/Edge Gateway 역할을 합니다.

현재 import용 flow:

```text
vibration-jsonl-replay-flow.json
```

로컬 MQTT topic:

```text
factory/motor/1/vibration/window
```

Docker Compose에서 Node-RED를 실행할 때는 JSONL 파일 경로를 `/project-data/jsonl/...` 형태로 맞추면 됩니다.

현재 flow는 설비별 JSONL 파일 3개를 병렬로 읽습니다.

```text
MOTOR_001 -> data/jsonl/MOTOR_001_H_H_8_6204_1200_2048_200.jsonl
MOTOR_002 -> data/jsonl/MOTOR_002_H_IR_8_6204_1200_2048_200.jsonl
MOTOR_003 -> data/jsonl/MOTOR_003_H_OR_8_6204_1200_2048_200.jsonl
```

각 브랜치 delay가 1 msg/sec이므로 전체 MQTT 유입은 약 3 msg/sec이고, 각 설비는 약 1초마다 갱신됩니다.

참고용 interleaved 샘플:

```text
/project-data/jsonl/PHM_3_MOTORS_8K_1200_2048_200_each_interleaved.jsonl
```
