INSERT INTO cures(name, cure_type)
VALUES ('test-cure1', 'MEDICINE'),
       ('test-cure2', 'PROCEDURE');

INSERT INTO employees(name, position, email, password)
VALUES ('doctor1 name', 'doctor1 position', 'doctor1@doc.ru',
        '$2a$12$T1yui3jXQrGQ7m9OyE13bua2Lx6hpSJNF1V4gmeREbDScJrE4whAa'),
       ('doctor2 name', 'doctor2 position', 'doctor2@doc.ru',
        '$2a$12$Acl3twa62Xn5MoMix9U4kOMFwPXUIfjjZPfc'),
       ('nurse name', 'nurse position', 'nurse1@nurse.ru',
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
