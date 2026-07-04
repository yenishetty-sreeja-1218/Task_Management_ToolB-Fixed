package com.TaskManagementToolIB_Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TaskManagemenToolB_Services.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/burnDownData/{sprintId}")
    public ResponseEntity<Object> getBurnDownDataReport(
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(reportService.burnDownData(sprintId));
    }

    @GetMapping("/velocity/{sprintId}")
    public ResponseEntity<Object> getVelocityReport(
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(reportService.velocity(sprintId));
    }

    @GetMapping("/sprintReport/{sprintId}")
    public ResponseEntity<Object> getSprintReport(
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(reportService.sprintReport(sprintId));
    }

    @GetMapping("/epicReport/{sprintId}")
    public ResponseEntity<Object> getEpicReport(
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(reportService.epicProgressReport(sprintId));
    }

    @GetMapping("/workLoadReport/{sprintId}")
    public ResponseEntity<Object> getWorkLoadReport(
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(reportService.workLoadReport(sprintId));
    }

    @GetMapping("/flowDiagram/{sprintId}")
    public ResponseEntity<Map<String, Object>> getFlowDiagramReport(
            @PathVariable Long sprintId) {

        return ResponseEntity.ok(reportService.flowDiagramReport(sprintId));
    }
}