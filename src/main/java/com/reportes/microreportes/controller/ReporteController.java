package com.reportes.microreportes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.reportes.microreportes.service.ReporteService;

@RestController
public class ReporteController {
    
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/reporte/cursos")
    public ResponseEntity<String> obtenerReporteCursos() {
        return ResponseEntity.ok(reporteService.reporteCurso());
    }

    @GetMapping("/reporte/cursos/{idCurso}")
    public ResponseEntity<String> obtenerReporteCursoPorId(@PathVariable String idCurso) {
        return ResponseEntity.ok(reporteService.reporteCursoId(idCurso));
    }

    @GetMapping("/reporte/cursos/cantidad")
    public ResponseEntity<String> obtenerReporteCursosCantidad() {
        return ResponseEntity.ok(reporteService.reporteCursosCantidad());
    }

}