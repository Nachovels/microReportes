package com.reportes.microreportes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.reportes.microreportes.service.ReporteService;

@RestController
public class ReporteController {
    
    @Autowired
    private ReporteService reporteService;
}
