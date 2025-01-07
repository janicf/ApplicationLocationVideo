package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import _class.Client;
import bd.DbConnection;

public class ClientDAO {

	// Fonction qui retourne un array de tous les clients
	public static ArrayList<Client> TousLesClients() throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "SELECT * FROM Client;";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Client> clients = new ArrayList<>();
		Client client_temps;
		while (rs.next()) { // changer en utilisant le constructeur
			client_temps = new Client();
			client_temps.setId(rs.getInt("id"));
			client_temps.setCourriel(rs.getString("courriel"));
			client_temps.setMot_de_passe(rs.getString("mot_de_passe"));
			client_temps.setPrenom(rs.getString("prenom"));// erreur dans la base de donner
			client_temps.setNom(rs.getString("nom"));
			client_temps.setDateNaissance(rs.getDate("dateNaissance")); // erreur dans la base de donner
			clients.add(client_temps);
		}
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return clients;

	}

	// Fonction qui permet chercher un client par id et le retourne
	public static ArrayList<Client> RecherherClientParObj(String filtreSQL) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "SELECT * FROM Client C LEFT JOIN Abonnement A on C.id = A.idClient" + " WHERE " + filtreSQL
				+ ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
//		pstmt.setObject(1, obj);
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Client> clients = new ArrayList<>();
		Client client_temps;
		while (rs.next()) { // changer en utilisant le constructeur
			client_temps = new Client();
			client_temps.setId(rs.getInt("id"));
			client_temps.setCourriel(rs.getString("courriel"));
			client_temps.setMot_de_passe(rs.getString("mot_de_passe"));
			client_temps.setPrenom(rs.getString("prenom"));// erreur dans la base de donner
			client_temps.setNom(rs.getString("nom"));
			client_temps.setDateNaissance(rs.getDate("dateNaissance")); // erreur dans la base de donner
			clients.add(client_temps);
		}
		rs.close();
		// fermer le prepared statement
		pstmt.close();
		return clients;

	}

	// Fonction pour vérifier la connexion
	public static Object[] VerifierConnexion(String courriel, String motDePasse) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "SELECT * FROM dbo.VerrifConnexion(?, ?)";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		pstmt.setString(1, courriel);
		pstmt.setString(2, motDePasse);
		ResultSet rs = pstmt.executeQuery();

		Object[] resultat = null;
		if (rs.next()) {
			int id = rs.getInt("id");
			String typeUtilisateur = rs.getString("type_utilisateur");
			resultat = new Object[] { id, typeUtilisateur };
		}
		rs.close();
		pstmt.close();
		return resultat;
	}

	// Fonction qui retourne le prenom et nom du client selon son idClient
	public static String getNomClient(int idClient) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "SELECT prenom, nom FROM Client WHERE id = " + idClient + ";";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		String nomClient;

		if (!rs.next()) {
			nomClient = "";
		} else {
			nomClient = rs.getString("prenom");
			nomClient += " " + rs.getString("nom");
		}

		rs.close();
		pstmt.close();
		return nomClient;

	}

	public static boolean courrielUnique(String courriel) throws SQLException {
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "SELECT COUNT(courriel) AS count FROM Client WHERE courriel = '" + courriel + "';";
		PreparedStatement pstmt = dbConnection.prepareStatement(sqlcmd);
		ResultSet rs = pstmt.executeQuery();

		rs.next();
		int count = rs.getInt("count");
		rs.close();
		pstmt.close();

		if (count == 0) {
			return true;
		}
		return false;
	}

	public static void nouveauClient(String prenom, String nom, String dateNaissance, String mdp, String courriel)
			throws SQLException {
		// TODO Auto-generated method stub
		DbConnection dbConnection = DbConnection.getInstance();
		String sqlcmd = "{CALL dbo.insertionClient (?, ?, ?, ?, ?)};";
		CallableStatement cs = dbConnection.prepareCall(sqlcmd);
		cs.setString(1, prenom);
		cs.setString(2, nom);
		cs.setString(3, dateNaissance);
		cs.setString(4, mdp);
		cs.setString(5, courriel);

		cs.execute();
		cs.close();
	}
}
