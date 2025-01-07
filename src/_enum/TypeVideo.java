package _enum;

public enum TypeVideo {
	FILM("film"), SERIE("serie"), VIDEO_AMATEUR("vidéo amateur"), AUTRE("autre");

	private final String type;

	TypeVideo(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	// Méthode pour obtenir le texte associé à chaque valeur de l'enum
	@Override
	public String toString() {
		return type;
	}

	// Méthode pour vérifier si le type vidéo correspond à la condition spécifiée
	public static boolean matchesCondition(String type) {
		for (TypeVideo value : TypeVideo.values()) {
			if (value.getType().equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}

	// Méthode pour obtenir l'objet enum à partir d'une chaîne de texte
	public static TypeVideo fromString(String text) {
		for (TypeVideo value : TypeVideo.values()) {
			if (value.getType().equalsIgnoreCase(text)) {
				return value;
			}
		}
		return null; // ou lancez une exception si la chaîne n'est pas valide
	}
}
