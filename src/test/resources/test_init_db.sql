DROP TABLE IF EXISTS employee_roles;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS cures CASCADE;

DROP SEQUENCE IF EXISTS general_seq;

CREATE SEQUENCE general_seq START WITH 1;

CREATE TABLE employees
(
    id       INTEGER DEFAULT general_seq.nextval PRIMARY KEY,
    name     VARCHAR NOT NULL,
    position VARCHAR NOT NULL,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE UNIQUE INDEX employees_unique_email_idx ON employees (email);

CREATE TABLE employee_roles
(
    employee_id INTEGER NOT NULL,
    role        VARCHAR NOT NULL,
    CONSTRAINT employee_roles_idx UNIQUE (employee_id, role),
    FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE
);

CREATE TABLE cures
(
    id        INTEGER DEFAULT general_seq.nextval PRIMARY KEY,
    name      VARCHAR NOT NULL,
    cure_type VARCHAR NOT NULL
);

CREATE UNIQUE INDEX cures_unique_name_idx ON cures (name);
