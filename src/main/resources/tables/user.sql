CREATE TABLE t_user (
  id              INTEGER PRIMARY KEY AUTOINCREMENT,
  username        VARCHAR(18) UNIQUE NOT NULL,
  password        CHAR(32)           NOT NULL,
  create_time     DATETIME,
  last_login_time DATETIME,
  authority       TINYINT            NOT NULL
);

CREATE INDEX index_user_username
  ON t_user (username);