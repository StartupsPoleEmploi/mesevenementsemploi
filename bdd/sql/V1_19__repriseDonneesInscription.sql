update candidat_evenement
set statut_inscription = 0
where inscrit = true
  and a_valider_prerequis = true;

update candidat_evenement
set statut_inscription = 2
where inscrit = false
  and a_valider_prerequis = true;

update candidat_evenement
set statut_inscription = 1
where inscrit = false
  and a_valider_prerequis = false;
