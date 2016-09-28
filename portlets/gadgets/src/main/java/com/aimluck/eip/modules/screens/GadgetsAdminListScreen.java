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
package com.aimluck.eip.modules.screens;

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.gadgets.GadgetsAdminSelectData;
import com.aimluck.eip.gadgets.util.GadgetsUtils;

/**
 *
 */
public class GadgetsAdminListScreen extends ALVelocityScreen {

  /**
   * @param rundata
   * @param context
   * @throws Exception
   */
  @Override
  protected void doOutput(RunData rundata, Context context) throws Exception {
    GadgetsAdminSelectData selectData = new GadgetsAdminSelectData();
    selectData.initField();
    selectData.doViewList(this, rundata, context);

    String template = "portlets/html/ajax-gadgets-admin-list.vm";
    setTemplate(rundata, context, template);
  }

  /**
   * @return
   */
  @Override
  protected String getPortletName() {
    return GadgetsUtils.GADGETS_ADMIN_PORTLET_NAME;
  }
}
