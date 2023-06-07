package redditAuth;

import java.util.Locale;
import java.util.UUID;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final String formatting(String str, long j12) {
        String format = String.format(Locale.US, "%d:%s:%d:%d:%s",
                Arrays.copyOf(new Object[] { 1, "android", 2, Long.valueOf(j12), str }, 5));
        return format;
    }

    public static void main(String[] args) {
        Locale locale = Locale.US;
        String json = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", args[0], args[1]);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        String decryptSigningKey = "8c7abaa5f905f70400c81bf3a1a101e75f7210104b1991f0cd5240aa80c4d99d";
        byte[] bytes = decryptSigningKey.getBytes(Charset.forName("UTF-8"));

        String format = String.format(locale, "Epoch:%d|Body:%s",
                Arrays.copyOf(new Object[] { Long.valueOf(seconds), json }, 2));
        String hmacFormat = XHmac.c(format, bytes);
        String headerFormat = formatting(hmacFormat, seconds);
        System.out.println("X-hmac-signed-body: " + headerFormat);

        // arbitrary version
        String userAgent = "Reddit/Version 2023.21.0/Build 956283/Android 11";
        String clientVendorID = getDeviceID();

        String format2 = String.format(locale, "Epoch:%d|User-Agent:%s|Client-Vendor-ID:%s",
                Arrays.copyOf(new Object[] { Long.valueOf(seconds), userAgent, clientVendorID }, 3));
        String hmacFormat2 = XHmac.c(format2, bytes);
        String headerFormat2 = formatting(hmacFormat2, seconds);
        System.out.println("X-hmac-signed-result: " + headerFormat2);
    }

    public static String getDeviceID() {
        return UUID.randomUUID().toString();
    }
}
