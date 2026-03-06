package com.consultorio.pacientes.dtos;

public class HistoriaClinicaDTO {

    private String motivoConsulta;
    private String observaciones;
   private Boolean activa; // wrapper para permitir null

    public HistoriaClinicaDTO() {}

   public HistoriaClinicaDTO(String motivoConsulta, String observaciones, Boolean activa) {
        this.motivoConsulta = motivoConsulta;
        this.observaciones = observaciones;
        this.activa=activa;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    

}
