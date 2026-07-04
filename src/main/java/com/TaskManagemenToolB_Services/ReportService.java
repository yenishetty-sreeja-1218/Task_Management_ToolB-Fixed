package com.TaskManagemenToolB_Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    public Map<String, Object> burnDownData(Long sprintId) {
        Map<String, Object> report = new HashMap<>();
        report.put("sprintId", sprintId);
        report.put("reportType", "Burndown Report");
        report.put("status", "Success");
        return report;
    }

    public Map<String, Object> velocity(Long sprintId) {
        Map<String, Object> report = new HashMap<>();
        report.put("sprintId", sprintId);
        report.put("reportType", "Velocity Report");
        return report;
    }

    public Map<String, Object> sprintReport(Long sprintId) {
        Map<String, Object> report = new HashMap<>();
        report.put("sprintId", sprintId);
        report.put("reportType", "Sprint Report");
        return report;
    }

    public Map<String, Object> epicProgressReport(Long sprintId) {
        Map<String, Object> report = new HashMap<>();
        report.put("sprintId", sprintId);
        report.put("reportType", "Epic Progress Report");
        return report;
    }

    public Map<String, Object> workLoadReport(Long sprintId) {
        Map<String, Object> report = new HashMap<>();
        report.put("sprintId", sprintId);
        report.put("reportType", "Workload Report");
        return report;
    }

    public Map<String, Object> flowDiagramReport(Long sprintId) {
        Map<String, Object> report = new HashMap<>();
        report.put("sprintId", sprintId);
        report.put("reportType", "Flow Diagram Report");
        return report;
    }
}