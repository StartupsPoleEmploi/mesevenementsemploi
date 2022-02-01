INSERT INTO roles(role_id, libelle)
VALUES (0, 'ROLE_AGENCE');
INSERT INTO roles(role_id, libelle)
VALUES (1, 'ROLE_SUPERVISEUR');
INSERT INTO roles(role_id, libelle)
VALUES (2, 'ROLE_ADMIN');

INSERT INTO public.etablissement (etablissement_id, code_etablissement, libelle, mot_de_passe)
VALUES (DEFAULT, '00001', 'DG', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08');
