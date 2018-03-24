CREATE TABLE t_document (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  title       VARCHAR(255) NOT NULL,
  content     TEXT,
  create_time DATETIME,
  status      TINYINT      NOT NULL, # 0:启用,1:停用,2:删除
  deleted     BOOLEAN
);