import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { HttpService } from '../services/http.service';
import { LocalizationService } from '../services/localization.service';


@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.scss'
})
export class SignInComponent implements OnInit {
  email: string = '';
  password: string = '';
  errorMessage: string | null = null;
  public imgURL = environment.imgURL;

  constructor(private httpService: HttpService, private router: Router, private localizationService: LocalizationService) { }
  ngOnInit() {
    this.localizationService.loadLanguage('en'); 
  }

  $t(key: string): string {
    return this.localizationService.translate(key);
  }

  onSubmit(event: Event) {
    event.preventDefault(); console.log('Clicked, submitting...');
    const data = { email: this.email, password: this.password };
    localStorage.clear();
    this.httpService.post('account/sign-in', data).subscribe({
      next: (response) => {
        console.log('Sign-in successful:', response);
        
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Sign-in failed:', error);
        this.errorMessage = 'Sign-in failed. Please check your credentials.'; 
      },
    });
  }
}