import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LocalizationService } from '../services/localization.service';
import { HttpService } from '../services/http.service';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-expense',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-expense.component.html',
  styleUrls: ['./add-expense.component.scss'], 
})
export class AddExpenseComponent implements OnInit {
  expense = {
    amount: null,
    category: '',
    description: ''
  };

  categories: string[] = []; 
  errorMessage = '';

  constructor(
    private router: Router, 
    private httpService: HttpService, 
    private localizationService: LocalizationService,
    private location: Location
  ) {}

  ngOnInit() {
    this.localizationService.loadLanguage('en');
    this.fetchCategories();
  }

  $t(key: string): string {
    return this.localizationService.translate(key);
  }

  fetchCategories() {
    const endpoint = 'expense-categories';
    this.httpService.get<string[]>(endpoint).subscribe({
      next: (data) => (this.categories = data),
      error: (error) => {
        console.error('Error occurred:', error);
        this.errorMessage = 'Failed to load categories. Please try again later.';
      },
    });
  }

  saveExpense() {
    const endpoint = 'expenses/add';
    this.expense.category = this.expense.category.toUpperCase();
    this.httpService.post(endpoint, this.expense).subscribe({
      next: () => {
        this.location.back();
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }
}
