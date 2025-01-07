package _test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import _class.Client;
import _class.Evenement;
import _class.Genre;
import _class.Utile;
import _class.Video;
import _enum.EtatVideo;
import _enum.TypeVideo;
import dao.ClientDAO;
import dao.EvenementDAO;
import dao.VideoDAO;

public class Client_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// --- TEST AJOUT FILM ---
			System.out.println("--- TEST AJOUT FILM ---");

			Date myDate1 = Utile.parseDate("2016-02-14");
			Date myDate2 = Utile.parseDate("2016-03-14");

			Video video_courant = new Video();
			video_courant.setPrix(12.99);
			video_courant.setTitre("test");
			video_courant.setType_video(TypeVideo.FILM);
			video_courant.setDescription("description test");
			video_courant.setDurre(12);
			video_courant.setDate_sortie(myDate1);
			video_courant.setDate_ajout(myDate2);
			video_courant.setEtat(EtatVideo.nouveauté);
			video_courant.setGratuit_abonne(true);
			System.out.println(VideoDAO.AjouterVideo(video_courant));
//			System.out.println(VideoDAO.TrouverIDVideo(video_courant));
			int id = VideoDAO.TrouverIDVideo(video_courant);
			ArrayList<Genre> mesGenres = new ArrayList<Genre>();
			Genre comique = new Genre(1, "Comédie");
			Genre science = new Genre(2, "Science-fiction");
			mesGenres.add(comique);
			mesGenres.add(science);
			System.out.println(VideoDAO.AjouterGenresVideo(id, mesGenres));
			// --- AFFICHER TOUS LES CLIENTS
			System.out.println("--- AFFICHER TOUS LES CLIENTS");
			ArrayList<Client> clients = ClientDAO.TousLesClients();
			for (Client i : clients) {
				System.out.println(i);
			}

			// --- AFFICHER UN CONNECTION ---
			System.out.println("--- AFFICHER UN CONNECTION ---");
			// Exemple 1 : Courriel et mot de passe existants dans la table AdminEmp
			Object[] resultat1 = ClientDAO.VerifierConnexion("claire.garcia@gmail.com", "P@ssw0rd789"); // Admin
			afficherResultat(resultat1);

			// Exemple 2 : Courriel et mot de passe existants dans la table Client
			Object[] resultat2 = ClientDAO.VerifierConnexion("sophie.lefebvre@hotmail.com", "Secret123!"); // Client
			afficherResultat(resultat2);

			// Exemple 3 : Courriel existant mais mot de passe incorrect
			Object[] resultat3 = ClientDAO.VerifierConnexion("admin@example.com", "mauvaismotdepasse"); // Erreur
			afficherResultat(resultat3);

			// --- AFFICHER CLIENTS PAR FILTRE
			System.out.println("--- AFFICHER CLIENTS PAR FILTRE ---");
			String[] parNom = { "nom", "Lefebvre" };
			String[] parPren = { "prenom", "Sophie" };
			ArrayList<String[]> filtre = new ArrayList<String[]>();
			filtre.add(parPren);
			filtre.add(parNom);
			ArrayList<Client> clientsRechercher = ClientDAO
					.RecherherClientParObj(Utile.CreationConditionWhereDuFiltre(filtre));
			for (Client i : clientsRechercher) {
				System.out.println(i);

				// --- AFFICHER TOUS LES EVENEMENTS
				System.out.println("--- AFFICHER TOUS LES EVENEMENTS ---");
				ArrayList<Evenement> envents = EvenementDAO.TousLesEvenements();
				for (Evenement e : envents) {
					System.out.println(e);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void afficherResultat(Object[] resultat) {
		int id = (int) resultat[0];
		String typeUtilisateur = (String) resultat[1];
		System.out.println("ID: " + id + ", Type utilisateur: " + typeUtilisateur);
	}

}
