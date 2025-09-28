package org.example.metrics;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter implements AutoCloseable {
    private final FileWriter writer;
    private boolean headerWritten = false;

    public CSVWriter(String fileName) throws IOException {
        writer = new FileWriter(fileName, true);
    }

    public void writeHeader() throws IOException {
        if (!headerWritten) {
            writer.write("time,n,algo,depth,comps,moves,seed,notes\n");
            headerWritten = true;
        }
    }

    public void writeRow(long time, int n, String algo,
                         int depth, long comps, long moves,
                         long seed, String notes) throws IOException {
        writeHeader();
        writer.write(String.format("%d,%d,%s,%d,%d,%d,%d,%s\n",
                time, n, algo, depth, comps, moves, seed, notes));
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}