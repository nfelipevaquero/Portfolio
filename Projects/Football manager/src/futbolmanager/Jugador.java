package futbolmanager;

import java.util.Objects;

/**
 * Classe que representa un jugador de futbol dins del sistema.
 * Hereta de {@link Persona} i afegeix dorsal, posició i qualitat.
 */
public class Jugador extends Persona {

    /**
     * Llista de posicions possibles permeses per als jugadors.
     */
    public static final String[] POSICIONS_POSSIBLES = {"POR", "DEF", "MIG", "DAV"};

    /**
     * Comptador global de jugadors creats.
     */
    private static int contadorJugadors = 0;

    /**
     * Dorsal que porta el jugador a l'equip.
     */
    private int dorsal;

    /**
     * Posició del jugador al camp (per exemple, "DEF", "MIG").
     */
    private String posicio;

    /**
     * Qualitat numèrica del jugador (habitualment entre 30 i 100).
     */
    private double qualitat;

    /**
     * Constructor principal de Jugador amb totes les dades necessàries.
     *
     * @param nom            nom del jugador
     * @param cognom         cognom del jugador
     * @param dataNaixement  data de naixement en format text
     * @param motivacio      motivació inicial
     * @param sou            sou anual
     * @param dorsal         dorsal del jugador
     * @param posicio        posició al camp
     * @param qualitat       qualitat inicial del jugador
     */
    public Jugador(String nom, String cognom, String dataNaixement,
                   double motivacio, double sou,
                   int dorsal, String posicio, double qualitat) {
        super(nom, cognom, dataNaixement, motivacio, sou);
        this.dorsal = dorsal;
        this.posicio = posicio;
        this.qualitat = qualitat;
        contadorJugadors++;
    }

    /**
     * Constructor per defecte que crea un jugador genèric amb valors per omissió.
     * Útil per a proves o inicialitzacions ràpides.
     */
    public Jugador() {
        super("Desconegut", "Desconegut", "01/01/2000", 50.0, 1000.0);
        this.dorsal = 0;
        this.posicio = "MIG";
        this.qualitat = 50.0;
        contadorJugadors++;
    }

    /**
     * Retorna el nombre total de jugadors creats.
     * (Signatura adaptada a l'enunciat/ús del Main.)
     *
     * @param a paràmetre no utilitzat
     * @param b paràmetre no utilitzat
     * @param c paràmetre no utilitzat
     * @param d paràmetre no utilitzat
     * @param e paràmetre no utilitzat
     * @return comptador global de jugadors
     */
    public int getContadorJugadors(String a, String b, String c, String d, String e) {
        return contadorJugadors;
    }

    /**
     * Retorna el dorsal del jugador.
     *
     * @return dorsal
     */
    public int getDorsal() { return dorsal; }

    /**
     * Actualitza el dorsal del jugador.
     *
     * @param dorsal nou dorsal
     */
    public void setDorsal(int dorsal) { this.dorsal = dorsal; }

    /**
     * Retorna la posició actual del jugador.
     *
     * @return posició al camp
     */
    public String getPosicio() { return posicio; }

    /**
     * Actualitza la posició del jugador.
     *
     * @param posicio nova posició al camp
     */
    public void setPosicio(String posicio) { this.posicio = posicio; }

    /**
     * Retorna la qualitat actual del jugador.
     *
     * @return qualitat
     */
    public double getQualitat() { return qualitat; }

    /**
     * Actualitza la qualitat del jugador.
     *
     * @param qualitat nova qualitat
     */
    public void setQualitat(double qualitat) { this.qualitat = qualitat; }

    /**
     * Sessió d'entrenament específica de jugador.
     * Crida primer {@link Persona#entrenament()} per pujar motivació
     * i després incrementa la qualitat en un valor aleatori (0.1, 0.2 o 0.3).
     */
    @Override
    public void entrenament() {
        super.entrenament();
        double increment = 0.0;
        double rand = Math.random();
        if (rand < 0.33) increment = 0.1;
        else if (rand < 0.66) increment = 0.2;
        else increment = 0.3;
        this.qualitat += increment;
        System.out.printf("Jugador %s ha millorat qualitat: %.1f -> %.1f%n",
                getNom(), qualitat - increment, qualitat);
    }

    /**
     * Intenta canviar la posició del jugador amb una probabilitat del 5%.
     * Si canvia, també incrementa la qualitat en 1 punt i mostra un missatge.
     */
    public void canviDePosicio() {
        if (Math.random() * 100 <= 5) {
            int indexNou = (int)(Math.random() * POSICIONS_POSSIBLES.length);
            String posicioAntiga = this.posicio;
            this.posicio = POSICIONS_POSSIBLES[indexNou];
            this.qualitat += 1.0;
            System.out.printf("Canvi de posició! %s: %s -> %s (qualitat +1 = %.1f)%n",
                    getNom(), posicioAntiga, this.posicio, this.qualitat);
        }
    }

    /**
     * Compara dos jugadors per nom i dorsal.
     *
     * @param o objecte a comparar
     * @return {@code true} si tenen el mateix nom i dorsal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador jugador = (Jugador) o;
        return dorsal == jugador.dorsal && Objects.equals(getNom(), jugador.getNom());
    }

    /**
     * Calcula el codi hash a partir del nom i del dorsal,
     * coherent amb {@link #equals(Object)}.
     *
     * @return valor hash del jugador
     */
    @Override
    public int hashCode() {
        return Objects.hash(getNom(), dorsal);
    }

    /**
     * Retorna una descripció textual del jugador amb les seves dades principals.
     *
     * @return cadena descriptiva del jugador
     */
    @Override
    public String toString() {
        return "Jugador{" + "nom=" + getNom() + ", cognom=" + getCognom() +
                ", dorsal=" + dorsal + ", posicio='" + posicio + '\'' +
                ", qualitat=" + qualitat + ", motivacio=" + getMotivacio() + '}';
    }
}
