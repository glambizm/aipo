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
package com.aimluck.eip.message;

import org.apache.jetspeed.portal.portlets.VelocityPortlet;

/**
 *
 */
public class MessageMockPortlet extends VelocityPortlet {

  private static final long serialVersionUID = 1805775366618467011L;

  public MessageMockPortlet() {
    super();
    setID("Message");
  }

  public MessageMockPortlet(String portletId) {
    super();
    setID(portletId);
  }
}
