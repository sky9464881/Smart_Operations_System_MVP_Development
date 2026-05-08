package com.example.phm.equipment.dto;

import java.time.LocalDateTime;

import com.example.phm.equipment.entity.Equipment;

public record EquipmentResponse(
        Long id,
        String equipmentCode,
        String equipmentName,
        String location,
        LocalDateTime createdAt
) {

    public static EquipmentResponse from(Equipment equipment) {
        return new EquipmentResponse(
                equipment.getId(),
                equipment.getEquipmentCode(),
                equipment.getEquipmentName(),
                equipment.getLocation(),
                equipment.getCreatedAt()
        );
    }
}
