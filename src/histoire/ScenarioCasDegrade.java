package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois gaul = new Gaulois("gaul", 100);
		etal.libererEtal();
		try {
			etal.acheterProduit(5, gaul);
		} catch (IllegalArgumentException e) {
			System.out.println("Erreur du a un étal non occupé confirmé");
		}
		etal.occuperEtal(gaul, "a", 5);
		try {
			etal.acheterProduit(-1, gaul);
		} catch (IllegalArgumentException e) {
			System.out.println("Erreur du a un nombre négatif confirmé");
		}
		
		etal.acheterProduit(5, null);
		
		
		System.out.println("Fin du test");
	}
}
