import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { CrudItem } from './crud.item';
import { NavigationService } from '../navigation.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

export abstract class CrudService<T extends CrudItem> {

  constructor(
    private navigationService: NavigationService,
    private http: HttpClient) { }

  abstract createCrudItem(): T;

  abstract getChildNames(): string[];

  abstract getBaseUrl(): string;

  genBaseUrl(): string {
    return `http://localhost:8080/jeff-farm-ws/${this.getBaseUrl()}`;
  }

  post(t: T): Observable<any> {
    return this.http.post<Object>(this.genBaseUrl(), t, httpOptions)
      .pipe(
        catchError(this.handleError),
        );
  }

  get(): Observable<T> {
    const url = `${this.genBaseUrl()}/${this.getId()}`;
    return this.http.get<T>(url, httpOptions)
      .pipe(
        map((data: T) => Object.assign(this.createCrudItem(), data)),
        catchError(this.handleError),
        );
  }

  getList(): Observable<T[]> {
    return this.http.get<T[]>(this.genBaseUrl(), httpOptions)
      .pipe(
        map((dataList: T[]) => dataList
          .map(data => Object.assign(this.createCrudItem(), data))),
        catchError(this.handleError),
        );
  }

  put(t: T): Observable<Object> {
    return this.http.put(this.genBaseUrl(), t, httpOptions)
      .pipe(
        catchError(this.handleError),
        );
  }

  delete(): Observable<Object> {
    const url = `${this.genBaseUrl()}/${this.getId()}`;
    return this.http.delete(url, httpOptions)
      .pipe(
        catchError(this.handleError),
        );
  }

  canDelete(): Observable<boolean> {
    const url = `${this.genBaseUrl()}/${this.getId()}/canDelete`;
    return this.http.get<boolean>(url, httpOptions)
      .pipe(
        catchError(this.handleError),
      );
  }

  private handleError(error: HttpErrorResponse) {
    console.error(error);
    alert(error.message);
    return throwError('error');
  }

  getId(): number {
    return this.navigationService.getRouteParam('id');
  }
}
