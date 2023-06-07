package redditAuth;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class XHmac {
    
    public static String c(String str, byte[] bArr) {
        try {
            Charset forName = Charset.forName("UTF-8");
            byte[] bytes = str.getBytes(forName);
            return b(a(bArr, bytes));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] a(byte[] data, byte[] signingKey) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(data, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] doFinal = mac.doFinal(signingKey);
        return doFinal;
    }

    public static String b(byte[] bArr) {
        StringBuilder sb2 = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                sb2.append('0');
            }
            sb2.append(hexString);
        }
        String sb3 = sb2.toString();
        return sb3;
    }
}
