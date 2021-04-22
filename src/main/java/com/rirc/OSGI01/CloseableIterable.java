package com.rirc.OSGI01;

import java.io.Closeable;

public interface CloseableIterable<T> extends Closeable, Iterable<T> {
}
