INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Visites d''entreprise', 2, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Portes ouvertes', 2, true);

INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Aides à l''emploi', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Création d''entreprise', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Insertion', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('International', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Marché du travail', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Recrutement sans CV', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Se préparer', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('S''informer', 3, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Sport et insertion', 3, true);

INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Jeux Olympiques Paris 2024', 4, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('La semaine de l''agriculture', 4, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('La semaine création d''entreprise', 4, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('La semaine du handicap', 4, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('La place de l''emploi', 4, true);
	
INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Travail avenir formation', 4, true);

INSERT INTO public.tag(
	libelle, type_tag, est_a_publier)
	VALUES ('Cadres', 0, true);
	
UPDATE public.evenement_tag set tag_id = 2 where tag_id = 4;

DELETE FROM public.tag where tag_id = 4;
