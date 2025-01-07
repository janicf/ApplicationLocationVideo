package _class;

import java.util.Objects;

public class Genre {
	private int id;
	private String nom;

	// --- CONSTRUCTEUR ---
	public Genre() {
		super();
	}

	public Genre(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	// --- GETTER AND SETTER ---
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
		Genre other = (Genre) obj;
		return Objects.equals(nom, other.nom);
	}

}
