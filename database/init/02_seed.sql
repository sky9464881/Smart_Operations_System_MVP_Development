USE smart_factory;

INSERT INTO equipment (equipment_code, equipment_name, location)
VALUES
  ('MOTOR_001', '1번 모터', '테스트 라인 A'),
  ('MOTOR_002', '2번 모터', '테스트 라인 B'),
  ('MOTOR_003', '3번 모터', '테스트 라인 C')
ON DUPLICATE KEY UPDATE
  equipment_name = VALUES(equipment_name),
  location = VALUES(location);
