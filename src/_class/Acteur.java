package _class;

import java.util.Date;
import java.util.Objects;

public class Acteur {

	private int id;
	private String prenom;
	private String nom;
	private Date dateNaissance;

	// --- CONSTRUCTEUR ---
	public Acteur() {
		super();
	}

	public Acteur(String prenom, String nom, Date dateNaissance) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.dateNaissance = dateNaissance;
	}

	public Acteur(int id, String prenom, String nom, Date dateNaissance) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.dateNaissance = dateNaissance;
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

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	@Override
	public String toString() {
		return this.prenom + " " + this.nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateNaissance, nom, prenom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Acteur other = (Acteur) obj;
		return Objects.equals(dateNaissance, other.dateNaissance) && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom);
	}

}
