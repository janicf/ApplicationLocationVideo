package _class;

import java.util.ArrayList;
import java.util.Date;

public class Client {

	private int id;
	private String courriel;
	private String mot_de_passe;
	private String prenom;
	private String nom;
	private Abonnement abonnement;
	private Date dateNaissance;
	private ArrayList<Carte_credit> cartes;
	private ArrayList<Adresse> adresses;

	// ---CONSTRUCTEUR---
	public Client() {
		super();
		cartes = new ArrayList<Carte_credit>();
		adresses = new ArrayList<Adresse>();
	}

	public Client(int id, String courriel, String mot_de_passe, String prenom, String nom, Abonnement abonnement,
			Date dateNaissance) {
		super();
		this.id = id;
		this.courriel = courriel;
		this.mot_de_passe = mot_de_passe;
		this.prenom = prenom;
		this.nom = nom;
		this.abonnement = abonnement;
		this.dateNaissance = dateNaissance;
	}

	// GETTER AND SETTER
	public String getCourriel() {
		return courriel;
	}

	public void setCourriel(String courriel) {
		this.courriel = courriel;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	@Override
	public String toString() {
		return "id=" + id + ", courriel=" + courriel + ", mot_de_passe=" + mot_de_passe + ", prenom=" + prenom
				+ ", nom=" + nom + ", dateNaissance=" + dateNaissance;
	}

	public Abonnement getAbonnement() {
		return abonnement;
	}

	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}

	public ArrayList<Carte_credit> getCartes() {
		return cartes;
	}

	public void setCartes(ArrayList<Carte_credit> cartes) {
		this.cartes = cartes;
	}

	public ArrayList<Adresse> getAdresses() {
		return adresses;
	}

	public void setAdresses(ArrayList<Adresse> adresses) {
		this.adresses = adresses;
	}

}
