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
package com.aimluck.eip.cayenne.om.security;

import com.aimluck.eip.cayenne.om.security.auto._TurbineRolePermission;

public class TurbineRolePermission extends _TurbineRolePermission {

  public Integer getRoleId() {
    TurbineRole role = this.getTurbineRole();

    if (role != null) {
      Object obj = getObjectId().getIdSnapshot().get(
          TurbineRole.ROLE_ID_PK_COLUMN);
      if (obj instanceof Long) {
        Long value = (Long) obj;
        return Integer.valueOf(value.intValue());
      } else {
        return (Integer) obj;
      }
    } else {
      return null;
    }
  }

  public void setRoleId(int id) {
    writeProperty("roleId", Integer.valueOf(id));
  }

  /** Read-only access to FK */
  public Integer getPermissionId() {
    TurbinePermission permission = this.getTurbinePermission();

    if (permission != null) {
      Object obj = getObjectId().getIdSnapshot().get(
          TurbinePermission.PERMISSION_ID_PK_COLUMN);
      if (obj instanceof Long) {
        Long value = (Long) obj;
        return Integer.valueOf(value.intValue());
      } else {
        return (Integer) obj;
      }
    } else {
      return null;
    }
  }

  public void setPermissionId(int id) {
    writeProperty("permissionId", Integer.valueOf(id));
  }

}
