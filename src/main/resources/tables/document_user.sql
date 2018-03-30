CREATE TABLE t_document_user (
  id      INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INT NOT NULL,
  document_id INT NOT NULL,
  CONSTRAINT fk_du_user_id
  FOREIGN KEY (user_id) REFERENCES t_user (id),
  CONSTRAINT fk_du_document_id
  FOREIGN KEY (document_id) REFERENCES t_document (id)
);

CREATE INDEX index_du_user_id
  ON t_document_user (user_id);

CREATE INDEX index_du_document_id
  ON t_document_user (document_id);

