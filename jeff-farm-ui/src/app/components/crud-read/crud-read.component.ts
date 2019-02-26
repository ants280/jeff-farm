import { Component, OnInit, Input } from '@angular/core';
import { CrudItem } from 'src/app/classes/crud.item';
import { CrudService } from 'src/app/services/crud.service';

@Component({
  selector: 'app-crud-read',
  templateUrl: './crud-read.component.html',
  styleUrls: ['./crud-read.component.css']
})
export class CrudReadComponent<T extends CrudItem> implements OnInit {

  @Input() crudService: CrudService<T>;
  @Input() itemNames: string[];
  items: T[];

  constructor() { }

  ngOnInit() {
    this.getItems();
  }

  getItems(): void {
    this.crudService.getList()
      .subscribe(items => 
        {
            this.items = items
            for (let item of items) {
              for (let itemName of this.itemNames) {
                let value = item[itemName];
                console.log(value);
              }
            }
        });
  }

  updateItem(item: T) {
    console.log("TODO: Update farm " + JSON.stringify(item));
  }

  deleteItem(item: T) {
    console.log("TODO: Delete item " + JSON.stringify(item));
  }
}
