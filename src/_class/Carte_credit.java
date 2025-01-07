package _class;

import java.math.BigInteger;
import java.sql.Date;

import _enum.TypeCarte;

public class Carte_credit {
	private int id;
	private BigInteger no_carte;
	private TypeCarte type_Carte;
	private Date expiration;
	private int cvv;

	// --- CONSTRUCTEUR ---
	public Carte_credit() {
		super();
	}

	public Carte_credit(int id, BigInteger no_carte, TypeCarte type_Carte, Date expiration, int cvv) {
		super();
		this.id = id;
		this.no_carte = no_carte;
		this.type_Carte = type_Carte;
		this.expiration = expiration;
		this.cvv = cvv;
	}

	// --- GETTER AND SETTER ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getNo_carte() {
		return no_carte;
	}

	public void setNo_carte(BigInteger no_carte) {
		this.no_carte = no_carte;
	}

	public TypeCarte getType_Carte() {
		return type_Carte;
	}

	public void setType_Carte(TypeCarte type_Carte) {
		this.type_Carte = type_Carte;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

}
