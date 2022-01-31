import IRole from "./IRole";

export default interface IAgency {
  id: number;
  libelle: string;
  codeAgence: string;
  parent: IAgency;
  email: string;
  role: IRole;
}
