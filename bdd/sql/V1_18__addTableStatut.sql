create table statut_inscription
(
    statut_id serial not null
        constraint statut_inscription_pkey
            primary key,
    libelle   varchar(20)
);

create table statut_inscription_aud
(
    statut_id serial not null
        constraint statut_inscription_pkey_aud
            primary key,
    libelle   varchar(20)
);

INSERT INTO public.statut_inscription (statut_id, libelle)
VALUES (0, 'inscrit');
INSERT INTO public.statut_inscription (statut_id, libelle)
VALUES (1, 'prerequis_non_valide');
INSERT INTO public.statut_inscription (statut_id, libelle)
VALUES (2, 'en_attente');
INSERT INTO public.statut_inscription (statut_id, libelle)
VALUES (3, 'annulation');

ALTER TABLE candidat_evenement
    ADD COLUMN statut_inscription integer;

ALTER TABLE candidat_evenement_aud
    ADD COLUMN statut_inscription integer;
