package futbolmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal de l'aplicació Politècnics Football Manager.
 * Gestiona el menú principal, el mercat de fitxatges, els equips i la lliga.
 */
public class Main {

    /**
     * Llista global d'equips gestionats per l'aplicació.
     */
    private static List<Equip> equips = new ArrayList<>();

    /**
     * Llista global de persones (jugadors i entrenadors) disponibles al mercat.
     */
    private static List<Persona> mercatFitxatges = new ArrayList<>();

    /**
     * Referència a la lliga actual (si s'ha creat i disputat).
     */
    private static Lliga lligaActual;

    /**
     * Escàner únic per llegir dades des de teclat.
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * Punt d'entrada de l'aplicació.
     * Carrega dades inicials, demana el rol de l'usuari i mostra el menú corresponent.
     *
     * @param args arguments de línia de comandes (no s'utilitzen)
     */
    public static void main(String[] args) {

        carregarMercat("mercat_fitxatges.txt");

        carregarEquips("equips.txt");

        String rol = demanarRolUsuari();

        if ("admin".equalsIgnoreCase(rol)) {
            menuAdmin();
        } else {
            menuGestor();
        }
    }

    /**
     * Carrega el mercat de fitxatges des d'un fitxer de text.
     * Cada línia representa un jugador (J;...) o un entrenador (E;...).
     *
     * @param nomFitxer nom del fitxer de mercat (per exemple "mercat_fitxatges.txt")
     */
    private static void carregarMercat(String nomFitxer) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(nomFitxer));
            String linia;

            while ((linia = br.readLine()) != null) {

                String[] camps = linia.split(";");

                char tipus = camps[0].charAt(0);

                if (tipus == 'J') {

                    String nom = camps[1];
                    String cognom = camps[2];
                    String dataNaixement = camps[3];
                    double motivacio = Double.parseDouble(camps[4]);
                    double sou = Double.parseDouble(camps[5]);
                    int dorsal = Integer.parseInt(camps[6]);
                    String posicio = camps[7];
                    double qualitat = Double.parseDouble(camps[8]);

                    Jugador j = new Jugador(nom, cognom, dataNaixement, motivacio, sou, dorsal, posicio, qualitat);

                    mercatFitxatges.add(j);

                } else if (tipus == 'E') {

                    String nom = camps[1];
                    String cognom = camps[2];
                    String dataNaixement = camps[3];
                    double motivacio = Double.parseDouble(camps[4]);
                    double sou = Double.parseDouble(camps[5]);
                    int tornejos = Integer.parseInt(camps[6]);
                    boolean seleccionador = Boolean.parseBoolean(camps[7]);

                    Entrenador e = new Entrenador(nom, cognom, dataNaixement, motivacio, sou, tornejos, seleccionador);

                    mercatFitxatges.add(e);
                }
            }

        } catch (IOException e) {
            System.out.println("Error llegint el fitxer de mercat: " + e.getMessage());
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                System.out.println("Error tancant el fitxer de mercat");
            }
        }
    }

    /**
     * Carrega els equips guardats prèviament des d'un fitxer.
     * El fitxer pot contenir línies EQUIP, ENTRENADOR, JUGADOR i FI_EQUIP.
     *
     * @param nomFitxer nom del fitxer d'equips (per exemple "equips.txt")
     */
    private static void carregarEquips(String nomFitxer) {
        BufferedReader br = null;
        Equip equipActual = null;

        try {
            br = new BufferedReader(new FileReader(nomFitxer));
            String linia;

            while ((linia = br.readLine()) != null) {
                String[] camps = linia.split(";");

                switch (camps[0]) {
                    case "EQUIP":
                        String nom = camps[1];
                        int anyFundacio = Integer.parseInt(camps[2]);
                        String ciutat = camps[3];
                        equipActual = new Equip(nom, anyFundacio, ciutat);
                        equips.add(equipActual);
                        break;

                    case "ENTRENADOR":
                        if (equipActual != null) {
                            String enNom = camps[1];
                            String enCognom = camps[2];
                            String enData = camps[3];
                            // TODO: crear Entrenador amb dades llegides
                            equipActual.setEntrenador(null);
                        }
                        break;

                    case "JUGADOR":
                        if (equipActual != null) {
                            String jNom = camps[1];
                            String jCognom = camps[2];
                            String jData = camps[3];
                            // TODO: crear Jugador amb dades llegides i afegir-lo a l'equip
                        }
                        break;

                    case "FI_EQUIP":
                        equipActual = null;
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("Fitxer d'equips no trobat (normal si és la primera execució)");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Demana a l'usuari si vol accedir com a administrador o com a gestor.
     *
     * @return la cadena "admin" o "gestor" segons l'elecció de l'usuari
     */
    private static String demanarRolUsuari() {
        System.out.println("Benvingut a Politècnics Football Manager!");
        System.out.println("Ets: 1- Admin   o   2- Gestor d'equip?");

        int opcio;
        do {
            System.out.print("Tria (1 o 2): ");
            opcio = sc.nextInt();
            sc.nextLine();

            if (opcio == 1) return "admin";
            if (opcio == 2) return "gestor";

            System.out.println("Opció no vàlida. Triï 1 (Admin) o 2 (Gestor)");
        } while (true);
    }

    /**
     * Desa tots els equips i la seva estructura bàsica en un fitxer de text.
     * El format es basa en línies EQUIP, ENTRENADOR, JUGADOR i FI_EQUIP.
     *
     * @param nomFitxer nom del fitxer on desar (per exemple "equips.txt")
     */
    private static void desarEquips(String nomFitxer) {
        FileWriter fw = null;

        try {
            fw = new FileWriter(nomFitxer);

            for (Equip equip : equips) {
                fw.write("EQUIP;" + equip.getNom() + ";" + equip.getAnyFundacio() + ";" + equip.getCiutat());
                fw.write("\n");

                if (equip.getEntrenador() != null) {
                    Entrenador ent = equip.getEntrenador();
                    fw.write("ENTRENADOR;" + ent.getNom() + ";" + ent.getCognom() + ";" + ent.getDataNaixement());
                    fw.write("\n");
                }

                for (Jugador j : equip.getJugadors()) {
                    fw.write("JUGADOR;" + j.getNom() + ";" + j.getCognom() + ";" + j.getDataNaixement());
                    fw.write("\n");
                }

                fw.write("FI_EQUIP");
                fw.write("\n");
            }

            System.out.println("Equips desats!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                System.out.println("ERROR");
            }
        }
    }

    /**
     * Desa el mercat de fitxatges (jugadors i entrenadors) en un fitxer de text.
     *
     * @param nomFitxer nom del fitxer a desar (per exemple "mercat_fitxatges.txt")
     */
    private static void desarMercatFitxatges(String nomFitxer) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(nomFitxer);

            for (Persona p : mercatFitxatges) {
                if (p instanceof Jugador) {
                    Jugador j = (Jugador) p;
                    fw.write(String.format("J;%s;%s;%s;%.1f;%.0f;%d;%s;%.1f\n",
                            j.getNom(), j.getCognom(), j.getDataNaixement(),
                            j.getMotivacio(), j.getSou(),
                            j.getDorsal(), j.getPosicio(), j.getQualitat()));
                } else if (p instanceof Entrenador) {
                    Entrenador e = (Entrenador) p;
                    fw.write(String.format("E;%s;%s;%s;%.1f;%.0f;%d;%b\n",
                            e.getNom(), e.getCognom(), e.getDataNaixement(),
                            e.getMotivacio(), e.getSou(),
                            e.getTornejosGuanyats(), e.isSeleccionadorNacional()));
                }
            }
            System.out.println("Mercat de fitxatges actualitzat!");

        } catch (IOException e) {
            System.out.println("Error desant mercat: " + e.getMessage());
        } finally {
            try { if (fw != null) fw.close(); } catch (IOException e) {}
        }
    }

    /**
     * Mostra i gestiona el menú principal per al rol d'administrador.
     * Permet crear equips, gestionar el mercat, disputar lligues, etc.
     */
    private static void menuAdmin() {
        int opcio;
        do {
            System.out.println("---------------------------------------------------------");
            System.out.printf("| %-53s |\n", "Benvingut, Admin");
            System.out.println("---------------------------------------------------------");
            System.out.printf("| %-47s %5s |\n", "Veure classificació lliga actual", "1");
            System.out.printf("| %-47s %5s |\n", "Donar d'alta equip", "2");
            System.out.printf("| %-47s %5s |\n", "Donar d'alta jugador/a o entrenador/a", "3");
            System.out.printf("| %-47s %5s |\n", "Consultar dades equip", "4");
            System.out.printf("| %-47s %5s |\n", "Consultar dades jugador/a equip", "5");
            System.out.printf("| %-47s %5s |\n", "Disputar nova lliga", "6");
            System.out.printf("| %-47s %5s |\n", "Realitzar sessió entrenament (mercat fitxatges)", "7");
            System.out.printf("| %-47s %5s |\n", "Desar dades equips", "8");
            System.out.printf("| %-47s %5s |\n", "Sortir", "0");
            System.out.println("---------------------------------------------------------");

            opcio = sc.nextInt();
            sc.nextLine();

            switch (opcio) {
                case 1:
                    if (lligaActual != null) {
                        lligaActual.mostrarClassificacio();
                    } else {
                        System.out.println("No s'ha disputat cap lliga encara!");
                    }
                    break;
                case 2:
                    donarAltaEquip();
                    break;
                case 3:
                    donarAltaPersonaMercat();
                    break;
                case 4:
                    consultarDadesEquip();
                    break;
                case 5:
                    consultarDadesJugadorEquip();
                    break;
                case 6:
                    disputarNovaLliga();
                    break;
                case 7:
                    sessioEntrenamentMercat();
                    break;
                case 8:
                    desarEquips("equips.txt");
                    desarMercatFitxatges("mercat_fitxatges.txt");
                    break;
                case 0:
                    System.out.println("Adéu!");
                    break;
                default:
                    System.out.println("Opció no vàlida!");
            }
        } while (opcio != 0);
    }

    /**
     * Mostra i gestiona el menú principal per al rol de gestor d'equip.
     * Permet consultar classificació, gestionar un equip concret, transferir jugadors, etc.
     */
    private static void menuGestor() {
        int opcio;
        do {
            System.out.println("---------------------------------------------------------");
            System.out.printf("| %-53s |\n", "Benvingut, Gestor");
            System.out.println("---------------------------------------------------------");
            System.out.printf("| %-47s %5s |\n", "Veure classificació lliga actual", "1");
            System.out.printf("| %-47s %5s |\n", "Gestionar el meu equip", "2");
            System.out.printf("| %-47s %5s |\n", "Consultar dades equip", "3");
            System.out.printf("| %-47s %5s |\n", "Consultar dades jugador/a equip", "4");
            System.out.printf("| %-47s %5s |\n", "Transferir jugador/a", "5");
            System.out.printf("| %-47s %5s |\n", "Desar dades equips", "6");
            System.out.printf("| %-47s %5s |\n", "Sortir", "0");
            System.out.println("---------------------------------------------------------");

            opcio = sc.nextInt();
            sc.nextLine();

            switch (opcio) {
                case 1:
                    if (lligaActual != null) {
                        lligaActual.mostrarClassificacio();
                    } else {
                        System.out.println("No s'ha disputat cap lliga encara!");
                    }
                    break;
                case 2:
                    gestionarEquip();
                    break;
                case 3:
                    consultarDadesEquip();
                    break;
                case 4:
                    consultarDadesJugadorEquip();
                    break;
                case 5:
                    transferirJugador();
                    break;
                case 6:
                    desarEquips("equips.txt");
                    desarMercatFitxatges("mercat_fitxatges.txt");
                    break;
                case 0:
                    System.out.println("Adéu!");
                    break;
                default:
                    System.out.println("Opció no vàlida!");
            }
        } while (opcio != 0);
    }

    /**
     * Dona d'alta un nou equip demanant totes les dades per teclat
     * i comprovant que el nom no estigui repetit.
     */
    private static void donarAltaEquip() {
        String nomEquip;
        boolean nomExisteix = false;

        do {
            System.out.print("Nom del nou equip: ");
            nomEquip = sc.nextLine().trim();

            for (Equip e : equips) {
                if (e.getNom().equalsIgnoreCase(nomEquip)) {
                    nomExisteix = true;
                    break;
                }
            }

            if (nomExisteix) {
                System.out.println("ERROR: L'equip '" + nomEquip + "' ja existeix!");
            }

        } while (nomExisteix);

        System.out.print("Any de fundació: ");
        int anyFundacio = sc.nextInt();
        sc.nextLine();

        System.out.print("Ciutat: ");
        String ciutat = sc.nextLine();

        System.out.print("Estadi (o Enter per omitir): ");
        String estadi = sc.nextLine();
        if (estadi.trim().isEmpty()) estadi = null;

        System.out.print("President/a (o Enter per omitir): ");
        String president = sc.nextLine();
        if (president.trim().isEmpty()) president = null;

        Equip nouEquip = new Equip(nomEquip, anyFundacio, ciutat, estadi, president);
        equips.add(nouEquip);

        System.out.println("Equip '" + nomEquip + "' creat correctament!");
    }

    /**
     * Dona d'alta un nou jugador o entrenador al mercat de fitxatges.
     * Pregunta el tipus (J/E) i les dades necessàries per teclat.
     */
    private static void donarAltaPersonaMercat() {
        System.out.println("Què vols donar d'alta?");
        System.out.println("1- Jugador/a");
        System.out.println("2- Entrenador/a");
        System.out.print("Opció: ");
        int opcio = sc.nextInt();
        sc.nextLine();

        if (opcio == 1) {
            System.out.println("=== Alta de jugador/a ===");
            System.out.print("Nom: ");
            String nom = sc.nextLine();

            System.out.print("Cognom: ");
            String cognom = sc.nextLine();

            System.out.print("Data naixement (dd/mm/aaaa): ");
            String dataNaixement = sc.nextLine();

            double motivacio = 5.0;

            System.out.print("Sou anual: ");
            double sou = sc.nextDouble();
            sc.nextLine();

            System.out.print("Dorsal: ");
            int dorsal = sc.nextInt();
            sc.nextLine();

            System.out.println("Posició (triar número):");
            for (int i = 0; i < Jugador.POSICIONS_POSSIBLES.length; i++) {
                System.out.println((i + 1) + "- " + Jugador.POSICIONS_POSSIBLES[i]);
            }
            System.out.print("Opció: ");
            int idxPosicio = sc.nextInt();
            sc.nextLine();
            String posicio = Jugador.POSICIONS_POSSIBLES[idxPosicio - 1];

            int qualitatAleatoria = 30 + (int) (Math.random() * 71);

            Jugador nouJugador = new Jugador(
                    nom, cognom, dataNaixement,
                    motivacio, sou,
                    dorsal, posicio, qualitatAleatoria
            );

            mercatFitxatges.add(nouJugador);
            System.out.println("Jugador/a afegit al mercat de fitxatges!");

        } else if (opcio == 2) {

            System.out.println("=== Alta d'entrenador/a ===");
            System.out.print("Nom: ");
            String nom = sc.nextLine();

            System.out.print("Cognom: ");
            String cognom = sc.nextLine();

            System.out.print("Data naixement (dd/mm/aaaa): ");
            String dataNaixement = sc.nextLine();

            double motivacio = 5.0;

            System.out.print("Sou anual: ");
            double sou = sc.nextDouble();
            sc.nextLine();

            System.out.print("Nombre de tornejos guanyats: ");
            int tornejosGuanyats = sc.nextInt();
            sc.nextLine();

            System.out.print("És seleccionador/a nacional? (s/n): ");
            String resp = sc.nextLine().trim().toLowerCase();
            boolean seleccionador = resp.equals("s");

            Entrenador nouEntrenador = new Entrenador(
                    nom, cognom, dataNaixement,
                    motivacio, sou,
                    tornejosGuanyats, seleccionador
            );

            mercatFitxatges.add(nouEntrenador);
            System.out.println("Entrenador/a afegit al mercat de fitxatges!");

        } else {
            System.out.println("Opció no vàlida.");
        }
    }

    /**
     * Consulta i mostra per pantalla les dades d'un equip, buscant-lo pel nom.
     */
    private static void consultarDadesEquip() {
        System.out.print("Digues el nom de l'equip que vols consultar: ");
        String nomBuscat = sc.nextLine();

        boolean equipTrobat = false;

        for (Equip e : equips) {
            if (e.getNom().equalsIgnoreCase(nomBuscat)) {

                System.out.println("=== Dades de l'equip ===");
                System.out.println(e.toString());
                equipTrobat = true;
                break;
            }
        }

        if (!equipTrobat) {
            System.out.println("Equip \"" + nomBuscat + "\" no trobat al sistema.");
        }
    }

    /**
     * Consulta les dades d'un jugador concret dins d'un equip (nom + dorsal).
     */
    private static void consultarDadesJugadorEquip() {

        System.out.print("Nom de l'equip: ");
        String nomEquip = sc.nextLine();

        Equip equipTrobat = null;

        for (Equip e : equips) {
            if (e.getNom().equalsIgnoreCase(nomEquip)) {
                equipTrobat = e;
                break;
            }
        }

        if (equipTrobat == null) {
            System.out.println("Equip \"" + nomEquip + "\" no trobat.");
            return;
        }

        System.out.print("Nom del/la jugador/a: ");
        String nomJugador = sc.nextLine();

        System.out.print("Dorsal del/la jugador/a: ");
        int dorsal = sc.nextInt();
        sc.nextLine();

        Jugador jugadorTrobat = null;

        for (Jugador j : equipTrobat.getJugadors()) {
            if (j.getNom().equalsIgnoreCase(nomJugador) && j.getDorsal() == dorsal) {
                jugadorTrobat = j;
                break;
            }
        }

        if (jugadorTrobat == null) {
            System.out.println("Jugador/a no trobat/da a l'equip " + equipTrobat.getNom());
        } else {
            System.out.println("=== Dades del/la jugador/a ===");
            System.out.println(jugadorTrobat.toString());
        }
    }

    /**
     * Executa una sessió d'entrenament per a totes les persones del mercat:
     * tothom entrena, els jugadors poden canviar de posició i els entrenadors
     * incrementen el sou.
     */
    private static void sessioEntrenamentMercat() {

        System.out.println("=== Sessió d'entrenament del mercat de fitxatges ===");

        for (Persona p : mercatFitxatges) {

            p.entrenament();

            if (p instanceof Jugador) {
                Jugador j = (Jugador) p;
                j.canviDePosicio();
            } else if (p instanceof Entrenador) {
                Entrenador e = (Entrenador) p;
                e.incrementarSou();
            }
        }

        System.out.println("Sessió d'entrenament completada per a tot el mercat.");
    }

    /**
     * Transfereix un jugador d'un equip origen a un equip destí,
     * controlant el dorsal i evitant duplicats al nou equip.
     */
    private static void transferirJugador() {

        System.out.print("Nom de l'equip origen: ");
        String nomOrigen = sc.nextLine();

        System.out.print("Nom de l'equip destí: ");
        String nomDesti = sc.nextLine();

        Equip equipOrigen = null;
        Equip equipDesti = null;

        for (Equip e : equips) {
            if (e.getNom().equalsIgnoreCase(nomOrigen)) {
                equipOrigen = e;
            }
            if (e.getNom().equalsIgnoreCase(nomDesti)) {
                equipDesti = e;
            }
        }

        if (equipOrigen == null || equipDesti == null) {
            System.out.println("Error: algun dels equips no existeix.");
            return;
        }

        System.out.print("Nom del/la jugador/a a transferir: ");
        String nomJugador = sc.nextLine();

        System.out.print("Dorsal actual del/la jugador/a: ");
        int dorsal = sc.nextInt();
        sc.nextLine();

        Jugador jugadorATransferir = null;
        for (Jugador j : equipOrigen.getJugadors()) {
            if (j.getNom().equalsIgnoreCase(nomJugador) && j.getDorsal() == dorsal) {
                jugadorATransferir = j;
                break;
            }
        }

        if (jugadorATransferir == null) {
            System.out.println("Jugador/a no trobat/da a l'equip origen.");
            return;
        }

        boolean dorsalOcupat;
        int nouDorsal = dorsal;

        do {
            dorsalOcupat = false;
            for (Jugador j : equipDesti.getJugadors()) {
                if (j.getDorsal() == nouDorsal) {
                    dorsalOcupat = true;
                    break;
                }
            }

            if (dorsalOcupat) {
                System.out.println("El dorsal " + nouDorsal + " ja està ocupat a l'equip destí.");
                System.out.print("Introdueix un nou dorsal lliure: ");
                nouDorsal = sc.nextInt();
                sc.nextLine();
            }

        } while (dorsalOcupat);

        jugadorATransferir.setDorsal(nouDorsal);

        equipOrigen.eliminarJugador(jugadorATransferir);
        equipDesti.afegirJugador(jugadorATransferir);

        System.out.println("Transferència completada: " +
                jugadorATransferir.getNom() + " passa de " + equipOrigen.getNom() +
                " a " + equipDesti.getNom() + " amb el dorsal " + nouDorsal + ".");
    }

    /**
     * Crea i disputa una nova lliga, demanant el nom de la lliga i
     * els equips participants. Valida el nombre d'equips i l'entrada numèrica.
     */
    private static void disputarNovaLliga() {
        System.out.print("Nom de la nova lliga: ");
        String nomLliga = sc.nextLine();

        int numEquips = 0;

        while (true) {
            try {
                System.out.print("Nombre d'equips que participaran: ");
                numEquips = sc.nextInt();
                sc.nextLine();
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERROR: Introdueix un NÚMERO enter (ej: 4)");
                sc.nextLine();
            }
        }

        if (numEquips <= 1) {
            System.out.println("Calen com a mínim 2 equips!");
            return;
        }

        lligaActual = new Lliga(nomLliga, numEquips);

        for (int i = 0; i < numEquips; i++) {
            Equip equipTrobat = null;
            do {
                System.out.print("Nom de l'equip " + (i + 1) + ": ");
                String nomEquip = sc.nextLine();

                equipTrobat = null;
                for (Equip e : equips) {
                    if (e.getNom().equalsIgnoreCase(nomEquip)) {
                        equipTrobat = e;
                        break;
                    }
                }

                if (equipTrobat == null) {
                    System.out.println("Equip \"" + nomEquip + "\" no trobat!");
                }
            } while (equipTrobat == null);

            lligaActual.afegirEquip(equipTrobat);
        }

        System.out.println("Comença la lliga \"" + nomLliga + "\"!");
        lligaActual.disputarLliga();
        System.out.println("Lliga finalitzada!");
    }

    /**
     * Submenú per gestionar un equip concret (donar-lo de baixa,
     * canviar president, destituir entrenador o fitxar del mercat).
     */
    private static void gestionarEquip() {
        System.out.print("Nom de l'equip a gestionar: ");
        String nomEquip = sc.nextLine();

        Equip equip = null;
        for (Equip e : equips) {
            if (e.getNom().equalsIgnoreCase(nomEquip)) {
                equip = e;
                break;
            }
        }

        if (equip == null) {
            System.out.println("Equip '" + nomEquip + "' no trobat!");
            return;
        }

        System.out.println("Gestionant equip: " + equip.getNom());

        int opcio;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.printf("| %-53s |\n", "Team Manager: " + equip.getNom().toUpperCase());
            System.out.println("=".repeat(55));
            System.out.printf("| %-47s %5s |\n", "Donar de baixa l'equip", "1");
            System.out.printf("| %-47s %5s |\n", "Modificar president/a", "2");
            System.out.printf("| %-47s %5s |\n", "Destituir entrenador/a", "3");
            System.out.printf("| %-47s %5s |\n", "Fitxar jugador/a o entrenador/a", "4");
            System.out.printf("| %-47s %5s |\n", "Sortir", "0");
            System.out.println("=".repeat(55));
            System.out.print("Tria opció: ");

            opcio = sc.nextInt();
            sc.nextLine();

            switch (opcio) {
                case 1:
                    donarBaixaEquip(equip);
                    return;
                case 2:
                    modificarPresident(equip);
                    break;
                case 3:
                    destituirEntrenador(equip);
                    break;
                case 4:
                    fitxarMercat(equip);
                    break;
                case 0:
                    System.out.println("Tornant al menú principal...");
                    break;
                default:
                    System.out.println("Opció no vàlida!");
            }
        } while (opcio != 0);
    }

    /**
     * Dona de baixa (elimina) un equip existent de la llista global,
     * demanant confirmació a l'usuari.
     *
     * @param equip equip a eliminar
     */
    private static void donarBaixaEquip(Equip equip) {
        System.out.print("Vols eliminar '" + equip.getNom() + "'? (s/n): ");
        String confirm = sc.nextLine().toLowerCase();
        if (confirm.equals("s")) {
            equips.remove(equip);
            System.out.println("Equip eliminat!");
        } else {
            System.out.println("Cancel·lat.");
        }
    }

    /**
     * Modifica el president/a d'un equip concret.
     *
     * @param equip equip al qual es vol canviar el president
     */
    private static void modificarPresident(Equip equip) {
        System.out.print("Nou president (actual: " +
                (equip.getPresident() != null ? equip.getPresident() : "cap") + "): ");
        String nouPresident = sc.nextLine();

        if (!nouPresident.trim().isEmpty()) {
            equip.setPresident(nouPresident);
            System.out.println("President: " + nouPresident);
        }
    }

    /**
     * Destitueix l'entrenador d'un equip i l'envia al mercat de fitxatges.
     *
     * @param equip equip del qual es vol destituir l'entrenador
     */
    private static void destituirEntrenador(Equip equip) {
        if (equip.getEntrenador() == null) {
            System.out.println("No té entrenador.");
            return;
        }

        System.out.print("Destituir " + equip.getEntrenador().getNom() + "? (s/n): ");
        if (sc.nextLine().toLowerCase().equals("s")) {
            mercatFitxatges.add(equip.getEntrenador());
            equip.setEntrenador(null);
            System.out.println("Entrenador al mercat!");
        }
    }

    /**
     * Fitxa una persona (jugador o entrenador) del mercat de fitxatges
     * cap a l'equip passat per paràmetre. Actualment està simulat.
     *
     * @param equip equip que realitza el fitxatge
     */
    private static void fitxarMercat(Equip equip) {
        System.out.print("1-Jugador 2-Entrenador: ");
        int tipus = sc.nextInt();
        sc.nextLine();

        System.out.println("Mercat disponible (simulat):");
        System.out.println("1- Lamine Yamal");
        System.out.println("2- Hansi Flick");

        System.out.print("Tria: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (tipus == 1) {
            System.out.println("Jugador fitxat!");

        } else {
            System.out.println("Entrenador fitxat!");

        }
    }

}
