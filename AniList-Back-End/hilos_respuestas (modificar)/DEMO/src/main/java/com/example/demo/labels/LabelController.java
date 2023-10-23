package com.example.demo.labels;

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
    public List<Label> getHilos() {
        List<Label> label = labelRepository.findAll();
        return label;
    }

    @PostMapping
    public ResponseEntity<Label> createHilo(@RequestBody Label label) {
        Label nuevoHilo=labelRepository.save(label);
        return new ResponseEntity<>(nuevoHilo, HttpStatus.CREATED);
    }
}

