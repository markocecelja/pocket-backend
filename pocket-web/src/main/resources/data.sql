INSERT INTO role (name, created_date_time, updated_date_time)
VALUES ('SYSTEM_ADMIN', now(), now()),
       ('ORGANIZATION_MEMBER', now(), now()),
       ('STUDENT', now(), now());

INSERT INTO organization_role (name, created_date_time, updated_date_time)
VALUES ('ADMIN', now(), now()),
       ('MEMBER', now(), now());

INSERT INTO category (active, name, created_date_time, updated_date_time)
VALUES (true, 'Kultura', now(), now()),
       (true, 'Sport', now(), now()),
       (true, 'Hrana', now(), now()),
       (true, 'Zabava', now(), now()),
       (false, 'Neaktivna kategorija', now(), now());

INSERT INTO users (id, first_name, last_name, created_date_time, updated_date_time)
VALUES (NEXTVAL('users_id_seq'), 'Ivan', 'Kovač', now(), now()),
       (NEXTVAL('users_id_seq'), 'Filip', 'Franjić', now(), now()),
       (NEXTVAL('users_id_seq'), 'Ana', 'Đukić', now(), now()),
       (NEXTVAL('users_id_seq'), 'Pero', 'Perić', now(), now()),
       (NEXTVAL('users_id_seq'), 'Ivan', 'Radić', now(), now());

INSERT INTO user_login (id, username, password, user_id, created_date_time, updated_date_time)
VALUES (NEXTVAL('user_login_id_seq'), 'ikovac', '$2a$12$JB8fZ4Kojd6/H2U.SS9mq.1fjg3i3dgmmURGCH0Huw0LnO06P/fb2', 1,
        now(), now()),
       (NEXTVAL('user_login_id_seq'), 'ffranjic', '$2a$12$z6kEhBBMjjSfS.Ld8ernYuAtU1Y2ChvKJ34swnD1sXzCBBEcTmxmK', 2,
        now(), now()),
       (NEXTVAL('user_login_id_seq'), 'adukic', '$2a$12$ZBQ3CxyZ8H9i9DVpeHTKBuBb7hZEHTkYOnpnZ0UZdaEUcWDlDeb.e', 3,
        now(), now()),
       (NEXTVAL('user_login_id_seq'), 'pperic', '$2a$12$w9yyRLSacCwFIulhIWPg5uWHoCINUH.vfkuAiO1FhSDAO9mv9orJu', 4,
        now(), now()),
       (NEXTVAL('user_login_id_seq'), 'iradic', '$2a$12$8ZQwQpqNrdVR1oZXAufp0OCYhDbY9jRGyOsDpLbB0/MWvpzs14/j.', 5,
        now(), now());

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 3);

INSERT INTO organization(id, name, description, active, created_date_time, updated_date_time)
VALUES (NEXTVAL('organization_id_seq'), 'Testna organizacija', 'Opis testne organizacije', true, now(), now()),
       (NEXTVAL('organization_id_seq'), 'Neaktivna organizacija', 'Opis neaktivne organizacije', false, now(), now());

INSERT INTO organization_code(id, value, expiration_date, organization_id, created_date_time, updated_date_time)
VALUES (NEXTVAL('organization_code_id_seq'), 'A18rT56PO9', DATEADD(DD, 30, now()), 1, now(), now()),
       (NEXTVAL('organization_code_id_seq'), 'PL87rTosp7', DATEADD(DD, 30, now()), 2, now(), now());

INSERT INTO organization_member(organization_id, user_id, organization_role_id)
VALUES (1, 2, 1),
       (1, 3, 2);

INSERT INTO post(id, active, title, description, category_id, organization_id, created_date_time, updated_date_time)
VALUES (NEXTVAL('post_id_seq'), true, 'Testna objava', 'Iskoristite kod 489766dad5 za ostvaranje popusta na hranu!', 3, 1, now(), now()),
       (NEXTVAL('post_id_seq'), true, 'Testna objava u drugoj kategoriji', 'Test', 2, 1, now(), now()),
       (NEXTVAL('post_id_seq'), false, 'Neaktivna objava', 'Neaktivno!', 2, 1, now(), now()),
       (NEXTVAL('post_id_seq'), false, 'Objava druge organizacije', 'Objava!', 4, 2, now(), now());

INSERT INTO chat(id, user_id, post_id, created_date_time, updated_date_time)
VALUES (NEXTVAL('chat_id_seq'), 5, 1, now(), now()),
       (NEXTVAL('chat_id_seq'), 5, 4, now(), now());

INSERT INTO message(id, created_by_id, chat_id, text, created_date_time, updated_date_time)
VALUES (NEXTVAL('message_id_seq'), 5, 1, 'Može pitanje?', '2022-05-26 12:40:28.831', '2022-05-26 12:40:28.831'),
       (NEXTVAL('message_id_seq'), 2, 1, 'Recite', '2022-05-26 12:45:28.831', '2022-05-26 12:45:28.831');