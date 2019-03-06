import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

import { CrudService } from '../crud/crud.service';
import { Hive } from './hive';

@Injectable({
  providedIn: 'root'
})
export class HivesService extends CrudService<Hive> {

  constructor(
    private httpClient: HttpClient,
    private activatedRoute: ActivatedRoute) {
      
    super(httpClient, activatedRoute);
  }

  createCrudItem(): Hive {
    return new Hive(this.getFarmId());
  }

  getChildNames(): string[] {
    return ['HiveInspections'];
  }

  getBaseUrl(): string {

    return `farms/${this.getFarmId()}/hives`;
  }

  getFarmId(): number {

    return this.getRouteParam('farm_id');
  }
}
