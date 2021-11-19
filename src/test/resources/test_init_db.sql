DROP TABLE IF EXISTS cures CASCADE;

DROP SEQUENCE IF EXISTS general_seq;

CREATE SEQUENCE general_seq START WITH 1;

CREATE TABLE cures
(
    id        INTEGER DEFAULT general_seq.nextval PRIMARY KEY,
    name      VARCHAR NOT NULL,
    cure_type VARCHAR NOT NULL
);

CREATE UNIQUE INDEX cures_unique_name_idx ON cures (name);
