package ar.edu.utn.dds.k3003.search_service.dto;


public class EstadoUpdateEvent {
    private String estado;

    public EstadoUpdateEvent() {
    }

    public EstadoUpdateEvent(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
