DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile`
(
    `id`       bigint       NOT NULL AUTO_INCREMENT,
    `user_id`  bigint       NOT NULL DEFAULT '0' COMMENT '用户id',
    `key`      varchar(64)  NOT NULL DEFAULT '' COMMENT 'key',
    `value`    varchar(256) NOT NULL DEFAULT '' COMMENT 'value',
    `comment`  varchar(256) NOT NULL DEFAULT '' COMMENT 'comment',
    `created`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_userid_key` (`user_id`,`key`) USING BTREE
) COMMENT='用户个人偏好设置';

DROP TABLE IF EXISTS `payment_card`;
CREATE TABLE `payment_card`
(
    `id`           int          NOT NULL AUTO_INCREMENT,
    `user_id`      bigint       NOT NULL DEFAULT '0' COMMENT '系统用户id',
    `card_number`  varchar(30)  NOT NULL DEFAULT '' COMMENT '卡号',
    `expire_year`  varchar(2)   NOT NULL DEFAULT '' COMMENT '到期年份，格式:MM，比如：26',
    `expire_month` varchar(2)   NOT NULL DEFAULT '' COMMENT '到期月份，格式:YY，比如：01',
    `cvs`          varchar(5)   NOT NULL DEFAULT '' COMMENT '信用卡验证码',
    `title`        varchar(100) NOT NULL DEFAULT '' COMMENT '信用卡标记',
    `is_default`   tinyint      NOT NULL DEFAULT '0' COMMENT '是否是默认卡',
    `brand`        varchar(20)  NOT NULL DEFAULT '' COMMENT '信用卡品牌，比如：Visa、MasterCard、American Express (Amex)、Discover等',
    `type`         varchar(10)  NOT NULL DEFAULT '' COMMENT '卡类型：credit/debit',
    `created`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    KEY            `idx_user_id` (`user_id`) USING BTREE
) COMMENT='买家信用卡/借记卡信息';

DROP TABLE IF EXISTS `payment_customer_address`;
CREATE TABLE `payment_customer_address`
(
    `id`            int          NOT NULL AUTO_INCREMENT,
    `user_id`       bigint       NOT NULL DEFAULT '0' COMMENT '系统用户id',
    `title`         varchar(100) NOT NULL DEFAULT '' COMMENT '标题，用来区分不同的地址',
    `first_name`    varchar(50)  NOT NULL DEFAULT '' COMMENT '名字',
    `last_name`     varchar(50)  NOT NULL DEFAULT '' COMMENT '姓氏',
    `phone_number`  varchar(50)  NOT NULL DEFAULT '' COMMENT '电话号码',
    `address_line1` varchar(255) NOT NULL DEFAULT '' COMMENT '详细地址1',
    `address_line2` varchar(255) NOT NULL DEFAULT '' COMMENT '详细地址2',
    `post_code`     varchar(20)  NOT NULL DEFAULT '' COMMENT '邮编',
    `city`          varchar(50)  NOT NULL DEFAULT '' COMMENT '城市',
    `state`         varchar(50)  NOT NULL DEFAULT '' COMMENT '州/省',
    `country`       varchar(50)  NOT NULL DEFAULT '' COMMENT '国家',
    `is_default`    tinyint      NOT NULL DEFAULT '0' COMMENT '是否是默认地址',
    `type`          varchar(20)  NOT NULL DEFAULT '' COMMENT 'billing/delivery：账单地址/买家收货地址',
    `created`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    KEY             `idx_user_id` (`user_id`) USING BTREE
) COMMENT='账单地址/买家收货地址';

alter table goods_trade
    add seller_id bigint NOT NULL DEFAULT '0' COMMENT '卖家id' after customer;

insert into setting(`key`, `value`, `comment`)
values ('productTaxPercentage', '8', '商税百分比'),
       ('sameDayDeliveryCharge', '18', '同城配送费用'),
       ('shippingMethods', '["Public Meetup","Puggo Same-day Delivery"]', '交易方式');
update setting set comment = '系统默认货币（英文缩写）' where `key` = 'moneyKind';
update setting set comment = '系统IM账号' where `key` = 'systemImAction';

alter table user add payment_customer_id varchar(50) not null default '' comment '支付账户id' after `is_verified`;
alter table user add key idx_nickname(nickname) using btree;