package main;

import java.util.*;

public class PieceTheatre extends Spectacle {
	private static int idT =999;
	private int idTheatre;
	private String metteurEnScene;
	private int nbEntractes;
	private Set<SeanceTheatre> listeSeance;

	public PieceTheatre(String titre,String metteurEnScene, int nbEntractes,String... interpretes) {
		super(titre,interpretes);
		idTheatre = ++idT;
		this.metteurEnScene=metteurEnScene;
		this.nbEntractes=nbEntractes;
		listeSeance = new TreeSet<SeanceTheatre>();
	}

	//si la le jour de la seance est deja pris renvoit false et n'ajoute pas
	public boolean ajouterSeanceTheatre(SeanceTheatre s){
		for (SeanceTheatre i : listeSeance){
			if (i.getCreneau().getJour() == s.getCreneau().getJour())
				return false;
		}
		listeSeance.add(s);
		return true;
	}

	//Cherche la seance correspondante au jour

	public SeanceTheatre chercherSeance(int jour, Horaire horaire){
		for (SeanceTheatre it : listeSeance){
			if(it.getCreneau().getJour()==jour && it.getCreneau().getHoraireDebut().compareTo(horaire) == 0){
				return it;
			}
		}
		return null;
	}

	public SeanceTheatre chercherSeance(int jour){
		for (SeanceTheatre itr : listeSeance){
			if(itr.getCreneau().getJour()==jour){
				return itr;
			}
		}
		System.out.println("Aucune seance ne correspond e ce jour de la semaine");
		return null;
	}

	public Set chercherDesSeances(int jour){
		Set<SeanceTheatre> listeDesSeances = new TreeSet<SeanceTheatre>();
		for (SeanceTheatre it : listeSeance){
			if (it.getCreneau().getJour() == jour){
				listeDesSeances.add(it);
			}
		}
		if (listeDesSeances.isEmpty()) {
			return null;
		}
		return listeSeance;
	}

	// taux de remplissage moyen en pourcentage de la pièce de Théâtre
	public double tauxRemplissageMoyen(){
		double i=0;
		int y=0;
		for (SeanceTheatre itr : listeSeance){
			i += itr.tauxRemplissage();
			y +=1;
		}
		return i/y;
	}

	//chiffre d'affaires de la piece
	public double chiffreAffaire(){
		double i=0;
		for (SeanceTheatre itr : listeSeance){
			i+= itr.getNbPlacesVenduesTN()*itr.getSalleTheatre().getTarif() + itr.getSalleTheatre().getPrixFauteuil()*(itr.getNbFauteuilsVendus()) ;
		}
		return i;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((metteurEnScene == null) ? 0 : metteurEnScene.hashCode());
		result = prime * result + nbEntractes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PieceTheatre other = (PieceTheatre) obj;
		if (metteurEnScene == null) {
			if (other.metteurEnScene != null)
				return false;
		} else if (!metteurEnScene.equals(other.metteurEnScene))
			return false;
		if (nbEntractes != other.nbEntractes)
			return false;
		return true;
	}

	public String getMetteurEnScene() {
		return metteurEnScene;
	}


	public int getIdTheatre() {
		return idTheatre;
	}

	public String getListeSeance() {
		if(listeSeance.isEmpty())
			return "";
		String i ="\n[";
		for (SeanceTheatre y : listeSeance){
			i+=y.toString()+"\n";
		}
		return i+"]";
	}

	public void reinitialierlesSeances(){
		listeSeance.clear();
	}

	public void setIdT(int idT) {
		PieceTheatre.idT = idT;
	}

	public String toString() {
		return super.toString()+"\nidTheatre : "+idTheatre+"\nMetteur en scene : " + metteurEnScene + "\n Nombre d'entractes : " + nbEntractes+"\n";
	}

}
