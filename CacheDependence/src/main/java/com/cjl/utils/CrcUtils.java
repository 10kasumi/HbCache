package com.cjl.utils;

public class CrcUtils {

    public static short CRC16(char[] data) {
        int len = data.length;
        short crc = 0xfff;
        for (int i = 0; i < len; ++i) {
            crc ^= (short) data[i] << 8;
            for (int j = 0; j < 8; ++j) {
                if ((crc & 0x8000) != 0) {
                    crc = (short) ((crc << 1) ^ 0x1021);
                } else {
                    crc <<= 1;
                }
            }
        }
        return crc >= 0 ? crc : (short) -crc;
    }
}
