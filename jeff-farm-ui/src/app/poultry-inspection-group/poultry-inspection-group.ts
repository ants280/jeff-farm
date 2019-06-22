import { CrudItem } from '../crud/crud-item';
import { CrudItemInspectionGroup } from '../crud/crud-item-inspection-group';
import { FormItem, FormItemType } from '../crud/form-item';
import { PoultryInspection } from './poultry-inspection';

export class PoultryInspectionGroup extends CrudItemInspectionGroup<PoultryInspection> {

    public notes: string;

    constructor(farmId: number) {
        super(farmId);
    }

    getFormItems(): FormItem[] {
        return [
            new FormItem('notes', FormItemType.TextArea, this.notes),
        ];
    }

    getDisplayValue(): string {
        return this.createdDate;
    }
}
