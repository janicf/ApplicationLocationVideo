/**
 * 
 */
package bd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbConnection {
	private static DbConnection instance;
	private Connection connection;
	// chaine de connexion pour sql server
	String nomBd = "ClubVideo";
	private String url = "jdbc:sqlserver://localhost:1433;" // instance par défaut
			+ "databaseName=ClubVideo;" // nom de la BD
			+ "encrypt=true;" // chiffrer la connexion
			+ "trustServerCertificate=true;" // remplacer par "no" quand vous installez un certificat valide sur le
												// serveur SQL
			+ "user=E9;" // authentification avec SQL Server
			+ "password=sql;"; // authentification avec SQL Server

	private DbConnection() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.connection = DriverManager.getConnection(url, null, null);
		} catch (ClassNotFoundException ex) {
			System.out.println("SQL Server JDBC Driver non trouvé: " + ex.getMessage());
		}
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		Connection connection = this.getConnection();
		return connection.prepareStatement(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		Connection connection = this.getConnection();
		return connection.prepareCall(sql);
	}

	public Connection getConnection() {
		return connection;
	}

	public Boolean closeConnection() throws SQLException {
		if (this.connection != null) {
			this.connection.close();
		}
		return true;
	}

	// cette méthode statique garantit l'existence d'une seule instance de la classe
	// DbConnection dans le programme
	// le patron singleton
	public static DbConnection getInstance() throws SQLException {
		if (instance == null || instance.getConnection().isClosed()) {
			instance = new DbConnection();
		}
		return instance;
	}

}
