import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorMessagesService {

  constructor (
    private authService: AuthService,
    private router: Router) { }

  messages: string[] = [];

  add(message: string) {
    this.messages.push(message);
  }

  clear() {
    this.messages = [];
  }

  handleError<T>(attemptedTask: string) {
    return (error: Error): Observable<T> => {
      let errorMessage: string = error.message;

      if (error instanceof HttpErrorResponse) {
        let  goToLogin: boolean = false;
        if(error.status == 0) {
          errorMessage = 'Server down'
          goToLogin = true;
        }
        else if(Math.floor(error.status / 100) == 4) {
          goToLogin = true;
        }
        else {
          errorMessage = error.error;
        }

        if (goToLogin) {
          this.authService.clearCredentials();
          this.router.navigate(['/login']);
        }
      }

      this.add(`${attemptedTask}: ${errorMessage}`);
      return of();
      };
  }
}
