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
        String urlCurso = "http://localhost:8080/cursos";
        String cursoData = restTemplate.getForObject(urlCurso, String.class);
        return cursoData;
    }

    public String reporteCursoId(String idCurso) {
        String urlCurso = "http://localhost:8080/cursos/" + idCurso;
        String cursoData = restTemplate.getForObject(urlCurso, String.class);
        return cursoData;
    }

    public String reporteCursosCantidad() {
        String urlCurso = "http://localhost:8080/cursos";
        String cursoData = restTemplate.getForObject(urlCurso, String.class);

        String urlConteo ="http://localhost:8080/cursos/cantidad";
        String cantidad = restTemplate.getForObject(urlConteo, String.class);

        return "Cursos: " + cursoData + "\nCantidad de cursos: " + cantidad;
    }

}
