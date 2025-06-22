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

    //Gestor de cursos
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


    //Gestor de usuarios
    public ResponseEntity<String> reporteEstudiantesCantidad(){
        try{
            String urlEstudiantes = "http://gestor-usuarios:8082/administrador/Estudiantes";
            String estudiantesData = restTemplate.getForObject(urlEstudiantes, String.class);
            String urlConteo = "http://gestor-usuarios:8082/administrador/contar-estudiantes";
            String cantidad = restTemplate.getForObject(urlConteo, String.class);
            
            if(estudiantesData != null && !estudiantesData.isEmpty()) {
                return ResponseEntity.ok("Estudiantes: " + estudiantesData + "\nCantidad de estudiantes: " + cantidad);
            } else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body("Cantidad de estudiantes: " + cantidad);
            }
    }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los datos de los estudiantes: " + e.getMessage());
        }
    }

    public ResponseEntity<String> reporteEstudiantesCorreo(String correo) {
        try {
            String urlEstudiantes = "http://micro-usuarios:8082/estudiante/traer/" + correo;
            String estudiantesData = restTemplate.getForObject(urlEstudiantes, String.class);
            if (estudiantesData == null || estudiantesData.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(estudiantesData);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error al obtener los datos del estudiante con correo " + correo + ": " + e.getMessage());
        }
    }


    public ResponseEntity<String> reporteInstructoresCantidad() {
        try {
            String urlInstructores = "http://micro-usuarios:8082/administrador/Instructores";
            String instructoresData = restTemplate.getForObject(urlInstructores, String.class);
            String urlConteo = "http://micro-usuarios:8082/administrador/contar-instructores";
            String cantidad = restTemplate.getForObject(urlConteo, String.class);
            
            if (instructoresData != null && !instructoresData.isEmpty()) {
                return ResponseEntity.ok("Instructores: " + instructoresData + "\nCantidad de instructores: " + cantidad);
            } else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body("Cantidad de instructores: " + cantidad);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los datos de los instructores: " + e.getMessage());
        }
    }

    public ResponseEntity<String> reporteInstructoresCorreo(String correo) {
        try {
            String urlInstructores = "http://micro-usuarios:8082/instructor/traer/" + correo;
            String instructoresData = restTemplate.getForObject(urlInstructores, String.class);
            if (instructoresData == null || instructoresData.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(instructoresData);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error al obtener los datos del instructor con correo " + correo + ": " + e.getMessage());
        }
    }

}
