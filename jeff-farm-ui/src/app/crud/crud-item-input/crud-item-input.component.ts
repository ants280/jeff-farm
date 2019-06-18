import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { FormGroup, FormArray, AbstractControl, FormBuilder } from '@angular/forms';

import { FormItem, FormItemType } from '../form-item';
import { CrudItem } from '../crud-item';
import { CrudItemService } from '../crud-item.service';
import { CrudItemGroupService } from '../crud-item-inspection-group.service';

@Component({
  selector: 'app-crud-item-input',
  templateUrl: './crud-item-input.component.html',
  styleUrls: ['./crud-item-input.component.css']
})
export class CrudItemInputComponent<T extends CrudItem> implements OnInit {

  @Input()
  crudItem: T;
  @Input()
  crudForm: FormGroup;
  formItems: FormItem[];
  formItemType = FormItemType; // used for the ngSwitch in the template
  selectTargets;
  objectKeys = Object.keys; // used in the template
  @ViewChildren(CrudItemInputComponent) groupEditors: QueryList<CrudItemInputComponent<T>>;

  constructor(
    private fb: FormBuilder,
    private crudItemService: CrudItemService<T>) { }

  ngOnInit() {
    this.formItems = this.crudItem.getFormItems();

    if (this.getInspectionItems() != null && this.crudItemService instanceof CrudItemGroupService) {
      this.crudItemService.getTargets()
        .subscribe((targets: Map<number, string>) => {
          this.selectTargets = {};
          this.selectTargets[0] = ' ';
          for (const [targetId, targetName] of Object.entries(targets)) {
            this.selectTargets[+targetId] = targetName;
          }
          this.getInspectionItems().controls
            .forEach((inspectionItem: AbstractControl) => {
              const targetId: number = inspectionItem.get('targetId').value;
              delete this.selectTargets[targetId];
            });
        });
    }
  }

  getInspectionItems(): FormArray {
    return this.crudForm.get('inspectionItems') as FormArray;
  }

  addInspectionItem(targetIndex: number) {
    if (targetIndex > 0 // not blank item
      && this.getInspectionItems() != null
      && this.crudItemService instanceof CrudItemGroupService) {
      const inspectionItem: FormGroup = this.crudItemService.createCrudItemInspection().getFormGroup(this.fb);
      const addTargetId: number = +Object.keys(this.selectTargets)[targetIndex];
      inspectionItem.patchValue({
        targetId: addTargetId,
        targetName: this.selectTargets[addTargetId]
      });
      delete this.selectTargets[addTargetId];
      this.getInspectionItems().push(inspectionItem);
    }
  }

  removeInspectionItem(removeTargetIdStr: string) {
    if (this.getInspectionItems() != null) {
      const targeremoveTargetId = +removeTargetIdStr;
      for (let i = 0; i < this.getInspectionItems().length; i++) {
        const inspectionItem: FormGroup = this.getInspectionItems().at(i) as FormGroup;
        if (inspectionItem.get('targetId').value === targeremoveTargetId) {
          this.selectTargets[targeremoveTargetId] = inspectionItem.get('targetName').value;
          this.getInspectionItems().removeAt(i);
          break;
        }
      }
    }
  }
}
