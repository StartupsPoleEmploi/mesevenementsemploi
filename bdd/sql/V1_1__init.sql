create table etablissement
(
    etablissement_id          bigserial not null
        constraint etablissement_pkey
            primary key,
    code_etablissement        varchar(5)
        constraint uk_code_etablissement unique,
    etablissement_parent_code varchar(5)
        constraint fk_etablissement_parent_code references etablissement (code_etablissement),
    libelle                   varchar(50),
    email                     varchar(10),
    mot_de_passe              varchar(255)
);

alter table etablissement owner to peactions;



create table roles
(
    role_id serial not null
        constraint roles_pkey
            primary key,
    libelle varchar(20)
);

alter table roles owner to peactions;

create table etablissement_roles
(
    etablissement_code varchar(255) not null
        constraint fk_etablissement_roles_etablissement
            references etablissement (code_etablissement),
    role_id            integer      not null
        constraint fk_etablissement_roles_role
            references roles,
    constraint etablissement_roles_pkey
        primary key (etablissement_code, role_id)
);

alter table etablissement_roles owner to peactions;



CREATE SEQUENCE candidat_seq;
create table candidat
(
    candidat_id             bigint DEFAULT nextval('candidat_seq'::regclass) not null
        constraint candidat_pkey primary key,
    civilite                varchar(10),
    email                   varchar(80),
    identifiant_pole_emploi varchar(100),
    nom                     varchar(50),
    prenom                  varchar(50),
    date_de_naissance       date
);

alter table candidat owner to peactions;

CREATE SEQUENCE etat_seq;
create table etat
(
    etat_id bigint DEFAULT nextval('etat_seq'::regclass) not null
        constraint etat_pkey primary key,
    libelle varchar(255)
);

alter table etat owner to peactions;


create table offre
(
    offre_id varchar(255) not null
        constraint offre_pkey primary key
);

alter table offre owner to peactions;



CREATE SEQUENCE categorie_prerequis_seq;
create table categorie_prerequis
(
    categorie_prerequis_id bigint DEFAULT nextval('categorie_prerequis_seq'::regclass) not null
        constraint categorie_prerequis_pkey primary key,
    libelle                varchar(255)
);

alter table categorie_prerequis owner to peactions;


CREATE SEQUENCE prerequis_seq;

create table prerequis
(
    prerequis_id           bigint DEFAULT nextval('prerequis_seq'::regclass)
        constraint prerequis_pkey primary key,
    categorie_prerequis_id bigint
        constraint fk_prerequis_categorie_prerequis references categorie_prerequis,
    libelle                varchar(255)
);

alter table prerequis owner to peactions;

CREATE SEQUENCE evenement_seq;

create table evenement
(
    evenement_id                bigint DEFAULT nextval('evenement_seq'::regclass)
        constraint evenement_pkey primary key,
    code_etablissement          varchar(5)
        constraint fk_evenement_etablissement references etablissement (code_etablissement),
    created_by                  VARCHAR(50),
    updated_by                  VARCHAR(50),
    created_on                  timestamp,
    updated_on                  timestamp,
    date_evenement              date,
    heure_debut                 time,
    heure_fin                   time,
    description                 varchar(1000),
    logo                        bytea,
    deroulement                 varchar(1000),
    est_a_publier               boolean,
    adresse                     varchar(100),
    code_postal                 varchar(5),
    ville                       varchar(50),
    naf_code                    varchar(10),
    rome_id                     varchar(255),
    timezone                    varchar(255),
    titre                       varchar(300),
    etat_id                     bigint
        constraint fk_evenement_etat references etat,
    lien_plus_information         varchar(200),
    libelle_lien_plus_information varchar(50)

);

alter table evenement owner to peactions;


CREATE SEQUENCE sondage_seq;


create table sondage_post_evenement
(
    sondage_id bigint DEFAULT nextval('sondage_seq'::regclass) constraint sondage_pkey primary key

);

alter table sondage_post_evenement owner to peactions;


create table evenement_offre
(
    evenement_id bigint       not null constraint fk_evenement_offre_evenement references evenement,
    offre_id varchar(50) not null
        constraint fk_evenement_offre_offre references offre
);

alter table evenement_offre owner to peactions;

CREATE SEQUENCE intervenant_seq;
create table intervenant
(
    intervenant_id bigint DEFAULT nextval('intervenant_seq'::regclass) not null
        constraint intervenant_pkey primary key,
    contact        varchar(100),
    libelle        varchar(100),
    site_web       varchar(200),
    evenement_id   bigint
        constraint fk_intervenant_evenement references evenement
);

alter table intervenant owner to peactions;

create table prerequis_evenement
(
    evenement_id  bigint  not null constraint fk_prerequis_evenement_evenement references evenement,
    prerequis_id  bigint  not null constraint fk_prerequis_evenement_prerequis references prerequis,
    indispensable boolean not null,
    constraint prerequis_evenement_pkey primary key (evenement_id, prerequis_id)
);

alter table prerequis_evenement owner to peactions;

CREATE SEQUENCE type_tag_seq;
create table type_tag
(
    type_tag_id bigint DEFAULT nextval('type_tag_seq'::regclass) not null
        constraint type_tag_pkey primary key,
    libelle     varchar(100),
    exclusif    boolean                                          not null
);

alter table type_tag owner to peactions;

CREATE SEQUENCE tag_seq;
create table tag
(
    tag_id       bigint DEFAULT nextval('tag_seq'::regclass) not null
        constraint tag_pkey primary key,
    libelle      varchar(100),
    type_tag     bigint
        constraint fk_tag_type_tag references type_tag,
    est_a_publier boolean not null
);

alter table tag owner to peactions;

create table evenement_tag
(
    evenement_id bigint not null
        constraint fk_evenement_tag_evenement references evenement,
    tag_id       bigint not null
        constraint fk_evenement_tag_tag references tag
);

alter table evenement_tag owner to peactions;

CREATE SEQUENCE modalite_acces_seq;
create table modalite_acces
(
    modalite_acces_id bigint DEFAULT nextval('modalite_acces_seq'::regclass) not null
        constraint modalite_acces_pkey primary key,
    libelle varchar(50)
);

alter table modalite_acces owner to peactions;


create table if not exists evenement_modalite_acces
(
    evenement_id bigint not null constraint fk_evenement_modalite_acces_evenement references evenement
(
    evenement_id
),
    modalite_acces_id bigint not null constraint fk_evenement_modalite_acces_modalite_acces references modalite_acces,
    nombre_inscrit integer,
    nombre_place integer,
    nombre_present integer,
    url_acces varchar
(
    200
),
    constraint evenement_modalite_acces_pkey primary key
(
    evenement_id,
    modalite_acces_id
) );

alter table evenement_modalite_acces owner to peactions;

create table candidat_evenement
(
    candidat_id         bigint  not null
        constraint fk_candidat_evenement_candidat references candidat,
    evenement_id        bigint  not null
        constraint fk_candidat_evenement_evenement references evenement,
    sondage_id          bigint
        constraint fk_candidat_evenement_sondage references sondage_post_evenement,
    modalite_acces_id   bigint
        constraint fk_modalite_acces_modalite_accees references modalite_acces,
    a_valider_prerequis boolean not null,
    inscrit             boolean not null,
    present             boolean not null,
    annulation          boolean,
    commentaire         varchar(100),
    constraint candidat_evenement_pkey primary key (candidat_id, evenement_id)
);

alter table candidat_evenement owner to peactions;

COMMIT;
