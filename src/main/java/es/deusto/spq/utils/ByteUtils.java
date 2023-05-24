package es.deusto.spq.utils;

public class ByteUtils {
    
	/**
	 * This method converts bytes to bits
	 * @param bytes
	 * @return
	 */
    public static byte[] byteArrayToBits(byte[] bytes) {
        byte[] bits = new byte[(bytes.length + 7) / 8];
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != 0) {
                bits[bits.length - i / 8 - 1] |= 1 << (i % 8);
            }
        }
        for (int i = 0; i < bits.length / 2; i++) {
            bits[i] ^= bits[bits.length - i - 1];
            bits[bits.length - i - 1] ^= bits[i];
            bits[i] ^= bits[bits.length - i - 1];
        }
        return bits;
    }

    /**
     * This method converts bits to bytes
     * @param bits
     * @return
     */
    public static byte[] bitArrayToBytes(byte[] bits) {
        byte[] bytes = new byte[bits.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            if ((bits[i / 8] & 1 << (i % 8)) != 0) {
                bytes[i] = 1;
            } else {
                bytes[i] = 0;
            }
        }
        return bytes;
    }

}
