CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO
$$
    DECLARE
        roleAdminId BIGINT = (SELECT id
                              FROM roles
                              where code = 'ADMIN');
        rolePlayerId BIGINT = (SELECT id
                              FROM roles
                              where code = 'PLAYER');
        adminId     BIGINT;
        playerId    BIGINT;
    BEGIN
        INSERT INTO users(email, password, name, role_id)
        VALUES ('admin@mail.com', crypt('secureadmin', gen_salt('bf')), 'Витя', roleAdminId)
        RETURNING id INTO adminId;

        INSERT INTO user_admins(id)
        VALUES (adminId);

        INSERT INTO users(email, password, name, role_id)
        VALUES ('player@mail.com', crypt('secureplayer', gen_salt('bf')), 'Вася', rolePlayerId)
        RETURNING id INTO playerId;

        INSERT INTO user_players(id)
        VALUES (playerId);



    END
$$;
