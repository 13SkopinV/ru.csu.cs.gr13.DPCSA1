package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayOfStringsTest {

    @Test
    void testCreation() {
        ArrayOfStrings soa = new ArrayOfStrings(5);
        assertEquals(5, soa.a.length);
        assertEquals(5, soa.b.length);
        assertEquals(5, soa.c.length);
    }

    @Test
    void testAddAndRetrieve() {
        ArrayOfStrings soa = new ArrayOfStrings(3);
        soa.addSoA(0, 10, 20, 30);
        soa.addSoA(1, 40, 50, 60);
        soa.addSoA(2, 70, 80, 90);

        assertEquals(10, soa.a[0]);
        assertEquals(50, soa.b[1]);
        assertEquals(90, soa.c[2]);
    }

    @Test
    void testSumEmpty() {
        ArrayOfStrings soa = new ArrayOfStrings(10);
        assertEquals(0, soa.sumSoA());
    }

    @Test
    void testSumSingleElement() {
        ArrayOfStrings soa = new ArrayOfStrings(1);
        soa.addSoA(0, 42, 0, 0);
        assertEquals(42, soa.sumSoA());
    }

    @Test
    void testSumMultipleElements() {
        ArrayOfStrings soa = new ArrayOfStrings(4);
        soa.addSoA(0, 1, 0, 0);
        soa.addSoA(1, 2, 0, 0);
        soa.addSoA(2, 3, 0, 0);
        soa.addSoA(3, 4, 0, 0);
        assertEquals(10, soa.sumSoA());
    }

    @Test
    void testSumWithNegativeNumbers() {
        ArrayOfStrings soa = new ArrayOfStrings(3);
        soa.addSoA(0, 100, 0, 0);
        soa.addSoA(1, -50, 0, 0);
        soa.addSoA(2, -30, 0, 0);
        assertEquals(20, soa.sumSoA());
    }

    @Test
    void testSumIgnoresBAndC() {
        ArrayOfStrings soa = new ArrayOfStrings(2);
        soa.addSoA(0, 10, 999, 999);
        soa.addSoA(1, 20, 999, 999);
        assertEquals(30, soa.sumSoA());
    }

    @Test
    void testOverwriteValue() {
        ArrayOfStrings soa = new ArrayOfStrings(2);
        soa.addSoA(0, 10, 0, 0);
        soa.addSoA(0, 50, 0, 0);
        assertEquals(50, soa.sumSoA());
    }

    @Test
    void testAddToLastIndex() {
        ArrayOfStrings soa = new ArrayOfStrings(3);
        soa.addSoA(2, 100, 0, 0);
        assertEquals(100, soa.sumSoA());
    }
}