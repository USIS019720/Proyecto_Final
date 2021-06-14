package com.example.appvoto;

public class Elecciones {
    String id, candidato, voto;

    public Elecciones(String id, String candidato, String voto) {
        this.id = id;
        this.candidato = candidato;
        this.voto = voto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidato() {
        return candidato;
    }

    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }
}
