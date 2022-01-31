ALTER TABLE candidat_evenement
  DROP COLUMN date_inscription;
  
ALTER TABLE candidat_evenement_aud
  DROP COLUMN date_inscription;
  

ALTER TABLE candidat_evenement
	ADD COLUMN date_inscription timestamp,
	ADD COLUMN date_modification timestamp,
	ADD COLUMN date_desinscription timestamp;

ALTER TABLE candidat_evenement_aud
	ADD COLUMN date_inscription timestamp,
	ADD COLUMN date_modification timestamp,
	ADD COLUMN date_desinscription timestamp;

ALTER TABLE evenement
	ADD COLUMN date_annulation timestamp;

ALTER TABLE evenement_aud
	ADD COLUMN date_annulation timestamp;
	