import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

import { AuthService } from '../auth/auth.service';
import { CrudItemService } from '../crud/crud-item.service';
import { User } from './user';
import { ErrorMessagesService } from '../error-messages/error-messages.service';
import { LoginService } from '../login/login.service';
import { CrudChild } from '../crud/crud-child';
import { UserPasswordReplacement } from './user-password-replacement';
import { FormItem } from '../crud/form-item';

@Injectable()
export class UserService extends CrudItemService<User> {

  constructor(
    private authService: AuthService,
    private loginService: LoginService,
    private router: Router,
    errorMessagesService: ErrorMessagesService,
    http: HttpClient) {
    super(errorMessagesService, http);
  }

  createCrudItem(): User {
    return new User();
  }

  getCrudChildren(): CrudChild[] {
    return [{ name: 'Update Password', path: 'update/password' }];
  }

  getTypeName(): string {
    return 'user';
  }

  protected getBaseUrl(): string {
    return this.authService.isLoggedIn() ? 'user' : 'login/create';
  }

  put(user: User, updatedValue: any): Observable<any> {
    return this.isRoutePasswordReset()
      ? this.putPassword(Object.assign(new UserPasswordReplacement(user.id), updatedValue))
      : super.put(user, updatedValue);
  }

  private putPassword(userPasswordReplacement: UserPasswordReplacement): Observable<boolean> {
    const url = `${this.getBaseUrl()}/${this.getId()}/password`;
    return this.http.put(url, userPasswordReplacement)
      .pipe(
        catchError(this.errorMessagesService.handleError<any>('update-password')),
      );
  }

  delete(): Observable<object> {

    return super.delete().pipe(tap(_ => this.loginService.logout()));
  }

  getCanDeleteMessage(): string {
    return 'Cannot delete user because it is the only user with permission to one or more farms.';
  }

  protected getId(): string {
    // if we are not in the UserModule, getId() returns null.
    return super.getId() || this.authService.getUserId();
  }

  getViewStepsToParent(): number {
    let viewStepsToParent: number = super.getViewStepsToParent();
    if (!this.authService.isAdminUser() || this.isRoutePasswordReset()) {
      viewStepsToParent++;
    }
    return viewStepsToParent;
  }

  protected getParentId(): string {
    return '';
  }

  getFormItems(crudItem: User): FormItem[] {
    if (this.isRoutePasswordReset()) {
      return new UserPasswordReplacement(crudItem.id).getFormItems();
    } else {
      const formItems: FormItem[] = super.getFormItems(crudItem);
      if (!this.isRouteLoginCreate()) {
        return formItems
          .filter((formItem: FormItem) => formItem.name !== 'userName' && formItem.name !== 'password');
      }
      return formItems;
    }
  }

  getCrudForm(crudItem: User, fb: FormBuilder) {
    if (this.isRoutePasswordReset()) {
      return new UserPasswordReplacement(+this.getId()).getFormGroup(fb);
    } else {
      const crudForm: FormGroup = super.getCrudForm(crudItem, fb);
      if (!this.isRouteLoginCreate()) {
        crudForm.removeControl('userName');
        crudForm.removeControl('password');
      }
      return crudForm;
    }
  }

  private isRoutePasswordReset(): boolean {
    const url: string = this.router.url;
    return url.endsWith('/update/password');
  }

  private isRouteLoginCreate(): boolean {
    const url: string = this.router.url;
    return url.endsWith('/login/create');
  }
}
