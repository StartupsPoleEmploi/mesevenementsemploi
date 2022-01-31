create table naf
(
    code    varchar(2)
        constraint naf_pkey primary key,
    type    varchar(15),
    libelle varchar(150),
    niveau  varchar(15)
);

alter table naf owner to peactions;


insert into naf (code, type, libelle, niveau)
values ('01', 'divisionNaf', 'Culture et production animale, chasse et services annexes', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('02', 'divisionNaf', 'Sylviculture et exploitation forestière', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('03', 'divisionNaf', 'Pêche et aquaculture', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('05', 'divisionNaf', 'Extraction de houille et de lignite', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('06', 'divisionNaf', 'Extraction d''hydrocarbures', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('07', 'divisionNaf', 'Extraction de minerais métalliques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('08', 'divisionNaf', 'Autres industries extractives', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('09', 'divisionNaf', 'Services de soutien aux industries extractives', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('10', 'divisionNaf', 'Industries alimentaires', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('11', 'divisionNaf', 'Fabrication de boissons', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('12', 'divisionNaf', 'Fabrication de produits à base de tabac', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('13', 'divisionNaf', 'Fabrication de textiles', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('14', 'divisionNaf', 'Industrie de l''habillement', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('15', 'divisionNaf', 'Industrie du cuir et de la chaussure', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('16', 'divisionNaf',
        'Travail du bois et fabrication d''articles en bois et en liège, à l''exception des meubles ; fabrication d''articles en vannerie et sparterie',
        'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('17', 'divisionNaf', 'Industrie du papier et du carton', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('18', 'divisionNaf', 'Imprimerie et reproduction d''enregistrements', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('19', 'divisionNaf', 'Cokéfaction et raffinage', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('20', 'divisionNaf', 'Industrie chimique', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('21', 'divisionNaf', 'Industrie pharmaceutique', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('22', 'divisionNaf', 'Fabrication de produits en caoutchouc et en plastique', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('23', 'divisionNaf', 'Fabrication d''autres produits minéraux non métalliques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('24', 'divisionNaf', 'Métallurgie', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('25', 'divisionNaf', 'Fabrication de produits métalliques, à l''exception des machines et des équipements',
        'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('26', 'divisionNaf', 'Fabrication de produits informatiques, électroniques et optiques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('27', 'divisionNaf', 'Fabrication d''équipements électriques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('28', 'divisionNaf', 'Fabrication de machines et équipements n.c.a.', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('29', 'divisionNaf', 'Industrie automobile', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('30', 'divisionNaf', 'Fabrication d''autres matériels de transport', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('31', 'divisionNaf', 'Fabrication de meubles', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('32', 'divisionNaf', 'Autres industries manufacturières', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('33', 'divisionNaf', 'Réparation et installation de machines et d''équipements', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('35', 'divisionNaf', 'Production et distribution d''électricité, de gaz, de vapeur et d''air conditionné',
        'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('36', 'divisionNaf', 'Captage, traitement et distribution d''eau', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('37', 'divisionNaf', 'Collecte et traitement des eaux usées', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('38', 'divisionNaf', 'Collecte, traitement et élimination des déchets ; récupération', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('39', 'divisionNaf', 'Dépollution et autres services de gestion des déchets', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('41', 'divisionNaf', 'Construction de bâtiments', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('42', 'divisionNaf', 'Génie civil', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('43', 'divisionNaf', 'Travaux de construction spécialisés', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('45', 'divisionNaf', 'Commerce et réparation d''automobiles et de motocycles', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('46', 'divisionNaf', 'Commerce de gros, à l''exception des automobiles et des motocycles', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('47', 'divisionNaf', 'Commerce de détail, à l''exception des automobiles et des motocycles', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('49', 'divisionNaf', 'Transports terrestres et transport par conduites', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('50', 'divisionNaf', 'Transports par eau', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('51', 'divisionNaf', 'Transports aériens', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('52', 'divisionNaf', 'Entreposage et services auxiliaires des transports', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('53', 'divisionNaf', 'Activités de poste et de courrier', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('55', 'divisionNaf', 'Hébergement', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('56', 'divisionNaf', 'Restauration', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('58', 'divisionNaf', 'Édition', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('59', 'divisionNaf',
        'Production de films cinématographiques, de vidéo et de programmes de télévision ; enregistrement sonore et édition musicale',
        'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('60', 'divisionNaf', 'Programmation et diffusion', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('61', 'divisionNaf', 'Télécommunications', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('62', 'divisionNaf', 'Programmation, conseil et autres activités informatiques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('63', 'divisionNaf', 'Services d''information', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('64', 'divisionNaf', 'Activités des services financiers, hors assurance et caisses de retraite', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('65', 'divisionNaf', 'Assurance', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('66', 'divisionNaf', 'Activités auxiliaires de services financiers et d''assurance', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('68', 'divisionNaf', 'Activités immobilières', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('69', 'divisionNaf', 'Activités juridiques et comptables', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('70', 'divisionNaf', 'Activités des sièges sociaux ; conseil de gestion', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('71', 'divisionNaf',
        'Activités d''architecture et d''ingénierie ; activités de contrôle et analyses techniques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('72', 'divisionNaf', 'Recherche-développement scientifique', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('73', 'divisionNaf', 'Publicité et études de marché', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('74', 'divisionNaf', 'Autres activités spécialisées, scientifiques et techniques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('75', 'divisionNaf', 'Activités vétérinaires', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('77', 'divisionNaf', 'Activités de location et location-bail', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('78', 'divisionNaf', 'Activités liées à l''emploi', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('79', 'divisionNaf',
        'Activités des agences de voyage, voyagistes, services de réservation et activités connexes', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('80', 'divisionNaf', 'Enquêtes et sécurité', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('81', 'divisionNaf', 'Services relatifs aux bâtiments et aménagement paysager', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('82', 'divisionNaf', 'Activités administratives et autres activités de soutien aux entreprises', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('84', 'divisionNaf', 'Administration publique et défense ; sécurité sociale obligatoire', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('85', 'divisionNaf', 'Enseignement', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('86', 'divisionNaf', 'Activités pour la santé humaine', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('87', 'divisionNaf', 'Hébergement médico-social et social', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('88', 'divisionNaf', 'Action sociale sans hébergement', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('90', 'divisionNaf', 'Activités créatives, artistiques et de spectacle', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('91', 'divisionNaf', 'Bibliothèques, archives, musées et autres activités culturelles', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('92', 'divisionNaf', 'Organisation de jeux de hasard et d''argent', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('93', 'divisionNaf', 'Activités sportives, récréatives et de loisirs', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('94', 'divisionNaf', 'Activités des organisations associatives', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('95', 'divisionNaf', 'Réparation d''ordinateurs et de biens personnels et domestiques', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('96', 'divisionNaf', 'Autres services personnels', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('97', 'divisionNaf', 'Activités des ménages en tant qu''employeurs de personnel domestique', 'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('98', 'divisionNaf',
        'Activités indifférenciées des ménages en tant que producteurs de biens et services pour usage propre',
        'DIVISION');
insert into naf (code, type, libelle, niveau)
values ('99', 'divisionNaf', 'Activités des organisations et organismes extraterritoriaux', 'DIVISION');



