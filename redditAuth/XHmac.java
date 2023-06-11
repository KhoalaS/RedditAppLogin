package redditAuth;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class XHmac {

    public static String getSignedHexString(String data, byte[] key) {
        try {
            Charset charset = Charset.forName("UTF-8");
            byte[] bytes = data.getBytes(charset);
            return toHexString(signMessage(key, bytes));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] signMessage(byte[] key, byte[] msg)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] doFinal = mac.doFinal(msg);
        return doFinal;
    }

    public static String toHexString(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(hexString);
        }
        String result = stringBuilder.toString();
        return result;
    }
}
