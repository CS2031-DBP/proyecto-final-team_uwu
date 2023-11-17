package com.example.demo.Labels.application;

import com.example.demo.CapaSeguridad.exception.ItemNotFoundException;
import com.example.demo.Labels.domain.Label;
import com.example.demo.Labels.domain.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/labels")
public class LabelController {
    @Autowired
    private LabelRepository labelRepository;

    @GetMapping
    public ResponseEntity<List<Label>> obtenerTodasLasEtiquetas() {
        List<Label> etiquetas = labelRepository.findAll();

        if (etiquetas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(etiquetas);
    }

    @GetMapping("/{valor}")
    public ResponseEntity<Label> obtenerEtiquetaPorValor(@PathVariable("valor") String valor) {
        Label etiqueta = labelRepository.findByValor(valor);

        if (etiqueta == null) {
            throw new ItemNotFoundException();
        }

        return ResponseEntity.ok(etiqueta);
    }

    @DeleteMapping("/{valor}")
    public ResponseEntity<String> borrarEtiquetaPorValor(@PathVariable("valor") String valor) {
        Label etiqueta = labelRepository.findByValor(valor);

        if (etiqueta == null) {
            throw new ItemNotFoundException();
        }

        labelRepository.delete(etiqueta);
        return ResponseEntity.ok("Etiqueta eliminada con éxito");
    }




}

