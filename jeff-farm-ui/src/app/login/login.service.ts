import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { AuthService } from '../auth/auth.service';
import { ErrorMessagesService } from '../error-messages/error-messages.service';
import { User } from '../user/user';
import { LoginSuccess } from './login-success';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private authService: AuthService,
    private errorMessagesService: ErrorMessagesService,
    private httpClient: HttpClient) { }

  login(user: User): Observable<LoginSuccess> {
    return this.httpClient.post<LoginSuccess>('login', user)
      .pipe(
        catchError(this.errorMessagesService.handleError<LoginSuccess>('login')),
      );
  }

  logout(): Observable<any> {
    this.authService.clearCredentials(); // clear even if logout fails.
    return this.httpClient.get<any>('user/logout')
      .pipe(
        catchError(this.errorMessagesService.handleError<any>('logout')),
      );
  }

  serverRunning(): Observable<boolean> {
    return this.httpClient.get<boolean>('login/status')
      .pipe(
        catchError(this.errorMessagesService.handleError<any>('login-status')),
        map(_ => true),
      );
  }
}
