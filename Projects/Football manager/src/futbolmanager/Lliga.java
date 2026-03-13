package futbolmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe Lliga que emmagatzema la llista d'equips
 * i permet disputar tots els partits i mostrar la classificació.
 */
public class Lliga {

    /**
     * Nom identificatiu de la lliga.
     */
    private String nom;

    /**
     * Nombre d'equips previst inicialment per a la lliga.
     * (Només informatiu, la llista pot tenir-ne més o menys.)
     */
    private int numEquipsPrevist;

    /**
     * Llista d'equips participants en la lliga.
     */
    private List<Equip> equips;

    /**
     * Constructor de la lliga.
     *
     * @param nom              nom de la lliga
     * @param numEquipsPrevist nombre d'equips previst per a la competició
     */
    public Lliga(String nom, int numEquipsPrevist) {
        this.nom = nom;
        this.numEquipsPrevist = numEquipsPrevist;
        this.equips = new ArrayList<>();
    }

    /**
     * Retorna la llista d'equips de la lliga.
     *
     * @return llista d'equips participants
     */
    public List<Equip> getEquips() {
        return equips;
    }

    /**
     * Afegeix un equip a la lliga, evitant duplicats pel seu nom.
     *
     * @param equip equip a afegir
     */
    public void afegirEquip(Equip equip) {
        for (Equip e : equips) {
            if (e.getNom().equalsIgnoreCase(equip.getNom())) {
                System.out.println("⚠️ Equip " + equip.getNom() + " ja existeix a la lliga!");
                return;
            }
        }
        equips.add(equip);
        System.out.println("✅ Equip " + equip.getNom() + " afegit a la lliga.");
    }

    /**
     * Disputa tots els partits de la lliga en format lliga regular (tots contra tots).
     * Reinicia les estadístiques, genera resultats aleatoris ponderats per qualitat
     * i actualitza gols i punts de cada equip.
     */
    public void disputarLliga() {
        System.out.println("\n⚽ DISPUTANT PARTITS DE LA LIGA " + nom.toUpperCase());
        System.out.println("-".repeat(60));

        // Reinici d'estadístiques
        for (Equip e : equips) {
            e.afegirPunts(-e.getPunts());
            e.afegirGolsAFavor(-e.getGolsAFavor());
            e.afegirGolsEnContra(-e.getGolsEnContra());
        }

        // Tots els equips juguen entre ells una vegada
        for (int i = 0; i < equips.size(); i++) {
            for (int j = i + 1; j < equips.size(); j++) {
                Equip equipLocal = equips.get(i);
                Equip equipVisitant = equips.get(j);

                double qualitatLocal = calcularQualitatMitjana(equipLocal);
                double qualitatVisitant = calcularQualitatMitjana(equipVisitant);

                int golsLocal = (int)(Math.random() * 4) + 1;
                int golsVisitant = (int)(Math.random() * 4) + 1;

                if (qualitatLocal > qualitatVisitant + 15) {
                    golsLocal += 1;
                } else if (qualitatVisitant > qualitatLocal + 15) {
                    golsVisitant += 1;
                }

                equipLocal.afegirGolsAFavor(golsLocal);
                equipLocal.afegirGolsEnContra(golsVisitant);
                equipVisitant.afegirGolsAFavor(golsVisitant);
                equipVisitant.afegirGolsEnContra(golsLocal);

                if (golsLocal > golsVisitant) {
                    equipLocal.afegirPunts(3);
                    System.out.printf("%s %d-%d %s [VICTORIA LOCAL]%n",
                            equipLocal.getNom(), golsLocal, golsVisitant, equipVisitant.getNom());
                } else if (golsVisitant > golsLocal) {
                    equipVisitant.afegirPunts(3);
                    System.out.printf("%s %d-%d %s [VICTORIA VISITANT]%n",
                            equipLocal.getNom(), golsLocal, golsVisitant, equipVisitant.getNom());
                } else {
                    equipLocal.afegirPunts(1);
                    equipVisitant.afegirPunts(1);
                    System.out.printf("%s %d-%d %s [EMPAT]%n",
                            equipLocal.getNom(), golsLocal, golsVisitant, equipVisitant.getNom());
                }
            }
        }
        System.out.println("-".repeat(60));
        System.out.println("Lliga " + nom + " finalitzada!");
    }

    /**
     * Calcula una qualitat mitjana d'un equip, tenint en compte entrenador i jugadors.
     *
     * @param equip equip del qual es vol calcular la qualitat
     * @return valor de qualitat mitjana (o 50.0 si no hi ha dades)
     */
    private double calcularQualitatMitjana(Equip equip) {
        double totalQualitat = 0;
        int count = 0;

        if (equip.getEntrenador() != null) {
            totalQualitat += equip.getEntrenador().getMotivacio() * 3;
            count++;
        }

        for (Jugador j : equip.getJugadors()) {
            totalQualitat += j.getQualitat();
            count++;
        }

        return count > 0 ? totalQualitat / count : 50.0;
    }

    /**
     * Retorna l'equip amb més gols a favor en la lliga.
     *
     * @return equip amb més gols a favor o {@code null} si no hi ha equips
     */
    public Equip getEquipMesGolsAFavor() {
        Equip millor = null;
        int maxGols = -1;

        for (Equip e : equips) {
            if (e.getGolsAFavor() > maxGols) {
                maxGols = e.getGolsAFavor();
                millor = e;
            }
        }
        return millor;
    }

    /**
     * Retorna l'equip amb més gols en contra en la lliga.
     *
     * @return equip amb més gols en contra o {@code null} si no hi ha equips
     */
    public Equip getEquipMesGolsEnContra() {
        Equip pitjor = null;
        int maxGolsEnContra = -1;

        for (Equip e : equips) {
            if (e.getGolsEnContra() > maxGolsEnContra) {
                maxGolsEnContra = e.getGolsEnContra();
                pitjor = e;
            }
        }
        return pitjor;
    }

    /**
     * Mostra per pantalla la classificació de la lliga, ordenada per:
     * <ul>
     *     <li>Punts (descendent)</li>
     *     <li>Diferència de gols (descendent)</li>
     * </ul>
     * També mostra els equips amb més gols a favor i en contra.
     */
    public void mostrarClassificacio() {

        List<Equip> equipsClassificacio = new ArrayList<>(equips);

        Collections.sort(equipsClassificacio, new Comparator<Equip>() {
            @Override
            public int compare(Equip e1, Equip e2) {

                int cmpPunts = Integer.compare(e2.getPunts(), e1.getPunts());
                if (cmpPunts != 0) return cmpPunts;

                int diff1 = e1.getGolsAFavor() - e1.getGolsEnContra();
                int diff2 = e2.getGolsAFavor() - e2.getGolsEnContra();
                return Integer.compare(diff2, diff1);
            }
        });

        System.out.println("\n CLASIFICACIÓ " + nom.toUpperCase());
        System.out.println("=".repeat(90));
        System.out.printf("%-4s | %-22s | %-4s | %-4s | %-4s | %-5s | %-12s%n",
                "POS", "EQUIP", "PTS", "GF", "GC", "DG", "PRESIDENT");
        System.out.println("-".repeat(90));

        for (int i = 0; i < equipsClassificacio.size(); i++) {
            Equip e = equipsClassificacio.get(i);
            int posicio = i + 1;
            int diffGols = e.getGolsAFavor() - e.getGolsEnContra();
            String president = e.getPresident() != null ? e.getPresident() : "-";

            System.out.printf("%-4d | %-22s | %-4d | %-4d | %-4d | %3d  | %-12s%n",
                    posicio, e.getNom(), e.getPunts(),
                    e.getGolsAFavor(), e.getGolsEnContra(),
                    diffGols, president);
        }
        System.out.println("=".repeat(90));

        Equip maxGF = getEquipMesGolsAFavor();
        Equip maxGC = getEquipMesGolsEnContra();
        System.out.printf("%n MÉS GOLS A FAVOR: %s (%d gols)%n",
                maxGF != null ? maxGF.getNom() : "Cap",
                maxGF != null ? maxGF.getGolsAFavor() : 0);
        System.out.printf("MÉS GOLS EN CONTRA: %s (%d gols)%n",
                maxGC != null ? maxGC.getNom() : "Cap",
                maxGC != null ? maxGC.getGolsEnContra() : 0);
    }
}
