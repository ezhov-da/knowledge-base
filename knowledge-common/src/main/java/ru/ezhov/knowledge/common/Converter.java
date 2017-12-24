package ru.ezhov.knowledge.common;

import java.util.List;

public interface Converter<T, V> {
    List<T> convert(V value);
}
