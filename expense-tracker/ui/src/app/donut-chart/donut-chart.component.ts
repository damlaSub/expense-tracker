import { Component, OnInit } from '@angular/core';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-donut-chart',
  standalone: true,
  imports: [ChartModule], 
  templateUrl: './donut-chart.component.html',
  styleUrls: ['./donut-chart.component.scss']
})
export class DonutChartComponent implements OnInit {
  data: any; 
  options: any; // Holds chart configuration

  constructor() {}

  ngOnInit(): void {
    // Set up chart data
    this.data = {
      labels: ['A', 'B', 'C', 'D'], 
      datasets: [
        {
          data: [44, 55, 13, 43], 
          backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'], // Segment colors
          hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0']
        }
      ]
    };

    // Set up chart options
    this.options = {
      responsive: true, 
      plugins: {
        legend: {
          position: 'bottom' 
        }
      }
    };
  }
}
