package com.example.demo.Hilos;

import com.example.demo.Labels.Label;
import com.example.demo.Respuesta.Respuesta;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hilo") // Configura el nombre de la tabla
public class Hilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tema;

    @Column(nullable = false)
    private String contenido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false) // Configura el nombre de la columna
    private Date fechaCreacion;

    private Long cantidadReaccciones;

    private Long cantidadReports;
    
    @ManyToMany
    @JoinTable(name = "hilo_label",
               joinColumns = @JoinColumn(name = "hilo_id"),
               inverseJoinColumns = @JoinColumn(name = "label_id"))
    private List<Label> labels;

    // Constructor vacío
    public Hilo() {
    }

    // Constructor con parámetros
    public Hilo(String tema, String contenido, Date fechaCreacion) {
        this.tema = tema;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @OneToMany(mappedBy = "hilo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Respuesta> respuestas = new ArrayList<>();

    // Getters y setters
    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public List<Label> getLabels(){return this.labels;}
    public void setLabels(List<Label> labels){this.labels=labels;}

    public Long getCantidadReaccciones() {
        return this.cantidadReaccciones;
    }

    public void getCantidadReaccciones(Long cantidadReaccciones) {
        this.cantidadReaccciones = cantidadReaccciones;
    }

     public Long getCantidadReports() {
        return this.cantidadReports;
    }

    public void setCantidadReports(Long cantidadReports) {
        this.cantidadReports= cantidadReports;
    }
}

 