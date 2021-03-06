import {ICandidat} from './ICandidat';
import {IPrerequisites} from './IPrerequisites';
import {IModeAcees} from './IModeAccess';
import ITag from './ITag';
import {IOffer} from './IOffer';
import IRecruiter from "./IRecruiters";

export interface IEvent {
  id: number;
  codeEtablissement: string;
  description: string;
  deroulement: string;
  estApublier: boolean;
  dateISO: Date;
  dateEvenement: string;
  heureDebut: string;
  heureFin: string;
  statut: string;
  etatId: number;
  etatLibelle: string;
  adresse: string;
  codePostal: string;
  ville: string;
  lieu: string;
  nafCode: string;
  nafLibelle: string;
  objectif: ITag[];
  objectifLibelle: string;
  timeZone: string;
  titre: string;
  typeEvenementId: number;
  romeId: number;
  romeLibelle: string;
  candidats: ICandidat[];
  prerequis: IPrerequisites[];
  modeAcceesList: IModeAcees[];
  tags: ITag[];
  offres: IOffer[];
  intervenants: IRecruiter[];
  urlParticipation: string;
  libelleLienPlusInformation: string;
  lienPlusInformation: string;
  remoteModality: boolean;
  availableRemotePlaces: number;
  onSiteModality: boolean;
  availableOnSitePlaces: number;
}
