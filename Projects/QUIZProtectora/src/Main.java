import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static final int NOMBRE_PREGUNTES = 20;
    static String[] preguntes = new String[NOMBRE_PREGUNTES];
    static String[] opcionsA = new String[NOMBRE_PREGUNTES];
    static String[] opcionsB = new String[NOMBRE_PREGUNTES];
    static String[] opcionsC = new String[NOMBRE_PREGUNTES];
    static char[] solucions = new char[NOMBRE_PREGUNTES];

    static Scanner lector = new Scanner(System.in);

    public static void main(String[] args) {
        String nom = demanarNomUsuari();
        boolean continuarJugant;

        do {
            int categoria = demanarCategoria();
            carregarPerCategoria(categoria);
            int nombrePreguntesQuiz = demanarNombrePreguntes();
            barrejarPreguntes(preguntes, opcionsA, opcionsB, opcionsC, solucions, NOMBRE_PREGUNTES);

            int[] resultat = jugarUnaPartida(nombrePreguntesQuiz);

            guardarEstadistiques(nom, categoria, resultat[0], resultat[1]);

            continuarJugant = demanarContinuar();
        } while (continuarJugant);

        System.out.println("Adéu!!");
    }

    private static String demanarNomUsuari() {
        System.out.println("Introdueix el teu nom d'usuari:");
        String nom = lector.next();
        System.out.println("------------------------------");
        return nom;
    }

    private static int demanarCategoria() {
        String[] categories = new String[5];
        categories[0] = "(1) Insectes";
        categories[1] = "(2) Ocells";
        categories[2] = "(3) Mamífers";
        categories[3] = "(4) Aquàtics";
        categories[4] = "(5) Rèptils";

        int respostaCategoria = 0;
        boolean categoriaValida = false;

        System.out.println("CATEGORIES:\n-----------------------------");
        do {
            for (String s : categories) {
                System.out.println(s);
            }
            System.out.println("-----------------------------\nQuina categoria vols?");
            respostaCategoria = lector.nextInt();
            System.out.println("------------------------------");

            if (respostaCategoria >= 1 && respostaCategoria <= 5) {
                categoriaValida = true;
            } else {
                System.out.println("----------------------");
                System.out.println("Opció incorrecta!!");
                System.out.println("----------------------");
            }
        } while (!categoriaValida);

        return respostaCategoria;
    }

    private static void carregarPerCategoria(int respostaCategoria) {
        switch (respostaCategoria) {
            case 1:
                System.out.println("Insectes");
                carregarPreguntesDesDeFitxer("src/fitxers/categoriaInsectes.txt");
                break;
            case 2:
                System.out.println("Ocells");
                carregarPreguntesDesDeFitxer("src/fitxers/categoriaOcells.txt");
                break;
            case 3:
                System.out.println("Mamífers");
                carregarPreguntesDesDeFitxer("src/fitxers/categoriaMamifers.txt");
                break;
            case 4:
                System.out.println("Aquàtics");
                carregarPreguntesDesDeFitxer("src/fitxers/categoriaAquatics.txt");
                break;
            case 5:
                System.out.println("Rèptils");
                carregarPreguntesDesDeFitxer("src/fitxers/categoriaReptils.txt");
                break;
            default:
                System.out.println("Opció incorrecta!!");
        }
    }

    private static int demanarNombrePreguntes() {
        boolean quantitatValida = false;
        int nombrePreguntesQuiz;

        do {
            System.out.println("Vols el qüestionari amb quantes preguntes? (mínim 5 i màxim 20)");
            nombrePreguntesQuiz = lector.nextInt();
            System.out.println("------------------------------");

            if (nombrePreguntesQuiz >= 5 && nombrePreguntesQuiz <= 20) {
                quantitatValida = true;
            } else {
                System.out.println("Error, valor incorrecte!\nTorna-ho a intentar.");
            }
        } while (!quantitatValida);

        return nombrePreguntesQuiz;
    }

    private static int[] jugarUnaPartida(int nombrePreguntesQuiz) {
        int encerts = 0;
        int errors = 0;

        for (int i = 0; i < nombrePreguntesQuiz; i++) {
            System.out.println("\nPregunta " + (i + 1) + ": " + preguntes[i]);
            System.out.println(opcionsA[i]);
            System.out.println(opcionsB[i]);
            System.out.println(opcionsC[i]);

            char respostaUsuari = demanarRespostaUsuari();

            if (respostaUsuari == solucions[i]) {
                System.out.println("Has encertat!!");
                encerts++;
            } else {
                System.out.println("Has fallat!!");
                errors++;
            }
            System.out.println("Encerts: " + encerts);
            System.out.println("Errors: " + errors);
        }

        mostrarResultat(encerts, nombrePreguntesQuiz);
        return new int[]{encerts, errors};
    }

    private static char demanarRespostaUsuari() {
        boolean intentValid = false;
        char respostaUsuari;

        do {
            System.out.print("-----------------------------\nResposta (A,B,C):");
            respostaUsuari = Character.toUpperCase(lector.next().charAt(0));

            if (respostaUsuari == 'A' || respostaUsuari == 'B' || respostaUsuari == 'C') {
                intentValid = true;
            } else {
                System.out.println("-----------------------------\nError, valor incorrecte! Introdueix A, B o C.\nTorna-ho a intentar.\n-----------------------------");
            }
        } while (!intentValid);

        return respostaUsuari;
    }

    private static void mostrarResultat(int encerts, int nombrePreguntesQuiz) {
        double percentatge = (encerts * 100.0) / nombrePreguntesQuiz;
        System.out.println("-----------------------------\nPercentatge: " + percentatge);

        if (percentatge >= 0 && percentatge <= 33) {
            System.out.println("Ni tan mal!");
        } else if (percentatge > 33 && percentatge <= 66) {
            System.out.println("Bon resultat!!");
        } else if (percentatge > 66 && percentatge < 100) {
            System.out.println("Ets dels millors!");
        } else if (percentatge == 100) {
            System.out.println("Màquina total!!!!!");
        }
    }

    private static boolean demanarContinuar() {
        System.out.println("Vols continuar jugant? (s/n)");
        char continuar = lector.next().charAt(0);
        System.out.println("------------------------------");
        return continuar != 'n';
    }

    static void carregarPreguntesDesDeFitxer(String fitxer) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fitxer));
            String linia;
            int i = 0;
            while ((linia = br.readLine()) != null && i < 20) {
                String[] parts = linia.split(";");
                if (parts.length == 5) {
                    preguntes[i] = parts[0];
                    opcionsA[i] = parts[1];
                    opcionsB[i] = parts[2];
                    opcionsC[i] = parts[3];
                    solucions[i] = parts[4].trim().toUpperCase().charAt(0);
                    i++;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error llegint fitxer: " + e.getMessage());
        }
    }

    static void barrejarPreguntes(String[] preguntes, String[] opcionsA,
                                  String[] opcionsB, String[] opcionsC,
                                  char[] solucions, int maxPreguntes) {
        Random rand = new Random();
        for (int i = maxPreguntes - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            String tempPregunta = preguntes[i];
            preguntes[i] = preguntes[j];
            preguntes[j] = tempPregunta;

            String tempA = opcionsA[i];
            opcionsA[i] = opcionsA[j];
            opcionsA[j] = tempA;

            String tempB = opcionsB[i];
            opcionsB[i] = opcionsB[j];
            opcionsB[j] = tempB;

            String tempC = opcionsC[i];
            opcionsC[i] = opcionsC[j];
            opcionsC[j] = tempC;

            char tempSol = solucions[i];
            solucions[i] = solucions[j];
            solucions[j] = tempSol;
        }
    }


    private static void guardarEstadistiques(String nomUsuari, int categoria,
                                             int encerts, int errors) {
        try {
            String nomFitxer = obtenirNomFitxerEstadistiques(categoria);

            LocalDateTime ara = LocalDateTime.now();
            DateTimeFormatter format =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataHora = ara.format(format);

            String linia = nomUsuari + ";" + dataHora + ";" + encerts + ";" + errors;

            PrintWriter pw = new PrintWriter(new FileWriter(nomFitxer, true));
            pw.println(linia);
            pw.close();

        } catch (Exception e) {
            System.out.println("Error escrivint estadístiques: " + e.getMessage());
        }
    }

    private static String obtenirNomFitxerEstadistiques(int categoria) {
        String base = "src/fitxers/";
        switch (categoria) {
            case 1: return base + "statsInsectes.txt";
            case 2: return base + "statsOcells.txt";
            case 3: return base + "statsMamifers.txt";
            case 4: return base + "statsAquatics.txt";
            case 5: return base + "statsReptils.txt";
            default: return base + "statsDesconegut.txt";
        }
    }
}
