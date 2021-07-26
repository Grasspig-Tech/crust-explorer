-- CRU浏览器建表
-- 全网概览数据表
drop table if exists ce_network_overview;
CREATE TABLE If Not Exists `ce_network_overview` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `total_storage` varchar(60) DEFAULT NULL COMMENT '总存储量',
  `total_circulation` varchar(60) DEFAULT NULL COMMENT '总发行量',
  `total_output_last24` varchar(60) DEFAULT NULL COMMENT '近24h总产出',
  `block_height` varchar(20) DEFAULT NULL COMMENT '区块高度',
  `block_height_confirmed` varchar(20) DEFAULT NULL COMMENT '已确认块高',
  `block_last_time` varchar(20) DEFAULT NULL COMMENT '最后出块时间',
  `pledge_minimum` varchar(60) DEFAULT NULL COMMENT '最低质押量',
  `pledge_avg` varchar(60) DEFAULT NULL COMMENT '平均质押量',
  `pledge_per` varchar(60) DEFAULT NULL COMMENT '每T质押量',
  `pledge_total_active` varchar(60) DEFAULT NULL COMMENT '有效质押总量',
  `pledge_able_num` varchar(60) DEFAULT NULL COMMENT '可质押总量',
  `era` int(11) DEFAULT NULL COMMENT '纪元：760',
  `era_start_timestamp` int(11) DEFAULT NULL COMMENT '纪元起始时间:1625120364',
  `countdown_era` varchar(20) DEFAULT NULL COMMENT 'Era倒计时',
  `countdown_session` varchar(20) DEFAULT NULL COMMENT 'session倒计时',
  `rate_flow` varchar(20) DEFAULT NULL COMMENT '流通率',
  `rate_inflation` varchar(20) DEFAULT NULL COMMENT '通胀率',
  `base_fee` varchar(30) DEFAULT NULL COMMENT '当前手续费',
  `account_hold` int DEFAULT '0' COMMENT '持有账号数',
  `number_guarantee` int DEFAULT '0' COMMENT '担保人数',
  `number_validator` int DEFAULT '0' COMMENT '验证人数',
  `number_transfer` int DEFAULT '0' COMMENT '转账次数',
  `number_trade` int DEFAULT '0' COMMENT '交易次数',
  `status` tinyint(1) DEFAULT '1' COMMENT '有效否：1-生效；0-失效',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_created` (`time_created`) USING BTREE COMMENT '创建时间'
) ENGINE=InnoDB COMMENT='全网概览数据表';
INSERT INTO `cru_explorer`.`ce_network_overview`(`id`, `total_storage`, `total_circulation`, `total_output_last24`, `block_height`, `block_height_confirmed`, `block_last_time`, `pledge_minimum`, `pledge_avg`, `pledge_per`, `pledge_total_active`, `pledge_able_num`, `era`, `era_start_timestamp`, `countdown_era`, `countdown_session`, `rate_flow`, `rate_inflation`, `base_fee`, `account_hold`, `number_guarantee`, `number_validator`, `number_transfer`, `number_trade`, `status`, `time_created`, `time_updated`) VALUES (1, '658807', '1054881', '253', '2500520', '2500512', '1626764508', '0.0041', '2817', '1', '707239', '765265', 832, 1626761796, '18900', '300', '0.9', '0.6', '0.4', 1325, 380, 40, 3125, 15, 1, '2021-07-13 18:49:54', '2021-07-20 10:02:00');

-- 区块表
drop table if exists ce_block;
CREATE TABLE If Not Exists `ce_block` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `finalized` tinyint(1) DEFAULT NULL COMMENT '块状态：1-已确认，0-确认中',
  `block_num` int(11) DEFAULT NULL COMMENT '区块号:2242969',
  `block_timestamp` int(11) DEFAULT NULL COMMENT '区块打包时间:1625209788',
  `hash` varchar(70) DEFAULT NULL COMMENT '区块hash:0x01ef73c2a08f44e902f45f0e6961143076db5c76c90704467c233e8550dc6865',
  `parent_hash` varchar(70) DEFAULT NULL COMMENT '父hash:0x93d0c198449410222354ebe20d968ec879091658219976856ba217ef7d686576',
  `event_count` int(11) DEFAULT NULL COMMENT '事件数:11',
  `extrinsics_count` int(11) DEFAULT NULL COMMENT '交易数:12',
  `state_root` varchar(70) DEFAULT NULL COMMENT '状态根:0x2c2f25b578f12c077a2d68faf07fe79116ab934368769698dcae7a46bd09d3f8',
  `extrinsics_root` varchar(70) DEFAULT NULL COMMENT '交易根:0x65514dede91c9fd870ab539eef95be1a42c52dbedd4949b242d6944011bde38d',
  `spec_version` int(11) DEFAULT NULL COMMENT '运行时版本:26',
  `validator` varchar(55) DEFAULT NULL COMMENT '验证人地址:5FBeV6U8HLrJnqK8j28LtB2icAGHiChV5zYHvptM2BU8SUD9',
  `account_display` text COMMENT '验证人json:{address: "5FBeV6U8HLrJnqK8j28LtB2icAGHiChV5zYHvptM2BU8SUD9", display: "1Miner矿池-11", judgements: null,…}',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_block` (`block_num`) USING BTREE COMMENT '区块号索引',
  UNIQUE KEY `uk_hash` (`hash`) USING BTREE COMMENT '区块hash索引',
  KEY `idx_block_timestamp` (`block_timestamp`) USING BTREE COMMENT '区块打包时间索引',
  KEY `idx_validator` (`validator`) USING BTREE COMMENT '验证人索引'
) ENGINE=InnoDB COMMENT='区块信息';

-- 事件信息表 (1、事件的hash和交易的hash不一样,但是不同块的时间hash可能会冲突)
drop table if exists ce_event;
CREATE TABLE If Not Exists `ce_event` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `block_num` int(11) DEFAULT NULL COMMENT '区块号:2242969',
  `block_timestamp` int(11) DEFAULT NULL COMMENT '区块打包时间:1625209788',
  `event_id` varchar(100) DEFAULT NULL COMMENT '操作（事件）:WorksReportSuccess',
  `event_idx` int(11) DEFAULT NULL COMMENT '事件idx:1',
  `event_index` varchar(15) DEFAULT NULL COMMENT '事件index:2242969-1',
  `event_sort` varchar(15) DEFAULT NULL COMMENT '事件sort:22429690001',
  `extrinsic_hash` varchar(70) DEFAULT NULL COMMENT '交易hash:0x55a6dd0bfc857895f45c3bedb52f9025e4b284d3b00794f1856a3e35eb346014',
  `extrinsic_index` varchar(15) DEFAULT NULL COMMENT '交易index:2242969-2',
  `extrinsic_idx` int(11) DEFAULT NULL COMMENT '交易idx:2',
  `finalized` tinyint(1) DEFAULT NULL COMMENT '状态：1-已确认，0-确认中',
  `success` tinyint(1) DEFAULT NULL COMMENT '结果：1-成功，0-失败',
  `type` varchar(50) DEFAULT NULL COMMENT '事件类型',
  `module_id` varchar(100) DEFAULT NULL COMMENT '操作（组件）:swork',
  `params` text COMMENT '参数:[{\"type\":\"AccountId\",\"value\":\"4a54ace1fc67f52c5248e811bebeb05794b93df12d3281b5fc2a74bf77e8e745\"}]',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_event_index` (`event_index`) USING BTREE COMMENT '事件index索引',
  UNIQUE KEY `uk_event_sort` (`event_sort`) USING BTREE COMMENT '事件sort索引',
  KEY `idx_block_num` (`block_num`) USING BTREE COMMENT '区块号索引',
  KEY `idx_block_timestamp` (`block_timestamp`) USING BTREE COMMENT '区块打包时间索引',
  KEY `idx_extrinsic_hash` (`extrinsic_hash`) USING BTREE COMMENT '交易hash索引',
  KEY `idx_extrinsic_index` (`extrinsic_index`) USING BTREE COMMENT '交易index索引',
  KEY `idx_type` (`type`) USING BTREE COMMENT '事件类型索引',
  KEY `idx_event_id` (`event_id`) USING BTREE COMMENT '事件id索引',
  KEY `idx_module_id` (`module_id`) USING BTREE COMMENT '操作id索引'
) ENGINE=InnoDB COMMENT='事件信息';

-- 交易信息表
drop table if exists ce_extrinsic;
CREATE TABLE If Not Exists `ce_extrinsic` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lifetime` varchar(255) DEFAULT NULL COMMENT 'Life Time:{birth: 2242965, death: 2243029}',
  `params` mediumtext COMMENT '参数[{name: "curr_pk", type: "SworkerPubKey",…}, {name: "ab_upgrade_pk", type: "SworkerPubKey",…},…]',
  `extrinsic_idx` int(11) DEFAULT NULL COMMENT '交易idx:2',
  `extrinsic_index` varchar(15) DEFAULT NULL COMMENT '交易index:2242969-2',
  `extrinsic_sort` varchar(15) DEFAULT NULL COMMENT '交易sort:22429690002',
  `extrinsic_hash` varchar(70) DEFAULT NULL COMMENT '交易hash：0x912fe2e6165127806244bc03f08ea5863c633722b0897fb2eb2dd53e82c80120',
  `block_hash` varchar(70) DEFAULT NULL COMMENT '区块hash:0x01ef73c2a08f44e902f45f0e6961143076db5c76c90704467c233e8550dc6865',
  `block_num` int(11) DEFAULT NULL COMMENT '区块号:2242969',
  `block_timestamp` int(11) DEFAULT NULL COMMENT '区块打包时间:1625209788',
  `call_module` varchar(100) DEFAULT NULL COMMENT '操作（组件）:swork',
  `call_module_function` varchar(100) DEFAULT NULL COMMENT '操作方法（调用）:report_works',
  `account_id` varchar(55) DEFAULT NULL COMMENT '发送账户账号地址:5CvfDWAQMPSgpN6sx2LADHx6AxCkrdQAxHPpnrVwvpg1ZtMe',
  `account_display` text COMMENT '发送账号json:{address: "5CvfDWAQMPSgpN6sx2LADHx6AxCkrdQAxHPpnrVwvpg1ZtMe", display: "", judgements: null,…}',
  `fee` decimal(50,20) DEFAULT NULL COMMENT '手续费:56642301',
  `finalized` tinyint(1) DEFAULT NULL COMMENT '状态：1-已确认，0-确认中',
  `success` tinyint(1) DEFAULT NULL COMMENT '结果：1-成功，0-失败',
  `signed` tinyint(1) DEFAULT NULL COMMENT '是否签名：1-是，0-否',
  `nonce` int(11) DEFAULT NULL COMMENT '随机数:3840',
  `signature` varchar(255) DEFAULT NULL COMMENT '签名:0xbc59e491fe045c8e1f1695f86a3f26239b976a9f310f6b6460ecb732e2ae3f6f20e07e45e58ca44c1038d11a9602c465807fd502e000d815902a18200a4cd480',
  `transfer` text COMMENT '转账json{from: "5CfLoSHYbCtCTcKgyPMcCK3i1pYCtkyUtTQBz8Gfxo8Yu2oc",…}',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_extrinsic_hash` (`extrinsic_hash`) USING BTREE COMMENT '交易hash索引',
  KEY `idx_extrinsic_sort` (`extrinsic_sort`) USING BTREE COMMENT '交易sort索引',
  KEY `idx_extrinsic_index` (`extrinsic_index`) USING BTREE COMMENT '交易index索引',
  KEY `idx_block` (`block_num`) USING BTREE COMMENT '块索引',
  KEY `idx_block_timestamp` (`block_timestamp`) USING BTREE COMMENT '区块打包时间索引',
  KEY `idx_account_id` (`account_id`) USING BTREE COMMENT '发送账户账号索引',
  KEY `idx_call_module` (`call_module`) USING BTREE COMMENT '操作索引',
  KEY `idx_call_module_function` (`call_module_function`) USING BTREE COMMENT '操作方法索引'
) ENGINE=InnoDB COMMENT='交易信息表';

-- 一个事件含有交易的信息，同时一个事件的event_id: "Transfer"说明它也是一笔转账
-- 转账和奖励惩罚都是从事件里面筛选出来的
-- 转账信息表
-- 转账的hash是事件hash还是交易hash？----------------- 估计是交易的hash
drop table if exists ce_transfer;
CREATE TABLE If Not Exists `ce_transfer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `amount` decimal(50,20) DEFAULT NULL COMMENT '数量:0.00018004716',
  `asset_symbol` varchar(20) DEFAULT NULL COMMENT '币种',
  `event_index` varchar(15) DEFAULT NULL COMMENT '事件index:2243376-1',
  `event_sort` varchar(15) DEFAULT NULL COMMENT '事件sort:22433760001',
  `extrinsic_index` varchar(15) DEFAULT NULL COMMENT '交易index:2243376-2',
  `block_num` int(11) DEFAULT NULL COMMENT '区块号:2243376',
  `block_timestamp` int(11) DEFAULT NULL COMMENT '区块打包时间:1625212242',
  `module` varchar(100) DEFAULT NULL COMMENT '操作:balances',
  `hash` varchar(70) DEFAULT NULL COMMENT '(交易)hash:0x042af912e370c505c6aa4f693916c941c40bb250208dd78df0f113bc0cb6aeed',
  `from` varchar(55) DEFAULT NULL COMMENT 'from:5ELHphbnebMSstj6yREdTwU8A2N8MsMyR4oi14o95uioGBaQ',
  `to` varchar(55) DEFAULT NULL COMMENT 'to:5EYCAe5g8L3FKZ6gRSZ3m85g9fjGYcfv8T4Z4RosnT7hghbx',
  `fee` decimal(50,20) DEFAULT NULL COMMENT '手续费:30310872',
  `nonce` int(11) DEFAULT NULL COMMENT '随机数:107-------------------------',
  `finalized` tinyint(1) DEFAULT NULL COMMENT '状态：1-已确认，0-确认中',
  `success` tinyint(1) DEFAULT NULL COMMENT '结果：1-成功，0-失败',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_event_index` (`event_index`) USING BTREE COMMENT '事件索引',
  UNIQUE KEY `uk_event_sort` (`event_sort`) USING BTREE COMMENT '事件sort索引',
  KEY `idx_block` (`block_num`) USING BTREE COMMENT '块索引',
  KEY `idx_block_timestamp` (`block_timestamp`) USING BTREE COMMENT '区块打包时间索引',
  KEY `idx_extrinsic_index` (`extrinsic_index`) USING BTREE COMMENT '交易索引',
  KEY `idx_hash` (`hash`) USING BTREE COMMENT 'hash索引',
  KEY `idx_to` (`to`) USING BTREE COMMENT 'to索引',
  KEY `idx_from` (`from`) USING BTREE COMMENT 'from索引'
) ENGINE=InnoDB COMMENT='转账信息表';

-- 奖励惩罚表
drop table if exists ce_reward_slash;
CREATE TABLE If Not Exists `ce_reward_slash` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `block_num` int(11) DEFAULT NULL COMMENT '区块号：2228117',
  `block_timestamp` int(11) DEFAULT NULL COMMENT '区块打包时间:1625120364',
  `event_id` varchar(100) DEFAULT NULL COMMENT '事件id:Reward',
  `event_idx` int(11) DEFAULT NULL COMMENT '事件idx:49',
  `event_index` varchar(15) DEFAULT NULL COMMENT '事件index:2228117-49',
  `event_sort` varchar(15) DEFAULT NULL COMMENT '事件sort:22281170001',
  `extrinsic_idx` int(11) DEFAULT NULL COMMENT '交易idx:13',
  `extrinsic_hash` varchar(70) DEFAULT NULL COMMENT '交易hash:0xc0d285261be7594e6a1536aa48eb0917ed1c68435f11bb9460ae121d0486e876',
  `validator_stash` varchar(55) DEFAULT NULL COMMENT '验证人账户地址*****************要看奖励惩罚和验证人怎么挂的关系',
  `account_id` varchar(55) DEFAULT NULL COMMENT '奖惩者地址*****************要看奖励惩罚和提名人怎么挂的关系',
  `module_id` varchar(100) DEFAULT NULL COMMENT '操作id:staking',
  `amount` decimal(50,20) DEFAULT NULL COMMENT '数量:9196868377476',
  `finalized` tinyint(1) DEFAULT NULL COMMENT '状态：1-已确认，0-确认中',
  `success` tinyint(1) DEFAULT NULL COMMENT '结果：1-成功，0-失败',
  `params` text COMMENT '参数:[{\"type\":\"AccountId\",\"value\":\"522d2567732682f0f71d679776aaf795b0c9c926b792ea3e4e8f2b68a9f3881f\"}]',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_event_index` (`event_index`) USING BTREE COMMENT '事件索引',
  UNIQUE KEY `uk_event_sort` (`event_sort`) USING BTREE COMMENT '事件sort索引',
  KEY `idx_block` (`block_num`) USING BTREE COMMENT '块索引',
  KEY `idx_block_timestamp` (`block_timestamp`) USING BTREE COMMENT '区块打包时间索引',
  KEY `idx_validator` (`validator_stash`) USING BTREE COMMENT '验证人账户地址索引',
  KEY `idx_account_id` (`account_id`) USING BTREE COMMENT '奖惩者地址索引',
  KEY `extrinsic_hash` (`extrinsic_hash`) USING BTREE COMMENT '交易hash索引'
) ENGINE=InnoDB COMMENT='奖励惩罚表';

-- 账户表（nominator,councilMember,techcomm,registrar,validator）
DROP TABLE IF EXISTS `ce_account`;
CREATE TABLE If Not Exists `ce_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_type` tinyint(1) DEFAULT NULL COMMENT '账户类型:1-（存储）账号,2-控制账户',
  `address` varchar(55) DEFAULT NULL COMMENT '账户hash地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW',
  `email` varchar(100) DEFAULT NULL COMMENT '账户邮箱：Crustnetwork@gmail.com',
  `twitter` varchar(100) DEFAULT NULL COMMENT 'twitter：@CrustNetwork',
  `web` varchar(100) DEFAULT NULL COMMENT 'web：https://crust.network/',
  `display` varchar(200) DEFAULT NULL COMMENT '账户显示（昵称）：德坤数矿(DKSK)星城',
  `account_display` text COMMENT '账户json:{account_index: "", address: "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW",…}',
  `judgements` text COMMENT '不为null就是身份等级[{index: 1, judgement: "Reasonable"}]',
  `balance` decimal(50,20) DEFAULT NULL COMMENT '余额:8499.134625110269',
  `balance_lock` decimal(50,20) DEFAULT NULL COMMENT '已锁定余额:8282.301101002825',
  `bonded` decimal(50,20) DEFAULT NULL COMMENT '已冻结余额:8282.301101002825',
  `reserved` decimal(50,20) DEFAULT NULL COMMENT '保留余额:5（可转账=余额-保留余额-已锁定余额）',
  `democracy_lock` decimal(50,20) DEFAULT NULL COMMENT '民主锁定:0',
  `election_lock` decimal(50,20) DEFAULT NULL COMMENT '选举锁定:0',
  `unbonding` decimal(50,20) DEFAULT NULL COMMENT '解冻中:0',
  `is_council_member` tinyint(1) DEFAULT NULL COMMENT '是否议会成员:1-Y,0-N',
  `is_evm_contract` tinyint(1) DEFAULT NULL COMMENT '是否evm合约:1-Y,0-N',
  `is_registrar` tinyint(1) DEFAULT NULL COMMENT '是否身份注册商:1-Y,0-N',
  `is_techcomm_member` tinyint(1) DEFAULT NULL COMMENT '是否技术委员会成员:1-Y,0-N',
  `legal` varchar(100) DEFAULT NULL COMMENT '法律身份:Withdraw to Ethereum ERC-20',
  `nonce` int(11) DEFAULT NULL COMMENT '随机数:171',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_address_type` (`address`,`account_type`) USING BTREE COMMENT '账户hash地址索引',
  KEY `idx_created` (`time_created`) USING BTREE COMMENT '创建时间'
) ENGINE=InnoDB COMMENT='账户表';

-- 成员表
DROP TABLE IF EXISTS `ce_member`;
CREATE TABLE If Not Exists `ce_member` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `era` int(11) DEFAULT NULL COMMENT '纪元：760',
  `role` tinyint(1) DEFAULT NULL COMMENT '角色:1-validator,2-waiting，3-提名人',
  `member_rank` int(11) DEFAULT NULL COMMENT '排名：1',
  `count_nominators` int(11) DEFAULT NULL COMMENT '提名人数：24',
  `grandpa_vote` int(11) DEFAULT NULL COMMENT '祖选举数：0',
  `session_key` text COMMENT 'Session Key{babe: "9421487f2f2efeead5ae82a3c73d15005e59ed625a32e389f6ccc599b47e2202",…}',
  `latest_mining` int(11) DEFAULT NULL COMMENT '最新出块号：2243773',
  `reward_point` int(11) DEFAULT NULL COMMENT '时代指数：820',
  `account_address` varchar(55) DEFAULT NULL COMMENT '账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW',
  `controller_account_address` varchar(55) DEFAULT NULL COMMENT '控制账户地址：5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2',
  `account_display` text COMMENT '账户json{address: "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW", display: "德坤数矿(DKSK)星城",…}',
  `controller_account_display` text COMMENT '控制账户json{address: "5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2", display: "", judgements: null,…}',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_era_account_address` (`era`,`account_address`) USING BTREE COMMENT '纪元账户索引',
  KEY `idx_era_control_account_address` (`era`,`controller_account_address`) USING BTREE COMMENT '纪元控制账户索引',
  KEY `idx_account_address` (`account_address`) USING BTREE COMMENT '账号地址索引',
  KEY `idx_controller_account_address` (`controller_account_address`) USING BTREE COMMENT '控制账户地址索引'
) ENGINE=InnoDB COMMENT='成员表';

-- 冻结质押信息表（包括当前验证人和候选验证人）
drop table if exists ce_bonded_pledge;
CREATE TABLE If Not Exists `ce_bonded_pledge` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `era` int(11) DEFAULT NULL COMMENT '纪元：760',
  `account_address` varchar(55) DEFAULT NULL COMMENT '账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW',
  `bonded_nominators` decimal(50,20) DEFAULT NULL COMMENT '提名者冻结CRU（全网冻结=提名冻结+验证人冻结）：15216975543390407',
  `bonded_owner` decimal(50,20) DEFAULT NULL COMMENT '验证人冻结CRU：8282301101002825',
  `owner_active_pledge` decimal(50,20) DEFAULT NULL COMMENT '自身有效质押',
  `other_active_pledge` decimal(50,20) DEFAULT NULL COMMENT '他人有效质押',
  `pledge_max` decimal(50,20) DEFAULT NULL COMMENT '质押上限',
  `pledge_total` decimal(50,20) DEFAULT NULL COMMENT '质押总量',
  `guarantee_fee` decimal(50,20) DEFAULT NULL COMMENT '扣留（担保）费率：300000000',
  `score` int(11) DEFAULT NULL COMMENT '得分',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_era_account_address` (`era`,`account_address`) USING BTREE COMMENT '纪元账户索引',
  KEY `idx_account_address` (`account_address`) USING BTREE COMMENT '账户地址索引'
) ENGINE=InnoDB COMMENT='冻结质押信息表';

-- 提名人信息表
drop table if exists ce_nominator;
CREATE TABLE If Not Exists `ce_nominator` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `era` int(11) DEFAULT NULL COMMENT '纪元：760',
  `nominator_rank` int(11) DEFAULT NULL COMMENT '排名：1',
  `bonded` decimal(50,20) DEFAULT NULL COMMENT '投票冻结CRU：117000000000000',
  `nominator_address` varchar(55) DEFAULT NULL COMMENT '提名人账户地址：5FAHiDm8EqUBq5bbXoh2EwKHqttbiQmFSHZnK4xf2d52qw8j',
  `validator_address` varchar(55) DEFAULT NULL COMMENT '验证人账户地址*****************要看提名人和验证人怎么挂的关系',
  `account_display` text COMMENT '提名人账户json{address: "5FAHiDm8EqUBq5bbXoh2EwKHqttbiQmFSHZnK4xf2d52qw8j", display: "", judgements: null,…}',
  `quotient` decimal(50,20) DEFAULT NULL COMMENT '份额（bonded/总数 * 100%）',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_era_validator_nominator` (`era`,`validator_address`,`nominator_address`) USING BTREE COMMENT '纪元验证人提名人索引',
  KEY `idx_era` (`era`) USING BTREE COMMENT '纪元索引',
  KEY `idx_nominator` (`nominator_address`) USING BTREE COMMENT '提名人账户地址索引',
  KEY `idx_validator` (`validator_address`) USING BTREE COMMENT '验证人账户地址索引'
) ENGINE=InnoDB COMMENT='提名人信息表';

-- 纪元信息表
drop table if exists ce_era_stat;
CREATE TABLE If Not Exists `ce_era_stat` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `era` int(11) DEFAULT NULL COMMENT '纪元：760',
  `start_block_timestamp` int(11) DEFAULT NULL COMMENT '起始块打包时间:1625120364',
  `end_block_timestamp` int(11) DEFAULT NULL COMMENT '结束块打包时间:1625120364',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：1-已结束；0-未结束',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_era` (`era`) USING BTREE COMMENT '纪元索引'
) ENGINE=InnoDB COMMENT='纪元信息表';

-- 纪元统计表
drop table if exists ce_era_stat_count;
CREATE TABLE If Not Exists `ce_era_stat_count` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `era` int(11) DEFAULT NULL COMMENT '纪元：760',
  `validator_stash` varchar(100) DEFAULT NULL COMMENT '验证人账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW',
  `start_block_num` int(11) DEFAULT NULL COMMENT '起始块：2242439',
  `end_block_num` int(11) DEFAULT NULL COMMENT '结束块：2243832',
  `start_block_timestamp` int(11) DEFAULT NULL COMMENT '起始块打包时间:1625120364',
  `end_block_timestamp` int(11) DEFAULT NULL COMMENT '结束块打包时间:1625120364',
  `block_produced` text COMMENT '所出块："2128829,2128902,2128997,2129062,2129182,2129223,2129247,2129271,2129312,2129421,2129446,2129447,2129454,2129515,2129570,2129704,2129716,2129718,2129731,2129748,2129774,2129844,2129852,2129892,2129995,2130013,2130030,2130042,2130077,2130084,2130202,2130210,2130273,2130290,2130327,2130353,2130393,2130442,2130447,2130497,2130513,2130596,2130604,2130675,2130679,2130699,2130730,2130808,2130813,2130817,2130873,2130928,2130947,2130999,2131004,2131108"',
  `block_produced_count` int(11) DEFAULT NULL COMMENT '出块数量:74',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：1-已结束；0-未结束',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_era` (`era`,`validator_stash`) USING BTREE COMMENT '纪元索引',
  KEY `idx_validator` (`validator_stash`) USING BTREE COMMENT '验证人账户地址索引'
) ENGINE=InnoDB COMMENT='纪元统计表';

/*
-- 系统配置表
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE If Not Exists `sys_config`  (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_key` varchar(255) NOT NULL COMMENT 'key',
  `config_value` varchar(300) DEFAULT NULL COMMENT 'value',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：1-开；0-关',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '系统配置表';
INSERT INTO `cru_explorer`.`sys_config`(`config_key`, `config_value`, `status`, `remark`, `time_updated`) VALUES ('chain_era_domain', 'http://172.18.128.90:9527', 1, '同步纪元数据域名', NULL);
INSERT INTO `cru_explorer`.`sys_config`(`config_key`, `config_value`, `status`, `remark`, `time_updated`) VALUES ('chain_refresh_block_domain', 'http://172.18.128.90:9527', 1, '刷新区块数据域名', NULL);
INSERT INTO `cru_explorer`.`sys_config`(`config_key`, `config_value`, `status`, `remark`, `time_updated`) VALUES ('chain_sync_block_domain', 'http://127.0.0.1:9527', 1, '同步区块数据域名', NULL);

-- 数据同步日志表
DROP TABLE IF EXISTS `sys_sync_log`;
CREATE TABLE If Not Exists `sys_sync_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sync_mode` varchar(30) DEFAULT NULL COMMENT '同步模式：auto,job,manual',
  `table_name` varchar(30) DEFAULT NULL COMMENT '表名',
  `table_no` int(11) DEFAULT NULL COMMENT '表号',
  `type` tinyint(1) DEFAULT NULL COMMENT '日志类型：1-同步完成；2-同步出错；3-已同步到？',
  `type_desc` varchar(50) DEFAULT NULL COMMENT '日志类型描述：1-done；2-error；3-已同步到？',
  `block_num` int(11) DEFAULT NULL COMMENT '区块号：2228117',
  `error_info` text COMMENT '错误详情',
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time_updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_created` (`time_created`) USING BTREE COMMENT '创建时间'
) ENGINE=InnoDB COMMENT='数据同步日志表';
*/


