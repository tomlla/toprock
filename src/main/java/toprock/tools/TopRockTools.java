package toprock.tools;

public class TopRockTools {
    public static String fmt(String fmt, Object... args) {
        return String.format(fmt, args);
    }

    public static void puts(String str){
        System.out.println(str);
    }

    public static boolean even(int n) {
        return (n % 2) == 0;
    }
}
