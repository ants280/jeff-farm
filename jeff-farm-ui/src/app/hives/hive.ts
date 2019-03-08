import { CrudItem } from '../crud/crud.item';
import { FormItem, FormItemType } from '../crud/form.item';

export class Hive extends CrudItem {

    public farmId: number;
    public name: string;
    public queenColor: string;

    constructor(farmId: number) {
        super();
        this.farmId = farmId;
    }

    getFormItems(): FormItem[] {
        return [
            new FormItem('name', FormItemType.String, this.name),
            new FormItem('queenColor', FormItemType.Color, this.queenColor),
        ];
    }

    getDisplayValue(): string {
        return this.name;
    }
}
