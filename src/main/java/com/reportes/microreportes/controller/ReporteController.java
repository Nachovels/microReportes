package com.reportes.microreportes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.reportes.microreportes.model.dto.ReporteEstudianteDTO;
import com.reportes.microreportes.service.ReporteService;


@RestController
public class ReporteController {
    
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/reporte/cursos")
    public ResponseEntity<String> obtenerReporteCursos() {
        return reporteService.reporteCurso();
    }

    @GetMapping("/reporte/cursos/{idCurso}")
    public ResponseEntity<String> obtenerReporteCursoPorId(@PathVariable String idCurso) {
        return reporteService.reporteCursoId(idCurso);
    }

    @GetMapping("/reporte/cursos/cantidad")
    public ResponseEntity<String> obtenerReporteCursosCantidad() {
        return reporteService.reporteCursosCantidad();
    }

    //Usuarios
    @GetMapping("/reporte/estudiantes/cantidad")
    public ResponseEntity<String> obtenerReporteEstudiantesCantidad(){
        return reporteService.reporteEstudiantesCantidad();
    }

    @GetMapping("/reporte/estudiantes/{correo}")
    public ResponseEntity<String> obtenerReporteEstudiantePorCorreo(@PathVariable String correo) {
        return reporteService.reporteEstudiantesCorreo(correo);
    }
    
    @GetMapping("/reporte/instructores/cantidad")
    public ResponseEntity<String> obtenerReporteInstructoresCantidad() {
        return reporteService.reporteInstructoresCantidad();
    }

    @GetMapping("/reporte/instructores/{correo}")
    public ResponseEntity<String> obtenerReporteInstructorPorCorreo(@PathVariable String correo) {
        return reporteService.reporteInstructoresCorreo(correo);
    }
    
    @GetMapping("/reporte/estudiantes/cursos/{correo}")
    public ReporteEstudianteDTO obtenerReporte(@PathVariable String correo){
        return reporteService.obtenerReporte(correo);
    }
         

}