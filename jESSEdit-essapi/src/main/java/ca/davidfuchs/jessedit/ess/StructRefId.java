package ca.davidfuchs.jessedit.ess;

import java.math.BigInteger;
import java.util.Arrays;

public class StructRefId {
    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public short getFormId() {
        return (short) ((bytes[0] & 0b11000000) >> 6);
    }

    public int getFormValue() {
        BigInteger bigInteger = new BigInteger(bytes);

        return bigInteger.and(new BigInteger(new byte[]{(byte) 0b00111111, (byte) 0b11111111, (byte) 0b11111111})).intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StructRefId refId = (StructRefId) o;

        if (!Arrays.equals(bytes, refId.bytes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return bytes != null ? Arrays.hashCode(bytes) : 0;
    }

    @Override
    public String toString() {
        return "StructRefId{" +
                "bytes=" + bytes +
                '}';
    }
}
