package toprock;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static org.junit.Assert.*;

public class HttpConsulTest {
    @Test
    public void consul() throws MalformedURLException {
        HttpConsul.getRequest("https://gitignore.io/api", Collections.emptyMap());
        HttpConsul.getRequest("http://www.ne.jp/asahi/hishidama/home/tech/git/index.html",


                Collections.emptyMap());
    }

}