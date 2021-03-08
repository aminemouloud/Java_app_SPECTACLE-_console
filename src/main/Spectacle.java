package main;

import java.util.HashSet;
import java.util.Set;

public abstract class Spectacle {
	private Set<String> interpretes = new HashSet<String>();
	private String titre;

	public Spectacle( String titre, String... interpretes) {
		for (String i : interpretes)
			this.interpretes.add(i);
		this.titre = titre;

	}


	public void setInterpretes(String interpretes) {
		this.interpretes.add(interpretes);
	}

	public String getTitre() {
		return titre;
	}

	public abstract Seance chercherSeance(int jour, Horaire horairedebut);
	public abstract Set chercherDesSeances(int jour);
	public abstract  double tauxRemplissageMoyen();
	public abstract double chiffreAffaire();

	@Override
	public int hashCode() {
		final int prime = 31;
		int resultat = 1;
		resultat = prime * resultat
				+ ((interpretes == null) ? 0 : interpretes.hashCode());
		resultat = prime * resultat + ((titre == null) ? 0 : titre.hashCode());
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
		Spectacle other = (Spectacle) obj;
		if (interpretes == null) {
			if (other.interpretes != null)
				return false;
		} else if (!interpretes.equals(other.interpretes))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}


	public String toString() {
		return " Titre :" + titre+"\n Interpretes :" + interpretes.toString();
	}
	
}