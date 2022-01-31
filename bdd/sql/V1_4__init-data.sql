INSERT INTO public.etat (etat_id, libelle)
VALUES (0, 'Brouillon');
INSERT INTO public.etat (etat_id, libelle)
VALUES (1, 'Valider');

SELECT setval('etat_seq', 2, true);


INSERT INTO public.categorie_prerequis (categorie_prerequis_id, libelle)
VALUES (0, 'Diplôme');
INSERT INTO public.categorie_prerequis (categorie_prerequis_id, libelle)
VALUES (1, 'Age');
INSERT INTO public.categorie_prerequis (categorie_prerequis_id, libelle)
VALUES (2, 'Permis');
INSERT INTO public.categorie_prerequis (categorie_prerequis_id, libelle)
VALUES (3, 'Savoir être');
INSERT INTO public.categorie_prerequis (categorie_prerequis_id, libelle)
VALUES (4, 'Contrainte');
SELECT setval('categorie_prerequis_seq', 4, true);

INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (0, 0, 'Avoir un Bac+5 ou plus');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (1, 0, 'Avoir un Bac+4');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (2, 0, 'Avoir un Bac+3');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (3, 0, 'Avoir un Bac+2');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (4, 0, 'Avoir un Bac');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (5, 0, 'Avoir le Niveau Bac');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (6, 0, 'Avoir un CAP ou BEP');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (7, 0, 'Savoir lire, écrire, compter en français');

INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (8, 1, 'Avoir moins de 26 ans');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (9, 1, 'Avoir moins de 31 ans (si en situation de handicap)');

INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (10, 2, 'Permis B - Voiture');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (11, 2, 'Permis C - Poids lourd');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (13, 2, 'Permis D - Transport de personnes');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (14, 2, 'Permis CACES - Cariste');

INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (15, 3, 'Avoir une appétence pour le bricolage, les activités manuelles');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (16, 3, 'Etre à l''écoute et au service du client');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (17, 3, 'Travailler en situation de stress');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (18, 3, 'Travailler seul(e)');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (19, 3, 'Avoir une appétence pour le numérique');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (20, 3, 'Etre polyvalent(e)');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (21, 3, 'Etre dynamique');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (22, 3, 'Travailler en équipe');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (23, 3, 'Etre rigoureux');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (24, 3, 'Avoir le sens de l''organisation');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (25, 3, 'Etre bienveillant');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (26, 3, 'Etre respectueux(se) des consignes / des règles de sécurité');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (27, 3, 'Avoir une bonne capacité d''adaptation');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (28, 3, 'Etre autonome');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (29, 3, 'Avoir le sens de l''analyse');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (30, 3, 'Etre curieux(se)');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (31, 3, 'Etre force de proposition');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (32, 3, 'Aimer conduire');

INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (34, 4, 'Travailler le samedi');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (35, 4, 'Travailler le dimanche');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (36, 4, 'Plage horaire 6h - 13h');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (37, 4, 'Plage horaire 13h - 20h');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (38, 4, 'Travailler la nuit');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (39, 4, 'Travailler le soir');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (40, 4, 'Porter des charges lourdes');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (41, 4, 'M''occuper d''enfants');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (42, 4, 'Assister des personnes adultes');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (43, 4, 'Assister des personnes en situation de handicap');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (44, 4, 'Me déplacer sur plusieurs lieux de travail');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (45, 4, 'Faire des longs déplacements');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (46, 4, 'Travailler toute la journée sur ordinateur');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (47, 4, 'Travailler en extérieur');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (48, 4, 'Travailleur en hauteur (pas de vertige)');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (49, 4, 'Travailler dans un environnement bruyant');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (50, 4, 'Travailler dans un environnement froid');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (51, 4, 'Travailler en station debout');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (52, 4, 'Avoir un casier judiciaire vierge');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (53, 4, 'Etre résident d''un quartier prioritaire politique de la ville (QPV)');
INSERT INTO public.prerequis (prerequis_id, categorie_prerequis_id, libelle)
VALUES (55, 4, 'Etre incrit comme demandeur ');

SELECT setval('prerequis_seq', 53, true);

INSERT INTO public.type_tag (type_tag_id, libelle, exclusif)
VALUES (0, 'Caractéristique cible', false);
INSERT INTO public.type_tag (type_tag_id, libelle, exclusif)
VALUES (1, 'Bénéfice de participation', false);
INSERT INTO public.type_tag (type_tag_id, libelle, exclusif)
VALUES (2, 'Type d''évènement', false);
INSERT INTO public.type_tag (type_tag_id, libelle, exclusif)
VALUES (3, 'Objectif de l''évènement', false);
INSERT INTO public.type_tag (type_tag_id, libelle, exclusif)
VALUES (4, 'Operation', false);
SELECT setval('type_tag_seq', 4, true);

INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Ouvert aux jeunes', 0, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Débutant(e) accepté(e)', 0, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Pas de diplôme exigé', 0, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Sans expérience professionnelle', 0, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Travailleur en situation de handicap', 0, true);

INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Rencontre recruteur', 1, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Formation assurée', 1, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Formation financée', 1, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Se diplômer', 1, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Opportunité d''emploi', 1, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'CDI', 1, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'candidater sans se déplacer', 1, true);

INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Réunion d''information', 2, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Forum', 2, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Conférence', 2, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Atelier', 2, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Salon en ligne', 2, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Job dating', 2, true);

INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Recrutement', 3, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Découverte secteur / métier', 3, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Découverte formation', 3, true);

INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, '1 jeune  1 solution', 4, false);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'La semaine du maritime', 4, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'La semaine du numérique', 4, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'La semaine de l''emploi', 4, true);
INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, '#TousMobilisés', 4, true);

INSERT INTO public.modalite_acces (modalite_acces_id, libelle)
VALUES (0, 'à distance');
INSERT INTO public.modalite_acces (modalite_acces_id, libelle)
VALUES (1, 'en physique');
SELECT setval('modalite_acces_seq', 1, true);
