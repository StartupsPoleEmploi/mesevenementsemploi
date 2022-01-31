import {environment} from '../../../environments/environment';
import axios, {AxiosInstance} from 'axios';
import {ErrorHandler, Injectable} from '@angular/core';
import {TokenStorageService} from './token-storage.service';
import {MatSnackBar} from '@angular/material/snack-bar';

export interface ErrorResponse {
  id: string;
  code: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})

export class ApiService {
  private axiosClient: AxiosInstance;
  private errorHandler: ErrorHandler;
  public token: string | null;
  public spinner = false;

  constructor(errorHandler: ErrorHandler, private tokenStorage: TokenStorageService, private snackBar: MatSnackBar) {
    this.errorHandler = errorHandler;
    this.token = this.tokenStorage.getToken();
    this.axiosClient = axios.create({
      headers: {
        'X-Initialized-At': Date.now().toString()
      }
    });
  }

  public async getApi<T>(path: string): Promise<T> {
    try {
      const axiosResponse = await this.axiosClient.request<T>({
        method: 'GET',
        url: environment.peactonsDomainePath + path,
        headers: {Authorization: '' + this.token}
      });
      return (axiosResponse.data);
    } catch (error) {
      return (Promise.reject(this.normalizeError(error)));
    }
  }

  public async getByIdFromApi<T>(path: string, id: number | string): Promise<any> {
    try {
      const axiosResponse = await this.axiosClient.request<T>({
        method: 'GET',
        url: environment.peactonsDomainePath + path + '/' + id
      });
      return (axiosResponse.data);
    } catch (error) {
      return (Promise.reject(this.normalizeError(error)));
    }
  }

  public async getByCodeFromApi<T>(path: string, code: string): Promise<T> {
    try {
      const axiosResponse = await this.axiosClient.request<T>({
        method: 'GET',
        url: environment.peactonsDomainePath + path + 'etablissement/' + code
      });
      return (axiosResponse.data);
    } catch (error) {
      return (Promise.reject(this.normalizeError(error)));
    }
  }

  public async postApi<T>(path: string, data: {}): Promise<any> {
    try {
      const axiosResponse = await this.axiosClient.request<T>({
        method: 'POST',
        url: environment.peactonsDomainePath + path,
        headers: {Authorization: this.token},
        data
      });
      return (axiosResponse.data);
    } catch (error) {
      if (error.response.status === 400) {
        this.spinner = false;
        return (Promise.reject(this.normalizeError(error)));
      }
    }
  }

  public async postCandidat<T>(path: string, data: {}): Promise<any> {
    try {
      const axiosResponse = await this.axiosClient.request<T>({
        method: 'POST',
        url: environment.peactonsDomainePath + path,
        data
      });
      return (axiosResponse.data);
    } catch (error) {
      if (error.response.status === 403) {
        return (Promise.reject(this.normalizeErrorFullEvent(error)));
      }
      if (error.response.status === 409) {
        return (Promise.reject(this.normalizeErrorCandidatDejaInscrit(error)));
      } else if (error.response.status === 500 || error.response.status === 400) {
        return (Promise.reject(this.normalizeError(error)));
      }
    }
  }

  public async putApi<T>(path: string, data: {}): Promise<any> {
    try {
      const axiosResponse = await this.axiosClient.request<T>({
        method: 'PUT',
        url: environment.peactonsDomainePath + path,
        headers: {Authorization: this.token},
        data
      });
      return (axiosResponse.data);
    } catch (error) {
      if (error.response.status === 400) {
        this.snackBar.open(
          'Une erreur est survenue lors de la création de votre évènement',
          '',
          {duration: 3000},
        );
        this.spinner = false;
        return (Promise.reject(this.normalizeError(error)));
      }
    }
  }

  private normalizeError(error: any): ErrorResponse {
    this.errorHandler.handleError(error);
    return ({
      id: '-1',
      code: 'UnknownError',
      message: 'An unexpected error occurred.'
    });
  }

  private normalizeErrorCandidatDejaInscrit(error: any): ErrorResponse {
    this.errorHandler.handleError(error);
    return ({
      id: '-1',
      code: '409',
      message: 'An unexpected error occurred.'
    });
  }

  private normalizeErrorFullEvent(error: any): ErrorResponse {
    this.errorHandler.handleError(error);
    return ({
      id: '-1',
      code: '403',
      message: 'An unexpected error occurred.'
    });
  }
}
