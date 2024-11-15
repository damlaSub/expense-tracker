import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalizationService } from '../services/localization.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent  implements OnInit {
  constructor(private router: Router, private localizationService: LocalizationService) {}

  ngOnInit() {
    this.localizationService.loadLanguage('en'); 
  }

  translate(key: string): string {
    return this.localizationService.$t(key);
  }

  navigateToSignIn() {
    this.router.navigate(['/sign-in']);
  }
}
