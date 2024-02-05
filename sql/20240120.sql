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


DROP TABLE IF EXISTS `statistics_user_comment_score`;
CREATE TABLE `statistics_user_comment_score`
(
    `id`                          bigint         NOT NULL AUTO_INCREMENT,
    `user_id`                     bigint         NOT NULL DEFAULT '0' COMMENT '用户id',
    `average_score`               decimal(10, 1) NOT NULL DEFAULT '0.0' COMMENT '平均评分，等于用户信用评分',
    `total_score`                 decimal(10, 1) NOT NULL DEFAULT '0.0' COMMENT '总评分',
    `from_seller_score`           decimal(10, 1) NOT NULL DEFAULT '0.0' COMMENT '来自卖家评分',
    `from_customer_score`         decimal(10, 1) NOT NULL DEFAULT '0.0' COMMENT '来自买家评分',
    `total_comment`               int            NOT NULL DEFAULT 0 COMMENT '总评论数',
    `total_from_seller_comment`   int            NOT NULL DEFAULT 0 COMMENT '来自卖家评论数',
    `total_from_customer_comment` int            NOT NULL DEFAULT 0 COMMENT '来自买家评论数',
    `created`                     timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`                    timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    KEY                           `idx_user_id` (`user_id`) USING BTREE
) COMMENT='用户评分统计';

DROP TABLE IF EXISTS `goods_trade`;
CREATE TABLE `goods_trade`
(
    `id`                          bigint         NOT NULL AUTO_INCREMENT,
    `trade_no`                    varchar(32)    NOT NULL DEFAULT '' COMMENT '订单编号',
    `title`                       varchar(50)    NOT NULL DEFAULT '' COMMENT '支付标题',
    `goods_id`                    bigint         NOT NULL DEFAULT '0' COMMENT '商品id',
    `customer_id`                 bigint         NOT NULL DEFAULT '0' COMMENT '买家用户id',
    `customer`                    varchar(64)    NOT NULL DEFAULT '' COMMENT '买家昵称',
    `seller_id`                   bigint         NOT NULL DEFAULT '0' COMMENT '卖家用户id',
    `trading_price`               decimal(19, 4) NOT NULL DEFAULT '0.0000' COMMENT '商品成交金额，不包括额外的运费',
    `trading_time`                timestamp      NOT NULL COMMENT '开单时间',
    `state`                       varchar(25)    NOT NULL DEFAULT '' COMMENT '交易状态：Pending payment、Payment failed、Completed 等等',
    `shipping_method`             int            NOT NULL DEFAULT '0' COMMENT '交易方式：1面交 2快递 4当日达 等等',
    `other_fees`                  decimal(19, 4) NOT NULL DEFAULT '0.0000' COMMENT '邮寄费用、AI检测费用等额外费用',
    `tax`                         decimal(19, 4) NOT NULL DEFAULT '0.0000' COMMENT '商品税，或者叫佣金，系统所得费用',
    `subtotal`                    decimal(19, 4) NOT NULL DEFAULT '0.0000' COMMENT '商家所得费用，如商品费用',
    `payment_method_id`           varchar(50)    NOT NULL DEFAULT '0' COMMENT '买家支付方式',
    `payment_type`                varchar(25)    NOT NULL DEFAULT '0' COMMENT '买家支付类型',
    `delivery_address_id`         int            NOT NULL DEFAULT '0' COMMENT '收货地址id',
    `billing_address_id`          int            NOT NULL DEFAULT '0' COMMENT '账单地址id',
    `is_same_as_delivery_address` tinyint        NOT NULL DEFAULT '0' COMMENT '当账单地址同收货地址时，账单地址id为0',
    `invoice_id`                  varchar(50)    NOT NULL DEFAULT '' COMMENT '支付成功后发票id',
    `payment_card_id`             int            NOT NULL DEFAULT '0' COMMENT '如果付款方式是card，支付卡号id',
    `created`                     timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`                    timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    KEY                           `idx_goods_trade_goods_id` (`goods_id`) USING BTREE
) COMMENT='商品交易';

alter table goods_trade
    add seller_id bigint NOT NULL DEFAULT '0' COMMENT '卖家id' after customer;

insert into setting(`key`, `value`, `comment`)
values ('productTaxPercentage', '8', '商税百分比'),
       ('sameDayDeliveryCharge', '18', '同城配送费用'),
       ('shippingMethods', '["Public Meetup","Puggo Same-day Delivery"]', '交易方式');
update setting
set comment = '系统默认货币（英文缩写）'
where `key` = 'moneyKind';
update setting
set comment = '系统IM账号'
where `key` = 'systemImAction';

alter table user
    add `payment_customer_id` varchar(50) not null default '' comment '买家身份支付账户id' after `is_verified`;
alter table user
    add `payment_seller_id` varchar(50) not null default '' comment '商家身份支付账户id' after `payment_customer_id`;
alter table user
    add key idx_nickname(nickname) using btree;

drop table if exists `user_logs`;
drop table if exists `user_simple_info`;
drop table if exists `user_address`;

alter table goods_comment change `rate` `score` decimal (10,1) NOT NULL DEFAULT '0.0' COMMENT '评分';

update goods
set state = 'RESERVED'
where state = 'OCCUPY';


