import {Component} from '@angular/core';
import {AuthenticationService} from '../../shared/services/authentication.service';
import {Router} from '@angular/router';
import {ToastService} from '../../shared/services/toast.service';
import {environment} from '../../../environments/environment';
import {TokenStorageService} from '../../shared/services/token-storage.service';
import {AuthInterceptor} from '../../shared/Interceptor/auth.interceptor';

@Component({
  selector: 'app-authentification',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss']
})
export class AuthenticationComponent {

  public isLoggedIn = false;
  public isLoginFailed = false;
  submitted = false;
  errorMessage = 'Oops, Identifiant ou mot de passe incorrect !';
  error: string | undefined;
  form: any = {
    identifier: null,
    password: null
  };
  public contactEmail: string = environment.emailContact;

  constructor(private authService: AuthenticationService, private tokenStorageService: TokenStorageService,
              private router: Router, private toastService: ToastService, private authInterceptor: AuthInterceptor) {
    this.authService = authService;
    if (this.authService.isAuthenticated()) {
      router.navigate(['evenements']);
      this.toastService.showToast('Vous êtes déjà connecté(e) à l`application', 'toast-info');
    }
  }

  public async signIn(): Promise<any> {
    this.submitted = true;
    this.authService.signIn(this.form.identifier, this.form.password).subscribe(
      res => {
        const token = res.accessToken;
        this.authInterceptor.getTokenInstance(token);
        this.router.navigate(['\evenements']);
        if (this.form.identifier && this.form.identifier !== undefined) {
          this.tokenStorageService.saveUser(this.form.identifier);
        }
        this.router.navigate(['\evenements']);
      },
      error => {
        this.error = error;
      });
  }
}
