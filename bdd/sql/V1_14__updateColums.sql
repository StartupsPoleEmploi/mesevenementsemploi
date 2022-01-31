ALTER TABLE candidat_evenement_aud
    ADD COLUMN date_inscription date;

ALTER TABLE candidat_aud
    ADD COLUMN id_rci bigint;
