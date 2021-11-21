DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS treatments CASCADE;
DROP TABLE IF EXISTS employee_roles;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS patients CASCADE;
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

CREATE TABLE patients
(
    id               INTEGER          DEFAULT general_seq.nextval PRIMARY KEY,
    insurance_number INTEGER NOT NULL,
    name             VARCHAR NOT NULL,
    birth_date       DATE    NOT NULL,
    address          VARCHAR NOT NULL,
    patient_state    VARCHAR NOT NULL DEFAULT 'TREATING'
);

CREATE UNIQUE INDEX patients_unique_insurance_number_idx ON patients (insurance_number);

CREATE TABLE treatments
(
    id             INTEGER          DEFAULT general_seq.nextval PRIMARY KEY,
    patient_id     INTEGER NOT NULL,
    doctor_id      INTEGER NOT NULL,
    treatment_date DATE    NOT NULL DEFAULT CURRENT_DATE,
    diagnosis      VARCHAR NOT NULL,
    close_date     DATE,
    closed         BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (patient_id) REFERENCES patients (id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES employees (id) ON DELETE CASCADE
);

CREATE TABLE events
(
    id              INTEGER          DEFAULT general_seq.nextval PRIMARY KEY,
    patient_id      INTEGER NOT NULL,
    nurse_id        INTEGER,
    prescription_id INTEGER NOT NULL,
    planned_date    DATE    NOT NULL,
    planned_time    TIME    NOT NULL DEFAULT '9:00',
    event_state     VARCHAR NOT NULL DEFAULT 'PLANNED',
    cure_id         INTEGER NOT NULL,
    dose            VARCHAR NOT NULL DEFAULT 'According to instruction.',
    end_date        DATE,
    end_time        TIME,
    comment         VARCHAR,
    FOREIGN KEY (patient_id) REFERENCES patients (id) ON DELETE CASCADE,
    FOREIGN KEY (nurse_id) REFERENCES employees (id) ON DELETE CASCADE,
--     FOREIGN KEY (prescription_id) REFERENCES prescriptions ON DELETE CASCADE,
    FOREIGN KEY (cure_id) REFERENCES cures (id) ON DELETE CASCADE
)
