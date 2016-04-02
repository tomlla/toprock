package toprock;

import lombok.val;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;
import static toprock.OptionalMyImpl.Optional.ofNullable;

public class OptionalMyImplTest {

    @Test
    public void sampleUseMyFn() {
        val countStringLength = new OptionalMyImpl.MyFn<String, Integer>() {
            @Override
            public Integer apply(final String o) {
                if (o == null) {
                    return 0;
                }
                return o.length();
            }
        };
        Integer strLen = countStringLength.apply("xxx");
        MatcherAssert.assertThat(strLen, is(3));
    }

    @Test
    public void test_absent() {
        Integer stringLen = ofNullable("John")
                .map(new OptionalMyImpl.MyFn<String, Integer>() {
                    @Override
                    public Integer apply(final String o) {
                        return o.length();
                    }
                })
                .get();
        System.out.println("stringLen:" + stringLen);
        MatcherAssert.assertThat(stringLen, is(4));
    }

    @Test
    public void test_null_get() {
        OptionalMyImpl.Optional<String> optionalName = OptionalMyImpl
                .Optional.ofNullable((String) null);
        OptionalMyImpl.Optional<Integer> optionalNameLen = optionalName.map(new OptionalMyImpl.MyFn<String, Integer>() {
            @Override
            public Integer apply(final String o) {
                return o.length();
            }
        });
        try {
            optionalNameLen.get();
        } catch (NullPointerException npe) {
            // test is success.
            return;
        }
        fail();
    }
}