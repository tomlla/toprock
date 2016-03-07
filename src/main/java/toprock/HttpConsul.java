package toprock;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;

public class HttpConsul {
    public static void getRequest(String strUtl, Map<String, String> reqHeaders) {
        URL url = trying(() -> new URL(strUtl));
        HttpURLConnection urlConn;
        final HttpURLConnection conn = trying(() -> (HttpURLConnection) url.openConnection());
        trying(() -> conn.setRequestMethod("GET"));
        final int rCode = trying(() -> conn.getResponseCode());
        if (rCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("http status code is " + rCode);
        }
        InputStream rStream = trying(() -> conn.getInputStream());
        InputStreamReader isReader = new InputStreamReader(rStream, StandardCharsets.UTF_8);
        BufferedReader bufferReader = new BufferedReader(isReader);
        Stream<String> bodyStream = bufferReader.lines();
        System.out.println("--<response body>--");
        bodyStream.forEach(l -> System.out.println(l));
        System.out.println("--</response body>--");
    }

    static void puts(Object args) {
        System.out.println(args);
    }

    public static void trying(ThrowableRunner r) {
        try {
            r.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T trying(ThrowableSupplier<T> fn) {
        try {
            return fn.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static int trying(ThrowableIntSupplier fn) {
        try {
            return fn.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    interface ThrowableRunner {
        public void run() throws Throwable;
    }

    @FunctionalInterface
    interface ThrowableSupplier<T> {
        public T get() throws Throwable;
    }

    @FunctionalInterface
    interface ThrowableIntSupplier<T> {
        public int get() throws Throwable;
    }
}
