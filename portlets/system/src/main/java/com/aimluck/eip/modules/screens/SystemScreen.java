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

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.common.ALEipConstants;
import com.aimluck.eip.system.SystemNetworkSelectData;
import com.aimluck.eip.system.util.SystemUtils;
import com.aimluck.eip.util.ALEipUtils;

/**
 * SystemScreen
 * 
 */
public class SystemScreen extends ALVelocityScreen {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(SystemScreen.class.getName());

  /**
   * 
   * @param rundata
   * @param context
   * @throws Exception
   */
  @Override
  protected void doOutput(RunData rundata, Context context) throws Exception {
    try {

      doSystem_info_network(rundata, context);

    } catch (Exception ex) {
      logger.error("[SystemScreen] Exception.", ex);
      ALEipUtils.redirectDBError(rundata);
    }
  }

  /**
   * ネットワーク情報を表示する
   * 
   * @param rundata
   * @param context
   * @throws Exception
   */
  public void doSystem_info_network(RunData rundata, Context context)
      throws Exception {
    ALEipUtils.setTemp(rundata, context, ALEipConstants.ENTITY_ID, "1");

    SystemNetworkSelectData detailData = new SystemNetworkSelectData();
    detailData.initField();
    detailData.doViewDetail(this, rundata, context);

    String layout_template = "portlets/html/ajax-system.vm";
    setTemplate(rundata, context, layout_template);
  }

  /**
   * @return
   */
  @Override
  protected String getPortletName() {
    return SystemUtils.SYSTEM_PORTLET_NAME;
  }
}
