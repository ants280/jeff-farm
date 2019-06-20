import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { FormGroup, FormArray, AbstractControl, FormBuilder } from '@angular/forms';

import { FormItemType } from '../form-item';
import { CrudItem } from '../crud-item';
import { CrudItemGroupService } from '../crud-item-inspection-group.service';
import { CrudItemInspectionGroup } from '../crud-item-inspection-group';
import { CrudItemInspection } from '../crud-item-inspection';
import { CrudItemInputComponent } from '../crud-item-input/crud-item-input.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-crud-item-inspection-group-input',
  templateUrl: './crud-item-inspection-group-input.component.html',
  styleUrls: ['./crud-item-inspection-group-input.component.css']
})
export class CrudItemInspectionGroupInputComponent<
  U extends CrudItem, V extends CrudItemInspection<U>,
  T extends CrudItemInspectionGroup<V>>
  implements OnInit {

  @Input()
  inspectionItems: FormArray;
  formItemType = FormItemType; // used for the ngSwitch in the template
  selectTargets: object;
  objectKeys = Object.keys; // used in the template
  @ViewChildren(CrudItemInputComponent) groupEditors: QueryList<CrudItemInputComponent<T>>;

  constructor(
    route: ActivatedRoute,
    private fb: FormBuilder,
    private crudItemGroupService: CrudItemGroupService<U, V, T>) {
    this.crudItemGroupService.setRoute(route); // TODO: why is this needed?
  }

  ngOnInit() {
    this.crudItemGroupService.getTargets()
      .subscribe((targets: Map<number, string>) => {
        this.selectTargets = {};
        this.selectTargets[0] = ' ';
        for (const [targetId, targetName] of Object.entries(targets)) {
          this.selectTargets[+targetId] = targetName;
        }
        this.inspectionItems.controls
          .forEach((inspectionItem: AbstractControl) => {
            const targetId: number = inspectionItem.get('targetId').value;
            delete this.selectTargets[targetId];
          });
      });
  }

  addInspectionItem(targetIndex: number) {
    if (targetIndex > 0 // not blank item
      && this.inspectionItems != null
      && this.crudItemGroupService instanceof CrudItemGroupService) {
      const inspectionItem: FormGroup = this.crudItemGroupService.createCrudItemInspection().getFormGroup(this.fb);
      const addTargetId: number = +Object.keys(this.selectTargets)[targetIndex];
      inspectionItem.patchValue({
        targetId: addTargetId,
        targetName: this.selectTargets[addTargetId]
      });
      delete this.selectTargets[addTargetId];
      this.inspectionItems.push(inspectionItem);
    }
  }

  removeInspectionItem(removeTargetIdStr: string) {
    if (this.inspectionItems != null) {
      const targeremoveTargetId = +removeTargetIdStr;
      for (let i = 0; i < this.inspectionItems.length; i++) {
        const inspectionItem: FormGroup = this.inspectionItems.at(i) as FormGroup;
        if (inspectionItem.get('targetId').value === targeremoveTargetId) {
          this.selectTargets[targeremoveTargetId] = inspectionItem.get('targetName').value;
          this.inspectionItems.removeAt(i);
          break;
        }
      }
    }
  }
}
