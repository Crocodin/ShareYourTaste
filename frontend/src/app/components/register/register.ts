import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  auth = inject(Auth);
  router = inject(Router);
  cdr = inject(ChangeDetectorRef);

  username: string = '';
  password: string = '';
  confirmPassword: string = '';
  email: string = '';
  errorMessage: string = '';

  async onSubmit() {
    // validate passwords match before sending to backend
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      this.cdr.detectChanges();
      return;
    }

    try {
      await this.auth.register(this.username, this.password, this.email);
      this.navigateToLogin();
    } catch (error: any) {
      this.errorMessage = error.error || 'Registration failed';
      this.cdr.detectChanges();
    }
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }
}