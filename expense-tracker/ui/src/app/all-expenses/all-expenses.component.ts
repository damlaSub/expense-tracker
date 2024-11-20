import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalizationService } from '../services/localization.service';
import { HttpService } from '../services/http.service';
import { CommonModule } from '@angular/common';

import { DailyExpensesListComponent } from '../daily-expenses-list/daily-expenses-list.component';

@Component({
  selector: 'app-all-expenses',
  standalone: true,
  imports: [CommonModule, DailyExpensesListComponent ],
  templateUrl: './all-expenses.component.html',
  styleUrl: './all-expenses.component.scss'
})
export class AllExpensesComponent  implements OnInit {
  constructor(private router: Router, private localizationService: LocalizationService, private httpService: HttpService) {}
  expensesReports: any[] = [];

  // Mapping categories to icons
  categoryIcons: { [key: string]: string } = {
    FOOD: 'bi-basket',
    TRAVEL: 'bi-airplane',
    ENTERTAINMENT: 'bi-film',
    UTILITIES: 'bi-lightbulb',
    HEALTH: 'bi-heart-pulse',
    GROCERIES: 'bi-cart4',
    TRANSPORTATION: 'bi-bus-front',
    EDUCATION: 'bi-book',
    PERSONAL_CARE: 'bi-person',
    SHOPPING: 'bi-bag',
    BILLS: 'bi-credit-card',
    INVESTMENTS: 'bi-graph-up',
    OTHER: 'bi-box-seam'
  };

  ngOnInit() {
   // this.localizationService.loadLanguage('en'); 
    this.fetchAllExpenses();
  }

  $t(key: string): string {
    return this.localizationService.translate(key);
  }

  fetchAllExpenses(): void {
    const endpoint = 'expenses/by-date';
    this.httpService.get(endpoint).subscribe(
      (data: any) => this.processAllExpensesData(data),
      (error) => console.error('Error fetching all expenses:', error)
    );
  }

  processAllExpensesData(response: any): void {
    console.log('Response Data:', response); 
    this.expensesReports = response;
  }
  getIconForCategory(category: string): string {
    return this.categoryIcons[category] || 'bi-question-circle'; // Default icon for unknown categories
  }
}
