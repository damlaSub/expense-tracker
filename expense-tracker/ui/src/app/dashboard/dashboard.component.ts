import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalizationService } from '../services/localization.service';
import { DonutChartComponent } from '../donut-chart/donut-chart.component';
import { DailyExpensesListComponent } from '../daily-expenses-list/daily-expenses-list.component';
import { ExpenseItemComponent } from '../expense-item/expense-item.component';
import { WebSocketService } from '../services/websocket.service';
import { Message } from '@stomp/stompjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpService } from '../services/http.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, DonutChartComponent, DailyExpensesListComponent, ExpenseItemComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  userName: string = '';
  selectedTimePeriod: string = 'month'; // Default selection
  notifications: any[] = [];
  newNotificationsCount: number = 0
  showNotificationsList: boolean = false;

  constructor(private router: Router, private localizationService: LocalizationService, private webSocketService: WebSocketService,  private httpService: HttpService) {}

  ngOnInit() {
    this.localizationService.loadLanguage('en');
    this.userName = localStorage.getItem('userName') || '';
    const savedNotifications = localStorage.getItem('notifications');
    const savedCount = localStorage.getItem('newNotificationsCount');

  this.notifications = savedNotifications ? JSON.parse(savedNotifications) : [];
  this.newNotificationsCount = savedCount ? parseInt(savedCount, 10) : 0;

  this.webSocketService.connect((message) => {
    this.handleNotification(message);
  });
    console.log(this.notifications)
  }

  $t(key: string): string {
    return this.localizationService.translate(key);
  }

  updateTimePeriod(period: string) {
    this.selectedTimePeriod = period; 
  }

  navigateToAllExpenses(){
    console.log('click')
    this.router.navigate(['/expenses']);
  }

  handleNotification(message: Message) {
    console.log('Received message:', message.body);
  const parsedMessage = JSON.parse(message.body);

  // Check if a notification with the same subject already exists
  const isDuplicate = this.notifications.some(
    (notification) => notification.subject === parsedMessage.subject
  );

  if (!isDuplicate) {
    // Add the new notification and increase the count
    this.notifications = [...this.notifications, parsedMessage];
    this.newNotificationsCount++;

    // Save notifications and count in localStorage
    localStorage.setItem('notifications', JSON.stringify(this.notifications));
    localStorage.setItem('newNotificationsCount', this.newNotificationsCount.toString());
  }
  console.log("this.newNotificationsCount", this.newNotificationsCount);
  console.log(this.notifications);
  }

  showNotifications() {
    this.newNotificationsCount = 0;  
    this.notifications = this.notifications.filter(
      (notification, index, self) =>
        index === self.findIndex((n) => n.subject === notification.subject)
    );
  }

  dismissNotification(notification: any) {
    this.notifications = this.notifications.filter((n) => n !== notification);
    if (this.notifications.length === 0) {
      this.showNotificationsList = false; // Hide the container when the last notification is removed
    }
    localStorage.setItem('notifications', JSON.stringify(this.notifications));
  }

  toggleNotificationsList() {
    this.showNotificationsList = !this.showNotificationsList;
    if (this.showNotificationsList) {
      this.newNotificationsCount = 0;
      localStorage.setItem('newNotificationsCount', '0');
    } 
 }

  navigateToAddExpense(){
    console.log('click')
    this.router.navigate(['/add-expense']);
  }


}
