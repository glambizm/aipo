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
package com.aimluck.eip.services.config;

import javax.servlet.ServletConfig;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.jetspeed.services.rundata.JetspeedRunData;
import org.apache.jetspeed.services.rundata.JetspeedRunDataService;
import org.apache.turbine.services.InitializationException;
import org.apache.turbine.services.TurbineBaseService;
import org.apache.turbine.services.TurbineServices;
import org.apache.turbine.services.rundata.RunDataService;

/**
 * 
 */
public abstract class ALConfigFactoryService extends TurbineBaseService {

  /** logger */
  @SuppressWarnings("unused")
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(ALConfigFactoryService.class.getName());

  public static final String SERVICE_NAME = "ALConfigFactoryService";

  protected JetspeedRunDataService runDataService = null;

  /**
   * 
   * @return
   */
  public static ALConfigFactoryService getInstance() {
    return (ALConfigFactoryService) TurbineServices.getInstance().getService(
      ALConfigFactoryService.SERVICE_NAME);
  }

  public abstract ALConfigHandler getConfigHandler();

  public JetspeedRunData getRunData() {
    JetspeedRunData rundata = null;
    if (this.runDataService != null) {
      rundata = this.runDataService.getCurrentRunData();
    }
    return rundata;
  }

  /**
   *
   */
  @Override
  public synchronized void init(ServletConfig conf)
      throws InitializationException {
    if (getInit()) {
      return;
    }

    super.init(conf);

    this.runDataService =
      (JetspeedRunDataService) TurbineServices.getInstance().getService(
        RunDataService.SERVICE_NAME);

    setInit(true);
  }
}
