package toprock;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static toprock.tools.TopRockTools.*;

/** ComponentSystemとかDI的なもののをぼんやりと考えていた時のメモ */
@RequiredArgsConstructor
public class DIO {

    final DIOApplication app;
    final List<DIOComponentEntry> components;

    @Getter
    @Accessors(fluent = true)
    @RequiredArgsConstructor
    static class DIOComponentEntry {
        private final String name;
        private final Class<? extends DIOComponent> type;
        @Setter
        private DIOComponent obj;
    }

    interface DIOApplication {
        public void run(DIO ctx);
    }

    interface DIOComponent {

    }

    /**
     * @param components[n * 2]   : String componentName
     * @param components[n * 2 +1]: Class  componentClass
     */
    @SneakyThrows
    public static DIO setUp(Class<? extends DIOApplication> appClass,
                             Object... components) {
        List<DIOComponentEntry> componentList = toComponentListWithValidate
                (components);
        DIOApplication app = appClass.newInstance();
        return new DIO(app, componentList);
    }

    static List<DIOComponentEntry> toComponentListWithValidate(Object... args) {
        if (!even(args.length)) {
            throw new IllegalArgumentException("");
        }
        val components = new ArrayList<DIOComponentEntry>();
        for (int n = 0; n < args.length; n += 2) {
            val componentName = (String) args[n];
            val componentClass = (Class) args[n + 1];
            components.add(new DIOComponentEntry(componentName, componentClass));
        }
        return components;
    }

    public void fire() {
        initEachComponents();
        app.run(this);
    }

    private void initEachComponents() {
        this.components.forEach(c -> initComponent(c));
    }

    @SneakyThrows
    private void initComponent(DIOComponentEntry unInittedComponent) {
        DIOComponent c = (DIOComponent) unInittedComponent.type.newInstance();
        unInittedComponent.obj(c);
    }


    private DIOComponent getBean(final String componentName) {
        return components.stream()
                .filter(c -> c.name.equals(componentName))
                .findFirst()
                .map(c -> c.obj())
                .get();
    }

    static class Application implements DIOApplication {

        static class HibernateComponent implements DIOComponent {
            public Object getSession() {
                puts("built Hibernate session");
                return Collections.emptyMap();
            }
        }

        static class UndertowComponent implements DIOComponent {
            public void startServer() {
                puts("[UndertowComponent] starting web server");
            }
            public void stopServer() {
                puts("[UndertowComponent] stopping web server");
            }
        }

        public static void main(String[] args) throws Exception {
            DIO.setUp(Application.class, "hibernate-component",
                    HibernateComponent.class,
                    "undertow-component", UndertowComponent.class)
                    .fire();
        }

        @Override
        public void run(final DIO ctx) {
            val undertow = (UndertowComponent) ctx.getBean
                    ("undertow-component");
            val hibenate = (HibernateComponent) ctx.getBean("hibernate-component");

            undertow.startServer();
            undertow.stopServer();
        }
    }


}
