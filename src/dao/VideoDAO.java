package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import _class.Acteur;
import _class.Genre;
import _class.Realisateur;
import _class.Sujet;
import _class.Video;
import _enum.EtatVideo;
import _enum.TypeVideo;
import bd.DbConnection;

public class VideoDAO {

	// tous les videos de la base de données
	// si isVueClient est true alors tous les videos dont l'état n'est pas archivé
	public static ArrayList<Video> TousLesVideos(Boolean isVueClient) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Video";
		if (isVueClient) {
			sqlcmd += " WHERE etat != 'archivé';";
		}
		sqlcmd += ";";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Video> videos = new ArrayList<>();
		Video video_courant;

		while (rs.next()) {
			video_courant = new Video();
			video_courant.setId(rs.getInt("id"));
			video_courant.setPrix(rs.getDouble("prix"));
			video_courant.setTitre(rs.getString("titre"));
			TypeVideo typeVideoEnum = TypeVideo.fromString(rs.getString("type_video")); // conversion du string vers
																						// l'Enum
																						// TypeVideo
			video_courant.setType_video(typeVideoEnum);
			video_courant.setDescription(rs.getString("description_video"));
			video_courant.setDurre(rs.getInt("duree"));
			video_courant.setDate_sortie(rs.getDate("date_sortie"));
			video_courant.setDate_ajout(rs.getDate("date_ajout"));
			EtatVideo etatVideoEnum = EtatVideo.valueOf(rs.getString("etat")); // conversion du string vers l'Enum
																				// EtatVideo
			video_courant.setEtat(etatVideoEnum);
			video_courant.setGratuit_abonne(rs.getBoolean("gratuit_abonne"));
			/*
			 * TODO obtenir par d'autres requetes sql ?! private ArrayList<Genre> genres;
			 * private ArrayList<Sujet> sujets; private ArrayList<Acteur> acteurs; private
			 * ArrayList<Realisateur> realisateurs;
			 */
			videos.add(video_courant);
		}

		// fermer le dataset
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return videos;
	}

	// tous les videos de la base de données
	// TODO vieille fonction - à retirer
	public static ArrayList<Video> TousLesVideos() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Video;";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Video> videos = new ArrayList<>();
		Video video_courant;

		while (rs.next()) {
			video_courant = new Video();
			video_courant.setId(rs.getInt("id"));
			video_courant.setPrix(rs.getDouble("prix"));
			video_courant.setTitre(rs.getString("titre"));
			TypeVideo typeVideoEnum = TypeVideo.fromString(rs.getString("type_video")); // conversion du string vers
																						// l'Enum
																						// TypeVideo
			video_courant.setType_video(typeVideoEnum);
			video_courant.setDescription(rs.getString("description_video"));
			video_courant.setDurre(rs.getInt("duree"));
			video_courant.setDate_sortie(rs.getDate("date_sortie"));
			video_courant.setDate_ajout(rs.getDate("date_ajout"));
			EtatVideo etatVideoEnum = EtatVideo.valueOf(rs.getString("etat")); // conversion du string vers l'Enum
																				// EtatVideo
			video_courant.setEtat(etatVideoEnum);
			video_courant.setGratuit_abonne(rs.getBoolean("gratuit_abonne"));
			/*
			 * TODO obtenir par d'autres requetes sql ?! private ArrayList<Genre> genres;
			 * private ArrayList<Sujet> sujets; private ArrayList<Acteur> acteurs; private
			 * ArrayList<Realisateur> realisateurs;
			 */
			videos.add(video_courant);
		}

		// fermer le dataset
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return videos;
	}

	/**
	 * Cette fonction retourne un string contenant tous les conditions WHERE d'une
	 * requête SQL à partir d'un arraylist. Chaque item de l'ArrayList est un
	 * tableau à deux dimension de String {key, value}.
	 * 
	 * @param filtre
	 * @return String requeteConditionWhere
	 */
	public static String CreationConditionWhereDuFiltre(ArrayList<String[]> filtre) {
		String requeteConditionWhere = "";
		for (int i = 0; i < filtre.size(); i++) {
			String key = filtre.get(i)[0];
			String value = filtre.get(i)[1];
			requeteConditionWhere += key + " = '" + value + "'";
			if (i != filtre.size() - 1) {
				requeteConditionWhere += " AND ";
			}
		}
		return requeteConditionWhere;
	}

	// Tous les video qui correspondent au filtre (ajout des leftjoins selon le
	// filtre)
	public static ArrayList<Video> VideosSelonFiltre(String leftJoinRequete, String filtreSQL, Boolean isVueClient)
			throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT distinct(V.id), V.prix, V.titre, V.type_video, V.description_video, V.duree, "
				+ "V.date_sortie, V.date_ajout, V.etat, V.gratuit_abonne " + "FROM Video V " + leftJoinRequete
				+ "WHERE " + filtreSQL;
		if (isVueClient) {
			sqlcmd += "AND etat != 'archivé'";
		}
		sqlcmd += ";";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Video> videos = new ArrayList<>();
		Video video_courant;

		while (rs.next()) {
			video_courant = new Video();
			video_courant.setId(rs.getInt("id"));
			video_courant.setPrix(rs.getDouble("prix"));
			video_courant.setTitre(rs.getString("titre"));
			TypeVideo typeVideoEnum = TypeVideo.fromString(rs.getString("type_video")); // conversion du string vers
																						// l'Enum TypeVideo
			video_courant.setType_video(typeVideoEnum);
			video_courant.setDescription(rs.getString("description_video"));
			video_courant.setDurre(rs.getInt("duree"));
			video_courant.setDate_sortie(rs.getDate("date_sortie"));
			video_courant.setDate_ajout(rs.getDate("date_ajout"));
			EtatVideo etatVideoEnum = EtatVideo.valueOf(rs.getString("etat")); // conversion du string vers l'Enum
																				// EtatVideo
			video_courant.setEtat(etatVideoEnum);
			video_courant.setGratuit_abonne(rs.getBoolean("gratuit_abonne"));
			/*
			 * TODO obtenir par d'autres requetes sql ?! private ArrayList<Genre> genres;
			 * private ArrayList<Sujet> sujets; private ArrayList<Acteur> acteurs; private
			 * ArrayList<Realisateur> realisateurs;
			 */
			videos.add(video_courant);
		}

		rs.close();
		pstmt.close();
		return videos;
	}

	// ========================================================================
	// fonctions pour obtenir les choix de filtre du combo box.
	// ========================================================================

	// tous les videos
	// si isVueClient est true alors tous les videos dont l'état n'est pas archivé
	public static ArrayList<String> TousLesTitresVideos(Boolean isVueClient) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT titre FROM Video";
		if (isVueClient) {
			sqlcmd += " WHERE etat != 'archivé';";
		}
		sqlcmd += ";";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<String> titreVideos = new ArrayList<>();

		while (rs.next()) {
			titreVideos.add(rs.getString("titre"));
		}

		rs.close();
		pstmt.close();
		return titreVideos;
	}

	// tous les genres
	public static ArrayList<Genre> TousLesGenres() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Genre;";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Genre> genres = new ArrayList<>();
		Genre genreTemps;
		while (rs.next()) {
			genreTemps = new Genre();
			genreTemps.setId(rs.getInt("id"));
			genreTemps.setNom(rs.getString("nom"));
			genres.add(genreTemps);
		}

		rs.close();
		pstmt.close();
		return genres;
	}

	// tous les sujets
	public static ArrayList<Sujet> TousLesSujets() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Sujet;";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Sujet> sujets = new ArrayList<>();
		Sujet sujetTemps;
		while (rs.next()) {
			sujetTemps = new Sujet();
			sujetTemps.setId(rs.getInt("id"));
			sujetTemps.setNom(rs.getString("nom"));
			sujets.add(sujetTemps);
		}

		rs.close();
		pstmt.close();
		return sujets;
	}

	// tous les acteurs
	public static ArrayList<Acteur> TousLesActeurs() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Acteur;";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Acteur> acteurs = new ArrayList<>();
		Acteur acteurTemps;
		while (rs.next()) {
			acteurTemps = new Acteur();

			acteurTemps.setId(rs.getInt("id"));
			acteurTemps.setPrenom(rs.getString("prenom"));
			acteurTemps.setNom(rs.getString("nom"));
			acteurTemps.setDateNaissance(rs.getDate("DateNaissance"));
			acteurs.add(acteurTemps);
		}

		rs.close();
		pstmt.close();
		return acteurs;
	}

	// tous les realisateurs
	public static ArrayList<Realisateur> TousLesRealisateurs() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Realisateur;";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Realisateur> realisateurs = new ArrayList<>();
		Realisateur realisateurTemps;
		while (rs.next()) {
			realisateurTemps = new Realisateur();

			realisateurTemps.setId(rs.getInt("id"));
			realisateurTemps.setPrenom(rs.getString("prenom"));
			realisateurTemps.setNom(rs.getString("nom"));
			realisateurTemps.setDateNaissance(rs.getDate("DateNaissance"));
			realisateurs.add(realisateurTemps);
		}

		// fermer le dataset
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return realisateurs;
	}

	// ========================================================================
	// fonctions pour obtenir tous les genres, sujets, acteurs et realisateur
	// pour un film spécifique
	// ========================================================================

	// tous les genres pour un video spécifique
	public static ArrayList<Genre> GenresDuFilm(int idVideo) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT nom, G.id FROM VideoGenre VG INNER JOIN Genre G "
				+ "on VG.idGenre = G.id WHERE VG.idVideo = " + idVideo + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Genre> genres = new ArrayList<Genre>();

		while (rs.next()) {
			Genre tmp = new Genre();
			tmp.setId(rs.getInt("id"));
			tmp.setNom(rs.getString("nom"));
			genres.add(tmp);
		}

		rs.close();
		pstmt.close();
		return genres;
	}

	// tous les sujets pour un video spécifique
	public static ArrayList<Sujet> SujetsDuFilm(int idVideo) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT nom, S.id FROM VideoSujet VS INNER JOIN Sujet S "
				+ "on VS.idSujet = S.id WHERE VS.idVideo = " + idVideo + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Sujet> sujets = new ArrayList<Sujet>();

		while (rs.next()) {
			Sujet tmp = new Sujet();
			tmp.setId(rs.getInt("id"));
			tmp.setNom(rs.getString("nom"));
			sujets.add(tmp);
		}

		rs.close();
		pstmt.close();
		return sujets;
	}

	// tous les acteurs pour un video spécifique
	public static ArrayList<Acteur> ActeursDuFilm(int idVideo) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT A.id, nom, prenom, dateNaissance " + "FROM VideoActeur VA INNER JOIN Acteur A "
				+ "on VA.idActeur = A.id WHERE VA.idVideo = " + idVideo + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Acteur> acteurs = new ArrayList<Acteur>();

		while (rs.next()) {
			Acteur tmp = new Acteur();
			tmp.setId(rs.getInt("id"));
			tmp.setNom(rs.getString("nom"));
			tmp.setPrenom(rs.getString("prenom"));
			tmp.setDateNaissance(rs.getDate("dateNaissance"));
			acteurs.add(tmp);
		}

		rs.close();
		pstmt.close();
		return acteurs;
	}

	// tous les realisateurs pour un video spécifique
	public static ArrayList<Realisateur> RealisateursDuFilm(int idVideo) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT R.id, nom, prenom, dateNaissance "
				+ "FROM VideoRealisateur VR INNER JOIN Realisateur R "
				+ "on VR.idRealisateur = R.id WHERE VR.idVideo = " + idVideo + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Realisateur> realisateurs = new ArrayList<Realisateur>();

		while (rs.next()) {
			Realisateur tmp = new Realisateur();
			tmp.setId(rs.getInt("id"));
			tmp.setNom(rs.getString("nom"));
			tmp.setPrenom(rs.getString("prenom"));
			tmp.setDateNaissance(rs.getDate("dateNaissance"));
			realisateurs.add(tmp);
		}

		rs.close();
		pstmt.close();
		return realisateurs;
	}

	// ========================================================================
	// fonctions pour obtenir les videos d'un client, video si abonnement et les
	// nouveautés
	// ========================================================================

	public static ArrayList<Video> VideosLocationsActifsClient(int idClient) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		// location moins de 48h avec
		// VC.debut_location > DATEADD(HOUR, -48, CONVERT(DATETIME2, GETDATE()))
		String sqlcmd = "SELECT * FROM Video V LEFT JOIN VideoClient VC on V.id = VC.idVideo WHERE VC.idClient = "
				+ idClient + "AND VC.debut_location > DATEADD(HOUR, -48, CONVERT(DATETIME2, GETDATE())) ;";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Video> videos = new ArrayList<>();
		Video video_courant;

		while (rs.next()) {
			video_courant = new Video();
			video_courant.setId(rs.getInt("id"));
			video_courant.setPrix(rs.getDouble("prix"));
			video_courant.setTitre(rs.getString("titre"));
			TypeVideo typeVideoEnum = TypeVideo.fromString(rs.getString("type_video")); // conversion du string vers
																						// l'Enum
																						// TypeVideo
			video_courant.setType_video(typeVideoEnum);
			video_courant.setDescription(rs.getString("description_video"));
			video_courant.setDurre(rs.getInt("duree"));
			video_courant.setDate_sortie(rs.getDate("date_sortie"));
			video_courant.setDate_ajout(rs.getDate("date_ajout"));
			EtatVideo etatVideoEnum = EtatVideo.valueOf(rs.getString("etat")); // conversion du string vers l'Enum
																				// EtatVideo
			video_courant.setEtat(etatVideoEnum);
			video_courant.setGratuit_abonne(rs.getBoolean("gratuit_abonne"));
			videos.add(video_courant);
		}

		rs.close();
		pstmt.close();
		return videos;
	}

	// faire verifier l'abonnement du client avant d'appeler cette fonction
	// tous les videos inclus avec un abonnement
	public static ArrayList<Video> VideosInclusAbonnementClient(int idClient, Boolean isVueClient) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Video V WHERE V.gratuit_abonne = 1";
		if (isVueClient) {
			sqlcmd += " AND etat != 'archivé';";
		}
		sqlcmd += ";";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Video> videos = new ArrayList<>();
		Video video_courant;

		while (rs.next()) {
			video_courant = new Video();
			video_courant.setId(rs.getInt("id"));
			video_courant.setPrix(rs.getDouble("prix"));
			video_courant.setTitre(rs.getString("titre"));
			TypeVideo typeVideoEnum = TypeVideo.fromString(rs.getString("type_video")); // conversion du string vers
																						// l'Enum
																						// TypeVideo
			video_courant.setType_video(typeVideoEnum);
			video_courant.setDescription(rs.getString("description_video"));
			video_courant.setDurre(rs.getInt("duree"));
			video_courant.setDate_sortie(rs.getDate("date_sortie"));
			video_courant.setDate_ajout(rs.getDate("date_ajout"));
			EtatVideo etatVideoEnum = EtatVideo.valueOf(rs.getString("etat")); // conversion du string vers l'Enum
																				// EtatVideo
			video_courant.setEtat(etatVideoEnum);
			video_courant.setGratuit_abonne(rs.getBoolean("gratuit_abonne"));
			videos.add(video_courant);
		}

		rs.close();
		pstmt.close();
		return videos;
	}

	// tous les videos de nouveauté
	public static ArrayList<Video> VideosNouveaute() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Video WHERE etat = 'nouveauté';";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Video> videos = new ArrayList<>();
		Video video_courant;

		while (rs.next()) {
			video_courant = new Video();
			video_courant.setId(rs.getInt("id"));
			video_courant.setPrix(rs.getDouble("prix"));
			video_courant.setTitre(rs.getString("titre"));
			TypeVideo typeVideoEnum = TypeVideo.fromString(rs.getString("type_video")); // conversion du string vers
																						// l'Enum
																						// TypeVideo
			video_courant.setType_video(typeVideoEnum);
			video_courant.setDescription(rs.getString("description_video"));
			video_courant.setDurre(rs.getInt("duree"));
			video_courant.setDate_sortie(rs.getDate("date_sortie"));
			video_courant.setDate_ajout(rs.getDate("date_ajout"));
			EtatVideo etatVideoEnum = EtatVideo.valueOf(rs.getString("etat")); // conversion du string vers l'Enum
																				// EtatVideo
			video_courant.setEtat(etatVideoEnum);
			video_courant.setGratuit_abonne(rs.getBoolean("gratuit_abonne"));
			videos.add(video_courant);
		}

		rs.close();
		pstmt.close();
		return videos;
	}

	// ========================================================================
	// verification validité de l'abonnement
	// ========================================================================

	public static Boolean VerificationValiditeAbonnement(int idClient) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT actif, dateFin FROM Abonnement WHERE idClient = " + idClient + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		// rs vide
		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return false;
		}

		Boolean isActif = false;
		Boolean rsActif = rs.getBoolean("actif");
		Date rsDateFin = rs.getDate("dateFin");

		java.util.Date utilDate = new java.util.Date();
		Date sqlDate = new Date(utilDate.getTime());

		if (sqlDate.compareTo(rsDateFin) >= 0) { // non actif selon la date de fin
			isActif = false;
		} else if (rsActif) {
			isActif = true;
		}

		rs.close();
		pstmt.close();
		return isActif;
	}

	// ============================================================================
	// verification validité de la location (moins de 48h) pour un video specifique
	// ============================================================================

	public static Boolean VerificationValiditeLocationCientVideo(int idClient, int idVideo) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT idVideo FROM VideoClient WHERE idClient = " + idClient + " AND idVideo = " + idVideo
				+ " AND debut_location > DATEADD(HOUR, -48, CONVERT(DATETIME2, GETDATE()));";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		Boolean isValide = true;

		// rs vide => video location invalide
		if (!rs.next()) {
			isValide = false;
		}

		rs.close();
		pstmt.close();
		return isValide;
	}

	// ========================================================================
	// remplir infos video
	// ========================================================================

	public static Video remplirInfosVideo(Video videoSelectionne) {
		if (videoSelectionne.getGenres().isEmpty()) {
			// doit faire la requete pour obtenir tous les genres, sujets, acteurs et
			// realisateurs de ce film
			try {
				videoSelectionne.setGenres(VideoDAO.GenresDuFilm(videoSelectionne.getId()));
				videoSelectionne.setSujets(VideoDAO.SujetsDuFilm(videoSelectionne.getId()));
				videoSelectionne.setActeurs(VideoDAO.ActeursDuFilm(videoSelectionne.getId()));
				videoSelectionne.setRealisateurs(VideoDAO.RealisateursDuFilm(videoSelectionne.getId()));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return videoSelectionne;
	}

	public static int AjouterVideo(Video video) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		int gratuit_abonne = 0;
		if (video.isGratuit_abonne()) {
			gratuit_abonne = 1;
		}
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		// INSERT VIDEO SANS RIEN
		String sqlcmd = "INSERT INTO Video (prix, titre, type_video, description_video, duree, date_sortie, date_ajout, etat, gratuit_abonne) VALUES "
				+ "(" + video.getPrix() + ", " + "'" + video.getTitre() + "', " + "'" + video.getType_video().toString()
				+ "', " + "'" + video.getDescription() + "', " + video.getDurre() + ", " + "'"
				+ simpleDateFormat.format(video.getDate_sortie()) + "', " + "'"
				+ simpleDateFormat.format(video.getDate_ajout()) + "', " + "'" + video.getEtat().toString() + "', "
				+ gratuit_abonne + ");";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		int rs = pstmt.executeUpdate();

		pstmt.close();
		return rs;
	}

	// Fonction sert trouver id d'un film
	public static int TrouverIDVideo(Video video) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		int gratuit_abonne = 0;
		if (video.isGratuit_abonne()) {
			gratuit_abonne = 1;
		}
		String testSortie = simpleDateFormat.format(video.getDate_sortie());
		String testAjout = simpleDateFormat.format(video.getDate_ajout());
		String sqlcmd = "SELECT id FROM Video WHERE prix = " + video.getPrix() + " AND titre = '" + video.getTitre()
				+ "'" + " AND type_video = '" + video.getType_video().toString() + "'" + " AND date_sortie = '"
				+ testSortie + "'" + " AND date_ajout = '" + testAjout + "'" + " AND etat = '"
				+ video.getEtat().toString() + "'" + " AND gratuit_abonne = " + gratuit_abonne + ";";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();
		int id = -1;
		if (rs.next()) {
			id = rs.getInt("id");
		}
		// fermer le dataset
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return id;
	}

	public static int AjouterGenresVideo(int video, ArrayList<Genre> genres) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		// Assure que sa va marcher
		if (genres.isEmpty() || genres == null) {
			return 0;
		}
		String values = "";
		for (int i = 0; i < genres.size(); i++) {
			values += "(" + video + "," + genres.get(i).getId() + ")";
			if (i != genres.size() - 1) {
				values += ", ";
			}
		}
		String sqlcmd = "INSERT INTO VideoGenre VALUES " + values + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		int rs = pstmt.executeUpdate();

		pstmt.close();
		return rs;
	}

	public static int AjouterSujetVideo(int video, ArrayList<Sujet> sujets) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		// Assure que sa va marcher
		if (sujets.isEmpty() || sujets == null) {
			return 0;
		}
		String values = "";
		for (int i = 0; i < sujets.size(); i++) {
			values += "(" + video + "," + sujets.get(i).getId() + ")";
			if (i != sujets.size() - 1) {
				values += ", ";
			}
		}
		String sqlcmd = "INSERT INTO VideoSujet VALUES " + values + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		int rs = pstmt.executeUpdate();

		pstmt.close();
		return rs;
	}

	public static int AjouterActeurVideo(int video, ArrayList<Acteur> acteurs) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		// Assure que sa va marcher
		if (acteurs.isEmpty() || acteurs == null) {
			return 0;
		}
		String values = "";
		for (int i = 0; i < acteurs.size(); i++) {
			values += "(" + video + "," + acteurs.get(i).getId() + ")";
			if (i != acteurs.size() - 1) {
				values += ", ";
			}
		}
		String sqlcmd = "INSERT INTO VideoActeur VALUES " + values + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		int rs = pstmt.executeUpdate();

		pstmt.close();
		return rs;
	}

	public static int AjouterReaVideo(int video, ArrayList<Realisateur> realisateurs) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		// Assure que sa va marcher
		if (realisateurs.isEmpty() || realisateurs == null) {
			return 0;
		}
		String values = "";
		for (int i = 0; i < realisateurs.size(); i++) {
			values += "(" + video + "," + realisateurs.get(i).getId() + ")";
			if (i != realisateurs.size() - 1) {
				values += ", ";
			}
		}
		String sqlcmd = "INSERT INTO VideoRealisateur VALUES " + values + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		int rs = pstmt.executeUpdate();

		pstmt.close();
		return rs;
	}
	// ========================================================================
	// Nouvelle location par un client
	// ========================================================================

	public static void nouvelleLocation(int idClient, int idVideo) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "INSERT INTO VideoClient (idClient, idVideo, debut_location) VALUES" + "(" + idClient + " ,"
				+ idVideo + ", CONVERT(DATETIME2, GETDATE()) );";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public static void CreateGenre(String nomGenreAdd) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "INSERT INTO Genre (Nom) VALUES ('" + nomGenreAdd + "');";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public static void CreateSujet(String nomSujetAdd) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "INSERT INTO Sujet (Nom) VALUES ('" + nomSujetAdd + "');";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public static void CreateActeur(Acteur a) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String dateNaissance = simpleDateFormat.format(a.getDateNaissance());
		String sqlcmd = "INSERT INTO Acteur (prenom, nom, dateNaissance) VALUES ('" + a.getPrenom() + "', '"
				+ a.getNom() + "', '" + dateNaissance + "');";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public static void CreateRea(Realisateur r) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String dateNaissance = simpleDateFormat.format(r.getDateNaissance());
		String sqlcmd = "INSERT INTO Realisateur (prenom, nom, dateNaissance) VALUES ('" + r.getPrenom() + "', '"
				+ r.getNom() + "', '" + dateNaissance + "');";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public static Genre RechercheGenreParSonNom(String nom) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "SELECT * FROM Genre WHERE nom = " + "'" + nom + "';";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();
		Genre genre;
		if (!rs.next()) {
			genre = null;
		} else {
			genre = new Genre();
			genre.setId(rs.getInt("id"));
			genre.setNom(rs.getString("nom"));
		}
		// fermer le dataset
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return genre;

	}

	public static Sujet RechercheSujetParSonNom(String nom) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "SELECT * FROM Sujet WHERE nom = " + "'" + nom + "';";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();
		Sujet sujet;
		if (!rs.next()) {
			sujet = null;
		} else {
			sujet = new Sujet();
			sujet.setId(rs.getInt("id"));
			sujet.setNom(rs.getString("nom"));
		}
		// fermer le dataset
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return sujet;

	}

	// UPDATE
	public static void archiverFilm(int id) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "UPDATE Video SET etat =? where id = ?;";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.setString(1, String.valueOf(EtatVideo.archivé)); // 1er paramètre:
		pstmt.setInt(2, id); // 2ème paramètre:

		int nbLignesAffectees = pstmt.executeUpdate();// exécution

		// fermer le prepared statement
		pstmt.close();
		System.out.println("Mise à jour terminée, " + nbLignesAffectees + " Ligne(s) affectée(s)\n\n");
		;
	}

	// UPDATE
	public static void desarchiverFilm(int id) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "UPDATE Video SET etat =? where id = ?;";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.setString(1, String.valueOf(EtatVideo.normal)); // 1er paramètre:
		pstmt.setInt(2, id); // 2ème paramètre:

		int nbLignesAffectees = pstmt.executeUpdate();// exécution

		// fermer le prepared statement
		pstmt.close();
		System.out.println("Mise à jour terminée, " + nbLignesAffectees + " Ligne(s) affectée(s)\n\n");
		;
	}
}
