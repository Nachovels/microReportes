package com.reportes.microreportes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.reportes.microreportes.model.Curso;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteEstudianteDTO {
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private List<Curso> cursos;
}
