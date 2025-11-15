package ar.edu.utn.dds.k3003.search_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "hechos_indexados")
public class HechoIndexado {
    @Id
    private String id; //El id interno de mongoDB
    @Indexed(unique = true)
    private String hechoId;
    @TextIndexed(weight = 3)
    private String nombre;
    @TextIndexed(weight = 2)
    private String descripcion;
    @TextIndexed(weight = 1)
    private String contenidoPdis;

    @Indexed
    private List<String> tags;
    @Indexed
    private String estado;

    private String coleccion;
    private String urlImagenPrincipal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public HechoIndexado(String id, String hechoId, String nombre, String descripcion, String contenidoPdis, List<String> tags, String estado, String coleccion, String urlImagenPrincipal) {
        this.id = id;
        this.hechoId = hechoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contenidoPdis = contenidoPdis;
        this.tags = tags;
        this.estado = estado;
        this.coleccion = coleccion;
        this.urlImagenPrincipal = urlImagenPrincipal;
    }

    public HechoIndexado() {
    }
}
