import { Component } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { HttpService } from '../services/http.service'; 

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.scss'
})
export class SignInComponent {
  email: string = '';
  password: string = '';
  errorMessage: string | null = null;
  public imgURL = environment.imgURL;

  constructor(private httpService: HttpService, private router: Router) {}


  onSubmit(event: Event) {
    event.preventDefault(); console.log('Clicked, submitting...');
    const data = { email: this.email, password: this.password };
    this.httpService.post('account/sign-in', data).subscribe({
      next: (response) => {
        console.log('Sign-in successful:', response);
        // Navigate to dashboard upon success
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Sign-in failed:', error);
        this.errorMessage = 'Sign-in failed. Please check your credentials.';  // Show error message
      },
    });
  }
}