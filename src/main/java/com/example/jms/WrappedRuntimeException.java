package com.example.jms;

public class WrappedRuntimeException extends RuntimeException {
    public WrappedRuntimeException(Exception cause) {
        super(cause);
    }

    @Override
    public Exception getCause() {
        return (Exception) super.getCause();
    }

    public static <T, E extends Exception> T get(ThrowableSupplier<T, E> f) {
        try {
            return f.get();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new WrappedRuntimeException(ex);
        }
    }

    public static <E extends Exception> void run(ThrowableRunnable<E> f) {
        try {
            f.run();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new WrappedRuntimeException(ex);
        }
    }
}
