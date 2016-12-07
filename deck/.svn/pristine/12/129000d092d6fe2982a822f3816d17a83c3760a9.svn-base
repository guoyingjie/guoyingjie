package com.broadeast.util;

public class PortalUtil
{
  public static String Getbyte2HexString(byte[] b)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
    }
    return "[" + sb.toString() + "]";
  }

  public static String Getbyte2MacString(byte[] b) {
    StringBuilder sb = new StringBuilder();
    sb.append("");
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
      if (i < b.length - 1) {
        sb.append(":");
      }
    }
    return sb.toString();
  }

  public static byte[] SerialNo() {
    byte[] SerialNo = new byte[2];
    short SerialNo_int = (short)(int)(1.0D + Math.random() * 32767.0D);
    for (int i = 0; i < 2; i++) {
      int offset = (SerialNo.length - 1 - i) * 8;
      SerialNo[i] = (byte)(SerialNo_int >>> offset & 0xFF);
    }
    return SerialNo;
  }

  public static byte[] SerialNo(short SerialNo_int) {
    byte[] SerialNo = new byte[2];
    for (int i = 0; i < 2; i++) {
      int offset = (SerialNo.length - 1 - i) * 8;
      SerialNo[i] = (byte)(SerialNo_int >>> offset & 0xFF);
    }
    return SerialNo;
  }
}