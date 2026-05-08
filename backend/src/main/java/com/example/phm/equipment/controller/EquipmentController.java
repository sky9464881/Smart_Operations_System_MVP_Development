package com.example.phm.equipment.controller;

import java.util.List;

import com.example.phm.equipment.dto.EquipmentResponse;
import com.example.phm.equipment.repository.EquipmentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    private final EquipmentRepository equipmentRepository;

    public EquipmentController(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping
    public List<EquipmentResponse> findAll() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentResponse::from)
                .toList();
    }
}
