import {ErrorHandler, Injectable} from '@angular/core';
import axios from 'axios';
import {ApiService} from './api.service';
import {Router} from '@angular/router';
import {AuthInterceptor} from '../Interceptor/auth.interceptor';
import {environment} from '../../../environments/environment';
import {TokenStorageService} from './token-storage.service';
import {ToastService} from './toast.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService extends ApiService {

  constructor(
    errorHandler: ErrorHandler,
    private router: Router,
    private authInterceptor: AuthInterceptor,
    private tokenStorageService: TokenStorageService,
    private toastService: ToastService,
    snackBar: MatSnackBar) {
    super(errorHandler, tokenStorageService, snackBar);
  }

  public logIn(identifiant: string, password: string) {
    const baseUrl = environment.peactonsDomainePath + '/auth/signin';
    axios.post(baseUrl, {identifiant, motDePasse: password}).then(res => {
      const token = res.data.accessToken;
      this.authInterceptor.getTokenInstance(token);
      this.router.navigate(['\evenements']);
      if (identifiant && identifiant !== undefined) {
        this.tokenStorageService.saveUser(identifiant);
      }
    }).catch(err => {
      this.toastService.showToast('Identifiant ou mot de passe incorrect', 'toast-error');
    });
  }

  public signIn(identifiant: string, password: string): Observable<any> {
    const baseUrl = '/auth/signin';
    try {
      const promise = this.postApi(baseUrl, {identifiant, motDePasse: password});
      const result = from(promise);
      this.router.navigate(['\evenements']);
      return result;
    } catch (error) {
      throw new Error('An error occured when api called: api not available');
      return error;
    }
  }


  public isAuthenticated(): boolean {
    const token = window.sessionStorage.getItem('Authorization');
    return !!token;

  }

}
