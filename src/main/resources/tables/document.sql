CREATE TABLE t_document (
  id          INTEGER PRIMARY KEY AUTOINCREMENT ,
  title       VARCHAR(255) NOT NULL,
  content     TEXT,
  create_time DATETIME,
  status      TINYINT      NOT NULL,
  deleted     BOOLEAN      NOT NULL DEFAULT FALSE
);