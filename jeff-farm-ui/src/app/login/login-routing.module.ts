import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CrudFormComponent } from '../crud/crud-form/crud-form.component';
import { LoginHomeComponent } from './login-home.component';
import { LoginComponent } from './login.component';

const routes: Routes = [
    {
        path: '',
        component: LoginHomeComponent,
        children: [
            {
                path: 'create',
                component: CrudFormComponent,
                // Go to parent when creating a user rather than /login/{id} :
                data: { redirectToParent: true },
            },
            { path: '', component: LoginComponent },
        ],
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
export class LoginRoutingModule { }
