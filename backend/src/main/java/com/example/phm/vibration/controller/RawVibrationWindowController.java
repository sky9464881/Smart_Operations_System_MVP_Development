package com.example.phm.vibration.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.phm.vibration.dto.RawVibrationSeriesResponse;
import com.example.phm.vibration.dto.RawVibrationWindowResponse;
import com.example.phm.vibration.dto.VibrationWindowMessage;
import com.example.phm.vibration.entity.VibrationWindow;
import com.example.phm.vibration.repository.VibrationWindowRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RawVibrationWindowController {

    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Seoul");

    private final VibrationWindowRepository vibrationWindowRepository;
    private final ObjectMapper objectMapper;

    public RawVibrationWindowController(
            VibrationWindowRepository vibrationWindowRepository,
            ObjectMapper objectMapper
    ) {
        this.vibrationWindowRepository = vibrationWindowRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/api/equipments/{equipmentCode}/vibration-windows/latest/raw")
    public ResponseEntity<RawVibrationWindowResponse> latestRaw(@PathVariable String equipmentCode) throws IOException {
        return vibrationWindowRepository.findTopByEquipmentCodeOrderByMeasuredAtDescIdDesc(equipmentCode)
                .map(this::readRawWindow)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/equipments/{equipmentCode}/vibration-windows/raw-series")
    public ResponseEntity<RawVibrationSeriesResponse> rawSeries(
            @PathVariable String equipmentCode,
            @RequestParam(defaultValue = "20") int limit
    ) {
        int safeLimit = Math.max(1, Math.min(limit, 50));
        List<VibrationWindow> windows = vibrationWindowRepository
                .findTop100ByEquipmentCodeOrderByMeasuredAtDescIdDesc(equipmentCode)
                .stream()
                .limit(safeLimit)
                .toList();

        if (windows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<VibrationWindow> orderedWindows = new ArrayList<>(windows);
        Collections.reverse(orderedWindows);

        List<RawVibrationSeriesResponse.RawVibrationPoint> points = new ArrayList<>();
        Integer samplingRate = orderedWindows.get(0).getSamplingRate();

        for (VibrationWindow window : orderedWindows) {
            VibrationWindowMessage message = readRawWindowMessage(window);
            List<Double> values = message.getValues() == null ? List.of() : message.getValues();
            double baseTimestamp = receivedTimestampMillis(window);
            double intervalMillis = window.getSamplingRate() == null ? 1.0 : 1000.0 / window.getSamplingRate();

            for (int index = 0; index < values.size(); index++) {
                points.add(new RawVibrationSeriesResponse.RawVibrationPoint(
                        baseTimestamp + index * intervalMillis,
                        values.get(index),
                        window.getWindowIndex()
                ));
            }
        }

        VibrationWindow first = orderedWindows.get(0);
        VibrationWindow last = orderedWindows.get(orderedWindows.size() - 1);
        return ResponseEntity.ok(new RawVibrationSeriesResponse(
                equipmentCode,
                orderedWindows.size(),
                points.size(),
                samplingRate,
                first.getWindowIndex(),
                last.getWindowIndex(),
                points
        ));
    }

    private RawVibrationWindowResponse readRawWindow(VibrationWindow vibrationWindow) {
        VibrationWindowMessage message = readRawWindowMessage(vibrationWindow);
        return RawVibrationWindowResponse.from(vibrationWindow, message);
    }

    private VibrationWindowMessage readRawWindowMessage(VibrationWindow vibrationWindow) {
        try {
            String payload = Files.readString(Path.of(vibrationWindow.getRawFilePath()));
            return objectMapper.readValue(payload, VibrationWindowMessage.class);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read raw vibration window file", exception);
        }
    }

    private double receivedTimestampMillis(VibrationWindow window) {
        LocalDateTime base = window.getCreatedAt() == null ? window.getMeasuredAt() : window.getCreatedAt();
        return base.atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
    }
}
