package main;

public class SeanceTheatre extends Seance{
	private SalleTheatre salleTheatre;
	private int nbFauteuilsVendus;
	public SeanceTheatre(SalleTheatre salleTheatre,Creneau creneau,int nbPlacesVenduesTN,int nbFauteuilsVendus){
		super(creneau,nbPlacesVenduesTN);
		this.salleTheatre=salleTheatre;
		this.nbFauteuilsVendus=nbFauteuilsVendus;
	}

	
	public int nbPlacesDispo() {
		return salleTheatre.getCapacite()-getNbPlacesVenduesTN();
	}

	public double tauxRemplissage() {
		return 100-((100*nbPlacesDispo())/salleTheatre.getCapacite());
	}

	//Total des ventes FAUTEUILS + PlacesTN
	public int totalVendu() {
		return getNbPlacesVenduesTN()+getNbFauteuilsVendus();
	}

	//Donne le nombre de places FAUTEUIL disponibles
	public int nbFauteuilsDispo(){
		return salleTheatre.getNbFauteuils()-nbFauteuilsVendus;
	}

	// vendre des places AVEC FAUTEUIL
	public void vendrePlacesFauteuil(int nbre){
		nbFauteuilsVendus += nbre;
	}

	//compareTo, seulement selon les jours car il n'y a qu'une seule seance par jour
	public int compareTo(Seance s2) {
		if(this.getCreneau().getJour() < s2.getCreneau().getJour()){
			return -1;
		}
		else{
			if(this.getCreneau().getJour() > s2.getCreneau().getJour()){
				return 1;
			}
			return 0;
		}
	}


	public int getNbFauteuilsVendus() {
		return nbFauteuilsVendus;
	}
	public SalleTheatre getSalleTheatre() {
		return salleTheatre;
	}
	//Redefinition toString()
		public String toString(){
			return  "Séance du "+getCreneau().getJour()+" "+getCreneau().getHoraireDebut()+" "+getCreneau().getHoraireFin()
					+"\n Nombre de places vendues : "+getNbPlacesVenduesTN()
					+"\n Nombre de places vendues au tarif fauteuil : "+nbFauteuilsVendus
					+"\n En salle numéro : "+getSalleTheatre().getIdSalle();
		}
}