import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import axios from 'axios';
import {TokenStorageService} from '../../services/token-storage.service';

@Injectable()
export class AuthInterceptor {
    constructor(private tokenStorageService: TokenStorageService) {
    }

    getTokenInstance(tokenSession: string): Observable<{}> {
        this.tokenStorageService.setToken('Bearer ' + tokenSession);
        return new Observable<{}>((observe) => {
            axios.interceptors.request.use(
                config => {
                    const token = this.tokenStorageService.getToken();
                    if (token) {
                        config.headers.Authorization = 'Bearer ' + token;
                    }
                    return config;
                },
                error => {
                    Promise.reject(error);
                });
        });
    }

    public error(message: string): any {
        return throwError({error: {message}});
    }

    getESDTokenInstance(tokenAccess: string): Observable<{}> {
        this.tokenStorageService.setESDToken('Bearer ' + tokenAccess);
        return new Observable<{}>((observe) => {
            axios.interceptors.request.use(
                config => {
                    const token = this.tokenStorageService.getESDToken();
                    if (token) {
                        config.headers.Authorization = 'Bearer ' + token;
                    }
                    return config;
                },
                error => {
                    Promise.reject(error);
                });
        });
    }
}

