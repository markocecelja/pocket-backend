INSERT INTO role (name, created_date_time, updated_date_time)
VALUES ('SYSTEM_ADMIN', now(), now()),
       ('ORGANIZATION_MEMBER', now(), now()),
       ('STUDENT', now(), now());

INSERT INTO organization_role (name, created_date_time, updated_date_time)
VALUES ('ADMIN', now(), now()),
       ('MEMBER', now(), now());

INSERT INTO users (id, first_name, last_name, created_date_time, updated_date_time)
VALUES (NEXTVAL('users_id_seq'), 'Ivan', 'Kovač', now(), now()),
       (NEXTVAL('users_id_seq'), 'Filip', 'Franjić', now(), now()),
       (NEXTVAL('users_id_seq'), 'Ana', 'Đukić', now(), now());

INSERT INTO user_login (id, username, password, user_id, created_date_time, updated_date_time)
VALUES (NEXTVAL('user_login_id_seq'), 'ikovac', '$2a$12$JB8fZ4Kojd6/H2U.SS9mq.1fjg3i3dgmmURGCH0Huw0LnO06P/fb2', 1,
        now(), now()),
       (NEXTVAL('user_login_id_seq'), 'ffranjic', '$2a$12$z6kEhBBMjjSfS.Ld8ernYuAtU1Y2ChvKJ34swnD1sXzCBBEcTmxmK', 2,
        now(), now()),
       (NEXTVAL('user_login_id_seq'), 'adukic', '$2a$12$ZBQ3CxyZ8H9i9DVpeHTKBuBb7hZEHTkYOnpnZ0UZdaEUcWDlDeb.e', 3,
        now(), now());

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2);

INSERT INTO organization(id, name, description, active, created_date_time, updated_date_time)
VALUES (NEXTVAL('organization_id_seq'), 'Testna organizacija', 'Opis testne organizacije', true, now(), now()),
       (NEXTVAL('organization_id_seq'), 'Neaktivna organizacija', 'Opis neaktivne organizacije', false, now(), now());

INSERT INTO organization_code(id, value, expiration_date, organization_id, created_date_time, updated_date_time)
VALUES (NEXTVAL('organization_code_id_seq'), 'A18rT56PO9', DATEADD(DD, 30, now()), 1, now(), now());

INSERT INTO organization_member(organization_id, user_id, organization_role_id)
VALUES (1, 2, 1),
       (1, 3, 2);