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
      FOOD: '#FF6384',
      TRAVEL: '#36A2EB',
      ENTERTAINMENT: '#FFCE56',
      UTILITIES: '#4BC0C0',
      HEALTH: '#9966FF',
      GROCERIES: '#FF9F40',
      TRANSPORTATION: '#FF6384',
      EDUCATION: '#4CAF50',
      PERSONAL_CARE: '#795548',
      SHOPPING: '#FFC107',
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
