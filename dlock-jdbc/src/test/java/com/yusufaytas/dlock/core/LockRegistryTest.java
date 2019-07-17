package com.yusufaytas.dlock.core;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class LockRegistryTest {

    @Test(expected = NullPointerException.class)
    public void requireNonNullParameter() {
        LockRegistry.setLock(null);
    }

    @Test
    public void expectEmptyLock() {
        assertFalse(LockRegistry.getLock().isPresent());
    }
}


