package dev.allisson.algasensors.device.management.common;

import io.hypersistence.tsid.TSID;

public final class IdGenerator {
    private static final TSID.Factory TSID_FACTORY = TSID.Factory.builder().build();

    private IdGenerator() {
        // Prevent instantiation
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static TSID generateId() {
        return TSID_FACTORY.generate();
    }
}
