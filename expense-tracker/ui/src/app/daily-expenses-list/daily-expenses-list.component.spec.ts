import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DailyExpensesListComponent } from './daily-expenses-list.component';

describe('DailyExpensesListComponent', () => {
  let component: DailyExpensesListComponent;
  let fixture: ComponentFixture<DailyExpensesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailyExpensesListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DailyExpensesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
