/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020-2021 baudoliver7.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.lightweight.db;

/**
 * Random database name.
 * @since 0.1
 */
public final class RandomDatabaseName {

    /**
     * Minimum of random interval.
     */
    private static final int DEFAULT_MIN = 0;

    /**
     * Maximum of random interval.
     */
    private static final int DEFAULT_MAX = 1_000_000;

    /**
     * Minimum of random interval.
     */
    private final int min;

    /**
     * Maximum of random interval.
     */
    private final int max;

    /**
     * Ctor.
     * <p>Random interval.
     */
    public RandomDatabaseName() {
        this(
            RandomDatabaseName.DEFAULT_MIN,
            RandomDatabaseName.DEFAULT_MAX
        );
    }

    /**
     * Ctor.
     * @param min Minimum of random interval
     * @param max Maximum of maximum interval
     */
    public RandomDatabaseName(final int min, final int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Value.
     * @return Name
     */
    public String value() {
        final int randomnumber = this.min + (int) (Math.random() * ((this.max - this.min) + 1));
        return String.format(
            "test_db%s_%s",
            Thread.currentThread().getId(),
            randomnumber
        );
    }
}
