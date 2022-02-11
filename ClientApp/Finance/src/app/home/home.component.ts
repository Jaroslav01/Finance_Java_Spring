import {Component, OnInit, ViewChild} from '@angular/core';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexTitleSubtitle,
  ApexDataLabels,
  ApexFill,
  ApexMarkers,
  ApexYAxis,
  ApexXAxis,
  ApexTooltip
} from "ng-apexcharts";
import {dataSeries} from "./data-series";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpEvent} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {FinanceRecord} from "../web-api";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private financeRecords: FinanceRecord[] = [];

  ngOnInit(): void {
    this.monthlyPlanForm = new FormGroup({
      amount: new FormControl(100, [
        Validators.required,
      ]),
    });

    this.addRecordForm = new FormGroup({
      amount: new FormControl("", [
        Validators.required,
      ]),
      type: new FormControl(1, [
        Validators.required
      ])
    });

    this.http.get<FinanceRecord[]>(environment.apiUrl + '/api/FinanceRecord/getAllByUser', this.addRecordForm.value).subscribe(response =>{
      console.log(response)
      // @ts-ignore
      this.financeRecords = response;
      // this.financeRecords = response;
      this.initChartData();

    })
  }

  public monthlyPlanForm!: FormGroup;
  public addRecordForm!: FormGroup;

  public series!: any;
  public chart!: ApexChart;
  public dataLabels!: ApexDataLabels;
  public markers!: ApexMarkers;
  public title!: ApexTitleSubtitle;
  public fill!: ApexFill;
  public yaxis!: ApexYAxis;
  public xaxis!: ApexXAxis;
  public tooltip!: ApexTooltip;

  public lastUpdatedTime: number = Date.now();

  constructor(
    private http: HttpClient
  ) {
  }

  public initChartData(): void {
    let ts2 = 1484418600000;
    let dates: (number | Date | undefined)[][] = [];
    let d1 = new Date();

    this.financeRecords.forEach(financeRecord => {
      console.log(financeRecord.amount);
      console.log(financeRecord.createdDate);
      ts2 = ts2 + 86400000;
      if(financeRecord.createdDate) dates.push([ts2, financeRecord.amount]);
    })

    // for (let i = 0; i < 120; i++) {
    //   ts2 = ts2 + 86400000;
    //   dates.push([ts2, dataSeries[1][i].value]);
    // }

    this.series = [
      {
        name: "Money",
        data: dates
      }
    ];
    this.chart = {
      type: "area",
      stacked: false,
      height: 350,
      zoom: {
        type: "x",
        enabled: true,
        autoScaleYaxis: true
      },
      toolbar: {
        autoSelected: "zoom"
      }
    };
    this.dataLabels = {
      enabled: false
    };
    this.markers = {
      size: 0
    };
    this.title = {
      text: "Stock Price Movement",
      align: "left"
    };
    this.fill = {
      type: "gradient",
      gradient: {
        shadeIntensity: 1,
        inverseColors: false,
        opacityFrom: 0.5,
        opacityTo: 0,
        stops: [0, 90, 100]
      }
    };
    this.yaxis = {
      labels: {
        formatter: function(val) {
          return (val).toFixed(0); // return (val / 1000000).toFixed(0);
        }
      },
      title: {
        text: "Money"
      }
    };
    this.xaxis = {
      type: "datetime"
    };
    this.tooltip = {
      shared: false,
      y: {
        formatter: function(val) {
          return (val).toFixed(0); // return (val / 1000000).toFixed(0);
        }
      }
    };
  }

  addRecord() {
    this.http.post<any>(environment.apiUrl + '/api/FinanceRecord/create', this.addRecordForm.value).subscribe(response =>{

    })
  }

  updateMonthlyPlanForm() {

  }
}
