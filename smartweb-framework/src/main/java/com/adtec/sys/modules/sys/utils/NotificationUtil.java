package com.adtec.sys.modules.sys.utils;

import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.entity.SysNoticeDO;
import com.adtec.sys.modules.sys.service.SysNoticeService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * <p> </p>
 *
 * @author lijb
 * @since 2021-01-14
 */
public class NotificationUtil {

    private static final String NOTICE_MESSAGE_DESTINATION = "/sys/topic/noticeMessage";

    private static final SimpMessagingTemplate SIMP_MESSAGING_TEMPLATE = SpringContextHolder
            .getBean(SimpMessagingTemplate.class);

    /**
     * 发送消息给当前用户
     *
     * @param message 通知消息
     */
    public static void notifyCurrentUser(SysNoticeDO message) {
        notifyUser(UserUtils.getUser().getLoginName(), message);
    }

    /**
     * 发送消息给当前用户
     *
     * @param title   消息标题
     * @param message 消息内容
     */
    public static void notifyCurrentUser(String title, String message) {
        notifyUser(UserUtils.getUser().getLoginName(), title, message);
    }

    /**
     * 发送消息到指定用户
     *
     * @param username 用户名
     * @param title    消息标题
     * @param message  消息内容
     */
    public static void notifyUser(String username, String title, String message) {
        SysNoticeDO notice = new SysNoticeDO();
        notice.setNoteTitle(title);
        notice.setNoteCntt(message);
        notice.setNoteScp(SysNoticeService.NoteScp.USER);
        notice.setPopupFlg("Y");
        notifyUser(username, notice);
    }

    /**
     * 发送消息给指定用户
     *
     * @param username 用户名
     * @param message  通知消息
     */
    public static void notifyUser(String username, SysNoticeDO message) {
        sendMessageToUser(username, NOTICE_MESSAGE_DESTINATION, message);
    }

    /**
     * 发送消息给所有在线用户
     *
     * @param message 通知消息
     */
    public static void notifyEveryone(SysNoticeDO message) {
        sendMessage(NOTICE_MESSAGE_DESTINATION, message);
    }

    public static void sendMessage(String destination, Object message) {
        SIMP_MESSAGING_TEMPLATE.convertAndSend(destination, message);
    }

    public static void sendMessageToUser(String user, String destination, Object message) {
        SIMP_MESSAGING_TEMPLATE.convertAndSendToUser(user, destination, message);
    }

    private NotificationUtil() {
    }
}
