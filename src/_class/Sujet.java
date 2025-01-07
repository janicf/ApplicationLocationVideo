package _class;

import java.util.Objects;

public class Sujet {
	private int id;
	private String nom;

	// --- CONSTRUCTEUR ---
	public Sujet() {
		super();
	}

	// --- GETTER AND SETTER ---
	public Sujet(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return this.nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom);
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
		Sujet other = (Sujet) obj;
		return Objects.equals(nom, other.nom);
	}

}
