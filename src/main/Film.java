package main;

import java.util.Set;
import java.util.TreeSet;

public class Film extends Spectacle{
	private static int id =99;
	private int idFilm;
	private String realisateur;
	private int duree;
	private Set<SeanceFilm> listeSeance;

	public Film( String titre,String realisateur, int duree, String... interpretes) {
		super(titre,interpretes);
		idFilm = ++id;
		this.realisateur = realisateur;
		this.duree = duree;
		listeSeance = new TreeSet<SeanceFilm>();
	}

	public String getRealisateur() {
		return realisateur;
	}
	public int getDuree() {
		return duree;
	}

	public boolean ajouterSeanceFilm(SeanceFilm s){
		if (listeSeance.contains(s))
				return false;
		listeSeance.add(s);
		return true;
	}

	public SeanceFilm chercherSeance(int jour, Horaire horaire){
		for (SeanceFilm it : listeSeance){
			if(it.getCreneau().getJour()==jour && it.getCreneau().getHoraireDebut().compareTo(horaire) == 0){
				return it;
			}
		}
		return null;
	}

	public Set chercherDesSeances(int jour){
		Set<SeanceFilm> listeDesSeances = new TreeSet<SeanceFilm>();
		for (SeanceFilm it : listeSeance){
			if (it.getCreneau().getJour() == jour){
				listeDesSeances.add(it);
			}
		}
		if (listeDesSeances.isEmpty()) {
			return null;
		}
		return listeDesSeances;
	}

	// taux de remplissage moyen en pourcentage du Film
	public double tauxRemplissageMoyen(){
		double i=0;
		int y=0;
		for (SeanceFilm it : listeSeance){
			i += it.tauxRemplissage();
			y +=1;
		}
		return i/y;
	}

	// chiffre d'affaire defini dans la classe Spectable vu que c la meme chose pour film et theatre et que les SeanceFilm/Theatre hérite de Seance
	public double chiffreAffaire() {
		double i=0;
		for (SeanceFilm it : listeSeance){
			i += ((it.getNbPlacesVenduesTN()+0.6*it.getNbPlacesVenduesTR()) * it.getSalle().getTarif());
		}
		return i;
	}

	public int getIdFilm() {
		return idFilm;
	}

	public String getListeSeance() {
		if(listeSeance.isEmpty())
			return "";
		String i ="\n[";
		for (SeanceFilm y : listeSeance){
			i+=y.toString()+"\n";
		}
		return i+"]";
	}

	public void reinitialierlesSeances(){
		listeSeance.clear();
	}

	public void setId(int id) {
		Film.id = id;
	}

	public String toString() {
		return super.toString()+
				"\nidFilm :"+ idFilm+"\n Realisateur :" + realisateur + "\n Durée :" + duree+" minutes\n\n";

	}


}
