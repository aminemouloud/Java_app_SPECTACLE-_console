package main;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args){
    GestionProgrammationSemaine semaine = new GestionProgrammationSemaine();
        int fin = 0;
        while (fin ==0){
            try {
                System.out.println("réinitialiser programmation (0)");
                System.out.println("ajouter un film (1)");
                System.out.println("ajouter une pièce de théâtre (2)");
                System.out.println("ajouter un nom d'interprète à un spectacle (3)");
                System.out.println("ajouter une séance pour un film (4)");
                System.out.println("ajouter une séance pour une pièce de théâtre (5)");
                System.out.println("vendre des places pour un film (6)");
                System.out.println("vendre des places pour une pièce de théâtre (7)");
                System.out.println("consulter le chiffre d'affaires et le taux de remplissage d'un spectacle (8)");
                System.out.println("quitter (9)");
                Scanner sc = new Scanner(System.in);
                if (sc.hasNextInt()) {
                    int nb = sc.nextInt();
                    if (nb == 0)
                        semaine.reinitialiserProgrammation();
                    else if (nb == 1) {

                        System.out.print("Titre :");
                        Scanner choixfilm = new Scanner(System.in);
                        String titre = choixfilm.next();

                        System.out.print("réalisateur :");
                        choixfilm = new Scanner(System.in);
                        String realisateur = choixfilm.next();

                        int duree = -1;
                        System.out.print("durée :");
                        while (duree <= 0) {
                            choixfilm = new Scanner(System.in);
                            if (choixfilm.hasNextInt())
                                duree = choixfilm.nextInt();
                        }
                        semaine.ajouterFilm(titre, realisateur, duree);


                    } else if (nb == 2) {

                        System.out.print("Titre :");
                        Scanner choixtheatre = new Scanner(System.in);
                        String titre = choixtheatre.next();

                        System.out.print("metteur en scène :");
                        choixtheatre = new Scanner(System.in);
                        String metteurenscene = choixtheatre.next();

                        int nbentractes = -1;
                        System.out.print("nombre d'entractes :");
                        while (nbentractes <= 0) {
                            choixtheatre = new Scanner(System.in);
                            if (choixtheatre.hasNextInt())
                                nbentractes = choixtheatre.nextInt();
                        }
                        semaine.ajouterPiece(titre, metteurenscene, nbentractes);

                    } else if (nb == 3) {
                        if (semaine.getLesFilms().isEmpty() && semaine.lesPieces().isEmpty()) {
                            System.out.println("Aucun film et Aucune Pièce");
                            continue;
                        } else {
                            System.out.println(semaine.lesFilms());
                            System.out.println(semaine.lesPieces());
                            System.out.print("Choix idSpectacle : ");
                            Scanner choix = new Scanner(System.in);
                            if (choix.hasNextInt()) {
                                int id = choix.nextInt();
                                if (semaine.existeFilm(id)) {
                                    System.out.print("Saisie interprète : ");
                                    choix = new Scanner(System.in);
                                    String interprete = choix.next();
                                    semaine.ajouterInterprete(id, interprete);
                                } else {
                                    System.out.println("Numéro de Spectacle inexistant");
                                    continue;
                                }
                            }
                        }

                    } else if (nb == 4) {
                        if (semaine.getLesFilms().isEmpty()) {
                            System.out.println("Aucun film");
                            continue;
                        } else {
                            System.out.println(semaine.lesFilms());
                            int idfilm = -1;
                            int jour = -1;
                            int heures = -1;
                            int minutes = -1;
                            int idsalle = -1;
                            while (idfilm == -1 || jour == -1 || heures == -1 || minutes == -1 || idsalle == -1) {
                                System.out.print("choix idFilm : ");
                                Scanner choix = new Scanner(System.in);
                                if (choix.hasNextInt()) {
                                    idfilm = choix.nextInt();
                                    if (!semaine.existeFilm(idfilm)) {
                                        System.out.println("le film n'existe pas");
                                        break;
                                    }
                                }
                                else {
                                    System.out.println("Erreur dans le idFilm");
                                    continue;
                                }
                                System.out.print("saisie jour : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    jour = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans le jour");
                                    continue;
                                }
                                if (jour < 1 || 7 < jour) {
                                    System.out.println("le jour n'est pas valide");
                                    break;
                                }
                                System.out.print("saisie heures : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    heures = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans l'heure'");
                                    continue;
                                }
                                System.out.print("saisie minutes : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    minutes = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans les minutes");
                                    continue;
                                }

                                if (minutes < 0 || minutes >59 || heures < 0 || heures > 23) {
                                    System.out.println("Minutes ou/et heures invalide");
                                    break;
                                }

                                System.out.print("choix idSalle : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt()) {
                                    idsalle = choix.nextInt();
                                    if (!semaine.existeSalleFilm(idsalle)) {
                                        System.out.println("Salle inconnue");
                                    }
                                }
                                else {
                                    System.out.println("Erreur dans le idSalle");
                                    continue;
                                }


                                Horaire debut = new Horaire(0, heures);
                                debut = debut.transformHoraire(minutes);
                                semaine.ajouterSeanceFilm(idfilm, jour, debut, idsalle);
                            }
                        }
                    } else if (nb == 5) {
                        if (semaine.getLesPieces().isEmpty()) {
                            System.out.println("Aucune Pièce");
                            continue;
                        } else {
                            System.out.println(semaine.lesPieces());
                            int idPiece = -1;
                            int jour = -1;
                            int heures = -1;
                            int minutes = -1;
                            int idsalle = -1;
                            while (idPiece == -1 || jour == -1 || heures == -1 || minutes == -1 || idsalle == -1) {
                                System.out.print("choix idPiece : ");
                                Scanner choix = new Scanner(System.in);
                                if (choix.hasNextInt()){
                                    idPiece = choix.nextInt();
                                    if (!semaine.existePiece(idPiece)) {
                                        System.out.println("la Pièce n'existe pas");
                                        break;

                                    }
                                }
                                else {
                                    System.out.println("Erreur dans le idPiece");
                                    continue;
                                }

                                System.out.print("saisie jour : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt()) {
                                    jour = choix.nextInt();
                                    if (jour < 1 || 7 < jour) {
                                        System.out.println("le jour n'est pas valide");
                                        break;
                                    }
                                }
                                else {
                                    System.out.println("Erreur dans le jour");
                                    continue;
                                }

                                if(semaine.existeSeanceCeJour(idPiece,jour))
                                    throw new IllegalArgumentException("il existe déjà une séance programée ce jour");
                                System.out.print("saisie heures : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    heures = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans l'heure'");
                                    continue;
                                }
                                System.out.print("saisie minutes : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    minutes = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans les minutes");
                                    continue;
                                }

                                if (minutes < 0 || minutes >59 || heures < 0 || heures > 23) {
                                    System.out.println("Minutes ou/et heures invalide");
                                    break;
                                }

                                System.out.print("choix idSalle : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    idsalle = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans le idSalle");
                                    continue;
                                }

                                if (!semaine.existeSalleTheatre(idsalle)) {
                                    System.out.println("Salle inconnue");
                                    break;
                                }

                                Horaire debut = new Horaire(0, heures);
                                debut = debut.transformHoraire(minutes);
                                semaine.ajouterSeanceTheatre(idPiece, jour, debut, idsalle);
                            }
                        }
                    } else if (nb == 6) {
                        if(semaine.getLesFilms().isEmpty()){
                            System.out.println("Aucun Film");
                            continue;
                        }
                        System.out.println(semaine.lesFilms());
                        int idfilm = -1;
                        int jour = -1;
                        int heures = -1;
                        int minutes = -1;
                        int nbplaceTN = -1;
                        int nbplaceTR = -1;
                        boolean confirmation = false;

                        while(!confirmation) {
                            while (idfilm == -1 || jour == -1 || heures == -1 || minutes == -1 || nbplaceTN == -1 || nbplaceTR == -1) {
                                System.out.print("choix idFilm : ");
                                Scanner choix = new Scanner(System.in);
                                if (choix.hasNextInt()) {
                                    idfilm = choix.nextInt();
                                    if (!semaine.existeFilm(idfilm)) {
                                        System.out.println("le  film selectionné n'existe pas");
                                        continue;
                                    }else if(semaine.lesSeancesFilm(idfilm) == "") {
                                        System.out.println("Pas de Séance disponible pour ce film");
                                        confirmation = true;
                                        break;
                                    }
                                } else {
                                    System.out.println("Erreur dans le idFilm");
                                    continue;
                                }
                                System.out.println("Les séances du film :\n"+semaine.lesSeancesFilm(idfilm));
                                System.out.print("choix jour : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt()) {
                                    jour = choix.nextInt();
                                    if (jour < 1 || 7 < jour || semaine.getLesFilms().get(idfilm).chercherDesSeances(jour) == null) {
                                        System.out.println("le jour n'est pas valide");
                                        break;
                                    }
                                } else {
                                    System.out.println("Erreur dans le jour");
                                    continue;
                                }
                                System.out.print("saisie heures : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    heures = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans l'heure'");
                                    continue;
                                }
                                System.out.print("saisie minutes : ");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    minutes = choix.nextInt();
                                else {
                                    System.out.println("Erreur dans les minutes");
                                    continue;
                                }
                                if (minutes < 0 || minutes >59 || heures < 0 || heures > 23) {
                                    System.out.println("Minutes ou/et heures invalide");
                                    break;
                                }

                                Horaire debut = new Horaire(0, heures);
                                debut = debut.transformHoraire(minutes);
                                try {
                                    System.out.println("Nombre de places restante dans la salle : " + semaine.getNbPlacesDispo(idfilm, jour, heures, minutes));
                                    confirmation = true;
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    continue;
                                }
                                while (nbplaceTN < 0) {
                                    System.out.print("saisie nombre de places tarif normal :");
                                    choix = new Scanner(System.in);
                                    if (choix.hasNextInt())
                                        nbplaceTN = choix.nextInt();
                                }

                                semaine.vendrePlaceFilmTN(idfilm, jour, debut, nbplaceTN);

                                while (nbplaceTR < 0) {
                                    System.out.print("saisie nombre de places tarif réduit :");
                                    choix = new Scanner(System.in);
                                    if (choix.hasNextInt())
                                        nbplaceTR = choix.nextInt();
                                }
                                try {
                                semaine.vendrePlaceFilmTR(idfilm, jour, debut, nbplaceTR);
                                }catch(Exception e){
                                    System.out.println(e.getMessage());
                                    semaine.getLesFilms().get(idfilm).chercherSeance(jour,debut).setNbPlacesVenduesTN(semaine.getLesFilms().get(idfilm).chercherSeance(jour,debut).getNbPlacesVenduesTN()-nbplaceTN); //remettre les places en tarif normal prix jsute avant car tout à été annulé
                                }
                            }
                        }
                    } else if (nb == 7) {
                        if (semaine.getLesPieces().isEmpty()){
                            System.out.println("Aucune Pièce");
                            continue;
                        }
                        System.out.println(semaine.lesPieces());
                        int idPiece = -1;
                        int jour = -1;
                        int nbplaceTN = -1;
                        int nbfauteuil = -1;

                        while (idPiece == -1 || jour == -1 || nbplaceTN == -1 || nbfauteuil == -1) {
                            System.out.print("choix idPiece : ");
                            Scanner choix = new Scanner(System.in);
                            if (choix.hasNextInt()) {
                                idPiece = choix.nextInt();
                                if (!semaine.existePiece(idPiece)) {
                                    System.out.println("la Pièce selectionné n'existe pas");
                                    continue;
                                }else if(semaine.lesSeancesTheatre(idPiece) == "") {
                                    System.out.println("Pas de Séance disponible pour cette Pièce");
                                    break;
                                }
                            } else {
                                System.out.println("Erreur dans le idFilm");
                                continue;
                            }
                            System.out.println("Les séances de la pièce :\n"+semaine.lesSeancesTheatre(idPiece));
                            System.out.print("choix jour : ");
                            choix = new Scanner(System.in);
                            if (choix.hasNextInt()) {
                                jour = choix.nextInt();
                                if (jour < 1 || 7 < jour || semaine.getLesPieces().get(idPiece).chercherDesSeances(jour) == null) {
                                    System.out.println("le jour n'est pas valide");
                                    continue;
                                }
                            } else {
                                System.out.println("Erreur dans le jour");
                                continue;
                            }
                            System.out.println("Nombre de places Standard disponibles : "+semaine.getNbPlacesDispo(idPiece, jour));
                            System.out.println("Nombre de fauteuils disponibles : "+semaine.getNbFauteuilsDispo(idPiece, jour));
                            while (nbplaceTN < 0) {
                                System.out.print("saisie nombre de places Standard :");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt()) {
                                    nbplaceTN = choix.nextInt();
                                }
                            }

                                semaine.vendrePlacePieceTN(idPiece, jour, nbplaceTN);

                            while (nbfauteuil < 0) {
                                System.out.print("saisie nombre de fauteuils :");
                                choix = new Scanner(System.in);
                                if (choix.hasNextInt())
                                    nbfauteuil = choix.nextInt();
                            }

                            try{
                                semaine.vendrePlaceFauteuilPiece(idPiece, jour, nbfauteuil);
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                                semaine.getLesPieces().get(idPiece).chercherSeance(jour).setNbPlacesVenduesTN(semaine.getLesPieces().get(idPiece).chercherSeance(jour).getNbPlacesVenduesTN()-nbplaceTN);; //comme pour les films
                            }

                        }
                    } else if (nb == 8) {
                        if(semaine.getLesFilms().isEmpty() && semaine.getLesPieces().isEmpty()) {
                            System.out.println("Aucun film et Aucune Pièce");
                            continue;
                        }
                        int idSpectacle = -1;
                        System.out.println(semaine.lesFilms());
                        System.out.println(semaine.lesPieces());
                        System.out.print("choix du numSpectacle : ");
                        Scanner choix = new Scanner(System.in);
                        while (idSpectacle < 0) {
                            if (choix.hasNextInt()) {
                                idSpectacle = choix.nextInt();
                            }
                        }
                        System.out.println("chiffre d'affaire :" + semaine.chiffreAffaires(idSpectacle));
                        System.out.println("taux de remplissage :" + semaine.getTauxRemplissage(idSpectacle)+"%");
                    } else if (nb == 9)
                        fin = 1;


                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }


        }
    }
}
