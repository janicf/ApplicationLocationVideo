package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import _class.Evenement;
import _enum.TypeEvent;
import bd.DbConnection;

public class EvenementDAO {

	// Fonction qui retourne un array de tous les evenements
	public static ArrayList<Evenement> TousLesEvenements() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "Select * from Evenement;";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Evenement> events = new ArrayList<>();
		Evenement event_temp;
		while (rs.next()) { // changer en utilisant le constructeur
			event_temp = new Evenement();
			event_temp.setId(rs.getInt("id"));
			event_temp.setPrix(rs.getDouble("prix"));
			event_temp.setTitre(rs.getString("titre"));
			event_temp.setType_event(TypeEvent.valueOf(rs.getString("type_event")));
			event_temp.setLieu(rs.getString("Lieu"));
			event_temp.setDateDebut(rs.getTimestamp("DateDebut").toLocalDateTime());
			event_temp.setDateFin(rs.getTimestamp("DateFin").toLocalDateTime());
			// rs.getString("type_event")
			events.add(event_temp);
		}

		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return events;

	}

	// Fonction qui permet chercher Evenement
	public static ArrayList<Evenement> RecherherEvenementParObj(String filtreSQL) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "Select * from Evenement WHERE " + filtreSQL + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
//		pstmt.setObject(1, obj);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Evenement> events = new ArrayList<>();
		Evenement event_temp;
		while (rs.next()) { // changer en utilisant le constructeur
			event_temp = new Evenement();
			event_temp.setId(rs.getInt("id"));
			event_temp.setPrix(rs.getDouble("prix"));
			event_temp.setTitre(rs.getString("titre"));
			event_temp.setType_event(TypeEvent.valueOf(rs.getString("type_event")));
			event_temp.setLieu(rs.getString("Lieu"));
			event_temp.setDateDebut(rs.getTimestamp("DateDebut").toLocalDateTime());
			event_temp.setDateFin(rs.getTimestamp("DateFin").toLocalDateTime());
			// rs.getString("type_event")
			events.add(event_temp);
		}
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return events;

	}

	public static void nouvelleParticipation(int idClient, int idEvent) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "INSERT INTO ClientEvenement (idClient, idEvenement) VALUES" + "(" + idClient + " ," + idEvent
				+ ");";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.executeUpdate();
		pstmt.close();

	}

	public static Boolean VerificationParticipationClient(int idClient, int idEvent) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT idEvenement FROM ClientEvenement WHERE idClient = " + idClient + " AND idEvenement = "
				+ idEvent + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		Boolean isValide = true;

		// rs vide => participation evenement invalide
		if (!rs.next()) {
			isValide = false;
		}

		rs.close();
		pstmt.close();
		return isValide;
	}

	public static ArrayList<Evenement> TousLesEvents() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT * FROM Evenement;";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Evenement> events = new ArrayList<Evenement>();
		Evenement event_courant;

		while (rs.next()) {
			event_courant = new Evenement();
			event_courant.setId(rs.getInt("id"));
			event_courant.setPrix(rs.getDouble("prix"));
			event_courant.setTitre(rs.getString("titre"));
			TypeEvent typeEventEnum = TypeEvent.valueOf(rs.getString("type_event")); // conversion du string vers l'Enum
																						// TypeVideo
			event_courant.setType_event(typeEventEnum);
			event_courant.setLieu(rs.getString("Lieu"));
			event_courant.setDescription(rs.getString("description_event"));
			event_courant.setDateDebut(rs.getTimestamp("DateDebut").toLocalDateTime()); // cast sqlDateFormat to
																						// localDateTime
			event_courant.setDateFin(rs.getTimestamp("DateFin").toLocalDateTime());

			events.add(event_courant);
		}

		rs.close();
		pstmt.close();
		return events;
	}

	public static ArrayList<Evenement> EventClient(int idClient) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "SELECT E.id, E.prix, E.titre, E.type_event, E.Lieu, "
				+ "E.description_event, E.DateDebut, E.DateFin " + "FROM Evenement E LEFT JOIN ClientEvenement CE "
				+ "on E.id = CE.idEvenement WHERE CE.idClient = " + idClient + ";";

		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Evenement> events = new ArrayList<Evenement>();
		Evenement event_courant;

		while (rs.next()) {
			event_courant = new Evenement();
			event_courant.setId(rs.getInt("id"));
			event_courant.setPrix(rs.getDouble("prix"));
			event_courant.setTitre(rs.getString("titre"));
			TypeEvent typeEventEnum = TypeEvent.valueOf(rs.getString("type_event")); // conversion du string vers l'Enum
																						// TypeVideo
			event_courant.setType_event(typeEventEnum);
			event_courant.setLieu(rs.getString("Lieu"));
			event_courant.setDescription(rs.getString("description_event"));
			event_courant.setDateDebut(rs.getTimestamp("DateDebut").toLocalDateTime()); // cast sqlDateFormat to
																						// localDateTime
			event_courant.setDateFin(rs.getTimestamp("DateFin").toLocalDateTime());

			events.add(event_courant);
		}

		rs.close();
		pstmt.close();
		return events;
	}

	public static void AjouterEvenement(Evenement evenement) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();

		String sqlcmd = "INSERT INTO Evenement (titre, prix, type_event, description_event, lieu, DateDebut, DateFin) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd)) {
			pstmt.setString(1, evenement.getTitre());
			pstmt.setDouble(2, evenement.getPrix());
			pstmt.setString(3, evenement.getType_event().toString()); // Convertir l'enum en string
			pstmt.setString(4, evenement.getDescription());
			pstmt.setString(5, evenement.getLieu());
			pstmt.setTimestamp(6, Timestamp.valueOf(evenement.getDateDebut()));
			pstmt.setTimestamp(7, Timestamp.valueOf(evenement.getDateFin()));

			pstmt.executeUpdate();
		}
	}
}
