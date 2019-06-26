import * as Highcharts from 'highcharts';

import { getUrlScheme } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';

import { CrudItem } from '../crud-item';
import { CrudItemInspection } from '../crud-item-inspection';
import { CrudItemInspectionGroup } from '../crud-item-inspection-group';
import { CrudItemInspectionGroupService } from '../crud-item-inspection-group.service';
import { FormItem, FormItemType } from '../form-item';

@Component({
  selector: 'app-crud-chart',
  templateUrl: './crud-chart.component.html',
})
export class CrudChartComponent
  <U extends CrudItem,
  V extends CrudItemInspection<U>,
  T extends CrudItemInspectionGroup<V>>
  implements OnInit {

  public options = {
    chart: {
      type: 'column',
    },
    title: {
      text: '',
    },
    credits: {
      enabled: false
    },
    tooltip: {
      formatter() {
        return this.y;
      }
    },
    xAxis: {
      categories: [],
      crosshair: true
    },
    yAxis: {
      title: {
        text: '',
      }
    },
    plotOptions: {
      series: {
        cursor: 'pointer',
        point: {
          events: {
            click() {
              location.href = this.series.options.groupUrls[this.index];
            }
          }
        }
      }
    },
    series: []
  };
  formItems: FormItem[];
  private groups: T[];
  private groupUrls: string[];

  constructor(
    private router: Router,
    route: ActivatedRoute,
    private titleService: Title,
    private crudItemInspectionGroupService: CrudItemInspectionGroupService<U, V, T>) {
    this.crudItemInspectionGroupService.setRoute(route);
  }

  ngOnInit() {
    this.titleService.setTitle(`${this.crudItemInspectionGroupService.getTypeName()} chart`);
    this.options.title.text
      = `${this.crudItemInspectionGroupService.getTypeName()} chart`;
    this.formItems = this.crudItemInspectionGroupService.createCrudItemInspection()
      .getFormItems()
      .filter(f => [FormItemType.Integer, FormItemType.Stars].indexOf(f.type) >= 0);
    this.crudItemInspectionGroupService.getList()
      .subscribe((groups: T[]) => {
        this.groups = groups;
        this.groups.reverse();
        this.groupUrls = this.groups.map(group => this.getUrl(group));
        this.options.xAxis.categories = this.groups.map(group => group.createdDate);
        this.chartFormItem(0);
      });
    this.crudItemInspectionGroupService.getTargets()
      .subscribe((targets: Map<number, string>) => {
        this.options.series = Object.keys(targets).map(targetId => {
          return {
            name: targets[targetId],
            data: [],
          };
        });
        this.chartFormItem(0);
      });
  }

  private getUrl(group: T): string {
    const url = this.router.url;
    const lastSlash = url.lastIndexOf('/') + 1;
    return url.substr(0, lastSlash) + group.id;
  }

  chartFormItem(index: number) {
    if (this.groups == null || !this.options.series.length) {
      return;
    }

    // reset the series
    this.options.series.forEach(s => {
      s.data = Array(this.groups.length).fill('');
      // It would be nice to do this ONCE when the groups/targets are retrieved:
      s.groupUrls = this.groupUrls;
    });

    // add data to the empty series
    const formItemName: string = this.formItems[index].name;
    this.options.yAxis.title.text = formItemName;
    for (let x = 0; x < this.groups.length; x++) {
      const group = this.groups[x];
      for (const inspectionItem of group.inspectionItems) {
        const seriesIndex = this.options.series
          .findIndex(s => s.name === inspectionItem.targetName);
        if (seriesIndex >= 0) {
          this.options.series[seriesIndex].data[x] = inspectionItem[formItemName];
        }
      }
    }

    Highcharts.chart('chart', this.options); // Plot the data to the "chart" div.
  }
}
