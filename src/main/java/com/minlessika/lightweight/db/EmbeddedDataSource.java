/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020-2021 Minlessika, Inc.
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

package com.minlessika.lightweight.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 * Embedded PostgreSQL DataSource.
 * <p>It's based on H2 database features.
 * We use HikariCP for Connection Pooling.
 * @since 0.1
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
public final class EmbeddedDataSource extends DataSourceWrap {

    /**
     * Default maximum pool size.
     */
    @SuppressWarnings("PMD.LongVariable")
    public static final int DEFAULT_MAX_POOL_SIZE = 2;

    /**
     * Mode.
     */
    private final String mode;

    /**
     * If it has been initialized.
     */
    private volatile boolean initialized;

    /**
     * Ctor.
     * @param dbname Database name
     * @param mode Mode
     * @param maxpoolsize Max pool size
     */
    public EmbeddedDataSource(final String dbname, final String mode, final int maxpoolsize) {
        super(makeDataSource(dbname, mode, maxpoolsize));
        this.mode = mode;
    }

    @Override
    public Connection getConnection() throws SQLException {
        this.tryToInitialize();
        return super.getConnection();
    }

    @Override
    public Connection getConnection(
        final String username,
        final String password
    ) throws SQLException {
        this.tryToInitialize();
        return super.getConnection(username, password);
    }

    @Override
    public String toString() {
        return String.format("Embedded %s database.", this.mode);
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
     * @param dbname Database name
     * @param mode Mode
     * @param maxpoolsize Max pool size
     * @return Data source
     */
    private static DataSource makeDataSource(
        final String dbname,
        final String mode,
        final int maxpoolsize
    ) {
        final HikariConfig configdb = new HikariConfig();
        configdb.setJdbcUrl(
            String.format(
                "jdbc:h2:~/%s;MODE=%s;DATABASE_TO_LOWER=TRUE",
                dbname,
                mode
            )
        );
        configdb.setDriverClassName("org.h2.Driver");
        configdb.setMaximumPoolSize(maxpoolsize);
        return new HikariDataSource(configdb);
    }
}
