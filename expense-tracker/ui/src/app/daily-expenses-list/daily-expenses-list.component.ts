import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-daily-expenses-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './daily-expenses-list.component.html',
  styleUrl: './daily-expenses-list.component.scss'
})
export class DailyExpensesListComponent implements OnInit {
  expenses: any[] = [];
  dailyTotal: number = 0;
  formattedDate: string = '';

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

  constructor(private httpService: HttpService) {}

  ngOnInit(): void {
    this.fetchDailyExpenses();
  }

  fetchDailyExpenses(): void {
    const endpoint = 'expenses/reports-day';
    this.httpService.get(endpoint).subscribe(
      (data: any) => this.processDailyExpensesData(data),
      (error) => console.error('Error fetching daily expenses:', error)
    );
  }

  processDailyExpensesData(response: any): void {
    this.formattedDate = response.formattedDate;
    this.expenses = response.expenses;
    this.dailyTotal = response.dailyTotal;
  }

  getIconForCategory(category: string): string {
    return this.categoryIcons[category] || 'bi-question-circle'; // Default icon for unknown categories
  }
}
