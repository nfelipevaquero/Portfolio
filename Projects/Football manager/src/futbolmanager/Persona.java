package futbolmanager;

/**
 * Classe abstracta que representa una persona dins del sistema (jugador o entrenador).
 * Conté dades bàsiques com nom, cognom, data de naixement, motivació i sou.
 */
public abstract class Persona {

    /**
     * Nom de la persona.
     */
    private String nom;

    /**
     * Cognom de la persona.
     */
    private String cognom;

    /**
     * Data de naixement de la persona en format text (per exemple, "01/01/2000").
     */
    private String dataNaixement;

    /**
     * Nivell de motivació actual de la persona.
     */
    private double motivacio;

    /**
     * Sou anual de la persona.
     */
    private double sou;

    /**
     * Constructor principal amb totes les dades obligatòries.
     *
     * @param nom            nom de la persona
     * @param cognom         cognom de la persona
     * @param dataNaixement  data de naixement en format text
     * @param motivacio      motivació inicial de la persona
     * @param sou            sou anual de la persona
     */
    public Persona(String nom, String cognom, String dataNaixement, double motivacio, double sou) {
        this.nom = nom;
        this.cognom = cognom;
        this.dataNaixement = dataNaixement;
        this.motivacio = motivacio;
        this.sou = sou;
    }

    /**
     * Retorna el nom de la persona.
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retorna el cognom de la persona.
     *
     * @return cognom
     */
    public String getCognom() {
        return cognom;
    }

    /**
     * Retorna la data de naixement.
     *
     * @return data de naixement en format text
     */
    public String getDataNaixement() {
        return dataNaixement;
    }

    /**
     * Retorna la motivació actual.
     *
     * @return valor de motivació
     */
    public double getMotivacio() {
        return motivacio;
    }

    /**
     * Actualitza la motivació.
     * Es podria limitar perquè sempre estigui entre 1 i 10.
     *
     * @param motivacio nou valor de motivació
     */
    public void setMotivacio(double motivacio) {
        this.motivacio = motivacio;
    }

    /**
     * Retorna el sou anual.
     *
     * @return sou anual
     */
    public double getSou() {
        return sou;
    }

    /**
     * Actualitza el sou anual.
     *
     * @param sou nou sou anual
     */
    public void setSou(double sou) {
        this.sou = sou;
    }

    /**
     * Mètode d'entrenament comú per a totes les persones.
     * Per defecte, augmenta la motivació en 0.2 punts.
     * Les subclasses poden cridar super.entrenament() o sobreescriure'l.
     */
    public void entrenament() {
        this.motivacio += 0.2;
        // Aquí podries limitar a màxim 10 amb un if.
    }

    /**
     * Retorna una representació textual de la persona amb les seves dades principals.
     *
     * @return cadena descriptiva amb nom, cognom, data de naixement, motivació i sou
     */
    @Override
    public String toString() {
        return nom + " " + cognom + " (" + dataNaixement + "), motivació=" + motivacio + ", sou=" + sou;
    }
}
