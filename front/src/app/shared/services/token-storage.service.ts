import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() {
  }

  signOut(): void {
    sessionStorage.clear();
    window.location.reload();
  }

  public saveUser(str: string): void {
    localStorage.setItem('identifiant', str);
  }

  public getUser(): string | null {
    return localStorage.getItem('identifiant');
  }

  public saveToken(token: string): void {
    localStorage.removeItem('Authorization');
    localStorage.setItem('Authorization', `Bearer ${token}`);
  }

  public getToken(): string | null {
    return localStorage.getItem('Authorization');
  }

  public setToken(token: string): string | null {
    localStorage.setItem('Authorization', token);
    return token;
  }

}
