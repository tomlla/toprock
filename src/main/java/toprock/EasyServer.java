package toprock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class EasyServer {
    HttpServer server;

    public static void main(String[] args) throws IOException {
    }

    @SneakyThrows
    EasyServer (int port) {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/hello", r(t -> {
            System.out.println("hello");
            String httpMehotd = t.getRequestMethod();
            OutputStream responseOutStream = t.getResponseBody();
            responseOutStream.write("hello!".getBytes());
            responseOutStream.close();
        }));
        server.setExecutor(null);
    }

    void start() {
        server.start();
    }
    void stop() {
        val waitTime = 1;
        server.stop(waitTime);
    }

    public static EasyHandler r(ThrowingConsumer<HttpExchange> controller) {
        return new EasyHandler(controller);
    }

    @RequiredArgsConstructor
    public static class EasyHandler implements HttpHandler {
        private final ThrowingConsumer<HttpExchange> controller;

        @Override
        public void handle(final HttpExchange httpExchange) throws IOException {
            try {
                controller.accept(httpExchange);
            }catch (IOException ioe) {
                throw ioe;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FunctionalInterface
    interface ThrowingConsumer<T> {
        void accept(T t) throws Exception;
    }
}
