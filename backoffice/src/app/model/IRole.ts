enum ERole {
  ROLE_AGENCE = 'ROLE_AGENCE',
  ROLE_SUPERVISEUR = 'ROLE_SUPERVISEUR',
  ROLE_ADMIN = 'ROLE_ADMIN'
}

export default interface IRole {
  id: number;
  name: ERole;
}
