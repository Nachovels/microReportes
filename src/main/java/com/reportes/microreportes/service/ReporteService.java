package com.reportes.microreportes.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.reportes.microreportes.model.Curso;
import com.reportes.microreportes.model.Estudiante;
import com.reportes.microreportes.model.dto.ReporteEstudianteDTO;
import com.reportes.microreportes.repository.ReporteRepository;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


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


    public ResponseEntity<String> reporteEstudiantesCantidad(){
        try{
            String urlEstudiantes = "http://micro-usuarios:8082/administrador/Estudiantes";
            String estudiantesData = restTemplate.getForObject(urlEstudiantes, String.class);
            String urlConteo = "http://micro-usuarios:8082/administrador/contar-estudiantes";
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

    public ReporteEstudianteDTO obtenerReporte(String correo) {
    Estudiante estudiante = restTemplate.getForObject(
        "http://micro-usuarios:8082/estudiante/traer/" + correo,
        Estudiante.class
    );

    System.out.println("IDs de cursos a buscar: " + estudiante.getCursoInscrito());

    List<String> idsCursos = estudiante.getCursoInscrito();
    if (idsCursos == null || idsCursos.isEmpty()) {
        return new ReporteEstudianteDTO(estudiante.getNombre(), estudiante.getApellido(), List.of());
    }

    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<List<String>> request = new HttpEntity<>(idsCursos, headers);

    ResponseEntity<List<Curso>> response = restTemplate.exchange(
        "http://gestor-cursos:8080/cursos/lista",
        HttpMethod.POST,
        request,
        new ParameterizedTypeReference<List<Curso>>() {}
    );

    return new ReporteEstudianteDTO(estudiante.getNombre(), estudiante.getApellido(), response.getBody());
    }
}
