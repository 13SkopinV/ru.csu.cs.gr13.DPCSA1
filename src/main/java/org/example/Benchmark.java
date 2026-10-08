package org.example;

import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    static final int WARMUP = 100;

    private static int getMeasure(int n) {
        if (n <= 10_000) return 200;
        if (n <= 100_000) return 100;
        if (n <= 1_000_000) return 50;
        return 20;
    }

    static volatile long sink = 0;

    public static void main(String[] args) {
        int[] sizesN = {10000, 20000, 40000, 80000, 160000, 320000, 640000, 1280000, 2560000, 5120000};

        System.out.println("N\tSoA_add_ms\tAoS_add_ms\tSoA_sum_ms\tAoS_sum_ms");

        for (int n : sizesN) {
            Random rnd = new Random(42);
            long[] dataA = new long[n];
            long[] dataB = new long[n];
            long[] dataC = new long[n];
            for (int i = 0; i < n; i++) {
                dataA[i] = rnd.nextLong(1000);
                dataB[i] = rnd.nextLong(1000);
                dataC[i] = rnd.nextLong(1000);
            }

            warmup(dataA, dataB, dataC, n);

            ArrayOfStrings soaFull = new ArrayOfStrings(n);
            ArrayOfRecords aosFull = new ArrayOfRecords(n);
            for (int i = 0; i < n; i++) {
                soaFull.addSoA(i, dataA[i], dataB[i], dataC[i]);
                aosFull.addAoS(i, dataA[i], dataB[i], dataC[i]);
            }

            long soaAddTime = measureAddSoA(dataA, dataB, dataC, n);
            long aosAddTime = measureAddAoS(dataA, dataB, dataC, n);
            long soaSumTime = measureSumSoA(soaFull, n);
            long aosSumTime = measureSumAoS(aosFull, n);

            System.out.printf("%d\t%.3f\t%.3f\t%.3f\t%.3f\n",
                    n, soaAddTime / 1e6, aosAddTime / 1e6, soaSumTime / 1e6, aosSumTime / 1e6);
        }
    }

    private static void warmup(long[] dataA, long[] dataB, long[] dataC, int n) {
        for (int w = 0; w < WARMUP; w++) {
            ArrayOfStrings soa = new ArrayOfStrings(n);
            ArrayOfRecords aos = new ArrayOfRecords(n);
            for (int i = 0; i < n; i++) {
                soa.addSoA(i, dataA[i], dataB[i], dataC[i]);
                aos.addAoS(i, dataA[i], dataB[i], dataC[i]);
            }
            sink += soa.sumSoA();
            sink += aos.sumAoS();
        }
    }

    private static long measureAddSoA(long[] dataA, long[] dataB, long[] dataC, int n) {
        int MEASURE = getMeasure(n);
        long[] times = new long[MEASURE];

        for (int r = 0; r < MEASURE; r++) {
            ArrayOfStrings soa = new ArrayOfStrings(n);
            long t0 = System.nanoTime();
            for (int i = 0; i < n; i++) {
                soa.addSoA(i, dataA[i], dataB[i], dataC[i]);
            }
            times[r] = System.nanoTime() - t0;
            sink += soa.sumSoA();
        }
        Arrays.sort(times);
        return times[MEASURE / 2];
    }

    private static long measureAddAoS(long[] dataA, long[] dataB, long[] dataC, int n) {
        int MEASURE = getMeasure(n);
        long[] times = new long[MEASURE];
        for (int r = 0; r < MEASURE; r++) {
            ArrayOfRecords aos = new ArrayOfRecords(n);
            long t0 = System.nanoTime();
            for (int i = 0; i < n; i++) {
                aos.addAoS(i, dataA[i], dataB[i], dataC[i]);
            }
            times[r] = System.nanoTime() - t0;
            sink += aos.sumAoS();
        }
        Arrays.sort(times);
        return times[MEASURE / 2];
    }

    private static long measureSumSoA(ArrayOfStrings soa, int n) {
        int MEASURE = getMeasure(n);
        long[] times = new long[MEASURE];
        for (int r = 0; r < MEASURE; r++) {
            long t0 = System.nanoTime();
            sink += soa.sumSoA();
            times[r] = System.nanoTime() - t0;
        }
        Arrays.sort(times);
        return times[MEASURE / 2];
    }

    private static long measureSumAoS(ArrayOfRecords aos, int n) {
        int MEASURE = getMeasure(n);
        long[] times = new long[MEASURE];
        for (int r = 0; r < MEASURE; r++) {
            long t0 = System.nanoTime();
            sink += aos.sumAoS();
            times[r] = System.nanoTime() - t0;
        }
        Arrays.sort(times);
        return times[MEASURE / 2];
    }

}