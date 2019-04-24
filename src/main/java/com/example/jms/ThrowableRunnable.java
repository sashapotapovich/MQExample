package com.example.jms;

public interface ThrowableRunnable<E extends Exception> {
    void run() throws E;

    default Runnable toRunnable() {
        return () -> WrappedRuntimeException.run(this);
    }
}
