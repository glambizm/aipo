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

import com.aimluck.eip.timeline.TimelineSelectData;
import com.aimluck.eip.util.ALEipUtils;

/**
 * タイムライントピックの一覧を処理するクラスです。
 * 
 */
public class TimelineListScreen extends TimelineScreen {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(TimelineListScreen.class.getName());

  /**
   * 
   * @param rundata
   * @param context
   * @throws Exception
   */
  @Override
  protected void doOutput(RunData rundata, Context context) throws Exception {

    // VelocityPortlet portlet = ALEipUtils.getPortlet(rundata, context);
    // String mode = rundata.getParameters().getString(ALEipConstants.MODE);
    try {
      int rows = 20;
      int scrollTop = 0;
      if (rundata.getParameters().containsKey("rows")) {
        rows = rundata.getParameters().getInt("rows");
      }
      if (rundata.getParameters().containsKey("scrollTop")) {
        scrollTop = rundata.getParameters().getInt("scrollTop");
      }
      if (rundata.getUserAgent().trim().indexOf("Mac") != -1) {
        context.put("isMacOS", "true");
      }
      TimelineSelectData listData = new TimelineSelectData();
      listData.initField();
      listData.setContentHeightMax(Integer.parseInt(ALEipUtils.getPortlet(
        rundata,
        context).getPortletConfig().getInitParameter("p2a-rows", "0")));
      listData.setRowsNum(rows);
      listData.setScrollTop(scrollTop);
      listData.doViewList(this, rundata, context);

      String layout_template = "portlets/html/ajax-timeline-list.vm";
      setTemplate(rundata, context, layout_template);

    } catch (Exception ex) {
      logger.error("[TimelineListScreen] Exception.", ex);
      ALEipUtils.redirectDBError(rundata);
    }
  }
}
