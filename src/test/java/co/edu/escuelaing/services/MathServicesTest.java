package co.edu.escuelaing.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MathServicesTest {

    @Test
    void shouldSquarePositiveNumber() {
        assertEquals(25, MathServices.square(5));
    }

    @Test
    void shouldSquareNegativeNumber() {
        assertEquals(196, MathServices.square(-14));
    }

    @Test
    void shouldSquareZero() {
        assertEquals(0, MathServices.square(0));
    }
}
