import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalizationService } from '../services/localization.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  userName: string = '';

  constructor(private router: Router, private localizationService: LocalizationService) {}

  ngOnInit() {
    this.localizationService.loadLanguage('en'); 
    this.userName = localStorage.getItem('userName') || '';
  }

  $t(key: string): string {
    return this.localizationService.translate(key);
  }


}
