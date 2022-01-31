ALTER TABLE evenement_modalite_acces
DROP COLUMN nombre_inscrit;

ALTER TABLE evenement_modalite_acces
DROP COLUMN nombre_present;

ALTER TABLE evenement_modalite_acces_aud
DROP COLUMN nombre_inscrit;

ALTER TABLE evenement_modalite_acces_aud
DROP COLUMN nombre_present;

ALTER TABLE candidat_evenement
    ADD COLUMN date_inscription date;

ALTER TABLE candidat
    ADD COLUMN id_rci bigint;