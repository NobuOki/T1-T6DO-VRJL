package pe.edu.cibertec.vilchezrojasjoseluis.loyalty.simple;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LoyaltyPointsCalculatorTest {

    private final LoyaltyPointsCalculator calculator =
            new LoyaltyPointsCalculator();

    @Test
    void debeLanzarExcepcionCuandoElMontoEsNulo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateAccumulatedPoints(
                        null, "REGULAR")
        );
    }

    @Test
    void debeLanzarExcepcionCuandoElMontoEsCero() {

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateAccumulatedPoints(
                        BigDecimal.ZERO, "REGULAR")
        );
    }

    @Test
    void debeLanzarExcepcionCuandoElMontoEsNegativo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateAccumulatedPoints(
                        new BigDecimal("-50"), "REGULAR")
        );
    }

    @Test
    void debeCalcularLosPuntosCorrectosParaUnClienteRegular() {

        int points = calculator.calculateAccumulatedPoints(
                new BigDecimal("150.75"), "REGULAR"
        );

        assertEquals(150, points);
    }

    @Test
    void debeDuplicarLosPuntosCuandoElClienteEsVIP() {

        int points = calculator.calculateAccumulatedPoints(
                new BigDecimal("150.75"), "VIP"
        );

        assertEquals(301, points);
    }
}
