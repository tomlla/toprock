package toprock;

import lombok.NonNull;
import lombok.val;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TopRockTest {

    @Test
    public void test() {
        new FooSerive().exec();
    }

    private static class FooSerive implements MyLogInterface {
        public void exec() {
            final List<Integer> list = Arrays.asList(1);
//            list.forEach(i -> log("i = %d", i));

            for (val x : list) {
                MyLogInterface.printST("for (x : list) {..}");
            }

            list.forEach(i -> MyLogInterface.printST("forEach()-lambda"));
            Runnable r = () -> MyLogInterface.printST(" call with instanced runnbale object");
            r.run();

            InnerTopRockTest.run();

            new Object() {
                public void lambda$() {
                    MyLogInterface.printST("anonymouse class");
                }
            }.lambda$();

            new Object() {
                public void call() {
                    list.forEach(i -> MyLogInterface.printST("anonymouse class lambda"));
                }
            }.call();
        }

        static class InnerTopRockTest {
            static void run() {
                Arrays.asList(1).forEach(i -> MyLogInterface.printST("inner class lambda"));
            }
        }
    }

    public interface MyLogInterface {

        // default void log(@NonNull String fmt, Object... args) {
        default public void log(@NonNull String fmt, Object... args) {
            val className = getClass().getSimpleName();
            val prefix = "[" + className + "]";
            System.out.println(String.format(prefix + fmt, args));
        }

        public static void printST(String callfrom) {
            System.out.println(callfrom);
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (int i = 2; i < 6; i++) {
                val className = stackTrace[i].getClassName();
                val methodName = stackTrace[i].getMethodName();
                System.out.printf("[%d] %s::%s\n", i, className, methodName);
            }
        }

        public static void logInfo(String fmt, Object... args) {
            val className = null;
            val prefix = "[" + className + "]";
            System.out.println(String.format(prefix + fmt, args));
        }
    }

}