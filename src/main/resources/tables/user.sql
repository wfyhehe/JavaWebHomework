CREATE TABLE t_user (
  id              INT AUTO_INCREMENT PRIMARY KEY,
  username        VARCHAR(18) UNIQUE NOT NULL,
  password        CHAR(32)           NOT NULL,
  create_time     DATETIME,
  last_login_time DATETIME,
  status          TINYINT            NOT NULL, # 0:启用,1:停用,2:删除
  authority       TINYINT            NOT NULL # 0: superadmin 1: 增删改查 2:增查 3:只读
);

CREATE INDEX index_user_username
  ON t_user (username);