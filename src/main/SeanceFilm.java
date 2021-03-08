package main;

public class SeanceFilm extends Seance {
	private Salle salle;
	private int nbPlacesVenduesTR;
	public SeanceFilm(Salle salle, Creneau creneau, int nbPlacesVenduesTN, int nbPlacesVenduesTR){
		super(creneau,nbPlacesVenduesTN);
		this.salle=salle;
		this.nbPlacesVenduesTR=nbPlacesVenduesTR;
	}
	
	public int nbPlacesDispo() {
		return salle.getCapacite()-totalVendu();
	}

	public double tauxRemplissage() {
		return 100-((100*nbPlacesDispo())/salle.getCapacite());
	}

	//TN+TR
	public int totalVendu() {
		return getNbPlacesVenduesTN()+getNbPlacesVenduesTR();
	}

	public void vendrePlacesTR(int nb){
		nbPlacesVenduesTR += nb;
	}
	//tri par jour puis par horaire
	public int compareTo(Seance s2){
		if(this.getCreneau().getJour() < s2.getCreneau().getJour()){
			return -1;
		}
		else{
			if(this.getCreneau().getJour() > s2.getCreneau().getJour()){
				return 1;
			}
		}
		//Si jours egaux, compare les heures
		return (this.getCreneau().getHoraireDebut().compareTo(s2.getCreneau().getHoraireDebut()));
	}


	public int getNbPlacesVenduesTR() {
		return nbPlacesVenduesTR;
	}


	public Salle getSalle() {
		return salle;
	}


	
	public String toString(){
		return  "Séance du "+getCreneau().getJour()+" "+getCreneau().getHoraireDebut()+" "+getCreneau().getHoraireFin()
				+"\n Nombre de places vendues : "+getNbPlacesVenduesTN()
				+"\n Nombre de places vendues au tarif réduit : "+nbPlacesVenduesTR
				+"\n En salle numéro : "+getSalle().getIdSalle();
	}
}
