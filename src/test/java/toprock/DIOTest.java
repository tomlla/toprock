package toprock;

import lombok.val;
import org.junit.Test;

import static toprock.tools.TopRockTools.puts;

public class DIOTest {
    @Test
    public void test_run_app() throws Exception {
        val args = new String[0];
        DIO.Application.main(args);
    }

    @Test
    public void test_diocomponent() throws Exception {
        val component = new DIO.DIOComponentEntry("x", SampleComponent.class);
        val componentClass = component.type();
        component.obj((DIO.DIOComponent) componentClass.newInstance());

        SampleComponent sampleComponent = (SampleComponent) component.obj();
        puts("got component object's class : " + sampleComponent.getClass()
                .getName());
    }

    static class SampleComponent implements DIO.DIOComponent {
    }
}