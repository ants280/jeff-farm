import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CrudItemService } from '../crud/crud-item.service';
import { CrudModule } from '../crud/crud.module';
import { HiveService } from './hive.service';

@NgModule({
  providers: [
    {
      provide: CrudItemService,
      useClass: HiveService
    },
  ],
  imports: [
    CommonModule,
    CrudModule,
    RouterModule.forChild([{ path: '', loadChildren: () => import('../crud/crud.module').then(m => m.CrudModule) }]),
  ],
})
export class HiveModule { }
