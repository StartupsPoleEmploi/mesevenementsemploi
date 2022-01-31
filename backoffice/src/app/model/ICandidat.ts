import IAgency from './IAgency';

export interface ICandidat {
  id?: number;
  identifiantCrypter?: string;
  identifiantRci?: string;
  nom?: string;
  prenom?: string;
  email?: string;
  age?: number;
  civilite?: string;
  inscrit?: boolean;
  present?: boolean;
  agence?: IAgency;
  dateEvenement?: string;
  modaliteAccesId?: number;
  avaliderPrerequis?: boolean;
}
