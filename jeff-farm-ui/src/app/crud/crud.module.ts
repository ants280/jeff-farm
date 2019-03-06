import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';

import { NavigationComponent } from './navigation.component';
import { CrudHomeComponent } from './crud-home/crud-home.component';
import { CrudListComponent } from './crud-list/crud-list.component';
import { CrudViewComponent } from './crud-view/crud-view.component';
import { CrudFormComponent } from './crud-form/crud-form.component';

import { CrudRoutingModule } from './crud-routing.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    NavigationComponent,
    CrudHomeComponent,
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
    CrudHomeComponent,
    FormsModule,
    CommonModule,
    HttpClientModule,
  ]
})
export class CrudModule { }