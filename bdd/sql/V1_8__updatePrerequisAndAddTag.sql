INSERT INTO public.tag (tag_id, libelle, type_tag, est_a_publier)
VALUES (DEFAULT, 'Ouvert aux séniors', 4, true);

UPDATE public.prerequis
set libelle = 'Etre inscrit comme demandeur'
where prerequis_id = 55;
