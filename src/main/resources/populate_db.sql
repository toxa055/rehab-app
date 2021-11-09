INSERT INTO employees (name, position, email, password)
VALUES ('Ivanov Andey Alekseevich', 'Surgeon', 'doc1@doc.ru',
        '$2a$12$T1yui3jXQrGQ7m9OyE13bua2Lx6hpSJNF1V4gmeREbDScJrE4whAa'),
       ('Petrova Anna Pavlovna', 'Therapist', 'doc2@doc.ru',
        '$2a$12$Acl3twa62Xn5MoMix9U4kOMFwPXUIfjjZPfc/mUUsyium3uFG333y'),
       ('Klimova Natalya Olegovna', 'Nurse', 'nurse1@nurse.ru',
        '$2a$12$K88L.PuRN1rt9bOzGk2byex7LTm7POGIygvA4HRSq/d/GJG/b/EXq'),
       ('Pavlova Olga Andreevna', 'Nurse', 'nurse2@nurse.ru',
        '$2a$12$dhY9EPQDQUkF9H7VZVtGautMwTQIu8CVpcth6qrjzmuriY8NZSma2'),
       ('Egorov Ivan Petrovich', 'Administrator', 'admin@admin.ru',
        '$2a$12$KnWUqzXGwyacBFLmwghZaOT6BWiAE58Vbeougp/7ZZBdeb.RNc22S');

INSERT INTO employee_roles
VALUES (1, 'DOCTOR'),
       (1, 'ADMIN'),
       (2, 'DOCTOR'),
       (3, 'NURSE'),
       (4, 'NURSE'),
       (5, 'ADMIN');

INSERT INTO patients (insurance_number, name, address)
VALUES (1001, 'Andreeva Irina Anatolyevna', 'Saint-Petersburg, Nevsky, 13-28'),
       (1002, 'Saharov Dmitry Markovich', 'Saint-Petersburg, Frunze, 62/3-11'),
       (1003, 'Alekseev Ivan Ivanovich', 'Saint-Petersburg, Korablestroiteley, 102-210'),
       (1004, 'Ignatyev Pavel Alekseevich', 'Saint-Petersburg, Optikov, 12a-51');

INSERT INTO cures (name, cure_type)
VALUES ('Aspirin', 'MEDICINE'),
       ('Metamizol', 'MEDICINE'),
       ('Electrocardiography', 'PROCEDURE'),
       ('Electrophoresis', 'PROCEDURE'),
       ('Midol', 'MEDICINE'),
       ('Motrin', 'MEDICINE'),
       ('Ultram', 'MEDICINE'),
       ('Zipsor', 'MEDICINE'),
       ('Nuprin', 'MEDICINE'),
       ('Anacin', 'MEDICINE'),
       ('Anaferon', 'MEDICINE'),
       ('MRT', 'PROCEDURE'),
       ('Otoscopy', 'PROCEDURE'),
       ('Vaccination', 'PROCEDURE'),
       ('Phototherapy', 'PROCEDURE'),
       ('Celebrex', 'MEDICINE'),
       ('Fluoroscopy', 'PROCEDURE');
