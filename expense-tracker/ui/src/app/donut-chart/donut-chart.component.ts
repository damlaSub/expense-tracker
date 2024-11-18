import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { ChartModule } from 'primeng/chart'; 
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-donut-chart',
  standalone: true,  
  imports: [CommonModule, ChartModule],  //  CommonModule for ngIf and ChartModule for <p-chart>
  templateUrl: './donut-chart.component.html',
  styleUrls: ['./donut-chart.component.scss'],
})
export class DonutChartComponent implements OnInit {
  data: any;
  options: any;
  categories: string[] = [
    'FOOD',
    'TRAVEL',
    'ENTERTAINMENT',
    'UTILITIES',
    'HEALTH',
    'GROCERIES',
    'TRANSPORTATION',
    'EDUCATION',
    'PERSONAL_CARE',
    'SHOPPING',
    'BILLS',
    'INVESTMENTS',
    'OTHER',
  ];

  constructor(private httpService: HttpService) {}

  ngOnInit(): void {
    this.fetchReportData();
    this.initializeChartOptions();
  }

  fetchReportData() {
    this.httpService.get('expenses/reports-month').subscribe(
      (data) => {
        console.log('Data received:', data);
        this.processReportData(data); 
      },
      (error) => {
        console.error('Error occurred:', error);
      },
      () => {
        console.log('Request complete');
      }
    );
  }

  processReportData(response: any) {
    const totals = response.categoryTotals || {};
    const labels = Object.keys(totals);  // Get the categories
    const values = Object.values(totals); 

    // Update chart data
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
          position: 'top',
        },
      },
      responsive: true,
    };
  }
}
