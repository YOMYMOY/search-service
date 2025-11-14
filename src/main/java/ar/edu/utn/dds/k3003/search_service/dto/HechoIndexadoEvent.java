package ar.edu.utn.dds.k3003.search_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class HechoIndexadoEvent {
    private String hechoId;
    private String nombre;
    private String descripcionHecho;
    private String contenidoPdis;
    private List<String> tags;
    private String estado;
    private String coleccion;
    private String urlImagenPrincipal;

    public String getHechoId() {
        return hechoId;
    }

    public void setHechoId(String hechoId) {
        this.hechoId = hechoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionHecho() {
        return descripcionHecho;
    }

    public void setDescripcionHecho(String descripcionHecho) {
        this.descripcionHecho = descripcionHecho;
    }

    public String getContenidoPdis() {
        return contenidoPdis;
    }

    public void setContenidoPdis(String contenidoPdis) {
        this.contenidoPdis = contenidoPdis;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getColeccion() {
        return coleccion;
    }

    public void setColeccion(String coleccion) {
        this.coleccion = coleccion;
    }

    public String getUrlImagenPrincipal() {
        return urlImagenPrincipal;
    }

    public void setUrlImagenPrincipal(String urlImagenPrincipal) {
        this.urlImagenPrincipal = urlImagenPrincipal;
    }
}
