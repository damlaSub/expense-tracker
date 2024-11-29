import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component'; 
import { SignInComponent } from './sign-in/sign-in.component'; 
import { DashboardComponent } from './dashboard/dashboard.component';
import { AllExpensesComponent } from './all-expenses/all-expenses.component';
import { AddExpenseComponent } from './add-expense/add-expense.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'sign-in', component: SignInComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'expenses', component: AllExpensesComponent},
    { path: 'add-expense', component: AddExpenseComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }