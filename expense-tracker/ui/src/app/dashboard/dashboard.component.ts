import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalizationService } from '../services/localization.service';
import { DonutChartComponent } from '../donut-chart/donut-chart.component';
import { DailyExpensesListComponent } from '../daily-expenses-list/daily-expenses-list.component';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, DonutChartComponent, DailyExpensesListComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  userName: string = '';
  selectedTimePeriod: string = 'month'; // Default selection

  constructor(private router: Router, private localizationService: LocalizationService) {}

  ngOnInit() {
    this.localizationService.loadLanguage('en');
    this.userName = localStorage.getItem('userName') || '';
  }

  $t(key: string): string {
    return this.localizationService.translate(key);
  }

  updateTimePeriod(period: string) {
    this.selectedTimePeriod = period; 
  }
}
