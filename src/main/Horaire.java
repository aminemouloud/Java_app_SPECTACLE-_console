package main;

public class Horaire {
	private int minutes;
	private int heures;
	public Horaire(int minutes, int heures){
		if(minutes>59) {
			minutes = minutes % 60;
			heures += 1;
		}
		if(heures>23)
			heures = heures%24;

		this.heures = heures;
		this.minutes = minutes;
	}
	public int getMinutes() {
		return minutes;
	}
	public int getHeures() {
		return heures;
	}


	public int transformMin(){
		return minutes + heures*60;
	}

	public Horaire transformHoraire(int min){
		int additionmin = this.transformMin() + min;
		int heure = additionmin/60;
		int minute = additionmin%60;
		if(heure <0)
			throw new IllegalArgumentException("ne respecte pas le format 24h apres calcul");
		if(minute < 0 )
			throw new IllegalArgumentException("ne respecte pas le format 24h apres calcul");
		return new Horaire(minute,heure);
	}

	// compareTo()  les horraire 
	public int compareTo(Horaire horaire) {
		if(this.heures < horaire.heures){
			return -1;
		}
		else{
			if(this.heures > horaire.heures){
				return 1;
			}
			else{
				if(this.minutes < horaire.minutes){
					return -1;
				}
				else{
					if(this.minutes > horaire.minutes){
						return 1;
					}
				}
			}
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int resultat = 1;
		resultat = prime * resultat + heures;
		resultat = prime * resultat + minutes;
		return resultat;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Horaire other = (Horaire) obj;
		if (heures != other.heures)
			return false;
		if (minutes != other.minutes)
			return false;
		return true;
	}
	
	public String toString(){
		return heures+"h"+minutes;
	}
}
