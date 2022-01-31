CREATE SEQUENCE hibernate_sequence INCREMENT 1 MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

create table revinfo
(
    rev      integer not null
        constraint revinfo_pkey
            primary key,
    revtstmp bigint
);

alter table revinfo
    owner to peactions;

create table revtype
(
    id   integer not null
        constraint revtype_pkey
            primary key,
    type varchar(3)
);

alter table revtype
    owner to peactions;

INSERT INTO public.revtype (id, type)
VALUES (0, 'ADD');
INSERT INTO public.revtype (id, type)
VALUES (1, 'MOD');
INSERT INTO public.revtype (id, type)
VALUES (2, 'DEL');

create table candidat_aud
(
    candidat_id             bigint  not null,
    rev                     integer not null
        constraint fk_candidat_aud_revinfo
            references revinfo,
    revtype                 smallint,
    civilite                varchar(255),
    email                   varchar(255),
    identifiant_pole_emploi varchar(255),
    nom                     varchar(255),
    prenom                  varchar(255),
    date_de_naissance       date,
    constraint candidat_aud_pkey
        primary key (candidat_id, rev)
);

alter table candidat_aud
    owner to peactions;



create table candidat_evenement_aud
(
    candidat_id         bigint  not null,
    evenement_id        bigint  not null,
    rev                 integer not null
        constraint fk_candidat_evt_aud_revinfo
            references revinfo,
    revtype             smallint,
    a_valider_prerequis boolean,
    inscrit             boolean,
    present             boolean,
    modalite_acces_id   bigint,
    sondage_id          bigint,
    constraint candidat_evenement_aud_pkey
        primary key (candidat_id, evenement_id, rev)

);

alter table candidat_evenement_aud
    owner to peactions;

create table if not exists evenement_aud
(
    evenement_id                bigint  not null,
    rev                         integer not null
        constraint fk_evenement_aud_revinfo
            references revinfo,
    revtype                     smallint,
    date_evenement              date,
    created_by                  VARCHAR(50),
    updated_by                  VARCHAR(50),
    created_on                  timestamp,
    updated_on                  timestamp,
    deroulement                 varchar(1000),
    description                 varchar(1000),
    est_a_publier boolean,
    heure_debut time,
    heure_fin time,
    adresse varchar
(
    100
),
    code_postal varchar
(
    5
),
    ville varchar
(
    50
),
    logo bytea,
    naf_code varchar
(
    10
),
    rome_id varchar
(
    255
),
    timezone varchar(255),
    titre                       varchar(255),
    code_etablissement          bigint,
    etat_id                     bigint
        constraint fk_evenement_etat references etat,
    lien_plus_information         varchar(200),
    libelle_lien_plus_information varchar(50)
);

alter table evenement_aud
    owner to peactions;

create table if not exists evenement_modalite_acces_aud
(
    evenement_id      bigint  not null,
    modalite_acces_id bigint  not null,
    rev               integer not null
        constraint fk_evt_modalite_acces_aud_revinfo
            references revinfo,
    revtype           smallint,
    nombre_inscrit    integer,
    nombre_place      integer,
    nombre_present    integer,
    url_acces         varchar(200),
    constraint evenement_modalite_acces_aud_pkey
        primary key (evenement_id, modalite_acces_id, rev)
);

alter table evenement_modalite_acces_aud
    owner to peactions;

create table if not exists evenement_offre_aud
(
    rev          integer      not null
        constraint fk_evt_offre_aud_revinfo
            references revinfo,
    evenement_id bigint       not null,
    offre_id     varchar(255) not null,
    revtype      smallint,
    constraint evenement_offre_aud_pkey
        primary key (rev, evenement_id, offre_id)
);

alter table evenement_offre_aud
    owner to peactions;


create table if not exists evenement_tag_aud
(
    rev          integer not null
        constraint fk_evenement_tag_aud_revinfo
            references revinfo,
    evenement_id bigint  not null,
    tag_id       bigint  not null,
    revtype      smallint,
    constraint evenement_tag_aud_pkey
        primary key (rev, evenement_id, tag_id)
);

alter table evenement_tag_aud
    owner to peactions;


create table if not exists intervenant_aud
(
    intervenant_id bigint  not null,
    rev            integer not null
        constraint fk_evenement_tag_aud_revinfo
            references revinfo,
    revtype        smallint,
    contact        varchar(255),
    libelle        varchar(255),
    site_web       varchar(255),
    evenement_id   bigint,
    constraint intervenant_aud_pkey
        primary key (intervenant_id, rev)
);

alter table intervenant_aud
    owner to peactions;

create table if not exists prerequis_evenement_aud
(
    evenement_id  bigint  not null,
    prerequis_id  bigint  not null,
    rev           integer not null
        constraint fk_prerequis_evenement_aud_revinfo
            references revinfo,
    revtype       smallint,
    indispensable boolean,
    constraint prerequis_evenement_aud_pkey
        primary key (evenement_id, prerequis_id, rev)
);

alter table prerequis_evenement_aud
    owner to peactions;

