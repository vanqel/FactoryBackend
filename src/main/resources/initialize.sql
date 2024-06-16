INSERT INTO public.users (id, "updatedAt", deleted_at, username, password, "isBlocked", name)
VALUES (1, null, null, 'factory_admin', '$2a$10$zdOrCZ56mzaKMxWnlGZMCuqXE4V2S0ksNCAJL0lliuo.3vPpitfd.',
        false, 'Анастасия');

INSERT INTO public.roles(id, "role_name")
VALUES (1, 'ADMIN'),
       (2, 'DIMK'),
       (3, 'N_PLACE'),
       (4, 'N_EXPORT');

INSERT INTO public.depratment(id, name)
VALUES (1, 'Отдел администрирования и управления персоналом');

INSERT INTO public.users_department (user_id, department_id)
VALUES (1, 1);

INSERT INTO public.users_roles (user_id, role_id)
VALUES (1, 1)
