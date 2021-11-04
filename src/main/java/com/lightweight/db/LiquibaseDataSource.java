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

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * DataSource upgraded with liquibase.
 * @since 0.1
 */
public final class LiquibaseDataSource extends DataSourceWrap {

    /**
     * Change Log relative path.
     */
    private final String chglogpath;

    /**
     * If it has been initialized.
     */
    private volatile boolean initialized;

    /**
     * Ctor.
     * @param origin Origin
     * @param chglogpath Change log relative path
     */
    public LiquibaseDataSource(final DataSource origin, final String chglogpath) {
        super(origin);
        this.chglogpath = chglogpath;
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

    /**
     * Try to initialize.
     * @throws SQLException If fails
     */
    private void tryToInitialize() throws SQLException {
        if (!this.initialized) {
            synchronized (LiquibaseDataSource.class) {
                if (!this.initialized) {
                    try (
                        Connection connection = super.getConnection()
                    ) {
                        final liquibase.database.Database database =
                            DatabaseFactory.getInstance()
                                .findCorrectDatabaseImplementation(
                                    new JdbcConnection(connection)
                                );
                        final Liquibase liquibase =
                            new Liquibase(
                                this.chglogpath,
                                new ClassLoaderResourceAccessor(),
                                database
                            );
                        liquibase.update("");
                    } catch (final SQLException ex) {
                        throw new SQLException(
                            String.format(
                                "Error while getting database connexion (liquibase database %s).",
                                this.chglogpath
                            ),
                            ex
                        );
                    } catch (final liquibase.exception.DatabaseException ex) {
                        throw new SQLException(
                            String.format(
                                "Error while getting liquibase instance (changelog : %s).",
                                this.chglogpath
                            ),
                            ex
                        );
                    } catch (final LiquibaseException ex) {
                        throw new SQLException(
                            String.format(
                                "Error while loading liquibase changelog (%s).",
                                this.chglogpath
                            ),
                            ex
                        );
                    } finally {
                        this.initialized = true;
                    }
                }
            }
        }
    }
}
