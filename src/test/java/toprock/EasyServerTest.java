package toprock;

import org.junit.Test;

import static org.junit.Assert.*;

public class EasyServerTest {

    @Test
    public void test() {
        EasyServer server = new EasyServer(4000);
        server.start();
        server.stop();
    }
}