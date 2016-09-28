/*
 * Aipo is a groupware program developed by TOWN, Inc.
 * Copyright (C) 2004-2015 TOWN, Inc.
 * http://www.aipo.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aimluck.eip.orm.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cayenne.DataRow;
import org.apache.cayenne.access.DataContext;

import com.aimluck.eip.orm.Database;

public class SQLTemplate<M> extends AbstractQuery<M> {

  protected org.apache.cayenne.query.SQLTemplate delegate;

  protected Map<String, Object> parameters = new HashMap<String, Object>();

  public SQLTemplate(Class<M> rootClass, String sql) {
    super(rootClass);
    delegate = new org.apache.cayenne.query.SQLTemplate(rootClass, sql);
    delegate.setFetchingDataRows(true);
    dataContext = DataContext.getThreadDataContext();
  }

  public SQLTemplate(DataContext dataContext, Class<M> rootClass, String sql) {
    super(dataContext, rootClass);
    delegate = new org.apache.cayenne.query.SQLTemplate(rootClass, sql);
    delegate.setFetchingDataRows(true);
    this.dataContext = dataContext;
  }

  public void execute() {
    Database.beginTransaction(dataContext);
    delegate.setParameters(parameters);
    dataContext.performQuery(delegate);
    Database.commit(dataContext);
  }

  @Override
  public List<M> fetchList() {
    Database.beginTransaction(dataContext);
    delegate.setParameters(parameters);
    @SuppressWarnings("unchecked")
    List<org.apache.cayenne.DataRow> dataRows =
      dataContext.performQuery(delegate);
    List<M> results = new ArrayList<M>(dataRows.size());
    for (org.apache.cayenne.DataRow dataRow : dataRows) {
      M model = newInstanceFromRowData(dataRow, rootClass);
      if (model != null) {
        results.add(model);
      }
    }
    return results;
  }

  @SuppressWarnings("unchecked")
  public List<DataRow> fetchListAsDataRow() {
    Database.beginTransaction(dataContext);
    delegate.setParameters(parameters);
    return dataContext.performQuery(delegate);
  }

  public SQLTemplate<M> pageSize(int pageSize) {
    delegate.setPageSize(pageSize);
    return this;
  }

  public SQLTemplate<M> limit(int limit) {
    delegate.setFetchLimit(limit);
    return this;
  }

  public SQLTemplate<M> param(String key, Object value) {
    parameters.put(key, value);
    return this;
  }

  public org.apache.cayenne.query.SQLTemplate getSQLTemplate() {
    return delegate;
  }
}
