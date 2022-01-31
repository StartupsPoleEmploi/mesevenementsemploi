import IAgency from './IAgency';
import {PeConnectAuthorization} from './pe-connect-authorization';

export interface ICandidat {
  id?: number;
  identifiantCrypter?: string;
  nom?: string;
  prenom?: string;
  email?: string;
  dateNaissance?: Date;
  age?: number;
  civilite?: string;
  inscrit?: boolean;
  present?: boolean;
  annulation?: boolean;
  agence?: IAgency;
  dateEvenement?: string;
  modaliteAccesId?: number;
  prerequisValider?: boolean;
  peConnectAuthorization: PeConnectAuthorization | undefined;
  statutInscription?: number;
}
