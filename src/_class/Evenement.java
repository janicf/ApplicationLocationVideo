package _class;

import java.time.LocalDateTime;
import java.util.ArrayList;

import _enum.TypeEvent;

public class Evenement {
	private int id;
	private double prix;
	private String titre;
	private TypeEvent type_event;
	private String description;
	private String lieu;
	private LocalDateTime DateDebut;
	private LocalDateTime DateFin;
	private ArrayList<Client> participant;

	// --- CONSTRUCTEUR ---
	public Evenement() {
		super();
		this.participant = new ArrayList<Client>();
	}

	public Evenement(int id, double prix, String titre, TypeEvent type_event, String description, String lieu,
			LocalDateTime dateDebut, LocalDateTime dateFin) {
		super();
		this.id = id;
		this.prix = prix;
		this.titre = titre;
		this.type_event = type_event;
		this.description = description;
		this.lieu = lieu;
		DateDebut = dateDebut;
		DateFin = dateFin;
	}

	public Evenement(double prix, String titre, TypeEvent type_event, String description, String lieu,
			LocalDateTime dateDebut, LocalDateTime dateFin) {
		super();
		this.prix = prix;
		this.titre = titre;
		this.type_event = type_event;
		this.description = description;
		this.lieu = lieu;
		DateDebut = dateDebut;
		DateFin = dateFin;
	}

	// --- GETTER AND SETTER ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public TypeEvent getType_event() {
		return type_event;
	}

	public void setType_event(TypeEvent type_event) {
		this.type_event = type_event;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public LocalDateTime getDateDebut() {
		return DateDebut;
	}

	public void setDateDebut(LocalDateTime date) {
		DateDebut = date;
	}

	public LocalDateTime getDateFin() {
		return DateFin;
	}

	public void setDateFin(LocalDateTime dateFin) {
		DateFin = dateFin;
	}

	public ArrayList<Client> getParticipant() {
		return participant;
	}

	public void setParticipant(ArrayList<Client> participant) {
		this.participant = participant;
	}

	@Override
	public String toString() {
		return titre + " (" + type_event + ") --- " + DateDebut;
	}

}
