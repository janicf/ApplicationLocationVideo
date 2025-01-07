package _class;

import java.util.Date;

public class Abonnement {
	private boolean actif;
	private Date dateDebut;
	private Date dateFin;

// --- CONSTRUCTEUR ---
	public Abonnement() {
		super();
	}

	public Abonnement(boolean actif, Date dateDebut, Date dateFin) {
		super();
		this.actif = actif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	// --- GETTER AND SETTER ---
	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

}
