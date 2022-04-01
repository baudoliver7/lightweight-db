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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Embedded PostgreSQL DataSource.
 * <p>It's based on H2 database features.
 * We use HikariCP for connection pooling.
 * @since 0.1
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
public final class EmbeddedDataSource extends DataSourceWrap {

    /**
     * If it has been initialized.
     */
    private volatile boolean initialized;

    /**
     * Ctor.
     * @param url Url
     */
    public EmbeddedDataSource(final String url) {
        super(makeDataSource(url));
    }

    @Override
    public Connection getConnection() throws SQLException {
        this.tryToInitialize();
        return super.getConnection();
    }

    @Override
    public Connection getConnection(
        final String username, final String password
    ) throws SQLException {
        this.tryToInitialize();
        return super.getConnection(username, password);
    }

    /**
     * Try to initialize.
     * @throws SQLException If fails
     */
    private void tryToInitialize() throws SQLException {
        if (!this.initialized) {
            synchronized (EmbeddedDataSource.class) {
                if (!this.initialized) {
                    try (
                        Connection connection = super.getConnection()
                    ) {
                        try (Statement s = connection.createStatement()) {
                            s.execute("drop all objects delete files");
                        }
                    } finally {
                        this.initialized = true;
                    }
                }
            }
        }
    }

    /**
     * Make data source.
     * @param url Url
     * @return Data source
     */
    private static DataSource makeDataSource(final String url) {
        final JdbcDataSource src = new JdbcDataSource();
        src.setUrl(url);
        return src;
    }
}
