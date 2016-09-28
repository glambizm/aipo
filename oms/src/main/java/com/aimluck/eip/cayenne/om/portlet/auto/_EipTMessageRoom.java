package com.aimluck.eip.cayenne.om.portlet.auto;

import java.util.List;

/** Class _EipTMessageRoom was generated by Cayenne.
  * It is probably a good idea to avoid changing this class manually, 
  * since it may be overwritten next time code is regenerated. 
  * If you need to make any customizations, please use subclass. 
  */
public class _EipTMessageRoom extends org.apache.cayenne.CayenneDataObject {

    public static final String AUTO_NAME_PROPERTY = "autoName";
    public static final String CREATE_DATE_PROPERTY = "createDate";
    public static final String CREATE_USER_ID_PROPERTY = "createUserId";
    public static final String HAS_PHOTO_PROPERTY = "hasPhoto";
    public static final String LAST_MESSAGE_PROPERTY = "lastMessage";
    public static final String LAST_UPDATE_DATE_PROPERTY = "lastUpdateDate";
    public static final String NAME_PROPERTY = "name";
    public static final String PHOTO_PROPERTY = "photo";
    public static final String PHOTO_MODIFIED_PROPERTY = "photoModified";
    public static final String PHOTO_SMARTPHONE_PROPERTY = "photoSmartphone";
    public static final String ROOM_TYPE_PROPERTY = "roomType";
    public static final String UPDATE_DATE_PROPERTY = "updateDate";
    public static final String EIP_TMESSAGE_ROOM_MEMBER_PROPERTY = "eipTMessageRoomMember";
    public static final String EIP_TMESSAGES_PROPERTY = "eipTMessages";

    public static final String ROOM_ID_PK_COLUMN = "ROOM_ID";

    public void setAutoName(String autoName) {
        writeProperty("autoName", autoName);
    }
    public String getAutoName() {
        return (String)readProperty("autoName");
    }
    
    
    public void setCreateDate(java.util.Date createDate) {
        writeProperty("createDate", createDate);
    }
    public java.util.Date getCreateDate() {
        return (java.util.Date)readProperty("createDate");
    }
    
    
    public void setCreateUserId(Integer createUserId) {
        writeProperty("createUserId", createUserId);
    }
    public Integer getCreateUserId() {
        return (Integer)readProperty("createUserId");
    }
    
    
    public void setHasPhoto(String hasPhoto) {
        writeProperty("hasPhoto", hasPhoto);
    }
    public String getHasPhoto() {
        return (String)readProperty("hasPhoto");
    }
    
    
    public void setLastMessage(String lastMessage) {
        writeProperty("lastMessage", lastMessage);
    }
    public String getLastMessage() {
        return (String)readProperty("lastMessage");
    }
    
    
    public void setLastUpdateDate(java.util.Date lastUpdateDate) {
        writeProperty("lastUpdateDate", lastUpdateDate);
    }
    public java.util.Date getLastUpdateDate() {
        return (java.util.Date)readProperty("lastUpdateDate");
    }
    
    
    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }
    
    
    public void setPhoto(byte[] photo) {
        writeProperty("photo", photo);
    }
    public byte[] getPhoto() {
        return (byte[])readProperty("photo");
    }
    
    
    public void setPhotoModified(java.util.Date photoModified) {
        writeProperty("photoModified", photoModified);
    }
    public java.util.Date getPhotoModified() {
        return (java.util.Date)readProperty("photoModified");
    }
    
    
    public void setPhotoSmartphone(byte[] photoSmartphone) {
        writeProperty("photoSmartphone", photoSmartphone);
    }
    public byte[] getPhotoSmartphone() {
        return (byte[])readProperty("photoSmartphone");
    }
    
    
    public void setRoomType(String roomType) {
        writeProperty("roomType", roomType);
    }
    public String getRoomType() {
        return (String)readProperty("roomType");
    }
    
    
    public void setUpdateDate(java.util.Date updateDate) {
        writeProperty("updateDate", updateDate);
    }
    public java.util.Date getUpdateDate() {
        return (java.util.Date)readProperty("updateDate");
    }
    
    
    public void addToEipTMessageRoomMember(com.aimluck.eip.cayenne.om.portlet.EipTMessageRoomMember obj) {
        addToManyTarget("eipTMessageRoomMember", obj, true);
    }
    public void removeFromEipTMessageRoomMember(com.aimluck.eip.cayenne.om.portlet.EipTMessageRoomMember obj) {
        removeToManyTarget("eipTMessageRoomMember", obj, true);
    }
    public List getEipTMessageRoomMember() {
        return (List)readProperty("eipTMessageRoomMember");
    }
    
    
    public void addToEipTMessages(com.aimluck.eip.cayenne.om.portlet.EipTMessage obj) {
        addToManyTarget("eipTMessages", obj, true);
    }
    public void removeFromEipTMessages(com.aimluck.eip.cayenne.om.portlet.EipTMessage obj) {
        removeToManyTarget("eipTMessages", obj, true);
    }
    public List getEipTMessages() {
        return (List)readProperty("eipTMessages");
    }
    
    
}
