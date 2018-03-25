CREATE TABLE t_user (
  id              INT AUTO_INCREMENT PRIMARY KEY,
  username        VARCHAR(18) UNIQUE NOT NULL,
  password        CHAR(32)           NOT NULL,
  create_time     DATETIME,
  last_login_time DATETIME,
  authority       TINYINT            NOT NULL # 1:只读, 2:增查, 3:增删改查, 100:superadmin
);

CREATE INDEX index_user_username
  ON t_user (username);