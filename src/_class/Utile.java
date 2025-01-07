package _class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utile {
	/**
	 * Cette fonction retourne un string contenant tous les conditions WHERE d'une
	 * requête SQL à partir d'un arraylist. Chaque item de l'ArrayList est un
	 * tableau à deux dimension de String {key, value}.
	 * 
	 * @param filtre
	 * @return String requeteConditionWhere
	 */
	public static String CreationConditionWhereDuFiltre(ArrayList<String[]> filtre) {
		String requeteConditionWhere = "";
		for (int i = 0; i < filtre.size(); i++) {
			String key = filtre.get(i)[0];
			String value = filtre.get(i)[1];
			requeteConditionWhere += key + " = '" + value + "'";
			if (i != filtre.size() - 1) {
				requeteConditionWhere += " AND ";
			}
		}
		return requeteConditionWhere;
	}

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
}
