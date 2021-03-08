package main;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Salle {
	private static int id = 10;
	private int idSalle;
	private String nomSalle;
	private int capacite;
	private double tarif;
	private SortedMap<Integer, Set<Creneau>> lesCreneauxOccupes;

	public Salle(String nomSalle, int capacite, double tarif){
		this.nomSalle=nomSalle;
		this.idSalle = id;
		id+=10;
		this.capacite=capacite;
		this.tarif=tarif;
		lesCreneauxOccupes = new TreeMap<>();
	}

	public boolean estDisponible(Creneau c){
		if (lesCreneauxOccupes.containsKey(c.getJour())){
			for (Creneau i : lesCreneauxOccupes.get(c.getJour())){

				if (i.getHoraireDebut().compareTo(c.getHoraireDebut()) == -1 && c.getHoraireDebut().compareTo(i.getHoraireFin()) == -1) //si horaire idebut < cdebut < ifin
					return false;
				else if (i.getHoraireDebut().compareTo(c.getHoraireFin()) == -1 && c.getHoraireFin().compareTo(i.getHoraireFin()) == -1) //si idebut < cfin < ifin
					return false;
				else if (c.getHoraireDebut().compareTo(i.getHoraireDebut()) == 0) // si cdebut == idebut
					return false;
				else if (c.getHoraireFin().compareTo(i.getHoraireFin()) == 0) // si cfin == ifin
					return false;
				else if (c.getHoraireFin().compareTo(i.getHoraireDebut()) == -1) // si c fin < i debut
					continue;
				else if (c.getHoraireDebut().compareTo(i.getHoraireFin()) == 1) // si c debut >= horaire fin
					continue;
			}

		}else
			return true;
		return true;
	}

	public boolean ajouterCreneau(Creneau c){
		if (estDisponible(c)){
			if (lesCreneauxOccupes.containsKey(c.getJour())){
				lesCreneauxOccupes.get(c.getJour()).add(c);
				return true;
			}else{
				Set<Creneau> a = new HashSet<>();
				a.add(c);
				lesCreneauxOccupes.putIfAbsent(c.getJour(), a);
				return true;
			}
		}
		return false;
	}

	public boolean pasDeCreneauCeJour(int jour){
		return !lesCreneauxOccupes.containsKey(jour);
	}

	public String toString() {
		return "Salle : "+nomSalle
				+ "\n idSalle : " + idSalle
				+ "\n Capacite : " + capacite
				+ "\n Prix standard : " + tarif 
				+ "\n Prix reduit : " + tarif * 0.6;
	}

	public int getCapacite() {
		return capacite;
	}

	public double getTarif() {
		return tarif;
	}

	public int getIdSalle() {
		return idSalle;
	}

	public void reinitialiserlesCreneaux(){
		lesCreneauxOccupes.clear();
	}
	
}
