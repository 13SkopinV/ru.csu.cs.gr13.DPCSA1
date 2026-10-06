package org.example;

public class ArrayOfStrings {
    public long[] a;
    public long[] b;
    public long[] c;

    ArrayOfStrings(int n) {
        this.a = new long[n];
        this.b = new long[n];
        this.c = new long[n];
    }

    public void addSoA(int idx, long a, long b, long c) {
        this.a[idx] = a;
        this.b[idx] = b;
        this.c[idx] = c;
    }

    public long sumSoA() {
        long sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }
}
