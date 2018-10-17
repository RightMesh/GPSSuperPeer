package util;

import java.nio.ByteBuffer;

public class BytesToDouble {
    static double convertSingle(byte[] bytes) {
        if (bytes.length != Double.BYTES) {
            throw new IllegalArgumentException("Byte array length is not " + Double.BYTES);
        }
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static double[] convertMany(byte[] bytes, int numDoubles) {
        if (bytes.length != Double.BYTES * numDoubles) {
            throw new IllegalArgumentException("Byte array length is not " + Double.BYTES * numDoubles);
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        double[] doubles = new double[numDoubles];
        for (int i = 0; i < numDoubles; i++) {
            doubles[i] = buffer.getDouble();
        }

        return doubles;
    }
}
