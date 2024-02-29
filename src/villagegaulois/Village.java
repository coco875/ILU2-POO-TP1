package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsValide = marche.trouverEtals(produit);
		if (etalsValide.length == 0) {
			return "Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n";
		} else if (etalsValide.length == 1) {
			return "Seul le vendeur " + etalsValide[0].getVendeur().getNom() + " propose des " + produit
					+ " au marché.\n";
		}
		StringBuilder out = new StringBuilder("Les vendeurs qui proposent des " + produit + " sont :\n");
		for (int i = 0; i < etalsValide.length; i++) {
			out.append("- " + etalsValide[i].getVendeur().getNom()+"\n");
		}
		return out.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int etalFree = marche.trouverEtalLibre();
		marche.utiliserEtal(etalFree, vendeur, produit, nbProduit);
		return vendeur.getNom() + "cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n" + "Le vendeur "
				+ vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (etalFree+1) + ".\n";
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		return etal.libererEtal();
	}
	
	public String afficherMarche() {
		return "Le marché du village "+nom+" possède plusieurs étals :\n"+marche.afficherMarche();
	}

	class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
			int nbEtalsProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsProduit++;
				}
			}
			Etal[] etalsProduit = new Etal[nbEtalsProduit];
			int index = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsProduit[index++] = etals[i];
				}
			}
			return etalsProduit;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder out = new StringBuilder("");
			int emptyEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					out.append(etals[i].afficherEtal());
				} else {
					emptyEtals++;
				}
			}
			out.append("Il reste " + emptyEtals + " étals non utilisés dans le marché.\n");
			return out.toString();
		}
	}
}