package org.jboss.hal.testsuite.dmr;

import java.util.LinkedList;

/**
 * A context able to {@linkplain AddressTemplate#resolve(StatementContext, String...) resolve} patterns in an {@link
 * AddressTemplate}.
 * @deprecated use creaper
 */
public interface StatementContext {

    /**
     * Get a value matching the key.
     * Scoped to current context.
     */
    String get(String key);

    /**
     * Get a tuple matching the key.
     * Scoped to current context.
     */
    String[] getTuple(String key);

    /**
     * Resolves a value matching the key.
     * In a hierarchy of contexts this will match the first occurrence.
     */
    String resolve(String key);

    /**
     * Resolves a tuple matching the key.
     * In a hierarchy of contexts this will match the first occurrence.
     */
    String[] resolveTuple(String key);

    /**
     * Collects all values matching a key.
     * In a hierarchy of contexts the list will be sorted from child (n) to parent (n+1).
     * n being the list index.
     */
    LinkedList<String> collect(String key);

    /**
     * Collects all tuples matching a key.
     * In a hierarchy of contexts the list will be sorted from child (n) to parent (n+1).
     * n being the list index.
     */
    LinkedList<String[]> collectTuples(String key);
}
