import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CrudHomeComponent } from '../crud/crud-home/crud-home.component';
import { CrudDetailComponent } from '../crud/crud-detail/crud-detail.component';
import { CrudFormComponent } from '../crud/crud-form/crud-form.component';

const routes: Routes = [
    {
        path: '',
        component: CrudHomeComponent,
        children: [
            {
                path: ':id',
                component: CrudDetailComponent,
                children: [
                    {
                        path: 'update/password',
                        component: CrudFormComponent,
                    },
                ]
            }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ],
})
export class UserRoutingModule { }
