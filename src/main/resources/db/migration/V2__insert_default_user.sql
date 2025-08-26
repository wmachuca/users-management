DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM test.users WHERE correo = 'wmachuca@admin.local'
    ) THEN
        INSERT INTO test.users (correo, telefono, nombres, apellidos, contrasena)
        VALUES (
            'wmachuca@admin.local',
            '3125636503',
            'Wilmer',
            'Machuca',
            '$2a$10$I/AGzG.04KckXoOFOALxr.8ZemRnx2Q60t8ESAEw2iVdYQII1Un9i'
        );
    END IF;
END $$;