package com.reportes.microreportes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante {
    private String id;
    private String nombre;
    private String correo;
    private List<String> cursoInscrito;
}
