package com.reportes.microreportes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    private String idCurso;
    private String nombreCurso;
    private String descripcion;
}
