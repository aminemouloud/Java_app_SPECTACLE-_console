package main;

public class SalleTheatre extends Salle {
	private int nbFauteuils;
	private double prixFauteuil;
	public SalleTheatre(String nomSalle, int capacite, double tarif, int nbFauteuils,double prixFauteuil){
		super(nomSalle,capacite,tarif);
		this.nbFauteuils=nbFauteuils;
		this.prixFauteuil=prixFauteuil;
	}
	
	
	public String toString() {
		return super.toString()
				+"\n Prix fauteuil : " + prixFauteuil
				+"\n Nombre de fauteuils : " + nbFauteuils;
	}


	
	
	public int getNbFauteuils() {
		return nbFauteuils;
	}
	public double getPrixFauteuil() {
		return prixFauteuil;
	}

}
