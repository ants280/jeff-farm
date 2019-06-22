import { Injectable } from '@angular/core';
import { LoginSuccess } from '../login/login-success';
import { CachingService } from '../caching.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly USER_ID_KEY: string = 'userId';
  private readonly ADMIN_USER_KEY: string = 'isAdminUser';

  getUserId(): string {
    return localStorage.getItem(this.USER_ID_KEY);
  }

  isAdminUser(): boolean {
    return localStorage.getItem(this.ADMIN_USER_KEY) === String(true);
  }

  constructor(
    private cachingService: CachingService) { }

  clearCredentials() {
    localStorage.removeItem(this.USER_ID_KEY);
    localStorage.removeItem(this.ADMIN_USER_KEY);
    this.cachingService.clear();
  }

  setLoggedIn(loginSuccess: LoginSuccess) {
    localStorage.setItem(this.USER_ID_KEY, String(loginSuccess.userId));
    localStorage.setItem(this.ADMIN_USER_KEY, String(loginSuccess.adminUser));
  }

  isLoggedIn(): boolean {
    return +this.getUserId() > 0;
  }
}
