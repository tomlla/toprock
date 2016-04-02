/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toprock;

import java.util.Objects;

/**
 * Optionalがない場合のことをかんがえていた
 * 古いandroid環境や, java7環境でどうすべきか
 * <p>
 * Optional.map(...)を考えているとlambdaのない環境で
 * 汎用的なOptional<T>のようなクラスを用意するのはメリットがあまりないと感じた
 */
public class OptionalMyImpl {

    static class Optional<T> {
        final T o;

        public static <T> Optional<T> of(T o) {
            Objects.requireNonNull(o);
            return new Optional<T>(o);
        }

        public static <T> Optional<T> ofNullable(T o) {
            return new Optional<T>(o);
        }

        public static <T> Optional<T> empty() {
            return new Optional<T>(null);
        }

        private Optional(T o) {
            this.o = o;
        }

        public <R> Optional<R> map(MyFn<T, R> fn) {
            if (o == null) {
                return Optional.empty();
            }
            R appliedValue = fn.apply(o);
            return Optional.of(appliedValue);
        }

        public T get() {
            Objects.requireNonNull(o);
            return o;
        }

        public T getOr(T elseValue) {
            return o != null ? o : elseValue;
        }
    }

    public interface MyFn<T, R> {
        public R apply(T o);
    }
}
