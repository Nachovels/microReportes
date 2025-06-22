package com.reportes.microreportes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.reportes.microreportes.repository.ReporteRepository;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
    
    @Autowired
    private RestTemplate restTemplate;


    public ResponseEntity<String> reporteCurso() {
        try{String urlCurso = "http://gestor-cursos:8080/cursos";
        String cursoData = restTemplate.getForObject(urlCurso, String.class);
        if (cursoData == null || cursoData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cursoData);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error al obtener los datos del curso: " + e.getMessage());
    }
        
    }

    public ResponseEntity<String> reporteCursoId(String idCurso) {
        try {
            String urlCurso = "http://gestor-cursos:8080/cursos/" + idCurso;
            String cursoData = restTemplate.getForObject(urlCurso, String.class);
            if (cursoData == null || cursoData.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(cursoData);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error al obtener los datos del curso con ID " + idCurso + ": " + e.getMessage());
        }
    }

    public ResponseEntity<String> reporteCursosCantidad() {
        try{
            String urlCurso = "http://gestor-cursos:8080/cursos";
            String cursoData = restTemplate.getForObject(urlCurso, String.class);

            String urlConteo ="http://gestor-cursos:8080/cursos/cantidad";
            String cantidad = restTemplate.getForObject(urlConteo, String.class);

            if (cursoData != null && !cursoData.isEmpty()) {
                return ResponseEntity.ok("Datos de cursos: " + cursoData + "\nCantidad de cursos: " + cantidad);
            } else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body("Cantidad de cursos: " + cantidad);
                
            }}
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los datos de los cursos" + e.getMessage());
        }
    }



    public String reporteEstudianteCorreoCantidad(String correo){
        String urlCorreo = "http://localhost:8082/estudiantes/traer/{correo}";
        String correoData = restTemplate.getForObject(urlCorreo, String.class);
        String urlConteo = "http://localhost:8082/estudiantes/cantidad";
        String cantidad = restTemplate.getForObject(urlConteo, String.class);
        return "Estudiante: " + correoData + "\nCantidad de estudiantes: " + cantidad;
    }

}
