DROP TABLE IF EXISTS events CASCADE;

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
    FOREIGN KEY (prescription_id) REFERENCES prescriptions ON DELETE CASCADE,
    FOREIGN KEY (cure_id) REFERENCES cures (id) ON DELETE CASCADE
)
