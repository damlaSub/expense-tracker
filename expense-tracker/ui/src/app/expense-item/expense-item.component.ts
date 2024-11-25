import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-expense-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './expense-item.component.html',
  styleUrl: './expense-item.component.scss'
})
export class ExpenseItemComponent {

  @Input() expense: { category: string; description?: string; amount: number } = { category: '', amount: 0 };
  @Input() formattedDate: string = '';
  @Input() dailyTotal: number = 0;
  @Input() expenses: Array<{ category: string; description?: string; amount: number }> = [];
  @Input() showDescription: boolean = true;
  @Input() getIconForCategory: (category: string) => string = () => 'bi-question-circle';

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

}
