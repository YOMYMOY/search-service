package ar.edu.utn.dds.k3003.search_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class PdiIndexadoEvent {
    private String contenido;
    private List<String> tags;

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
