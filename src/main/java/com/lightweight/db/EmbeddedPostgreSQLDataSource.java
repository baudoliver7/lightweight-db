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

import com.baudoliver7.jdbc.toolset.wrapper.DataSourceWrap;

/**
 * Embedded PostgreSQL DataSource.
 * @see <a href="http://h2database.com/html/features.html?highlight=PostgreSQL%20Compatibility%20Mode&search=PostgreSQL%20Compatibility%20Mode#firstFound">H2 - PostgreSQL Compatibility Mode</a>
 * @since 0.1
 * @checkstyle AbbreviationAsWordInNameCheck (100 lines)
 */
public final class EmbeddedPostgreSQLDataSource extends DataSourceWrap {

    /**
     * Ctor.
     */
    public EmbeddedPostgreSQLDataSource() {
        this(new RandomDatabaseName().value());
    }

    /**
     * Ctor.
     * @param dbname Database name
     */
    public EmbeddedPostgreSQLDataSource(final String dbname) {
        super(
            new EmbeddedDataSource(
                String.format(
                    "jdbc:h2:~/%s;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
                    dbname
                )
            )
        );
    }
}
