DROP TABLE IF EXISTS prescriptions CASCADE;
DROP TABLE IF EXISTS treatments;
DROP TABLE IF EXISTS pattern_units;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS employee_roles;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS patterns;
DROP TABLE IF EXISTS periods;
DROP TABLE IF EXISTS cures;

DROP SEQUENCE IF EXISTS general_seq;

CREATE SEQUENCE general_seq START WITH 1;

CREATE TABLE employees
(
    id       INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
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
    id        INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
    name      VARCHAR NOT NULL,
    cure_type VARCHAR NOT NULL
);

CREATE UNIQUE INDEX cures_unique_name_idx ON cures (name);

CREATE TABLE patients
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
    insurance_number INTEGER NOT NULL,
    name             VARCHAR NOT NULL,
    address          VARCHAR NOT NULL,
    patient_state    VARCHAR NOT NULL    DEFAULT 'TREATING'
);

CREATE UNIQUE INDEX patients_unique_insurance_number_idx ON patients (insurance_number);

CREATE TABLE periods
(
    id    INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
    count INTEGER NOT NULL,
    unit  VARCHAR NOT NULL
);

CREATE TABLE patterns
(
    id    INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
    count INTEGER NOT NULL,
    unit  VARCHAR NOT NULL
);

CREATE TABLE pattern_units
(
    pattern_id INTEGER NOT NULL,
    unit       VARCHAR NOT NULL,
    CONSTRAINT pattern_units_idx UNIQUE (pattern_id, unit),
    FOREIGN KEY (pattern_id) REFERENCES patterns (id) ON DELETE CASCADE
);

CREATE TABLE treatments
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
    patient_id      INTEGER NOT NULL,
    doctor_id       INTEGER NOT NULL,
    prescription_id INTEGER,
    treatment_date  DATE    NOT NULL    DEFAULT CURRENT_DATE,
    diagnosis       VARCHAR NOT NULL,
    closed          BOOLEAN NOT NULL    DEFAULT FALSE,
    FOREIGN KEY (patient_id) REFERENCES patients (id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES employees (id) ON DELETE CASCADE
);

CREATE TABLE prescriptions
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
    doctor_id         INTEGER NOT NULL,
    patient_id        INTEGER NOT NULL,
    treatment_id      INTEGER NOT NULL,
    prescription_date DATE    NOT NULL    DEFAULT CURRENT_DATE,
    cure_id           INTEGER NOT NULL,
    pattern_id        INTEGER NOT NULL,
    period_id         INTEGER NOT NULL,
    dose              VARCHAR NOT NULL    DEFAULT 'According to instruction.',
    FOREIGN KEY (doctor_id) REFERENCES employees (id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients (id) ON DELETE CASCADE,
    FOREIGN KEY (cure_id) REFERENCES cures (id) ON DELETE CASCADE,
    FOREIGN KEY (pattern_id) REFERENCES patterns ON DELETE CASCADE,
    FOREIGN KEY (period_id) REFERENCES periods (id) ON DELETE CASCADE
);

ALTER TABLE treatments
    ADD CONSTRAINT prescription_fk FOREIGN KEY (prescription_id) REFERENCES prescriptions (id) ON DELETE CASCADE;

ALTER TABLE prescriptions
    ADD CONSTRAINT treatment_fk FOREIGN KEY (treatment_id) REFERENCES treatments (id) ON DELETE CASCADE;

CREATE TABLE events
(
    id           INTEGER PRIMARY KEY DEFAULT nextval('general_seq'),
    patient_id   INTEGER NOT NULL,
    nurse_id     INTEGER,
    planned_date DATE    NOT NULL,
    planned_time TIME    NOT NULL    DEFAULT '9:00',
    event_state  VARCHAR NOT NULL    DEFAULT 'PLANNED',
    cure_id      INTEGER NOT NULL,
    dose         VARCHAR NOT NULL    DEFAULT 'According to instruction.',
    end_date     DATE,
    end_time     TIME,
    comment      VARCHAR,
    FOREIGN KEY (patient_id) REFERENCES patients (id) ON DELETE CASCADE,
    FOREIGN KEY (nurse_id) REFERENCES employees (id) ON DELETE CASCADE,
    FOREIGN KEY (cure_id) REFERENCES cures (id) ON DELETE CASCADE
)
