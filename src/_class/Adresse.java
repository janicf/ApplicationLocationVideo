package _class;

public class Adresse {

	private int id;
	private String appartement;
	private int no_civic;
	private String rue;
	private String ville;
	private String pays;
	private String code_postal;
	private String province_etat;

	// --- CONSTRUCTEUR ---
	public Adresse() {
		super();
	}

	public Adresse(int id, String appartement, int no_civic, String rue, String ville, String pays, String code_postal,
			String province_etat) {
		super();
		this.id = id;
		this.appartement = appartement;
		this.no_civic = no_civic;
		this.rue = rue;
		this.ville = ville;
		this.pays = pays;
		this.code_postal = code_postal;
		this.province_etat = province_etat;
	}

	// --- GETTER AND SETTER ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppartement() {
		return appartement;
	}

	public void setAppartement(String appartement) {
		this.appartement = appartement;
	}

	public int getNo_civic() {
		return no_civic;
	}

	public void setNo_civic(int no_civic) {
		this.no_civic = no_civic;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getProvince_etat() {
		return province_etat;
	}

	public void setProvince_etat(String province_etat) {
		this.province_etat = province_etat;
	}

}
