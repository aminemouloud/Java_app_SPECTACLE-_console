package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GestionProgrammationSemaine extends Exception implements IProgrammationSemaine {
    private Map<Integer,Salle> lesSalles;
    private Map<Integer,SalleTheatre> lesSallesTheatre;
    private Map<Integer,Film> lesFilms;
    private Map<Integer,PieceTheatre> lesPieces;

    public GestionProgrammationSemaine(){
        // hashmap car pas besoin d'ordre
        lesSalles = new HashMap<Integer, Salle>();
        lesSallesTheatre = new HashMap<Integer,SalleTheatre>();
        lesFilms = new HashMap<Integer, Film>();
        lesPieces = new HashMap<Integer, PieceTheatre>();
        //création des 4 salles standard
        Salle salle1 = new Salle("Salle1",200,10.5);
        Salle salle2 = new Salle("Salle2",200,10.5);
        Salle salle3 = new Salle("Salle3",200,10.5);
        Salle salle4 = new Salle("Salle4",200,10.5);
        //création des 4 salles théâtre
        SalleTheatre salleTheatre1 = new SalleTheatre("SalleTheatre1",50,14,25,18);
        SalleTheatre salleTheatre2 = new SalleTheatre("SalleTheatre2",80,15,30,20);
        SalleTheatre salleTheatre3 = new SalleTheatre("SalleTheatre3",100,10,40,15);
        SalleTheatre salleTheatre4 = new SalleTheatre("SalleTheatre4",150,10,80,12);
        //insertion des salles standard dans lesSalles
        lesSalles.putIfAbsent(salle1.getIdSalle(),salle1);
        lesSalles.putIfAbsent(salle2.getIdSalle(),salle2);
        lesSalles.putIfAbsent(salle3.getIdSalle(),salle3);
        lesSalles.putIfAbsent(salle4.getIdSalle(),salle4);
        //insertion des salles théâtre dans lesSallesTheatre
        lesSallesTheatre.putIfAbsent(salleTheatre1.getIdSalle(),salleTheatre1);
        lesSallesTheatre.putIfAbsent(salleTheatre2.getIdSalle(),salleTheatre2);
        lesSallesTheatre.putIfAbsent(salleTheatre3.getIdSalle(),salleTheatre3);
        lesSallesTheatre.putIfAbsent(salleTheatre4.getIdSalle(),salleTheatre4);
    }
    /**
     * Recherche un film existant ayant pour titre et pour réalisateur ceux passés en paramètre
     * @param titre
     * @param realisateur
     * @return le film trouvé ou null si aucun film trouvé
     */
    public Film rechercherFilm (String titre, String realisateur){
        for (Film i : lesFilms.values()){
            if (i.getTitre().equalsIgnoreCase(titre) && i.getRealisateur().equalsIgnoreCase(realisateur))
                return i;
        }
        return null;
    }

    /**
     * Recherche un film ayant pour titre et pour réalisateur ceux passés en paramètre.
     * Si aucun film trouvé, crée le film et l'ajoute sinon lève une exception
     * @param titre
     * @param realisateur
     * @param duree
     * @throws IllegalArgumentException Le film existe déjà
     */
    public void ajouterFilm (String titre, String realisateur, int duree){
        if (rechercherFilm(titre,realisateur) != null)
            throw new IllegalArgumentException("Le film existe déjà");
        Film film = new Film(titre,realisateur,duree);
        lesFilms.putIfAbsent(film.getIdFilm(),film);
    }

    /**
     * Ajoute l'interprète passé en paramètre au spectacle correspondant au paramètre numSpectacle s'il existe sinon lève une exception
     * @param numSpectacle
     * @param interprete
     * @throws IllegalArgumentException Spectacle inexistant
     */
    public void ajouterInterprete( int numSpectacle, String interprete){
        if(numSpectacle>=1000){
            if(lesPieces.containsKey(numSpectacle))
                lesPieces.get(numSpectacle).setInterpretes(interprete);
        }else if (numSpectacle>=100){
            if(lesFilms.containsKey(numSpectacle))
                lesFilms.get(numSpectacle).setInterpretes(interprete);
        }else
            throw new IllegalArgumentException("Spectacle inexistant");
    }
    /**
     * Recherche une pièce de théâtre existante ayant pour titre et pour metteur en scène ceux passés en paramètre
     * @param titre
     * @param metteurEnScene
     * @return la pièce de théâtre trouvée ou null si aucune pièce trouvée
     */
    public PieceTheatre rechercherPiece (String titre, String metteurEnScene){
        for (PieceTheatre i : lesPieces.values()){
            if (i.getTitre().equalsIgnoreCase(titre) && i.getMetteurEnScene().equalsIgnoreCase(metteurEnScene))
                return i;
        }
        return null;
    }
    /**
     * Recherche une pièce de théâtre ayant pour titre et pour metteur en scène ceux passés en paramètre.
     * Si aucune pièce trouvée, crée la pièce et l'ajoute sinon lève une exception
     * @param titre
     * @param metteurEnScene
     * @param nbEntractes
     * @throws IllegalArgumentException La pièce existe déjà
     */
    public void ajouterPiece (String titre, String metteurEnScene, int nbEntractes){
        if (rechercherPiece(titre,metteurEnScene) != null)
            throw new IllegalArgumentException("La Pièce existe déjà");
        PieceTheatre piece = new PieceTheatre(titre,metteurEnScene,nbEntractes);
        lesPieces.putIfAbsent(piece.getIdTheatre(),piece);
    }

    /**
     * Crée et ajoute la séance au film correspondant à idFilm s'il existe
     * et si la salle est disponible sur le créneau défini par les paramètres jour et début et la durée du film.
     * Il faut que l'heure de l'horaire de fin calculée soit compris entre 0 et 23 et les minutes entre 0 et 59.
     * Lève une exception si aucune salle ne correspond ) idSalle
     * Ajoute également le créneau à la salle correspondante
     * @param idFilm
     * @param jour
     * @param debut
     * @param idSalle
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Salle inexistante
     * @throws IllegalStateException Créneau indisponible pour dans cette salle
     */
    public void ajouterSeanceFilm(int idFilm, int jour, Horaire debut, int idSalle){
        if(!lesFilms.containsKey(idFilm))
            throw new IllegalArgumentException("Film inexistant");
        if(!lesSalles.containsKey(idSalle))
            throw new IllegalArgumentException("Salle inexistante");
        Horaire fin = debut.transformHoraire(dureeFilm(idFilm));
        Creneau b = new Creneau(jour,debut,fin);
        if(lesSalles.get(idSalle).estDisponible(b)){
            lesSalles.get(idSalle).ajouterCreneau(b);
            SeanceFilm s = new SeanceFilm(lesSalles.get(idSalle),b,0,0);
            lesFilms.get(idFilm).ajouterSeanceFilm(s);
        }else
            throw new IllegalStateException("Créneau indisponible dans cette salle");
    }

    /**
     * Teste l'existence d'une séance pour la pièce de théâtre correspondant à idPiece si elle existe.
     * @param idPiece
     * @param jour
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     */
    public boolean existeSeanceCeJour(int idPiece, int jour){
        if(!lesPieces.containsKey(idPiece))
            throw  new IllegalArgumentException("Pièce inexistante");
        else {
            if (lesPieces.get(idPiece).chercherDesSeances(jour) == null)
                return false;
            else
                return true;
        }
    }
    /**
     * Crée et ajoute la séance à la pièce correspondant à idPiece s'il existe
     * et s'il n'y a pas déjà un créneau défini pour cette salle ce jour là.
     * Pour toute les pièces, on définira un créneau d'une durée de 3h. Si en ajoutant 3 heures à l'horaire de début, on passe au jour suivant (h>=24) il faut ramener l'heure entre 0 et 23.
     * Lève une exception si aucune salle ne correspond à idSalle
     * Ajoute également le créneau à la salle
     * @param idPiece
     * @param jour
     * @param debut
     * @param idSalle
     * @throws IllegalArgumentException Pièce inexistante
     * @throws IllegalArgumentException Salle inexistante
     * @throws IllegalStateException Créneau indisponible pour dans cette salle
     * @throws IllegalArgumentException Il existe déjà une séance programmée ce jour
     */
    public void ajouterSeanceTheatre(int idPiece, int jour, Horaire debut, int idSalle){
        if(!lesPieces.containsKey(idPiece))
            throw new IllegalArgumentException("Pièce inexistant");
        if(!lesSallesTheatre.containsKey(idSalle))
            throw new IllegalArgumentException("Salle inexistante");

        Horaire fin = debut.transformHoraire(180); // car 3h
        Creneau b = new Creneau(jour,debut,fin);
        if(lesSallesTheatre.get(idSalle).estDisponible(b)){
            lesSallesTheatre.get(idSalle).ajouterCreneau(b);
            SeanceTheatre s = new SeanceTheatre(lesSallesTheatre.get(idSalle),b,0,0);
            lesPieces.get(idPiece).ajouterSeanceTheatre(s);
        }else
            throw new IllegalStateException("Créneau indisponible dans cette salle");
    }

    /**
     * Retourne le chiffre d'affaires du spectacle correspondant au numéro passé en paramètre s'il existe
     * @param numSpectacle
     * @return
     * @throws IllegalArgumentException Spectacle inexistant
     */
    public double chiffreAffaires(int numSpectacle){
        if(numSpectacle>=1000){
            if(!lesPieces.containsKey(numSpectacle))
                throw new IllegalArgumentException("Spectacle inexistant");
            else
                return lesPieces.get(numSpectacle).chiffreAffaire();
        }else if (numSpectacle >=100){
            if(!lesFilms.containsKey(numSpectacle))
                throw new IllegalArgumentException("Spectacle inexistant");
            else
                return lesFilms.get(numSpectacle).chiffreAffaire();
        }else
            throw new IllegalArgumentException("Spectacle inexistant");
    }

    /**
     * Retourne le taux de remplissage du spectacle correspondant au numéro passé en paramètre s'il existe
     * @param numSpectacle
     * @return
     * @throws IllegalArgumentException Spectacle inexistant
     */
    public double getTauxRemplissage(int numSpectacle){
        if(numSpectacle>=1000){
            if(!lesPieces.containsKey(numSpectacle))
                throw new IllegalArgumentException("Spectacle inexistant");
            else
                return lesPieces.get(numSpectacle).tauxRemplissageMoyen();
        }else if (numSpectacle >=100){
            if(!lesFilms.containsKey(numSpectacle))
                throw new IllegalArgumentException("Spectacle inexistant");
            else
                return lesFilms.get(numSpectacle).tauxRemplissageMoyen();
        }else
            throw new IllegalArgumentException("Spectacle inexistant");
    }

    /**
     * Vend le nombre de places à tarif normal passé en paramètre pour le film correspondant à idFilm s'il existe
     * et pour la séance correspondant au jour et à l'horaire de début passés en paramètre à condition qu'il y ait assez de places disponibles
     * @param idFilm
     * @param jour
     * @param debut
     * @param nbPlacesTN
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Séance inexistante
     * @throws IllegalArgumentException Pas assez de places disponibles
     */

    public void vendrePlaceFilmTN(int idFilm, int jour, Horaire debut, int nbPlacesTN) {
        if(!lesFilms.containsKey(idFilm))
            throw new IllegalArgumentException("Film inexistant");
        else if(lesFilms.get(idFilm).chercherSeance(jour,debut) == null)
            throw new IllegalArgumentException("Séance inexistante");
        else if(lesFilms.get(idFilm).chercherSeance(jour,debut).nbPlacesDispo() < nbPlacesTN)
            throw new IllegalArgumentException("pas assez de places disponibles");
        else
            lesFilms.get(idFilm).chercherSeance(jour,debut).vendrePlacesTN(nbPlacesTN);
    }
    /**
     * Vend le nombre de places à tarif réduit passé en paramètre pour le film correspondant à idFilm s'il existe
     * et pour la séance correspondant au jour et à l'horaire de début passés en paramètre à condition qu'il y ait assez de places disponibles
     * @param idFilm
     * @param jour
     * @param debut
     * @param nbPlacesTR
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Séance inexistante
     * @throws IllegalArgumentException Pas assez de places disponibles
     */
    public void vendrePlaceFilmTR(int idFilm, int jour, Horaire debut, int nbPlacesTR){
        if(!lesFilms.containsKey(idFilm))
            throw new IllegalArgumentException("Film inexistant");
        else if(lesFilms.get(idFilm).chercherSeance(jour,debut) == null)
            throw new IllegalArgumentException("Séance inexistante");
        else if(lesFilms.get(idFilm).chercherSeance(jour,debut).nbPlacesDispo() < nbPlacesTR)
            throw new IllegalArgumentException("pas assez de places disponibles");
        else
            lesFilms.get(idFilm).chercherSeance(jour,debut).vendrePlacesTR(nbPlacesTR);
    }
    /**
     * Vend le nombre de places à tarif normal passé en paramètre pour la pièce correspondant à idPiece s'elle existe
     * et pour la séance correspondant au jour passé en paramètre à condition qu'il y ait assez de places disponibles
     * @param idPiece
     * @param jour
     * @param nbPlacesTN
     * @throws IllegalArgumentException Piece inexistante
     * @throws IllegalArgumentException Séance inexistante
     * @throws IllegalArgumentException Pas assez de places disponibles
     */
    public void vendrePlacePieceTN(int idPiece, int jour, int nbPlacesTN){
        if(!lesPieces.containsKey(idPiece))
            throw new IllegalArgumentException("Piece inexistante");
        else if(lesPieces.get(idPiece).chercherSeance(jour) == null)
            throw new IllegalArgumentException("Séance inexistante");
        else if(lesPieces.get(idPiece).chercherSeance(jour).nbPlacesDispo() < nbPlacesTN)
            throw new IllegalArgumentException("pas assez de places disponibles");
        else
            lesPieces.get(idPiece).chercherSeance(jour).vendrePlacesTN(nbPlacesTN);
    }
    /**
     * Vend le nombre de places à tarif fauteuils passé en paramètre pour la pièce correspondant à idPiece s'elle existe
     * et pour la séance correspondant au jour passé en paramètre à condition qu'il y ait assez de places disponibles
     * @param idPiece
     * @param jour
     * @param nbFauteuils
     * @throws IllegalArgumentException Piece inexistante
     * @throws IllegalArgumentException Séance inexistant
     * @throws IllegalArgumentException Pas assez de fauteuils
     */
    public void vendrePlaceFauteuilPiece(int idPiece, int jour, int nbFauteuils){
        if(!lesPieces.containsKey(idPiece))
            throw new IllegalArgumentException("Piece inexistante");
        else if(lesPieces.get(idPiece).chercherSeance(jour) == null)
            throw new IllegalArgumentException("Séance inexistante");
        else if(lesPieces.get(idPiece).chercherSeance(jour).nbFauteuilsDispo() < nbFauteuils)
            throw new IllegalArgumentException("pas assez de fauteuils");
        else
            lesPieces.get(idPiece).chercherSeance(jour).vendrePlacesFauteuil(nbFauteuils);
    }
    /**
     *
     * @return les films sous forme d'une chaîne de caractères
     */
    public String lesFilms (){
        String i ="";
        for (Film y : lesFilms.values()){
            i+=y.toString();
        }
        return i;
    }
    /**
     *
     * @return les pièces de théâtre sous forme d'une chaîne de caractères
     */
    public String lesPieces(){
        String i="";
        for (PieceTheatre y : lesPieces.values()){
            i+=y.toString();
        }
        return i;
    }
    /**
     *
     * @return les salles de film sous forme d'une chaîne de caractères
     */
    public String lesSallesFilm(){
        String i = "";
        for (Salle y : lesSalles.values()){
            i+=y.toString();
        }
        return i;
    }

    /**
     *
     * @return les salles de théâtre sous forme d'une chaîne de caractères
     */
    public String lesSallesTheatre(){
        String i="";
        for (SalleTheatre y : lesSallesTheatre.values()){
            i+=y.toString();
        }
        return i;
    }


    /**
     * retourne les séances de la pièce de théâtre correspondant à idPiece si elle existe sinon lève une exception
     * @param idPiece
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     */

    public String lesSeancesTheatre (int idPiece){
        if(!lesPieces.containsKey(idPiece))
            throw new IllegalArgumentException("Pièce inexistante");
        else
            return lesPieces.get(idPiece).getListeSeance();
    }
    /**
     * retourne les séances du film correspondant à idFilm s'il existe sinon lève une exception
     * @param idFilm
     * @return
     * @throws IllegalArgumentException Film inexistant
     */

    public String lesSeancesFilm (int idFilm){
        if(!lesFilms.containsKey(idFilm))
            throw new IllegalArgumentException("Film inexistant");
        else
            return lesFilms.get(idFilm).getListeSeance();
    }
    /**
     * Retourne le nombre de places standard disponibles pour le spectacle correspondant au numéro passé en paramètre s'il existe
     * et à la séance correspondant au jour et à l'horaire de début passés en paramètre si elle existe
     * @param numSpectacle
     * @param jour
     * @param heures
     * @param minutes
     * @return
     * @throws IllegalArgumentException Spectacle inexistant
     * @throws IllegalArgumentException Séance inexistante pour ce spectacle
     */
    public int getNbPlacesDispo(int numSpectacle, int jour, int heures, int minutes){
        Horaire a = new Horaire(0,heures);
        a=a.transformHoraire(minutes); // pour etre sur que le format soit en 24h
        if(numSpectacle>=1000){
            if(!lesPieces.containsKey(numSpectacle))
                throw new IllegalArgumentException("Spectacle inexistant");
            else if(lesPieces.get(numSpectacle).chercherSeance(jour,a) == null)
                throw new IllegalArgumentException("Séance inexistante pour ce spectable");
            else
                return lesPieces.get(numSpectacle).chercherSeance(jour,a).nbPlacesDispo();
        }else if(numSpectacle>=100){
            if(!lesFilms.containsKey(numSpectacle))
                throw new IllegalArgumentException("Spectacle inexistant");
            else if(lesFilms.get(numSpectacle).chercherSeance(jour,a) == null)
                throw new IllegalArgumentException("Séance inexistante pour ce spectable");
            else
                return lesFilms.get(numSpectacle).chercherSeance(jour,a).nbPlacesDispo();
        }else
            throw new IllegalArgumentException("Spectacle inexistant");
    }
    /**
     * @param idFilm
     * @return true si idFilm correspond à un film et false sinon
     */
    public boolean existeFilm (int idFilm){
        return lesFilms.containsKey(idFilm);
    }

    /**
     * @param idPiece
     * @return true si idPièce correspond à une pièce de théâtre et false sinon
     */
    public boolean existePiece (int idPiece){
        return lesPieces.containsKey(idPiece);
    }

    /**
     * Teste l'existance d'une séance pour le film dont l'idFilm est passé en paramètre s'il existe. Sinon lève une exception
     * @param idFilm
     * @param jour
     * @param heures
     * @param minutes
     * @return true s'il existe une séance correspondant à un créneau défini par un jour et un horaire de début donné par heures et minutes et false sinon
     * @throws IllegalArgumentException Film inexistant
     */
    public boolean existeSeanceFilm (int idFilm, int jour, int heures, int minutes ){
        if(!lesFilms.containsKey(idFilm))
            throw new IllegalArgumentException("Film inexistant");
        Horaire debut = new Horaire(0,heures);
        debut=debut.transformHoraire(minutes); // pour etre sur du format 24h
        Horaire fin = debut.transformHoraire(dureeFilm(idFilm));
        Creneau b = new Creneau(jour,debut,fin);
        return lesFilms.get(idFilm).chercherSeance(jour,debut).getCreneau().equals(b); // pas sur à verif plus tard

    }

    /**
     * @param idSalle
     * @return true si idSalle correspond à une salle de film et false sinon
     */
    public boolean existeSalleFilm (int idSalle){
        return lesSalles.containsKey(idSalle);
    }
    /**
     * @param idSalle
     * @return true si idSalle correspond à une salle de Theatre et false sinon
     */
    public boolean existeSalleTheatre (int idSalle){
        return lesSallesTheatre.containsKey(idSalle);
    }

    /**
     * Retourne la durée du film correspondant au paramètre s'il existe
     * @param idFilm
     * @return
     * @throws IllegalArgumentException Film inexistant
     */
    public int dureeFilm(int idFilm){
        if(!lesFilms.containsKey(idFilm))
            throw new IllegalArgumentException("Film inexistant");
        else
            return lesFilms.get(idFilm).getDuree();
    }

    /**
     * Teste la disponibilité de la salle dont l'idSalle est passé en paramètre si elle existe
     * @param jour
     * @param debut
     * @param duree
     * @param idSalle
     * @return Retourne true si la salle dont l'idSalle est passé en paramètre est disponible au créneau passé en paramètre sinon retourne false
     * @throws IllegalArgumentException Salle inexistante
     */
    public boolean salleStandardDisponible (int jour, Horaire debut, int duree, int idSalle){
        if(!lesSalles.containsKey(idSalle))
            throw new IllegalArgumentException("Salle Inexistante");
        Horaire fin = debut.transformHoraire(duree);
        Creneau b = new Creneau(jour,debut,fin);
        return lesSalles.get(idSalle).estDisponible(b);
    }

    /**
     * Supprime les films et les pièces de théâtre de la programmation en cours.
     * Il faut également supprimer les créneaux occupés de chaque salle
     */
    public void reinitialiserProgrammation(){
        if(!lesFilms.isEmpty())
            lesFilms.get(100).setId(99); // prends le premier film pour réinitialiser le compteur id
        for (Film a : lesFilms.values())
            a.reinitialierlesSeances();
        lesFilms.clear();
        if(!lesPieces.isEmpty())
            lesPieces.get(1000).setIdT(999);// prends la premiere piece pour réinitialiser le compteur idT
        for(PieceTheatre b : lesPieces.values())
            b.reinitialierlesSeances();
        lesPieces.clear();
        for(Salle i : lesSalles.values())
            i.reinitialiserlesCreneaux();;
        for (SalleTheatre y : lesSallesTheatre.values())
            y.reinitialiserlesCreneaux();
    }

    /**
     * Retourne le nombre de places de type fauteuil disponibles pour la pièce correspondant à idPiece s'elle existe
     * et s'il existe une séance le jour passé en paramètre
     * @param idPiece
     * @param jour
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     * @throws IllegalArgumentException Aucune séance ce jour;
     */
    public int getNbFauteuilsDispo(int idPiece, int jour){
        if(existeSeanceCeJour(idPiece,jour)) // le throw piece inexistante est dans le existeSeanceCeJour
            return lesPieces.get(idPiece).chercherSeance(jour).nbFauteuilsDispo();
        else
            throw new IllegalArgumentException("Aucune séance ce jour");
    }


    /**
     * Retourne le nombre de places standard disponibles pour la pièce correspondant à idPiece s'elle existe
     * et s'il existe une séance le jour passé en paramètre
     * @param idPiece
     * @param jour
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     * @throws IllegalArgumentException Aucune séance ce jour;
     */
    public int getNbPlacesDispo(int idPiece, int jour){
        if(existeSeanceCeJour(idPiece,jour)) // le throw piece inexistante est dans le existeSeanceCeJour
            return lesPieces.get(idPiece).chercherSeance(jour).nbPlacesDispo();
        else
            throw new IllegalArgumentException("Aucune séance ce jour");
    }

    /**
     *
     * @param numSpectacle
     * @return true si le numéro du spectacle passé en paramètre correspond à un numéro de film et false sinon
     */
    public boolean estUnFilm(int numSpectacle){
        if(numSpectacle>=1000)
            return false;
        else if (numSpectacle>=100)
            return true;
        else
            return false;
    }
    /**
     *
     * @param numSpectacle
     * @return true si le numéro du spectacle passé en paramètre correspond à un numéro de pièce de théâtre et false sinon
     */
    public boolean estUnePiece(int numSpectacle){
        if(numSpectacle>=1000)
            return true;
        else if (numSpectacle>=100)
            return false;
        else
            return false;
    }
    /**
     * Retourne le spectacle correspondant au numéro passé en paramètre s'il existe et null sinon
     * @param numSpectacle
     * @return
     */
    public Spectacle getSpectacle(int numSpectacle){
        if(estUnePiece(numSpectacle))
            return lesPieces.get(numSpectacle);
        else
            return null;
    }

    public Map<Integer, Film> getLesFilms() {
        return lesFilms;
    }

    public Map<Integer, PieceTheatre> getLesPieces() {
        return lesPieces;
    }


    public Map<Integer, Salle> getLesSalles() {
        return lesSalles;
    }

    @Override
    public boolean equals(Object o) { // à redefinir
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestionProgrammationSemaine that = (GestionProgrammationSemaine) o;
        return lesSalles.equals(that.lesSalles) &&
                lesSallesTheatre.equals(that.lesSallesTheatre) &&
                lesFilms.equals(that.lesFilms) &&
                lesPieces.equals(that.lesPieces);
    }

    @Override
    public int hashCode() { // à redefinir
        return Objects.hash(lesSalles, lesSallesTheatre, lesFilms, lesPieces);
    }
}

