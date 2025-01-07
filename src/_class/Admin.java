package _class;

public class Admin {

	private int id;
	private String prenom;
	private String nom;
	private String mot_de_passe;
	private String courriel;

	// --- CONSTRUCTEUR ---
	public Admin() {
		super();
	}

	public Admin(int id, String prenom, String nom, String mot_de_passe, String courriel) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.mot_de_passe = mot_de_passe;
		this.courriel = courriel;
	}

	// --- GETTER AND SETTER ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	public String getCourriel() {
		return courriel;
	}

	public void setCourriel(String courriel) {
		this.courriel = courriel;
	}

}
