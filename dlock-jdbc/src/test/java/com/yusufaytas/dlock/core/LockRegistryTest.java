/**
 * Copyright © 2019 Yusuf Aytas. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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


