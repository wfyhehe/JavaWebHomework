CREATE TABLE t_document (
  id          INT                   AUTO_INCREMENT PRIMARY KEY,
  title       VARCHAR(255) NOT NULL,
  content     TEXT,
  create_time DATETIME,
  status      TINYINT      NOT NULL, # 0:Pending, 1:Approved, 2:Denied
  deleted     BOOLEAN      NOT NULL DEFAULT FALSE
);