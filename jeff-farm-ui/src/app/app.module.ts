import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './components/page-not-found.component';
import { CrudReadComponent } from './components/crud-read/crud-read.component';
import { FarmsComponent } from './components/farms.component';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    CrudReadComponent,
    FarmsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
