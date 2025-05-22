package com.reportes.microreportes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.reportes.microreportes.repository.ReporteRepository;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    public String reporteCurso() {
        String urlCurso = "http://gestor-cursos:8080/cursos";
        String cursoData = restTemplate.getForObject(urlCurso, String.class);
        return cursoData;
    }

    public String reporteCursoId(String idCurso) {
        String urlCurso = "http://gestor-cursos:8080/cursos/" + idCurso;
        String cursoData = restTemplate.getForObject(urlCurso, String.class);
        return cursoData;
    }

    public String reporteCursosCantidad() {
        String urlCurso = "http://gestor-cursos:8080/cursos";
        String cursoData = restTemplate.getForObject(urlCurso, String.class);

        String urlConteo ="http://gestor-cursos:8080/cursos/cantidad";
        String cantidad = restTemplate.getForObject(urlConteo, String.class);

        return "Cursos: " + cursoData + "\nCantidad de cursos: " + cantidad;
    }

    public String reporteEstudianteCorreoCantidad(String correo){
        String urlCorreo = "http://localhost:8082/estudiantes/traer/{correo}";
        String correoData = restTemplate.getForObject(urlCorreo, String.class);
        String urlConteo = "http://localhost:8082/estudiantes/cantidad";
        String cantidad = restTemplate.getForObject(urlConteo, String.class);
        return "Estudiante: " + correoData + "\nCantidad de estudiantes: " + cantidad;
    }

}
