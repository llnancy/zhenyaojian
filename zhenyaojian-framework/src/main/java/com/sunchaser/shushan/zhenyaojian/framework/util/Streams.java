package com.sunchaser.shushan.zhenyaojian.framework.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * stream utils
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
public final class Streams {

    private Streams() {
    }

    public static <T> Stream<T> of(Iterable<T> iterable, boolean parallel) {
        Objects.requireNonNull(iterable, "iterable must not be null!");
        if (iterable instanceof Collection) {
            return parallel ? ((Collection<T>) iterable).parallelStream() : ((Collection<T>) iterable).stream();
        } else {
            return StreamSupport.stream(iterable.spliterator(), parallel);
        }
    }

    public static <T> Stream<T> of(Iterable<T> iterable) {
        return of(iterable, false);
    }

    public static <T, R> List<R> mapToList(Collection<T> coll, Function<? super T, ? extends R> mapper, boolean isParallel) {
        return Streams.of(coll, isParallel)
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T, R> List<R> mapToList(Collection<T> coll, Function<? super T, ? extends R> mapper) {
        return mapToList(coll, mapper, false);
    }

    public static <T, R> Set<R> mapToSet(Collection<T> coll, Function<? super T, ? extends R> mapper, boolean isParallel) {
        return Streams.of(coll, isParallel)
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public static <T, R> Set<R> mapToSet(Collection<T> coll, Function<? super T, ? extends R> mapper) {
        return mapToSet(coll, mapper, false);
    }

    public static <K, T> Map<K, List<T>> groupingBy(Collection<T> coll, Function<? super T, ? extends K> classifier, boolean isParallel) {
        return Streams.of(coll, isParallel)
                .collect(Collectors.groupingBy(classifier));
    }

    public static <K, T> Map<K, List<T>> groupingBy(Collection<T> coll, Function<? super T, ? extends K> classifier) {
        return groupingBy(coll, classifier, false);
    }

    public static <T> List<T> filterToList(Collection<T> coll, Predicate<? super T> predicate, boolean isParallel) {
        return Streams.of(coll, isParallel)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T> List<T> filterToList(Collection<T> coll, Predicate<? super T> predicate) {
        return filterToList(coll, predicate, false);
    }

    public static <T, R> Set<R> filterAndMapToSet(Collection<T> coll,
                                                  Predicate<? super T> predicate,
                                                  Function<? super T, ? extends R> mapper,
                                                  boolean isParallel) {
        return of(coll, isParallel)
                .filter(predicate)
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public static <T, R> Set<R> filterAndMapToSet(Collection<T> coll,
                                                  Predicate<? super T> predicate,
                                                  Function<? super T, ? extends R> mapper) {
        return filterAndMapToSet(coll, predicate, mapper, false);
    }
}
