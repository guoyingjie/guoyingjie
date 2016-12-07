package com.broadeast.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RadMd5 {

	public RadMd5() {
		// TODO Auto-generated constructor stub
	}

public static String md52(String inputStr,byte[] bytes) throws NoSuchAlgorithmException{
	   String md5Str = inputStr;
	   if(inputStr != null){
	      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	      messageDigest.update(join(bytes,inputStr.getBytes()));
	      BigInteger hash = new BigInteger(1, messageDigest.digest());
	      md5Str = hash.toString(16);
	      if((md5Str.length() % 2) != 0){
	         md5Str = "0" + md5Str;
	      }
	  }
	  return md5Str;
	}
public static String md5(String inputStr,byte[] bytes) throws NoSuchAlgorithmException{
	   String md5Str = inputStr;
	   if(inputStr != null){
	      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	      messageDigest.update(join(inputStr.getBytes(),bytes));
	      BigInteger hash = new BigInteger(1, messageDigest.digest());
	      md5Str = hash.toString(16);
	      if((md5Str.length() % 2) != 0){
	         md5Str = "0" + md5Str;
	      }
	  }
	  return md5Str;
	}
private static byte[] join(byte[] a, byte[] b) {
    // join two byte arrays
    final byte[] ret = new byte[a.length + b.length];
    System.arraycopy(a, 0, ret, 0, a.length);
    System.arraycopy(b, 0, ret, a.length, b.length);
    return ret;
}
public static byte[] pack(String str) {
      int nibbleshift = 4;
      int position = 0;
      int len = str.length() / 2 + str.length() % 2;
      byte[] output = new byte[len];
      for (char v : str.toCharArray()) {
          byte n = (byte) v;
          if (n >= '0' && n <= '9') {
              n -= '0';
          } else if (n >= 'A' && n <= 'F') {
              n -= ('A' - 10);
          } else if (n >= 'a' && n <= 'f') {
              n -= ('a' - 10);
          } else {
              continue;
          }
          output[position] |= (n << nibbleshift);
          if (nibbleshift == 0) {
              position++;
          }
          nibbleshift = (nibbleshift + 4) & 7;
      }
      return output;
  }

/**
    * 16进制的字符解压 类php中unpack
    *
    * @param is
    * @param len
    * @return
    * @throws IOException
    */
   public static String unpack(InputStream is, int len) throws IOException {
       byte[] bytes = new byte[len];
       is.read(bytes);
       return unpack(bytes);
   }

   /***
    * 16进制的字符解压  类php中unpack
    * @param bytes
    * @return
    */
   public static String unpack(byte[] bytes) {
       StringBuilder stringBuilder = new StringBuilder("");
       if (bytes == null || bytes.length <= 0) {
           return null;
       }
       for (int i = 0; i < bytes.length; i++) {
           int v = bytes[i] & 0xFF;
           String hv = Integer.toHexString(v);
           if (hv.length() < 2) {
               stringBuilder.append(0);
           }
           stringBuilder.append(hv);
       }
       return stringBuilder.toString();
   }
}
