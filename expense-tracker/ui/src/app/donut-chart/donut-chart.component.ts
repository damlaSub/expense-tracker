import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { HttpService } from '../services/http.service';
import { ChartModule } from 'primeng/chart';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-donut-chart',
  standalone: true,
  imports: [CommonModule, ChartModule],
  templateUrl: './donut-chart.component.html',
  styleUrls: ['./donut-chart.component.scss'],
})
export class DonutChartComponent implements OnChanges {
  @Input() timePeriod: string = 'month'; 
  data: any;
  options: any;
  startDate: string = '';
  endDate: string = '';
  periodTotal: number = 0;

  constructor(private httpService: HttpService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['timePeriod'] && changes['timePeriod'].currentValue) {
      this.fetchReportData();
      this.initializeChartOptions();
    }
  }

  fetchReportData() {
    const endpoint = `expenses/reports-${this.timePeriod}`;
    this.httpService.get(endpoint).subscribe(
      (data) => this.processReportData(data),
      (error) => console.error('Error occurred:', error)
    );
  }

  processReportData(response: any) {
    const totals = response.categoryTotals || {};
    const labels = Object.keys(totals);
    const values = Object.values(totals);
    this.startDate = response.startDate;
    this.endDate = response.endDate;
    this.periodTotal = response.periodTotal;

    this.data = {
      labels: labels,
      datasets: [
        {
          data: values,
          backgroundColor: this.getCategoryColors(labels),
          hoverBackgroundColor: this.getCategoryColors(labels, true),
        },
      ],
    };
  }

  getCategoryColors(labels: string[], hover: boolean = false): string[] {
    const baseColors: Record<string, string> = {
      FOOD: ' #f1948a',
      TRAVEL: ' #d2b4de',
      ENTERTAINMENT: ' #85c1e9',
      UTILITIES: ' #a2d9ce',
      HEALTH: ' #76d7c4',
      GROCERIES: '#f7dc6f',
      TRANSPORTATION: ' #f8c471',
      EDUCATION: ' #e59866',
      PERSONAL_CARE: '#d98880',
      SHOPPING: ' #85929e',
      BILLS: '#00ACC1',
      INVESTMENTS: '#607D8B',
      OTHER: '#9E9E9E',
    };

    return labels.map((label) =>
      hover ? `${baseColors[label]}A0` : baseColors[label]
    );
  }
  initializeChartOptions() {
    this.options = {
      plugins: {
        legend: {
          position: 'bottom',
        },
      },
      responsive: true,
    };
  }
}
