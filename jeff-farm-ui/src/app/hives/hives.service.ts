import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { CrudItemService, CrudChild } from '../crud/crud.item.service';
import { Hive } from './hive';
import { ErrorMessagesService } from '../error-messages/error-messages.service';

@Injectable()
export class HivesService extends CrudItemService<Hive> {

  constructor(
    errorsService: ErrorMessagesService,
    httpClient: HttpClient) {

    super(errorsService, httpClient);
  }

  createCrudItem(): Hive {
    return new Hive(this.getFarmId());
  }

  getPluralName(): string {
    return 'Hives';
  }

  getCrudChildren(): CrudChild[] {
    return [
      { pluralName: 'Hive Inspections', path: 'hiveInspections' },
    ];
  }

  getBaseUrl(): string {

    return `farms/${this.getFarmId()}/hives`;
  }

  getFarmId(): number {

    return +this.getRouteParam('farm_id');
  }
}
