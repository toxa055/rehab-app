INSERT INTO employees (name, position, email, password)
VALUES ('Ivanov Andey Alekseevich', 'Surgeon', 'doc1@doc.ru', '$2a$12$T1yui3jXQrGQ7m9OyE13bua2Lx6hpSJNF1V4gmeREbDScJrE4whAa'),
       ('Petrova Anna Pavlovna', 'Therapist', 'doc2@doc.ru', '$2a$12$Acl3twa62Xn5MoMix9U4kOMFwPXUIfjjZPfc/mUUsyium3uFG333y'),
       ('Klimova Natalya Olegovna', 'Nurse', 'nurse1@nurse.ru',
        '$2a$12$K88L.PuRN1rt9bOzGk2byex7LTm7POGIygvA4HRSq/d/GJG/b/EXq'),
       ('Pavlova Olga Andreevna', 'Nurse', 'nurse2@nurse.ru',
        '$2a$12$dhY9EPQDQUkF9H7VZVtGautMwTQIu8CVpcth6qrjzmuriY8NZSma2');

INSERT INTO employee_roles
VALUES (1, 'DOCTOR'),
       (1, 'ADMIN'),
       (2, 'DOCTOR'),
       (3, 'NURSE'),
       (4, 'NURSE');

INSERT INTO patients (insurance_number, name, address)
VALUES (1001, 'Andreeva Irina Anatolyevna', 'Saint-Petersburg, Nevsky, 13-28'),
       (1002, 'Saharov Dmitry Markovich', 'Saint-Petersburg, Frunze, 62/3-11'),
       (1003, 'Alekseev Ivan Ivanovich', 'Saint-Petersburg, Korablestroiteley, 102-210'),
       (1004, 'Ignatyev Pavel Alekseevich', 'Saint-Petersburg, Optikov, 12a-51');

INSERT INTO cures (name, cure_type)
VALUES ('Aspirin', 'MEDICINE'),
       ('Metamizol', 'MEDICINE'),
       ('Electrocardiography', 'PROCEDURE'),
       ('Electrophoresis', 'PROCEDURE');
/*
INSERT INTO periods (count, unit)
VALUES (3, 'DAY'),
       (2, 'WEEK'),
       (1, 'MONTH');

INSERT INTO patterns (count, unit)
VALUES (2, 'DAY'),
       (3, 'WEEK');

INSERT INTO pattern_units (pattern_id, unit)
VALUES (15, 'MORNING'),
       (15, 'EVENING'),
       (16, 'MONDAY'),
       (16, 'WEDNESDAY'),
       (16, 'FRIDAY');

INSERT INTO treatments (patient_id, doctor_id, diagnosis)
VALUES (4, 1, 'diagnosis1'),
       (5, 1, 'diagnosis2'),
       (6, 2, 'diagnosis3');

INSERT INTO prescriptions (doctor_id, patient_id, treatment_id, cure_id, pattern_id, period_id, dose)
VALUES (1, 4, 17, 8, 15, 12, 'dose1');

INSERT INTO prescriptions (doctor_id, patient_id, treatment_id, cure_id, pattern_id, period_id)
VALUES (2, 6, 19, 10, 16, 14);

INSERT INTO events (patient_id, nurse_id, planned_date, planned_time, cure_id, dose)
VALUES (4, 3, '2021-10-10', '9:30', 8, 'dose1'),
       (4, 3, '2021-10-10', '17:30', 8, 'dose1');

INSERT INTO events (patient_id, nurse_id, planned_date, cure_id)
VALUES (5, 3, '2021-10-10', 10),
       (5, 3, '2021-10-12', 10),
       (5, 3, '2021-10-14', 10);
*/
