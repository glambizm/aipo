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

import java.util.ArrayList;
import java.util.List;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.blog.BlogEntryCommentFormData;
import com.aimluck.eip.blog.BlogEntrySelectData;
import com.aimluck.eip.blog.util.BlogUtils;
import com.aimluck.eip.common.ALEipConstants;
import com.aimluck.eip.util.ALEipUtils;

/**
 * ブログの詳細画面を処理するクラスです。 <br />
 * 
 */
public class BlogDetailScreen extends ALVelocityScreen {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(BlogDetailScreen.class.getName());

  /** 返信用キー */
  private final String RESULT_ON_COMMENT_DETAIL = "resultOnCommentDetail";

  /** 返信用エラーメッセージキー */
  private final String ERROR_MESSAGE_LIST_ON_COMMENT_DETAIL =
    "errmsgsOnCommentDetail";

  /** 返信用 result */
  private Object resultOnCommentDetail;

  /** 返信用異常系のメッセージを格納するリスト */
  private List<String> errmsgListOnCommentDetail;

  /**
   * 
   * @param rundata
   * @param context
   * @throws Exception
   */
  @Override
  protected void doOutput(RunData rundata, Context context) throws Exception {
    try {
      BlogEntrySelectData detailData = new BlogEntrySelectData();
      detailData.initField();
      detailData.doViewDetail(this, rundata, context);

      BlogEntryCommentFormData formData = new BlogEntryCommentFormData();
      formData.initField();
      formData.doViewForm(this, rundata, context);

      String entityid =
        ALEipUtils.getTemp(rundata, context, ALEipConstants.ENTITY_ID);
      context.put(ALEipConstants.ENTITY_ID, entityid);

      String layout_template = "portlets/html/ajax-blog-entry-detail.vm";

      setTemplate(rundata, context, layout_template);
    } catch (Exception ex) {
      logger.error("[BlogDetailScreen] Exception.", ex);
      ALEipUtils.redirectDBError(rundata);
    }
  }

  /**
   * 
   * @param obj
   */
  public void setResultDataOnCommentDetail(Object obj) {
    resultOnCommentDetail = obj;
  }

  /**
   * 
   * @param msg
   */
  public void addErrorMessagesOnCommentDetail(List<String> msgs) {
    if (errmsgListOnCommentDetail == null) {
      errmsgListOnCommentDetail = new ArrayList<String>();
    }
    errmsgListOnCommentDetail.addAll(msgs);
  }

  public void putDataOnCommentDetail(RunData rundata, Context context) {
    context.put(RESULT_ON_COMMENT_DETAIL, resultOnCommentDetail);
    context
      .put(ERROR_MESSAGE_LIST_ON_COMMENT_DETAIL, errmsgListOnCommentDetail);
  }

  /**
   * @return
   */
  @Override
  protected String getPortletName() {
    return BlogUtils.BLOG_PORTLET_NAME;
  }
}
