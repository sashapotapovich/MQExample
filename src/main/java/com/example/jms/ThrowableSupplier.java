package com.example.jms;

import java.util.function.Supplier;

public interface ThrowableSupplier<T, E extends Exception> {
    T get() throws E;

    default Supplier<T> toSupplier() {
        return () -> WrappedRuntimeException.get(this);
    }
}