import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut(): void {
    sessionStorage.clear();
    window.location.reload();
  }

  public saveUser(str: string): void {
    window.sessionStorage.setItem('identifiant', str);
  }

  public getUser(): string | null {
    return window.sessionStorage.getItem('identifiant');
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem('Authorization');
    window.sessionStorage.setItem('Authorization', `Bearer ${token}`);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem('Authorization');
  }

  public setToken(token: string): string | null {
    window.sessionStorage.setItem('Authorization', token);
    return token;
  }

  public setESDToken(token: string): string | null {
    window.sessionStorage.setItem('access_token', token);
    return token;
  }

  public getESDToken(): string | null {
    return window.sessionStorage.getItem('access_token');
  }

  public saveESDToken(token: string): void {
    window.sessionStorage.removeItem('access_token');
    window.sessionStorage.setItem('access_token', `Bearer ${token}`);
  }
}
