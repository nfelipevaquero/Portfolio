import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class JocAnimalsTest {
    @Test
    void barrejarPreguntesMezclaPeroConservaContenido() {
        String[] preguntes = {"p1", "p2", "p3"};
        String[] a = {"a1", "a2", "a3"};
        String[] b = {"b1", "b2", "b3"};
        String[] c = {"c1", "c2", "c3"};
        char[] sol = {'A', 'B', 'C'};

        String[] preguntesOriginal = preguntes.clone();

        Main.barrejarPreguntes(preguntes, a, b, c, sol, 3);


        assertEquals(3, preguntes.length);

        for (String q : preguntesOriginal) {
            boolean trobat = false;
            for (String q2 : preguntes) {
                if (q.equals(q2)) trobat = true;
            }
            assertTrue(trobat);
        }
    }
    @Test
    void carregarPreguntesLlegeixCorrectament() {
        Main.carregarPreguntesDesDeFitxer("src/test/resources/testCategoria.txt");
        assertNotNull(Main.preguntes[0]);
        assertNotNull(Main.opcionsA[0]);
        assertNotEquals('\0', Main.solucions[0]);
    }

}
