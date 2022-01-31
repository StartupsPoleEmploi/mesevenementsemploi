CREATE TABLE IF NOT EXISTS departement
(
    departement_id
    numeric
    constraint
    departement_pkey
    primary
    key,
    departement_code
    varchar
(
    3
) DEFAULT NULL,
    departement_nom varchar
(
    255
) DEFAULT NULL
    );

alter table departement owner to peactions;



INSERT INTO departement (departement_id, departement_code, departement_nom)
VALUES (1, '09', 'Ariège'),
       (2, '11', 'Aude'),
       (3, '12', 'Aveyron'),
       (4, '30', 'Gard'),
       (5, '31', 'Haute-Garonne'),
       (6, '32', 'Gers'),
       (7, '34', 'Hérault'),
       (8, '46', 'Lot'),
       (9, '48', 'Lozère'),
       (10, '65', 'Hautes-Pyrénées'),
       (11, '66', 'Pyrénées-Orientales'),
       (12, '81', 'Tarn'),
       (13, '82', 'Tarn-et-Garonne')

