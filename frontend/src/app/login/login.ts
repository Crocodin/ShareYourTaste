import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule], // this is for ngModel
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username: string = '';
  password: string = '';
  auth: Auth = inject(Auth);
  router = inject(Router);
  errorMessage: string = '';
  cdr = inject(ChangeDetectorRef);

  async onSubmit() {
    try {
      await this.auth.login(this.username, this.password);
      if (this.auth.isLoggedIn()) {
        this.router.navigate(['/home']);

      } else {
        console.error('Login failed: Invalid credentials');
        this.errorMessage = 'Invalid username or password';
      }
    } catch (error: any) {
      console.error('Login failed:', error);
      this.errorMessage = error.error?.message || 'An error occurred during login. Please try again.';
    }
    this.cdr.detectChanges();
  }
}
