package com.reportes.microreportes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportes.microreportes.repository.ReporteRepository;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
}
