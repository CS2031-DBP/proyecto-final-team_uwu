package com.example.demo.Labels;


import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.demo.Hilos.Hilo;
import com.example.demo.Labels.Label;

@Entity
@Table(name = "label") // Configura el nombre de la tabla
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String valor;

    @ManyToMany(mappedBy = "labels")
    @JsonIgnoreProperties("labels")
    private List<Hilo> hilos;

    // Constructor vacío
    public Label() {
    }

    // Constructor con parámetros
    public Label(String valor,List<Hilo> hilos) {
        this.valor = valor;
        this.hilos = hilos;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvalor() {
        return this.valor;
    }

    public void setvalor(String valor) {
        this.valor = valor;
    }
    public List<Hilo> getHilos(){return this.hilos;}
    public void setHilos(List<Hilo>hilos){this.hilos=hilos;}
}
