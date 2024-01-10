package com.etrade.puggo.third.im.pojo;

import lombok.Data;

/**
 * @author niuzhenyu
 * @description : IM消息抄送DO
 * @date 2023/6/25 11:17
 **/
@Data
public class IMMessageDO {

    /**
     * eventType	Integer	值为1，表示是会话类型的消息 convType	String	会话具体类型： PERSON（单聊会话内消息）、TEAM（群聊会话内消息）、SUPER_TEAM（超大群聊会话内消息）
     * CUSTOM_PERSON（单聊自定义系统通知及内置好友系统通知）、CUSTOM_TEAM（群聊自定义系统通知及内置群聊系统通知），字符串类型
     * to	String	若convType为PERSON或CUSTOM_PERSON，则to为消息接收者的用户账号，字符串类型；
     * 若convType为TEAM或SUPER_TEAM或CUSTOM_TEAM，则to为tid，即群id，可转为Long型数据 fromAccount	String	消息发送者的用户账号，字符串类型
     * fromClientType	String	发送客户端类型： AOS、IOS、PC、WINPHONE、WEB、REST，字符串类型 fromDeviceId	String	发送设备id，字符串类型
     * fromNick	String	发送方昵称，字符串类型 msgTimestamp	String	消息发送时间，字符串类型
     * msgType	String	convType为PERSON、TEAM、SUPER_TEAM时对应的消息类型： TEXT //文本消息 PICTURE //图片消息 AUDIO //语音消息 VIDEO //视频消息
     * LOCATION //地理位置消息 NOTIFICATION //群通知消息，如群资料更新通知、群解散通知等。 FILE //文件消息 TIPS //提示消息 CUSTOM //自定义消息 NRTC_NETCALL //
     * 音视频2.0话单消息抄送 convType为CUSTOM_PERSON对应的通知消息类型： FRIEND_ADD //对方 请求/已经 添加为好友 FRIEND_DELETE //被对方删除好友 CUSTOM_P2P_MSG
     * //单聊自定义系统通知
     * <p>
     * convType为CUSTOM_TEAM对应的通知消息类型（请注意与NOTIFICATION区分）： TEAM_APPLY //申请入群 TEAM_APPLY_REJECT //拒绝入群申请 TEAM_INVITE
     * //邀请进群 TEAM_INVITE_REJECT //拒绝邀请 CUSTOM_TEAM_MSG //群组自定义系统通知 SUPERTEAM_APPLY //超大群申请入群 SUPERTEAM_APPLY_REJECT
     * //超大群拒绝入群申请 SUPERTEAM_INVITE //超大群邀请进群 SUPERTEAM_INVITE_REJECT //超大群拒绝进群邀请 CUSTOM_SUPERTEAM_MSG //超大群组自定义系统通知
     * body	String	消息内容，字符串类型。针对聊天室消息而言，无此字段。内容转由attach承载。 attach	String	附加消息，字符串类型。注意：音视频通话 2.0
     * 消息抄送中，attach字段包含通话类型、呼叫状态等通话详情。详细信息请参考话单消息结构（Android）或话单消息结构（iOS）。
     * msgidClient	String	客户端生成的消息id，仅在convType为PERSON或TEAM或SUPER_TEAM含此字段，字符串类型
     * msgidServer	String	服务端生成的消息id，可转为Long型数据 resendFlag	String	重发标记：0不是重发,
     * 1是重发。仅在convType为PERSON或TEAM或SUPER_TEAM时含此字段，可转为Integer类型数据 customSafeFlag	String	自定义系统通知消息是否存离线:0：不存，1：存。
     * 仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段，可转为Integer类型数据
     * customApnsText	String	自定义系统通知消息推送文本。仅在convType为CUSTOM_PERSON或CUSTOM_TEAM时含此字段，字符串类型
     * tMembers	String	当前群成员accid列表。仅在群成员不超过200人，且convType为TEAM或CUSTOM_TEAM时包含此字段，字符串类型。 tMembers格式举例： { ... // 其他字段
     * "tMembers":"[123, 456]" //相关的accid为 123 和 456 } ext	String	消息扩展字段
     * antispam	String	标识是否被反垃圾，仅在被反垃圾时才有此字段，可转为Boolean类型数据
     * yidunRes	String	安全通反垃圾的原始处理细节，只有接入了相关功能安全通反垃圾的应用才会有这个字段。详见以下1.4.5.1、P2P：文本消息 和 1.4.5.2、P2P：图片消息的举例说明。
     * <p>
     * 该字段中子字段释义如下：
     * <p>
     * 老版本安全通反垃圾： yidunBusType：0：文本反垃圾业务；1、图片反垃圾业务；2、用户资料反垃圾业务；3、用户头像反垃圾业务。 action：处理结果：检测结果，0：通过，1：嫌疑，2：不通过。
     * （只有yidunBusType为0或2时，抄送时才有此字段） labels：具体的反垃圾判断细节： 文本类反垃圾参考：
     * <a href="http://support.dun.163.com/documents/2018041901?docId=150425947576913920">...</a> labels字段的释义 图片类反垃圾参考：
     * <a href="http://support.dun.163.com/documents/2018041902?docId=150429557194936320">...</a> labels字段的释义
     * 备注：考虑到安全通反垃圾相关字段后续的扩展性（一般为新增属性），请注意做好解析兼容
     * <p>
     * 新版本安全通反垃圾： yidunApiVersion：安全通反垃圾接口版本；2、新版本安全通反垃圾接口。 yidunBusType：0：文本反垃圾业务；1、图片反垃圾业务；2、用户资料反垃圾业务；3、用户头像反垃圾业务。
     * result：具体的反垃圾返回结果： 文本类反垃圾参考： <a href="https://support.dun.163.com/documents/588434200783982592?docId=589310433773625344">...</a>
     * result字段的释义 图片类反垃圾参考： <a href="https://support.dun.163.com/documents/588434277524447232?docId=588512292354793472">...</a>
     * result字段的释义 备注：考虑到安全通反垃圾相关字段后续的扩展性（一般为新增属性），请注意做好解析兼容
     * <p>
     * 2021年9月28日19:29前接入安全通的客户，需要升级到最新版安全通，才可使用新版本能力。升级安全通请联系商务经理。2021年9月28日19:29后接入安全通的客户，将自动开通此能力。
     * blacklist	String	标识单聊消息是否黑名单，仅在消息发送方被拉黑时才有此字段，可转为Boolean类型数据 ip	String	消息发送方的客户端IP地址(仅SDK发送的消息才有该字段)
     * port	String	消息发送方的客户端端口号(仅SDK发送的消息才有该字段)
     */
    private Integer eventType;
    private String convType;
    private String to;
    private String fromAccount;
    private String fromClientType;
    private String fromDeviceId;
    private String fromNick;
    private String msgType;
    private String msgTimestamp;
    private String body;
    private String attach;
    private String msgidClient;
    private String msgidServer;
    private Byte resendFlag;
    private Byte customSafeFlag;
    private String customApnsText;
    private String ext;
    private String ip;
    private Integer port;

}
