import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';

import { NavigationComponent } from './navigation.component';
import { CrudHomeComponent } from './crud-home/crud-home.component';
import { CrudDetailComponent } from './crud-detail/crud-detail.component';
import { CrudListComponent } from './crud-list/crud-list.component';
import { CrudViewComponent } from './crud-view/crud-view.component';
import { CrudFormComponent } from './crud-form/crud-form.component';

import { CrudRoutingModule } from './crud-routing.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    NavigationComponent,
    CrudHomeComponent,
    CrudDetailComponent,
    CrudListComponent,
    CrudViewComponent,
    CrudFormComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    CrudRoutingModule,
  ],
  exports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    CrudHomeComponent,
  ]
})
export class CrudModule { }
