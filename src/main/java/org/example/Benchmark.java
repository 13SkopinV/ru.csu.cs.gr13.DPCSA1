package org.example;

import java.util.Random;

public class Benchmark {

    static final int N = 100_000;
    static final int WARMUP = 30;
    static final int MEASURE = 100;

    static volatile long sink = 0;

    public static void main(String[] args) {
        // 1. Данные
        Random rnd = new Random(42);
        long[] dataA = new long[N];
        long[] dataB = new long[N];
        long[] dataC = new long[N];
        for (int i = 0; i < N; i++) {
            dataA[i] = rnd.nextLong(1000);
            dataB[i] = rnd.nextLong(1000);
            dataC[i] = rnd.nextLong(1000);
        }

        warmup(dataA, dataB, dataC);

        ArrayOfStrings soaFull = new ArrayOfStrings(N);
        ArrayOfRecords aosFull = new ArrayOfRecords(N);
        for (int i = 0; i < N; i++) {
            soaFull.addSoA(i, dataA[i], dataB[i], dataC[i]);
            aosFull.addAoS(i, dataA[i], dataB[i], dataC[i]);
        }

        long soaAddTime = measureAddSoA(dataA, dataB, dataC);
        long aosAddTime = measureAddAoS(dataA, dataB, dataC);
        long soaSumTime = measureSumSoA(soaFull);
        long aosSumTime = measureSumAoS(aosFull);

        printResults(soaAddTime, aosAddTime, soaSumTime, aosSumTime);
    }

    private static void warmup(long[] dataA, long[] dataB, long[] dataC) {
        for (int w = 0; w < WARMUP; w++) {
            ArrayOfStrings soa = new ArrayOfStrings(N);
            ArrayOfRecords aos = new ArrayOfRecords(N);
            for (int i = 0; i < N; i++) {
                soa.addSoA(i, dataA[i], dataB[i], dataC[i]);
                aos.addAoS(i, dataA[i], dataB[i], dataC[i]);
            }
            sink += soa.sumSoA();
            sink += aos.sumAoS();
        }
    }

    private static long measureAddSoA(long[] dataA, long[] dataB, long[] dataC) {
        ArrayOfStrings soa = new ArrayOfStrings(N);
        long t0 = System.nanoTime();
        for (int i = 0; i < N; i++) {
            soa.addSoA(i, dataA[i], dataB[i], dataC[i]);
        }
        return System.nanoTime() - t0;
    }

    private static long measureAddAoS(long[] dataA, long[] dataB, long[] dataC) {
        ArrayOfRecords aos = new ArrayOfRecords(N);   // ← было soa
        long t0 = System.nanoTime();
        for (int i = 0; i < N; i++) {
            aos.addAoS(i, dataA[i], dataB[i], dataC[i]);
        }
        return System.nanoTime() - t0;
    }

    private static long measureSumSoA(ArrayOfStrings soa) {
        long total = 0;
        for (int r = 0; r < MEASURE; r++) {
            long t0 = System.nanoTime();
            sink += soa.sumSoA();
            total += (System.nanoTime() - t0);
        }
        return total / MEASURE;
    }

    private static long measureSumAoS(ArrayOfRecords aos) {
        long total = 0;
        for (int r = 0; r < MEASURE; r++) {
            long t0 = System.nanoTime();
            sink += aos.sumAoS();
            total += (System.nanoTime() - t0);
        }
        return total / MEASURE;
    }

    private static void printResults(long soaAdd, long aosAdd, long soaSum, long aosSum) {
        System.out.println("=== Benchmark: SoA (по столбцам) vs AoS (по строкам) ===");
        System.out.printf("Параметры: N = %,d | Прогрев = %d | Замеры суммы = %d\n\n",
                N, WARMUP, MEASURE);

        System.out.println("Результаты:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-28s | %8s | %8s | %7s\n",
                "Операция", "SoA (мс)", "AoS (мс)", "Лидер");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-28s | %8.3f | %8.3f | %7s\n",
                "Добавление " + N + " записей",
                soaAdd / 1e6, aosAdd / 1e6,
                soaAdd < aosAdd ? "SoA" : "AoS");
        System.out.printf("%-28s | %8.3f | %8.3f | %7s\n",
                "Сумма по полю 'a'",
                soaSum / 1e6, aosSum / 1e6,
                soaSum < aosSum ? "SoA" : "AoS");
        System.out.println("------------------------------------------------------------");
    }
}