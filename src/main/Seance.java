package main;

public abstract class Seance  implements Comparable<Seance>{
	private Creneau creneau;
	private int nbPlacesVenduesTN;
	public Seance(Creneau creneau,int nbPlacesVenduesTN){
		this.creneau=creneau;
		this.nbPlacesVenduesTN=nbPlacesVenduesTN;
	}

	//Renvoie le nbre de places restantes
	public abstract int nbPlacesDispo();

	//Calcule le taux de remplissage de la salle pour la seance (en %).
	public abstract double tauxRemplissage();

	//Renvoie le nombre de places vendues
	public abstract int totalVendu();
	
	// compareTo() pour une comparaison chronologique
	public abstract int compareTo(Seance s2);
	
	//Appeler pour vendre une place
	public void vendrePlacesTN(int nbre){
		nbPlacesVenduesTN += nbre;
	}

	public int getNbPlacesVenduesTN() {
		return nbPlacesVenduesTN;
	}

	public void setNbPlacesVenduesTN(int nbPlacesVenduesTN) {
		this.nbPlacesVenduesTN = nbPlacesVenduesTN;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	// pas de getjour()/getheure() car on va utilise getCreneau().getJour() et getCreneau().getHoraireDebut/Fin()
}
