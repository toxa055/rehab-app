INSERT INTO CURES(name, cure_type)
VALUES ('test-cure1', 'MEDICINE'),
       ('test-cure2', 'PROCEDURE');

INSERT INTO EMPLOYEES(name, position, email, password)
VALUES ('doctor name', 'doctor position', 'doctor1@doc.ru',
        '$2a$12$T1yui3jXQrGQ7m9OyE13bua2Lx6hpSJNF1V4gmeREbDScJrE4whAa'),
       ('nurse name', 'nurse position', 'nurse1@nurse.ru',
        '$2a$12$K88L.PuRN1rt9bOzGk2byex7LTm7POGIygvA4HRSq/d/GJG/b/EXq');

INSERT INTO employee_roles
VALUES (3, 'ADMIN'),
       (3, 'DOCTOR'),
       (4, 'NURSE');
