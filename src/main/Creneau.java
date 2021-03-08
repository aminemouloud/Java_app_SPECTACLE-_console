package main;

import java.util.Objects;

public class Creneau {
    private int jour;
    private Horaire horaireDebut;
    private Horaire horaireFin;

    public Creneau(int jour, Horaire horaireDebut, Horaire horaireFin){
        this.jour=jour;
        this.horaireDebut=horaireDebut;
        this.horaireFin=horaireFin;
    }

    public int getJour(){
        return jour;
    }

    public Horaire getHoraireDebut(){
        return horaireDebut;
    }

    public Horaire getHoraireFin(){
        return horaireFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creneau creneau = (Creneau) o;
        return jour == creneau.jour &&
                Objects.equals(horaireDebut, creneau.horaireDebut) &&
                Objects.equals(horaireFin, creneau.horaireFin);
    }

}
