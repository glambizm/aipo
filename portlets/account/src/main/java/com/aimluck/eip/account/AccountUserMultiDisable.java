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
package com.aimluck.eip.account;

import java.util.List;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.account.util.AccountUtils;
import com.aimluck.eip.cayenne.om.security.TurbineUser;
import com.aimluck.eip.common.ALAbstractCheckList;
import com.aimluck.eip.orm.Database;
import com.aimluck.eip.orm.query.SelectQuery;
import com.aimluck.eip.services.config.ALConfigHandler.Property;
import com.aimluck.eip.services.config.ALConfigService;
import com.aimluck.eip.services.datasync.ALDataSyncFactoryService;
import com.aimluck.eip.services.eventlog.ALEventlogConstants;
import com.aimluck.eip.services.eventlog.ALEventlogFactoryService;
import com.aimluck.eip.util.ALEipUtils;
import com.aimluck.eip.util.ALLocalizationUtils;

/**
 * ユーザアカウントを複数無効化するためのクラス． <BR>
 *
 */
public class AccountUserMultiDisable extends ALAbstractCheckList {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(AccountUserMultiDelete.class.getName());

  /**
   *
   * @param rundata
   * @param context
   * @param values
   * @param msgList
   * @return
   */
  @Override
  protected boolean action(RunData rundata, Context context,
      List<String> values, List<String> msgList) {

    try {
      // WebAPIのDBへ接続できるか確認
      if (!ALDataSyncFactoryService
        .getInstance()
        .getDataSyncHandler()
        .checkConnect()) {
        msgList.add(ALLocalizationUtils
          .getl10nFormat("ACCOUNT_ALERT_CONNECT_DB_FAILED"));
        return false;
      }

      Expression exp =
        ExpressionFactory.inExp(TurbineUser.LOGIN_NAME_PROPERTY, values);
      SelectQuery<TurbineUser> query = Database.query(TurbineUser.class, exp);
      List<TurbineUser> ulist = query.fetchList();
      if (ulist == null || ulist.size() == 0) {
        return false;
      }

      int size = ulist.size();
      int admin_count = 0;
      String[] user_name_list = new String[size];

      // 予めバリデーション
      for (TurbineUser user : ulist) {
        if (user.getLoginName().equals(rundata.getUser().getUserName())) {
          msgList.add(ALLocalizationUtils
            .getl10nFormat("ACCOUNT_ALERT_DISABLE_LOGINUSER"));
          return false;
        }
        if (ALEipUtils.isAdmin(user.getUserId())) {
          admin_count++;
        }
      }

      if (!AccountUtils.isAdminDeletable(admin_count)) {
        msgList.add(ALLocalizationUtils.getl10nFormat(
          "ACCOUNT_ALERT_NUMOFADMINS_LIMIT",
          Integer.valueOf(ALConfigService
            .get(Property.MINIMUM_ADMINISTRATOR_USER_COUNT))));
        return false;
      }

      for (int i = 0; i < size; i++) {
        TurbineUser record = ulist.get(i);
        String user_name = record.getLoginName();
        user_name_list[i] = user_name;
        if (user_name == null) {
          return false;
        }

        TurbineUser user =
          Database.get(
            TurbineUser.class,
            TurbineUser.LOGIN_NAME_COLUMN,
            user_name);

        if (ALEipUtils.isAdmin(user.getUserId())
          && !AccountUtils.isAdminDeletable()) {
          return false;
        }

        // ユーザーを無効化
        user.setDisabled("N");

        // ワークフロー自動承認
        AccountUtils.acceptWorkflow(user.getUserId());

        // イベントログに保存
        String name = "";
        if (user.getLastName() != null
          && !" ".equals(user.getLastName())
          && user.getFirstName() != null
          && !" ".equals(user.getFirstName())) {
          name =
            new StringBuffer().append(user.getLastName()).append(" ").append(
              user.getFirstName()).toString();
        } else {
          name = user.getEmail();
        }
        ALEventlogFactoryService.getInstance().getEventlogHandler().log(
          user.getUserId(),
          ALEventlogConstants.PORTLET_TYPE_ACCOUNT,
          "ユーザー「" + name + "」を無効化");
      }

      Database.commit();

      // WebAPIとのDB同期
      if (!ALDataSyncFactoryService
        .getInstance()
        .getDataSyncHandler()
        .multiDisableUser(user_name_list, size)) {
        return false;
      }

      return msgList.size() == 0;
    } catch (Exception e) {
      Database.rollback();
      logger.error("AccountUserMultiDisable.action", e);
      return false;
    }
  }
}
