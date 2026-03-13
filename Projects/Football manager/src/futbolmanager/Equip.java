package futbolmanager;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa un equip de futbol.
 * Conté informació bàsica del club, el seu entrenador, la plantilla de jugadors
 * i les estadístiques de la lliga.
 */
public class Equip {

    /**
     * Nom oficial de l'equip.
     */
    private String nom;

    /**
     * Any de fundació del club.
     */
    private int anyFundacio;

    /**
     * Ciutat a la qual pertany l'equip.
     */
    private String ciutat;

    /**
     * Nom de l'estadi on juga com a local.
     */
    private String estadi;

    /**
     * Nom del president o presidenta del club.
     */
    private String president;

    /**
     * Entrenador assignat actualment a l'equip.
     */
    private Entrenador entrenador;

    /**
     * Llista de jugadors que formen part de la plantilla.
     */
    private List<Jugador> jugadors;

    /**
     * Punts acumulats per l'equip a la lliga.
     */
    private int punts;

    /**
     * Gols marcats (a favor) per l'equip.
     */
    private int golsAFavor;

    /**
     * Gols rebuts (en contra) per l'equip.
     */
    private int golsEnContra;

    /**
     * Constructor bàsic d'equip amb nom, any de fundació i ciutat.
     * Inicialitza la llista de jugadors i les estadístiques a zero.
     *
     * @param nom         nom de l'equip
     * @param anyFundacio any de fundació
     * @param ciutat      ciutat de l'equip
     */
    public Equip(String nom, int anyFundacio, String ciutat) {
        this.nom = nom;
        this.anyFundacio = anyFundacio;
        this.ciutat = ciutat;
        this.jugadors = new ArrayList<>();
        this.punts = 0;
        this.golsAFavor = 0;
        this.golsEnContra = 0;
    }

    /**
     * Constructor complet amb estadi i president, a més de la informació bàsica.
     *
     * @param nom         nom de l'equip
     * @param anyFundacio any de fundació
     * @param ciutat      ciutat de l'equip
     * @param estadi      nom de l'estadi
     * @param president   nom del president/a
     */
    public Equip(String nom, int anyFundacio, String ciutat, String estadi, String president) {
        this(nom, anyFundacio, ciutat);
        this.estadi = estadi;
        this.president = president;
    }

    /**
     * Retorna el nom de l'equip.
     *
     * @return nom de l'equip
     */
    public String getNom() { return nom; }

    /**
     * Retorna l'any de fundació de l'equip.
     *
     * @return any de fundació
     */
    public int getAnyFundacio() { return anyFundacio; }

    /**
     * Retorna la ciutat de l'equip.
     *
     * @return ciutat
     */
    public String getCiutat() { return ciutat; }

    /**
     * Retorna el nom de l'estadi.
     *
     * @return estadi o {@code null} si no està definit
     */
    public String getEstadi() { return estadi; }

    /**
     * Retorna el nom del president/a del club.
     *
     * @return president o {@code null} si no està definit
     */
    public String getPresident() { return president; }

    /**
     * Retorna l'entrenador actual de l'equip.
     *
     * @return objecte {@link Entrenador} o {@code null} si no n'hi ha
     */
    public Entrenador getEntrenador() { return entrenador; }

    /**
     * Retorna una còpia de la llista de jugadors de l'equip.
     *
     * @return llista de jugadors
     */
    public List<Jugador> getJugadors() { return new ArrayList<>(jugadors); }

    /**
     * Retorna els punts acumulats per l'equip.
     *
     * @return punts a la classificació
     */
    public int getPunts() { return punts; }

    /**
     * Retorna el total de gols a favor.
     *
     * @return gols marcats
     */
    public int getGolsAFavor() { return golsAFavor; }

    /**
     * Retorna el total de gols en contra.
     *
     * @return gols rebuts
     */
    public int getGolsEnContra() { return golsEnContra; }

    /**
     * Defineix el nom de l'estadi de l'equip.
     *
     * @param estadi nou nom d'estadi
     */
    public void setEstadi(String estadi) { this.estadi = estadi; }

    /**
     * Defineix el nom del president/a del club.
     *
     * @param president nou president
     */
    public void setPresident(String president) { this.president = president; }

    /**
     * Assigna un entrenador a l'equip.
     *
     * @param entrenador nou entrenador
     */
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }

    /**
     * Suma punts a la classificació de l'equip.
     *
     * @param punts punts a afegir (pot ser negatiu si es vol restar)
     */
    public void afegirPunts(int punts) { this.punts += punts; }

    /**
     * Suma gols al total de gols a favor.
     *
     * @param gols gols marcats a afegir
     */
    public void afegirGolsAFavor(int gols) { this.golsAFavor += gols; }

    /**
     * Suma gols al total de gols en contra.
     *
     * @param gols gols rebuts a afegir
     */
    public void afegirGolsEnContra(int gols) { this.golsEnContra += gols; }

    /**
     * Afegeix un jugador a la plantilla, comprovant que el dorsal no estigui duplicat.
     *
     * @param jugador jugador a afegir
     */
    public void afegirJugador(Jugador jugador) {

        for (Jugador j : jugadors) {
            if (j.getDorsal() == jugador.getDorsal()) {
                System.out.println("❌ Dorsal " + jugador.getDorsal() + " ja ocupat!");
                return;
            }
        }
        jugadors.add(jugador);
        System.out.println("✅ " + jugador.getNom() + " afegit a l'equip!");
    }

    /**
     * Elimina un jugador de la plantilla.
     *
     * @param jugador jugador a eliminar
     */
    public void eliminarJugador(Jugador jugador) {
        jugadors.remove(jugador);
        System.out.println("✅ " + jugador.getNom() + " eliminat de l'equip!");
    }

    /**
     * Retorna el nombre actual de jugadors a la plantilla.
     *
     * @return mida de la llista de jugadors
     */
    public int getNumJugadors() { return jugadors.size(); }

    /**
     * Retorna una descripció textual de l'equip, incloent dades generals,
     * entrenador, nombre de jugadors i estadístiques bàsiques.
     *
     * @return cadena descriptiva de l'equip
     */
    @Override
    public String toString() {
        return String.format("Equip: %s (%d, %s)%n" +
                        "Estadi: %s%n" +
                        "President: %s%n" +
                        "Entrenador: %s%n" +
                        "Jugadors: %d%n" +
                        "Estadístiques: %d punts, %d GF, %d GC",
                nom, anyFundacio, ciutat,
                estadi != null ? estadi : "Sense estadi",
                president != null ? president : "Sense president",
                entrenador != null ? entrenador.getNom() : "Sense entrenador",
                jugadors.size(), punts, golsAFavor, golsEnContra);
    }
}
