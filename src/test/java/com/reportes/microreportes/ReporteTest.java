package com.reportes.microreportes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.reportes.microreportes.service.ReporteService;

public class ReporteTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReporteService reporteService;  // Tu servicio real

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void reporteCursoObtieneDatos() {
        String cursosJson = "[" +
            "{\"idCurso\":\"1\", \"nombreCurso\":\"Curso A\", \"descripcion\":\"Desc A\", \"instructorAsignado\":\"Juan\"}," +
            "{\"idCurso\":\"2\", \"nombreCurso\":\"Curso B\", \"descripcion\":\"Desc B\", \"instructorAsignado\":\"Laura\"}" +
        "]";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(cursosJson);

        ResponseEntity<String> response = reporteService.reporteCurso();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cursosJson, response.getBody());
    }

    @Test
    public void reporteCursoNoObtieneDatos() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(null);

        ResponseEntity<String> response = reporteService.reporteCurso();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void reporteCursoIdObtieneDatos() {
        String idCurso = "123";
        String cursoJson = "{\"idCurso\":\"123\", \"nombreCurso\":\"Curso Test\", \"descripcion\":\"Descripción\", \"instructorAsignado\":\"Juan\"}";

        when(restTemplate.getForObject("http://gestor-cursos:8080/cursos/" + idCurso, String.class))
            .thenReturn(cursoJson);

        ResponseEntity<String> response = reporteService.reporteCursoId(idCurso);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cursoJson, response.getBody());
    }

    @Test
    public void reporteCursoIdNoObtieneDatos() {
        String idCurso = "999";

        when(restTemplate.getForObject("http://gestor-cursos:8080/cursos/" + idCurso, String.class))
            .thenReturn(null);

        ResponseEntity<String> response = reporteService.reporteCursoId(idCurso);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void reporteCursosCantidadObtieneDatos() {
        String cursosJson = "[{\"idCurso\":\"1\", \"nombreCurso\":\"Curso A\"}]";
        String cantidad = "1";

        when(restTemplate.getForObject("http://gestor-cursos:8080/cursos", String.class))
            .thenReturn(cursosJson);

        when(restTemplate.getForObject("http://gestor-cursos:8080/cursos/cantidad", String.class))
            .thenReturn(cantidad);

        ResponseEntity<String> response = reporteService.reporteCursosCantidad();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Datos de cursos: " + cursosJson));
        assertTrue(response.getBody().contains("Cantidad de cursos: " + cantidad));
    }

    @Test
    public void reporteCursosCantidadNoObtieneDatos() {
        String cantidad = "0";

        when(restTemplate.getForObject("http://gestor-cursos:8080/cursos", String.class))
            .thenReturn(null);

        when(restTemplate.getForObject("http://gestor-cursos:8080/cursos/cantidad", String.class))
            .thenReturn(cantidad);

        ResponseEntity<String> response = reporteService.reporteCursosCantidad();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cantidad de cursos: " + cantidad, response.getBody());
    }

    @Test
    public void reporteEstudiantesCantidadObtieneDatos() {
        String estudiantesJson = "[{\"id\":\"1\", \"nombre\":\"Juan\"}]";
        String cantidad = "1";

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/Estudiantes", String.class))
            .thenReturn(estudiantesJson);

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/contar-estudiantes", String.class))
            .thenReturn(cantidad);

        ResponseEntity<String> response = reporteService.reporteEstudiantesCantidad();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Estudiantes: " + estudiantesJson));
        assertTrue(response.getBody().contains("Cantidad de estudiantes: " + cantidad));
    }

    @Test
    public void reporteEstudiantesCantidadNoObtieneDatos() {
        String cantidad = "0";

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/Estudiantes", String.class))
            .thenReturn(null);

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/contar-estudiantes", String.class))
            .thenReturn(cantidad);

        ResponseEntity<String> response = reporteService.reporteEstudiantesCantidad();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cantidad de estudiantes: " + cantidad, response.getBody());
    }

    @Test
    public void reporteEstudiantesCorreoObtieneDatos() {
        String correo = "test@mail.com";
        String estudianteJson = "{\"id\":\"1\", \"nombre\":\"Test Estudiante\"}";

        when(restTemplate.getForObject("http://micro-usuarios:8082/estudiante/traer/" + correo, String.class))
            .thenReturn(estudianteJson);

        ResponseEntity<String> response = reporteService.reporteEstudiantesCorreo(correo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estudianteJson, response.getBody());
    }

    @Test
    public void reporteEstudiantesCorreoNoObtieneDatos() {
        String correo = "noexiste@mail.com";

        when(restTemplate.getForObject("http://micro-usuarios:8082/estudiante/traer/" + correo, String.class))
            .thenReturn(null);

        ResponseEntity<String> response = reporteService.reporteEstudiantesCorreo(correo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void reporteInstructoresCantidadObtieneDatos() {
        String instructoresJson = "[{\"id\":\"1\", \"nombre\":\"Laura\"}]";
        String cantidad = "1";

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/Instructores", String.class))
            .thenReturn(instructoresJson);

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/contar-instructores", String.class))
            .thenReturn(cantidad);

        ResponseEntity<String> response = reporteService.reporteInstructoresCantidad();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Instructores: " + instructoresJson));
        assertTrue(response.getBody().contains("Cantidad de instructores: " + cantidad));
    }

    @Test
    public void reporteInstructoresCantidadNoObtieneDatos() {
        String cantidad = "0";

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/Instructores", String.class))
            .thenReturn(null);

        when(restTemplate.getForObject("http://micro-usuarios:8082/administrador/contar-instructores", String.class))
            .thenReturn(cantidad);

        ResponseEntity<String> response = reporteService.reporteInstructoresCantidad();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cantidad de instructores: " + cantidad, response.getBody());
    }
    
    @Test
    public void reporteInstructoresCorreoObtieneDatos() {
        String correo = "jperez@mail.com";
        String instructorJson = "{\"id\":\"1\", \"nombre\":\"Instructor Test\"}";

        when(restTemplate.getForObject("http://micro-usuarios:8082/instructor/traer/" + correo, String.class))
            .thenReturn(instructorJson);

        ResponseEntity<String> response = reporteService.reporteInstructoresCorreo(correo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(instructorJson, response.getBody());
    }

    @Test
    public void reporteInstructoresCorreoNoObtieneDatos() {
        String correo = "jperez@mail.com";

        when(restTemplate.getForObject("http://micro-usuarios:8082/instructor/traer/" + correo, String.class))
            .thenReturn(null);

        ResponseEntity<String> response = reporteService.reporteInstructoresCorreo(correo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}

