package com.example.demo.Labels.domain;


import com.example.demo.Hilos.domain.Hilo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "label")
public class Label{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String valor;

    @ManyToMany(mappedBy = "labels")
    @JsonIgnoreProperties("labels")
    private List<Hilo> hilos;

    public Label() {
    }

    public Label(Long id, String valor, List<Hilo> hilos) {
        this.id = id;
        this.valor = valor;
        this.hilos = hilos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Hilo> getHilos() {
        return hilos;
    }

    public void setHilos(List<Hilo> hilos) {
        this.hilos = hilos;
    }
}








