package org.example;

public class ArrayOfRecords {
    public Record[] records;

    public ArrayOfRecords(int n) {
        this.records = new Record[n];
    }

    public void addAoS(int idx, long a, long b, long c) {
        records[idx] = new Record(a, b, c);
    }

    public long sumAoS () {
        long sum = 0;
        for (int i = 0; i < records.length; i++) {
            sum += records[i].a;
        }
        return sum;
    }
}
