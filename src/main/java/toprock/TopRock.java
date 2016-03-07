package toprock;

import lombok.Value;
import lombok.experimental.Accessors;
import lombok.val;

public class TopRock {
    public static void main(String[] args) {
        System.out.println("Hello");
        val v =  new VO(1);
        System.out.println(v.i());
    }

    @Accessors(fluent = true)
    @Value
    public static class VO {
        private int i;
    }
}
