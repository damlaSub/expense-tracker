import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalizationService } from '../services/localization.service';
import { HttpService } from '../services/http.service';
import { CommonModule } from '@angular/common';
import { ExpenseItemComponent } from '../expense-item/expense-item.component';
import { DailyExpensesListComponent } from '../daily-expenses-list/daily-expenses-list.component';
import { InfiniteScrollDirective } from 'ngx-infinite-scroll';

@Component({
  selector: 'app-all-expenses',
  standalone: true,
  imports: [CommonModule, DailyExpensesListComponent, InfiniteScrollDirective, ExpenseItemComponent ],
  templateUrl: './all-expenses.component.html',
  styleUrl: './all-expenses.component.scss'
})
export class AllExpensesComponent  implements OnInit {
  constructor(private router: Router, private localizationService: LocalizationService, private httpService: HttpService) {}
  expensesReports: any[] = [];
  expenses: any[] = [];
  expense: any;
  dailyTotal: number = 0;
  formattedDate: string = '';
  getIconForCategory(category: string): string {
    return this.categoryIcons[category] || 'bi-question-circle'; 
  }
  page: number = 0; 
  pageSize: number = 10; 
  isMoreItem: boolean = true;

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
    this.localizationService.loadLanguage('en'); 
    this.fetchAllExpenses(this.page);
  }

  $t(key: string): string {
    return this.localizationService.translate(key);
  }



  fetchAllExpenses(page: number): void {
    const endpoint = `expenses/by-date?page=${page}&size=${this.pageSize}`;
    this.httpService.get(endpoint).subscribe(
      (data: any) => this.processAllExpensesData(data),
      (error) => console.error('Error fetching all expenses:', error)
    );
  }

  processAllExpensesData(response: any): void {
    console.log('Response Data:', response);
    console.log('Response Length:', response.length);
    console.log('Page Size:', this.pageSize);
    console.log('this.isMoreItem:', this.isMoreItem);

    const expenseLengthInResponse = response.reduce((total: any, item: { expenses: string | any[]; }) => total + (item.expenses?.length || 0), 0);
    if (expenseLengthInResponse === 0 || expenseLengthInResponse < this.pageSize) {
      console.log('No more expenses to fetch.');
      this.isMoreItem = false;
    }

    this.expensesReports = [...this.expensesReports, ...response];
  }

  onScroll(): void {
    if (this.isMoreItem) {
      this.page++;
      console.log('Loading more data: Page', this.page);
      this.fetchAllExpenses(this.page);
    } else {
      console.log('No more items to load');
    }
  }

}
