package pe.edu.cibertec.vilchezrojasjoseluis.rental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PenaltyCalculatorTest {

    private final PenaltyCalculator calculator =
            new PenaltyCalculator();

    @Test
    void debeLanzarExcepcionCuandoLosDiasDeRetrasoSonNegativos() {

        assertThrows(
                IllegalStateException.class,
                () -> calculator.calculateLatePenalty(-5)
        );
    }

    @Test
    void debeRetornarCeroCuandoNoExistenDiasDeRetraso() {

        int penalty = calculator.calculateLatePenalty(0);

        assertEquals(0, penalty);
    }

    @Test
    void debeCalcularLaPenalizacionCuandoElRetrasoEsMenorOIgualASieteDias() {

        int penalty = calculator.calculateLatePenalty(5);

        assertEquals(75, penalty);
    }

    @Test
    void debeAplicarElTopeMaximoCuandoLaPenalizacionSuperaLosTrescientos() {

        int penalty = calculator.calculateLatePenalty(20);

        assertEquals(300, penalty);
    }
}
