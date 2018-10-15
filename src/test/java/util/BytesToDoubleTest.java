package util;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BytesToDoubleTest {

    private final static double DELTA = 0.000000001;

    @Test
    public void convertSingle() {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        byte[] bytes = buffer.putDouble(90.0).array();
        assertEquals(BytesToDouble.convertSingle(bytes), 90.0, DELTA);
    }

    @Test
    public void convertMany() {
        int numDoubles = 3;
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES * numDoubles);

        double[] doubles = new double[numDoubles];
        for (int i = 0; i < numDoubles; i++) {
            double curr = (double) i;
            doubles[i] = curr;
            buffer.putDouble(curr);
        }
        assertArrayEquals(BytesToDouble.convertMany(buffer.array(), numDoubles), doubles, DELTA);
    }


    @Test
    public void illegalByteArraySingle() {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        byte[] bytes = buffer.putInt(90).array();
        assertThrows(IllegalArgumentException.class,
                () -> BytesToDouble.convertSingle(bytes),
                "Byte array length is not " + Double.BYTES);
    }

    @Test
    public void illegalByteArrayMany() {
        int numDoubles = 3;
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);

        byte[] bytes = buffer.putInt(90).array();
        assertThrows(IllegalArgumentException.class,
                () -> BytesToDouble.convertMany(bytes, numDoubles),
                "Byte array length is not " + Double.BYTES * numDoubles);
    }
}