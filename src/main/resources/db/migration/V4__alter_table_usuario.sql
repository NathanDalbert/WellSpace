
ALTER TABLE usuario
  ALTER COLUMN user_role SET DEFAULT 'LOCADOR';

UPDATE usuario
  SET user_role = 'LOCADOR'
  WHERE user_role IS NULL;
