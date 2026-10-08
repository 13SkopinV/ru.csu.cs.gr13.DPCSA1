package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayOfRecordsTest {

    @Test
    void testCreation() {
        ArrayOfRecords aos = new ArrayOfRecords(5);
        assertEquals(5, aos.records.length);
        assertNull(aos.records[0]);
    }

    @Test
    void testAddAndRetrieve() {
        ArrayOfRecords aos = new ArrayOfRecords(3);
        aos.addAoS(0, 10, 20, 30);
        aos.addAoS(1, 40, 50, 60);

        assertEquals(10, aos.records[0].a);
        assertEquals(50, aos.records[1].b);
        assertEquals(60, aos.records[1].c);
    }

    @Test
    void testSumSingleElement() {
        ArrayOfRecords aos = new ArrayOfRecords(1);
        aos.addAoS(0, 42, 0, 0);
        assertEquals(42, aos.sumAoS());
    }

    @Test
    void testSumMultipleElements() {
        ArrayOfRecords aos = new ArrayOfRecords(4);
        aos.addAoS(0, 1, 0, 0);
        aos.addAoS(1, 2, 0, 0);
        aos.addAoS(2, 3, 0, 0);
        aos.addAoS(3, 4, 0, 0);
        assertEquals(10, aos.sumAoS());
    }

    @Test
    void testSumWithNegativeNumbers() {
        ArrayOfRecords aos = new ArrayOfRecords(3);
        aos.addAoS(0, 100, 0, 0);
        aos.addAoS(1, -50, 0, 0);
        aos.addAoS(2, -30, 0, 0);
        assertEquals(20, aos.sumAoS());
    }

    @Test
    void testSumIgnoresBAndC() {
        ArrayOfRecords aos = new ArrayOfRecords(2);
        aos.addAoS(0, 10, 999, 999);
        aos.addAoS(1, 20, 999, 999);
        assertEquals(30, aos.sumAoS());
    }

    @Test
    void testOverwriteValue() {
        ArrayOfRecords aos = new ArrayOfRecords(2);
        aos.addAoS(0, 10, 0, 0);
        aos.addAoS(1, 0, 0, 0);   // ← добавили эту строку
        aos.addAoS(0, 50, 0, 0);
        assertEquals(50, aos.sumAoS());
    }
}