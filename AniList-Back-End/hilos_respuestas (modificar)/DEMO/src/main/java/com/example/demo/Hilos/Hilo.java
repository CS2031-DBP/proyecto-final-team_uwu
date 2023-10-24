package com.example.demo.Hilos;

import com.example.demo.Respuesta.Respuesta;
import com.example.demo.Usuario.Usuario;
import com.example.demo.Usuario.UsuarioRepository;
import com.example.demo.labels.Label;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hilo") // Configura el nombre de la tabla
public class Hilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tema;

    @Lob
    @Column(nullable = false)
    private String contenido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false) // Configura el nombre de la columna
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "hilo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Respuesta> respuestas = new ArrayList<>();

    private Long cantidadReaccciones;

    private Long cantidadReports;

    @ManyToMany
    @JoinTable(name = "hilo_label",
            joinColumns = @JoinColumn(name = "hilo_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    private List<Label> labels;


    public Hilo() {
    }

    public Hilo(Long id, String tema, String contenido, Date fechaCreacion, Usuario usuario, Long cantidadReaccciones, Long cantidadReports, List<Label> labels, List<Respuesta> respuestas) {
        this.id = id;
        this.tema = tema;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
        this.cantidadReaccciones = cantidadReaccciones;
        this.cantidadReports = cantidadReports;
        this.labels = labels;
        this.respuestas = respuestas;
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

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getCantidadReaccciones() {
        return cantidadReaccciones;
    }

    public void setCantidadReaccciones(Long cantidadReaccciones) {
        this.cantidadReaccciones = cantidadReaccciones;
    }

    public Long getCantidadReports() {
        return cantidadReports;
    }

    public void setCantidadReports(Long cantidadReports) {
        this.cantidadReports = cantidadReports;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
