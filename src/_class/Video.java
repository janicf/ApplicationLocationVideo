package _class;

import java.util.ArrayList;
import java.util.Date;

import _enum.EtatVideo;
import _enum.TypeVideo;

public class Video {
	private int id;
	private double prix;
	private String titre;
	private TypeVideo type_video;
	private String description;
	private int durre;
	private Date date_sortie;
	private Date date_ajout;
	private EtatVideo etat;
	private boolean gratuit_abonne;
	private ArrayList<Genre> genres;
	private ArrayList<Sujet> sujets;
	private ArrayList<Acteur> acteurs;
	private ArrayList<Realisateur> realisateurs;

	// --- CONSTRUCTEUR ---
	public Video() {
		super();
		this.genres = new ArrayList<Genre>();
		this.sujets = new ArrayList<Sujet>();
		this.acteurs = new ArrayList<Acteur>();
		this.realisateurs = new ArrayList<Realisateur>();
	}

	public Video(double prix, String titre, TypeVideo type_video, String description, int durre, Date date_sortie,
			Date date_ajout, EtatVideo etat, boolean gratuit_abonne) {
		super();
		this.prix = prix;
		this.titre = titre;
		this.type_video = type_video;
		this.description = description;
		this.durre = durre;
		this.date_sortie = date_sortie;
		this.date_ajout = date_ajout;
		this.etat = etat;
		this.gratuit_abonne = gratuit_abonne;
	}

	public Video(int id, double prix, String titre, TypeVideo type_video, String description, int durre,
			Date date_sortie, Date date_ajout, EtatVideo etat, boolean gratuit_abonne) {
		super();
		this.id = id;
		this.prix = prix;
		this.titre = titre;
		this.type_video = type_video;
		this.description = description;
		this.durre = durre;
		this.date_sortie = date_sortie;
		this.date_ajout = date_ajout;
		this.etat = etat;
		this.gratuit_abonne = gratuit_abonne;
	}

	public Video(int id, double prix, String titre, TypeVideo type_video, String description, int durre,
			Date date_sortie, Date date_ajout, EtatVideo etat, boolean gratuit_abonne, ArrayList<Genre> genres,
			ArrayList<Sujet> sujets, ArrayList<Acteur> acteurs, ArrayList<Realisateur> realisateurs) {
		super();
		this.id = id;
		this.prix = prix;
		this.titre = titre;
		this.type_video = type_video;
		this.description = description;
		this.durre = durre;
		this.date_sortie = date_sortie;
		this.date_ajout = date_ajout;
		this.etat = etat;
		this.gratuit_abonne = gratuit_abonne;
		this.genres = genres;
		this.sujets = sujets;
		this.acteurs = acteurs;
		this.realisateurs = realisateurs;
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

	public TypeVideo getType_video() {
		return type_video;
	}

	public void setType_video(TypeVideo type_video) {
		this.type_video = type_video;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDurre() {
		return durre;
	}

	public void setDurre(int durre) {
		this.durre = durre;
	}

	public Date getDate_sortie() {
		return date_sortie;
	}

	public void setDate_sortie(Date date_sortie) {
		this.date_sortie = date_sortie;
	}

	public Date getDate_ajout() {
		return date_ajout;
	}

	public void setDate_ajout(Date date_ajout) {
		this.date_ajout = date_ajout;
	}

	public EtatVideo getEtat() {
		return etat;
	}

	public void setEtat(EtatVideo etat) {
		this.etat = etat;
	}

	public boolean isGratuit_abonne() {
		return gratuit_abonne;
	}

	public void setGratuit_abonne(boolean gratuit_abonne) {
		this.gratuit_abonne = gratuit_abonne;
	}

	public ArrayList<Genre> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
	}

	public ArrayList<Sujet> getSujets() {
		return sujets;
	}

	public void setSujets(ArrayList<Sujet> sujets) {
		this.sujets = sujets;
	}

	public ArrayList<Acteur> getActeurs() {
		return acteurs;
	}

	public void setActeurs(ArrayList<Acteur> acteurs) {
		this.acteurs = acteurs;
	}

	public ArrayList<Realisateur> getRealisateurs() {
		return realisateurs;
	}

	public void setRealisateurs(ArrayList<Realisateur> realisateurs) {
		this.realisateurs = realisateurs;
	}

	@Override
	public String toString() {
		if (etat == EtatVideo.normal) {
			return this.titre + " (" + this.date_sortie + ")";
		} else {
			return this.titre + " (" + this.date_sortie + ")" + "   ---   " + etat;
		}
	}

}
