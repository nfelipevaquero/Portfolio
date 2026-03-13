package futbolmanager;

/**
 * Classe que representa un entrenador dins del sistema.
 * Hereta de {@link Persona} i afegeix informació esportiva com tornejos guanyats
 * i si és o no seleccionador nacional.
 */
public class Entrenador extends Persona {

    /**
     * Nombre total de tornejos guanyats per l'entrenador.
     */
    private int tornejosGuanyats;

    /**
     * Indica si l'entrenador és seleccionador/a nacional.
     */
    private boolean seleccionadorNacional;

    /**
     * Constructor principal de l'entrenador amb totes les dades rellevants.
     *
     * @param nom                   nom de l'entrenador
     * @param cognom                cognom de l'entrenador
     * @param dataNaixement         data de naixement en format text
     * @param motivacio             motivació inicial
     * @param sou                   sou anual
     * @param tornejosGuanyats      nombre de tornejos guanyats
     * @param seleccionadorNacional {@code true} si és seleccionador nacional, altrament {@code false}
     */
    public Entrenador(String nom, String cognom, String dataNaixement,
                      double motivacio, double sou,
                      int tornejosGuanyats, boolean seleccionadorNacional) {
        super(nom, cognom, dataNaixement, motivacio, sou);
        this.tornejosGuanyats = tornejosGuanyats;
        this.seleccionadorNacional = seleccionadorNacional;
    }

    /**
     * Constructor per defecte que crea un entrenador genèric amb valors per omissió.
     */
    public Entrenador() {
        super("Desconegut", "Desconegut", "01/01/2000", 50.0, 1000.0);
        this.tornejosGuanyats = 0;
        this.seleccionadorNacional = false;
    }

    /**
     * Retorna el nombre de tornejos guanyats.
     *
     * @return tornejos guanyats
     */
    public int getTornejosGuanyats() { return tornejosGuanyats; }

    /**
     * Actualitza el nombre de tornejos guanyats.
     *
     * @param tornejosGuanyats nou total de tornejos guanyats
     */
    public void setTornejosGuanyats(int tornejosGuanyats) { this.tornejosGuanyats = tornejosGuanyats; }

    /**
     * Indica si l'entrenador és seleccionador nacional.
     *
     * @return {@code true} si és seleccionador nacional, {@code false} altrament
     */
    public boolean isSeleccionadorNacional() { return seleccionadorNacional; }

    /**
     * Defineix si l'entrenador és seleccionador nacional.
     *
     * @param seleccionadorNacional nou valor del camp seleccionador nacional
     */
    public void setSeleccionadorNacional(boolean seleccionadorNacional) { this.seleccionadorNacional = seleccionadorNacional; }

    /**
     * Sessió d'entrenament específica per a l'entrenador.
     * No crida al mètode de la classe pare i ajusta directament la motivació:
     * +0.3 si és seleccionador nacional, +0.15 si no ho és.
     */
    @Override
    public void entrenament() {
        if (seleccionadorNacional) {
            setMotivacio(getMotivacio() + 0.3);
        } else {
            setMotivacio(getMotivacio() + 0.15);
        }
    }

    /**
     * Incrementa el sou actual de l'entrenador en un 0.5%.
     */
    public void incrementarSou() {
        setSou(getSou() * 1.005);
    }

    /**
     * Retorna una representació textual de l'entrenador amb les seves dades principals.
     *
     * @return cadena descriptiva de l'entrenador
     */
    @Override
    public String toString() {
        return "Entrenador{" + "nom=" + getNom() + ", cognom=" + getCognom() +
                ", tornejosGuanyats=" + tornejosGuanyats +
                ", seleccionadorNacional=" + seleccionadorNacional +
                ", motivacio=" + getMotivacio() + ", sou=" + getSou() + '}';
    }
}
