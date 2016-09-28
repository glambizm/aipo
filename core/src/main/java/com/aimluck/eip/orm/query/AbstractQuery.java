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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.DataRow;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.map.DbAttribute;
import org.apache.cayenne.map.DbEntity;
import org.apache.cayenne.map.ObjAttribute;
import org.apache.cayenne.map.ObjEntity;

import com.aimluck.eip.orm.Database;

public abstract class AbstractQuery<M> implements Query<M> {

  protected DataContext dataContext;

  protected Class<M> rootClass;

  public AbstractQuery(Class<M> rootClass) {
    this.rootClass = rootClass;
    dataContext = DataContext.getThreadDataContext();
  }

  public AbstractQuery(DataContext dataContext, Class<M> rootClass) {
    this.rootClass = rootClass;
    this.dataContext = dataContext;
  }

  @Override
  public M fetchSingle() {
    Database.beginTransaction(dataContext);
    List<M> list = fetchList();
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public void deleteAll() {
    List<M> list = fetchList();
    if (list.size() > 0) {
      dataContext.deleteObjects(list);
    }
  }

  @Override
  public DataContext getDataContext() {
    return dataContext;
  }

  protected Object getValueFromDataRow(DataRow dataRow, String key) {
    String lowerKey = key.toLowerCase(Locale.ENGLISH);
    if (dataRow.containsKey(lowerKey)) {
      return dataRow.get(lowerKey);
    } else {
      return dataRow.get(key.toUpperCase(Locale.ENGLISH));
    }
  }

  protected ObjectId createObjectId(String entityName, DataRow dataRow,
      DbEntity entity) {

    @SuppressWarnings("unchecked")
    List<DbAttribute> pk = entity.getPrimaryKey();
    if (pk.size() == 1) {
      DbAttribute attribute = pk.get(0);

      String key = attribute.getName();

      Object val = getValueFromDataRow(dataRow, key);
      if (val == null) {
        return null;
      } else {
        return new ObjectId(entityName, attribute.getName(), val);
      }
    }

    Map<String, Object> idMap = new HashMap<String, Object>(pk.size() * 2);
    DbAttribute attribute;
    Object val;
    for (Iterator<DbAttribute> it = pk.iterator(); it.hasNext(); idMap.put(
      attribute.getName(),
      val)) {
      attribute = it.next();
      String key = attribute.getName();

      val = getValueFromDataRow(dataRow, key);
      if (val == null) {
        return null;
      }
    }

    return new ObjectId(entityName, idMap);
  }

  protected M newInstanceFromRowData(DataRow dataRow, Class<M> rootClass) {
    try {
      M model = rootClass.newInstance();
      CayenneDataObject obj = (CayenneDataObject) model;

      ObjEntity objEntity =
        getDataContext().getEntityResolver().lookupObjEntity(obj);

      ObjectId objId =
        createObjectId(objEntity.getName(), dataRow, objEntity.getDbEntity());
      if (objId != null) {
        obj.setObjectId(objId);
      }

      @SuppressWarnings("unchecked")
      Iterator<ObjAttribute> iterator = objEntity.getAttributes().iterator();
      while (iterator.hasNext()) {
        ObjAttribute objAttribute = iterator.next();
        DbAttribute dbAttribute = objAttribute.getDbAttribute();
        Object value = getValueFromDataRow(dataRow, dbAttribute.getName());
        if (value != null) {
          obj.writeProperty(objAttribute.getName(), value);
        }
      }
      return model;
    } catch (InstantiationException ignore) {
      // ignore
    } catch (IllegalAccessException ignore) {
      // ignore
    }
    return null;
  }

}