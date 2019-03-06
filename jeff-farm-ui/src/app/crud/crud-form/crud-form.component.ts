import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { CrudService } from '../crud.service';
import { NavigationService } from '../../navigation.service';

import { CrudItem } from '../crud.item';
import { FormType } from '../form.type';
import { FormItem, FormItemType } from '../form.item';

@Component({
  selector: 'crud-form',
  templateUrl: './crud-form.component.html',
  styleUrls: ['./crud-form.component.css']
})
export class CrudFormComponent<T extends CrudItem> implements OnInit {

  crudItem: T
  formType: FormType;
  formItems: FormItem[];
  submitValue: string;
  formItemType = FormItemType; // used for the ngSwitch in the template

  constructor(
    private crudService: CrudService<T>,
    private navigationService: NavigationService) { }

  ngOnInit() {

    const urlParts: string[] = this.navigationService.getUrl().split('/');
    const endPath: string = urlParts[urlParts.length - 1];
    switch (endPath) {
      case 'create' :
        this.formType = FormType.Create;
        break;
      case 'update' :
        this.formType = FormType.Update;
        break;
      default:
        throw new Error(`Unknown endPath: ${endPath}.  Could not set FormType`);
    }

    this.submitValue = (this.formType === FormType.Update) ? 'Update' : 'Submit';

    if (this.formType == FormType.Create) {
      this.formItems = this.crudItem.getFormItems();
      this.crudItem = this.crudService.createCrudItem();
    }
    if (this.formType == FormType.Update) {
      this.crudService.get()
        .subscribe((data: CrudItem) => {
          this.crudItem = Object.assign(this.crudItem, data)
          this.formItems = this.crudItem.getFormItems();
        });
    }

    this.formItems = this.crudItem.getFormItems();
  }

  submitForm() {

    for (let formItem of this.formItems) {
      this.crudItem[formItem.name] = formItem.value;
    }

    let result: Observable<Object> = null;
    if (this.formType == FormType.Create) {
      result = this.crudService.create(this.crudItem);
    }
    if (this.formType == FormType.Update) {
      result = this.crudService.update(this.crudItem);
    }
    result.subscribe(result => this.navigationService.goBack() );
  }
}
