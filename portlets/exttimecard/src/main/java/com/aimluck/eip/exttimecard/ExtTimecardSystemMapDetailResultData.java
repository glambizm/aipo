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
package com.aimluck.eip.exttimecard;

import com.aimluck.commons.field.ALStringField;

/**
 * タイムカードの情報を保持する。
 * 
 * 
 */
public class ExtTimecardSystemMapDetailResultData extends
    ExtTimecardSystemMapResultData {

  private ALStringField create_date;

  private ALStringField update_date;

  /**
   * 
   * 
   */
  @Override
  public void initField() {
    create_date = new ALStringField();
    update_date = new ALStringField();
  }

  public ALStringField getCreateDate() {
    return create_date;
  }

  public ALStringField getUpdateDate() {
    return update_date;
  }

  public void setCreateDate(String str) {
    create_date.setValue(str);
  }

  public void setUpdateDate(String str) {
    update_date.setValue(str);
  }
}
