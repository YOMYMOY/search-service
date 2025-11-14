package ar.edu.utn.dds.k3003.search_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class HechoDTO {
    private String hechoId;
    private String nombre;
    private String coleccion;
    private String urlImagenPrincipal;

    public HechoDTO() {
    }

    public HechoDTO(String hechoId, String nombre, String coleccion, String urlImagenPrincipal) {
        this.hechoId = hechoId;
        this.nombre = nombre;
        this.coleccion = coleccion;
        this.urlImagenPrincipal = urlImagenPrincipal;
    }
}
