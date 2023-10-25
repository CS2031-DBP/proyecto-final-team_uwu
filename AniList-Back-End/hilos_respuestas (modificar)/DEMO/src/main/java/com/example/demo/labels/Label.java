package com.example.demo.labels;


import com.example.demo.Hilos.Hilo;
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







