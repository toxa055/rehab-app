INSERT INTO cures(name, cure_type)
VALUES ('test-cure1', 'MEDICINE'),
       ('test-cure2', 'PROCEDURE');

INSERT INTO employees(name, position, email, password)
VALUES ('doctor1 name', 'doctor1 position', 'doctor1@doc.ru',
        '$2a$12$T1yui3jXQrGQ7m9OyE13bua2Lx6hpSJNF1V4gmeREbDScJrE4whAa'),
       ('doctor2 name', 'doctor2 position', 'doctor2@doc.ru',
        '$2a$12$Acl3twa62Xn5MoMix9U4kOMFwPXUIfjjZPfc'),
       ('nurse1 name', 'nurse1 position', 'nurse1@nurse.ru',
        '$2a$12$K88L.PuRN1rt9bOzGk2byex7LTm7POGIygvA4HRSq/d/GJG/b/EXq');

INSERT INTO employee_roles
VALUES (3, 'ADMIN'),
       (3, 'DOCTOR'),
       (4, 'DOCTOR'),
       (5, 'NURSE');

INSERT INTO patients(insurance_number, name, birth_date, address)
VALUES (123400, 'test patient1', '1980-10-05', 'test patient1 address'),
       (567800, 'test patient2', '1995-02-17', 'test patient2 address');

INSERT INTO treatments(patient_id, doctor_id, diagnosis)
VALUES (6, 3, 'test diagnosis1'),
       (7, 3, 'test diagnosis2');

INSERT INTO events(patient_id, prescription_id, planned_date, planned_time, event_state, cure_id, dose)
VALUES (6, 0, '2021-11-20', '09:00', 'PLANNED', 1, '1 pill');

INSERT INTO events(patient_id, nurse_id, prescription_id, planned_date, planned_time, event_state, cure_id, dose)
VALUES (6, 5, 0, '2021-11-20', '17:00', 'PLANNED', 1, '1 pill');

INSERT INTO events(patient_id, nurse_id, prescription_id, planned_date, planned_time, event_state, cure_id, dose,
                   end_date, end_time)
VALUES (7, 5, 0, '2021-11-21', '17:00', 'PERFORMED', 1, '1 pill', '2021-11-21', '17:30');

INSERT INTO events(patient_id, prescription_id, planned_date, planned_time, event_state, cure_id, dose,
                   end_date, end_time, comment)
VALUES (7, 0, '2021-11-21', '17:00', 'CANCELLED', 1, '1 pill', '2021-11-21', '15:30', 'cancelled comment');

INSERT INTO employees(name, position, email, password)
VALUES ('nurse2 name', 'nurse2 position', 'nurse2@nurse.ru',
        '$2a$12$dhY9EPQDQUkF9H7VZVtGautMwTQIu8CVpcth6qrjzmuriY8NZSma2');

INSERT INTO employee_roles
VALUES (14, 'NURSE');

INSERT INTO events(patient_id, nurse_id, prescription_id, planned_date, planned_time, event_state, cure_id)
VALUES (6, 14, 0, '2021-11-20', '09:00', 'PLANNED', 2);

INSERT INTO patients(insurance_number, name, birth_date, address, patient_state)
VALUES (9988, 'test patient3', '1990-12-31', 'test patient3 address', 'DISCHARGED');

INSERT INTO periods(count, unit)
VALUES (2, 'DAY'),
       (1, 'WEEK');

INSERT INTO patterns(count, unit, pattern_units)
VALUES (2, 'DAY', 'MORNING, EVENING'),
       (3, 'WEEK', 'MONDAY, WEDNESDAY, FRIDAY');

INSERT INTO treatments(patient_id, doctor_id, diagnosis)
VALUES (7, 4, 'test diagnosis3');

