/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/

package org.apache.cayenne.dba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.access.DataNode;
import org.apache.cayenne.access.OperationObserver;
import org.apache.cayenne.access.QueryLogger;
import org.apache.cayenne.access.QueryTranslator;
import org.apache.cayenne.access.trans.QualifierTranslator;
import org.apache.cayenne.access.trans.QueryAssembler;
import org.apache.cayenne.access.types.ExtendedTypeMap;
import org.apache.cayenne.dba.db2.DB2Sniffer;
import org.apache.cayenne.dba.derby.DerbySniffer;
import org.apache.cayenne.dba.frontbase.FrontBaseSniffer;
import org.apache.cayenne.dba.hsqldb.HSQLDBSniffer;
import org.apache.cayenne.dba.ingres.IngresSniffer;
import org.apache.cayenne.dba.mysql.MySQLSniffer;
import org.apache.cayenne.dba.openbase.OpenBaseSniffer;
import org.apache.cayenne.dba.oracle.OracleSniffer;
import org.apache.cayenne.dba.postgres.PostgresSniffer;
import org.apache.cayenne.dba.sqlserver.SQLServerSniffer;
import org.apache.cayenne.dba.sybase.SybaseSniffer;
import org.apache.cayenne.map.DbAttribute;
import org.apache.cayenne.map.DbEntity;
import org.apache.cayenne.map.DbRelationship;
import org.apache.cayenne.query.BatchQuery;
import org.apache.cayenne.query.Query;
import org.apache.cayenne.query.SQLAction;

/**
 * A DbAdapter that automatically detects the kind of database it is running on
 * and instantiates an appropriate DB-specific adapter, delegating all
 * subsequent method calls to this adapter.
 * 
 * @since 1.2
 * @author Andrus Adamchik
 */
public class AutoAdapter implements DbAdapter {

  // hardcoded factories for adapters that we know how to auto-detect
  static final DbAdapterFactory[] DEFAULT_FACTORIES = new DbAdapterFactory[] {
    new MySQLSniffer(),
    new PostgresSniffer(),
    new OracleSniffer(),
    new SQLServerSniffer(),
    new HSQLDBSniffer(),
    new DB2Sniffer(),
    new SybaseSniffer(),
    new DerbySniffer(),
    new OpenBaseSniffer(),
    new FrontBaseSniffer(),
    new IngresSniffer() };

  /**
   * Returns a DbAdapterFactory configured to detect all databases officially
   * supported by Cayenne.
   */
  public static DbAdapterFactory getDefaultFactory() {
    return new DbAdapterFactoryChain(Arrays.asList(DEFAULT_FACTORIES));
  }

  protected DbAdapterFactory adapterFactory;

  protected DataSource dataSource;

  protected PkGenerator pkGenerator;

  /**
   * The actual adapter that is delegated method execution.
   */
  DbAdapter adapter;

  /**
   * Creates an AutoAdapter that can detect adapters known to Cayenne.
   */
  public AutoAdapter(DataSource dataSource) {
    this(null, dataSource);
  }

  /**
   * Creates an AutoAdapter with specified adapter factory and DataSource. If
   * adapterFactory is null, default factory is used.
   */
  public AutoAdapter(DbAdapterFactory adapterFactory, DataSource dataSource) {
    // sanity check
    if (dataSource == null) {
      throw new CayenneRuntimeException("Null dataSource");
    }

    this.adapterFactory =
      adapterFactory != null ? adapterFactory : createDefaultFactory();
    this.dataSource = dataSource;
  }

  /**
   * Called from constructor to initialize factory in case no factory was
   * specified by the object creator.
   */
  protected DbAdapterFactory createDefaultFactory() {
    return getDefaultFactory();
  }

  /**
   * Returns a proxied DbAdapter, lazily creating it on first invocation.
   */
  public DbAdapter getAdapter() {
    if (adapter == null) {
      synchronized (this) {
        if (adapter == null) {
          this.adapter = loadAdapter();
        }
      }
    }

    return adapter;
  }

  /**
   * Opens a connection, retrieves JDBC metadata and attempts to guess adapter
   * form it.
   */
  protected DbAdapter loadAdapter() {
    DbAdapter adapter = null;

    try {
      Connection c = dataSource.getConnection();

      try {
        adapter = adapterFactory.createAdapter(c.getMetaData());
      } finally {
        try {
          c.close();
        } catch (SQLException e) {
          // ignore...
        }
      }
    } catch (SQLException e) {
      throw new CayenneRuntimeException("Error detecting database type", e);
    }

    if (adapter == null) {
      QueryLogger.log("Failed to detect database type, using default adapter");
      adapter = new JdbcAdapter();
    } else {
      QueryLogger.log("Detected and installed adapter: "
        + adapter.getClass().getName());
    }

    return adapter;
  }

  // ---- DbAdapter methods ----

  @Override
  public String getBatchTerminator() {
    return getAdapter().getBatchTerminator();
  }

  /**
   * @deprecated since 1.2 this method is deprecated in DbAdapter interface.
   */
  @Deprecated
  @Override
  public DataNode createDataNode(String name) {
    return getAdapter().createDataNode(name);
  }

  /**
   * @deprecated since 1.2 this method is deprecated in DbAdapter interface.
   */
  @Deprecated
  @Override
  public QueryTranslator getQueryTranslator(Query query) throws Exception {
    return getAdapter().getQueryTranslator(query);
  }

  @Override
  public QualifierTranslator getQualifierTranslator(
      QueryAssembler queryAssembler) {
    return getAdapter().getQualifierTranslator(queryAssembler);
  }

  @Override
  public SQLAction getAction(Query query, DataNode node) {
    return getAdapter().getAction(query, node);
  }

  @Override
  public boolean supportsFkConstraints() {
    return getAdapter().supportsFkConstraints();
  }

  @Override
  public boolean supportsUniqueConstraints() {
    return getAdapter().supportsUniqueConstraints();
  }

  @Override
  public boolean supportsGeneratedKeys() {
    return getAdapter().supportsGeneratedKeys();
  }

  @Override
  public boolean supportsBatchUpdates() {
    return getAdapter().supportsBatchUpdates();
  }

  @Override
  public String dropTable(DbEntity entity) {
    return getAdapter().dropTable(entity);
  }

  @Override
  public String createTable(DbEntity entity) {
    return getAdapter().createTable(entity);
  }

  @Override
  public String createUniqueConstraint(DbEntity source, Collection columns) {
    return getAdapter().createUniqueConstraint(source, columns);
  }

  @Override
  public String createFkConstraint(DbRelationship rel) {
    return getAdapter().createFkConstraint(rel);
  }

  @Override
  public String[] externalTypesForJdbcType(int type) {
    return getAdapter().externalTypesForJdbcType(type);
  }

  @Override
  public ExtendedTypeMap getExtendedTypes() {
    return getAdapter().getExtendedTypes();
  }

  /**
   * Returns a primary key generator.
   */
  @Override
  public PkGenerator getPkGenerator() {
    return (pkGenerator != null) ? pkGenerator : getAdapter().getPkGenerator();
  }

  /**
   * Sets a PK generator override. If set to non-null value, such PK generator
   * will be used instead of the one provided by wrapped adapter.
   */
  public void setPkGenerator(PkGenerator pkGenerator) {
    this.pkGenerator = pkGenerator;
  }

  @Override
  public DbAttribute buildAttribute(String name, String typeName, int type,
      int size, int precision, boolean allowNulls) {

    return getAdapter().buildAttribute(
      name,
      typeName,
      type,
      size,
      precision,
      allowNulls);
  }

  @Override
  public void bindParameter(PreparedStatement statement, Object object,
      int pos, int sqlType, int precision) throws SQLException, Exception {
    getAdapter().bindParameter(statement, object, pos, sqlType, precision);
  }

  @Override
  public String tableTypeForTable() {
    return getAdapter().tableTypeForTable();
  }

  @Override
  public String tableTypeForView() {
    return getAdapter().tableTypeForView();
  }

  /**
   * @deprecated since 1.2 this method is deprecated in DbAdapter interface.
   */
  @Deprecated
  @Override
  public boolean shouldRunBatchQuery(DataNode node, Connection con,
      BatchQuery query, OperationObserver delegate) throws SQLException,
      Exception {
    return getAdapter().shouldRunBatchQuery(node, con, query, delegate);
  }
}
