--
-- Aipo is a groupware program developed by TOWN, Inc.
-- Copyright (C) 2004-2015 TOWN, Inc.
-- http://www.aipo.com
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU Affero General Public License as
-- published by the Free Software Foundation, either version 3 of the
-- License, or (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU Affero General Public License for more details.
--
-- You should have received a copy of the GNU Affero General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.
--

-- 20130124
UPDATE `eip_t_acl_portlet_feature` SET `feature_alias_name` = 'ユーザー名簿操作' WHERE `feature_name` = 'addressbook_address_inside' AND `feature_alias_name` = 'アドレス帳（社内アドレス）操作';
UPDATE `eip_t_acl_role` SET `role_name` = 'ユーザー名簿管理者' WHERE feature_id IN (SELECT feature_id FROM eip_t_acl_portlet_feature WHERE feature_name = 'addressbook_address_inside') AND `role_name` = 'アドレス帳（社内アドレス）管理者';
-- 20130124

-- 20130930
ALTER TABLE `turbine_user` ADD `migrate_version` int(11) NOT NULL DEFAULT 0 AFTER `tutorial_forbid`;
UPDATE `turbine_user` SET `migrate_version` = 0 ;
ALTER TABLE `turbine_user` CHANGE COLUMN `tutorial_forbid` `tutorial_forbid` varchar(64) COLLATE utf8_unicode_ci DEFAULT 'F';
-- 20130930

-- 20131111
CREATE TABLE `eip_m_gpdb_kubun` (
  `gpdb_kubun_id` int(11) NOT NULL AUTO_INCREMENT,
  `gpdb_kubun_name` text COLLATE utf8_unicode_ci NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`gpdb_kubun_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_m_gpdb_kubun_value` (
  `gpdb_kubun_value_id` int(11) NOT NULL AUTO_INCREMENT,
  `gpdb_kubun_id` int(11) NOT NULL,
  `gpdb_kubun_value` text COLLATE utf8_unicode_ci NOT NULL,
  `order_no` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`gpdb_kubun_value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_t_gpdb` (
  `gpdb_id` int(11) NOT NULL AUTO_INCREMENT,
  `gpdb_name` text COLLATE utf8_unicode_ci NOT NULL,
  `mail_flg` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`gpdb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_t_gpdb_item` (
  `gpdb_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `gpdb_id` int(11) NOT NULL,
  `gpdb_item_name` text COLLATE utf8_unicode_ci NOT NULL,
  `title_flg` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `required_flg` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL,
  `gpdb_kubun_id` int(11) DEFAULT NULL,
  `list_flg` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `detail_flg` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `size_col` int(11) DEFAULT NULL,
  `size_row` int(11) DEFAULT NULL,
  `line` int(11) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `default_sort_flg` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `asc_desc` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_user_id` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`gpdb_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 20131111
CREATE TABLE `eip_t_gpdb_record` (
  `gpdb_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `gpdb_id` int(11) NOT NULL,
  `gpdb_item_id` int(11) NOT NULL,
  `record_no` int(11) NOT NULL,
  `value` text COLLATE utf8_unicode_ci,
  `create_user_id` int(11) NOT NULL,
  `update_user_id` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`gpdb_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_gpdb_record` ADD INDEX (`record_no`);

CREATE TABLE `eip_t_gpdb_record_file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `gpdb_record_id` int(11) DEFAULT NULL,
  `file_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `file_path` text COLLATE utf8_unicode_ci NOT NULL,
  `file_thumbnail` blob,
  `create_date` date DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT INTO eip_m_gpdb_kubun VALUES (1, '都道府県', now(), now());

INSERT INTO eip_m_gpdb_kubun_value VALUES (1, 1, '北海道', 1, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (2, 1, '青森県', 2, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (3, 1, '岩手県', 3, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (4, 1, '宮城県', 4, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (5, 1, '秋田県', 5, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (6, 1, '山形県', 6, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (7, 1, '福島県', 7, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (8, 1, '茨城県', 8, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (9, 1, '栃木県', 9, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (10, 1, '群馬県', 10, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (11, 1, '埼玉県', 11, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (12, 1, '千葉県', 12, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (13, 1, '東京都', 13, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (14, 1, '神奈川県', 14, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (15, 1, '新潟県', 15, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (16, 1, '富山県', 16, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (17, 1, '石川県', 17, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (18, 1, '福井県', 18, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (19, 1, '山梨県', 19, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (20, 1, '長野県', 20, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (21, 1, '岐阜県', 21, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (22, 1, '静岡県', 22, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (23, 1, '愛知県', 23, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (24, 1, '三重県', 24, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (25, 1, '滋賀県', 25, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (26, 1, '京都府', 26, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (27, 1, '大阪府', 27, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (28, 1, '兵庫県', 28, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (29, 1, '奈良県', 29, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (30, 1, '和歌山県', 30, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (31, 1, '鳥取県', 31, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (32, 1, '島根県', 32, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (33, 1, '岡山県', 33, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (34, 1, '広島県', 34, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (35, 1, '山口県', 35, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (36, 1, '徳島県', 36, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (37, 1, '香川県', 37, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (38, 1, '愛媛県', 38, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (39, 1, '高知県', 39, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (40, 1, '福岡県', 40, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (41, 1, '佐賀県', 41, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (42, 1, '長崎県', 42, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (43, 1, '熊本県', 43, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (44, 1, '大分県', 44, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (45, 1, '宮崎県', 45, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (46, 1, '鹿児島県', 46, now(), now());
INSERT INTO eip_m_gpdb_kubun_value VALUES (47, 1, '沖縄県', 47, now(), now());

CREATE TABLE `eip_t_wiki` (
  `wiki_id` int(11) NOT NULL AUTO_INCREMENT,
  `wiki_name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `parent_id` int(11) DEFAULT 0 NOT NULL,
  `note` text COLLATE utf8_unicode_ci,
  `create_user_id` int(11) DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`wiki_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_t_wiki_file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `wiki_id` int(11) DEFAULT NULL,
  `file_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `file_path` text COLLATE utf8_unicode_ci NOT NULL,
  `file_thumbnail` blob,
  `create_date` date DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_wiki_file` ADD FOREIGN KEY (  `wiki_id` ) REFERENCES  `eip_t_wiki` (`wiki_id`) ON DELETE CASCADE ;
ALTER TABLE `eip_t_wiki` ADD INDEX (`wiki_name`, `parent_id`);

CREATE TABLE `eip_t_project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` text NOT NULL,
  `explanation` text,
  `admin_user_id` int(11) NOT NULL,
  `progress_flg` varchar(1) NOT NULL,
  `progress_rate` int(11) DEFAULT NULL,
  `create_user_id` int(11) NOT NULL,
  `update_user_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_t_project_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_project_member` ADD INDEX (`project_id`, `user_id`);

CREATE TABLE `eip_t_project_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_task_id` int(11) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `tracker` text NOT NULL,
  `task_name` text NOT NULL,
  `explanation` text,
  `status` text NOT NULL,
  `priority` text NOT NULL,
  `start_plan_date` date DEFAULT NULL,
  `end_plan_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `plan_workload` decimal(8,3) DEFAULT NULL,
  `progress_rate` int(11) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `create_user_id` int(11) NOT NULL,
  `update_user_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_project_task` ADD INDEX (`parent_task_id`);
ALTER TABLE `eip_t_project_task` ADD INDEX (`project_id`);

CREATE TABLE `eip_t_project_task_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `workload` decimal(8,3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_project_task_member` ADD INDEX (`task_id`, `user_id`);

CREATE TABLE `eip_t_project_task_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `comment` text NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_project_task_comment` ADD INDEX (`task_id`);

CREATE TABLE `eip_t_project_file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `file_name` varchar(128) NOT NULL,
  `file_path` text NOT NULL,
  `file_thumbnail` blob,
  `create_date` date DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_project_file` ADD INDEX (`project_id`);

CREATE TABLE `eip_t_project_task_file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  `file_name` varchar(128) NOT NULL,
  `file_path` text NOT NULL,
  `file_thumbnail` blob,
  `create_date` date DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_project_task_file` ADD INDEX (`task_id`);

CREATE TABLE `eip_t_project_task_comment_file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `comment_id` int(11) DEFAULT NULL,
  `file_name` varchar(128) NOT NULL,
  `file_path` text NOT NULL,
  `file_thumbnail` blob,
  `create_date` date DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `eip_t_project_task_comment_file` ADD INDEX (`comment_id`);

CREATE TABLE `eip_m_project_kubun` (
  `project_kubun_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_kubun_cd` text NOT NULL,
  `project_kubun_name` text NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`project_kubun_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_m_project_kubun_value` (
  `project_kubun_value_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_kubun_id` int(11) NOT NULL,
  `project_kubun_value_cd` text NOT NULL,
  `project_kubun_value` text NOT NULL,
  `order_no` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`project_kubun_value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 区分マスタデータ

INSERT INTO eip_m_project_kubun(project_kubun_id, project_kubun_cd, project_kubun_name) VALUES(1,'tracker','トラッカー');
INSERT INTO eip_m_project_kubun(project_kubun_id, project_kubun_cd, project_kubun_name) VALUES(2,'status','ステータス');
INSERT INTO eip_m_project_kubun(project_kubun_id, project_kubun_cd, project_kubun_name) VALUES(3,'priority','優先度');
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(1,1,'1','機能',1);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(2,1,'2','バグ',2);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(3,1,'3','サポート',3);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(4,2,'1','新規',1);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(5,2,'2','進行中',2);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(6,2,'3','フィードバック',3);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(7,2,'4','完了',4);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(8,2,'5','却下',5);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(9,2,'6','停止',6);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(10,3,'1','高',1);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(11,3,'2','中',2);
INSERT INTO eip_m_project_kubun_value(project_kubun_value_id, project_kubun_id, project_kubun_value_cd, project_kubun_value, order_no) VALUES(12,3,'3','低',3);

-- 20140801
CREATE INDEX eip_t_schedule_date_index ON eip_t_schedule(`start_date`, `end_date`, `update_date`);
-- 20140801

-- 20140901
CREATE TABLE `eip_t_message_room` (
    `room_id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) COLLATE utf8_unicode_ci,
    `room_type` varchar(1) DEFAULT 'G',
    `auto_name` varchar(1) DEFAULT 'F',
    `last_message` TEXT COLLATE utf8_unicode_ci,
    `last_update_date` datetime DEFAULT NULL,
    `create_user_id` int(11) NOT NULL,
    `photo` blob,
    `photo_smartphone` blob,
    `photo_modified` datetime DEFAULT NULL,
    `has_photo` varchar(1) COLLATE utf8_unicode_ci DEFAULT 'F',
    `create_date` datetime DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_t_message` (
    `message_id` int(11) NOT NULL AUTO_INCREMENT,
    `room_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `message` TEXT COLLATE utf8_unicode_ci,
    `member_count` int(11) NOT NULL,
    `create_date` datetime DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    FOREIGN KEY (`room_id`) REFERENCES `eip_t_message_room` (`room_id`) ON DELETE CASCADE,
    PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create index eip_t_message_room_id_create_date ON eip_t_message(`room_id`, `create_date`);

CREATE TABLE `eip_t_message_room_member` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `room_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `login_name` varchar(32) NOT NULL,
    `target_user_id` int(11),
    FOREIGN KEY (`room_id`) REFERENCES `eip_t_message_room` (`room_id`) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create index eip_t_message_room_member_target_user_id ON eip_t_message_room_member(`target_user_id`);
create index eip_t_message_room_member_user_id_target_user_id ON eip_t_message_room_member(`user_id`, `target_user_id`);

CREATE TABLE `eip_t_message_file` (
    `file_id` int(11) NOT NULL AUTO_INCREMENT,
    `owner_id` int(11),
    `message_id` int(11),
    `room_id` int(11),
    `file_name` varchar(128) NOT NULL,
    `file_path` text NOT NULL,
    `file_thumbnail` blob,
    `create_date` date DEFAULT NULL,
    `update_date` datetime DEFAULT NULL,
    FOREIGN KEY (`message_id`) REFERENCES `eip_t_message` (`message_id`) ON DELETE CASCADE,
    PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eip_t_message_read` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `message_id` int(11) NOT NULL,
    `room_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `is_read` varchar(1) DEFAULT 'F',
    FOREIGN KEY (`message_id`) REFERENCES `eip_t_message` (`message_id`) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create index eip_t_message_read_index1 ON eip_t_message_read(`room_id`, `user_id`, `is_read`);
create index eip_t_message_read_index2 ON eip_t_message_read(`room_id`, `message_id`,`is_read`);
-- 20140901

-- 20140911
CREATE INDEX eip_t_ext_timecard_user_id_index ON eip_t_ext_timecard(user_id);
CREATE INDEX eip_t_note_map_user_id_index ON eip_t_note_map(user_id);
CREATE INDEX eip_t_eventlog_event_type_index ON eip_t_eventlog(event_type);
CREATE INDEX eip_t_eventlog_user_id_index ON eip_t_eventlog(user_id);
CREATE INDEX eip_t_msgboard_topic_category_id_index ON eip_t_msgboard_topic(category_id);
CREATE INDEX eip_t_acl_user_role_map_role_id_index ON eip_t_acl_user_role_map(role_id);
CREATE INDEX eip_t_acl_role_acl_type_index ON eip_t_acl_role(acl_type);
CREATE INDEX eip_m_user_position_index ON eip_m_user_position(position);
ALTER TABLE `eip_m_position` ADD `sort` int(11) DEFAULT NULL AFTER `position_name`;
ALTER TABLE `eip_m_post` ADD `sort` int(11) DEFAULT NULL AFTER `group_name`;
ALTER TABLE `eip_facility_group` ADD `sort` int(11) DEFAULT NULL AFTER `group_id`;
ALTER TABLE `eip_m_facility_group` ADD `sort` int(11) DEFAULT NULL AFTER `group_name`;
ALTER TABLE `eip_t_gpdb_record` ADD INDEX (`gpdb_id`);
