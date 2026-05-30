import { Routes } from '@angular/router';
import { Login } from './components/login/login'
import { Home } from './components/home/home';
import { SongDetail } from './components/song-detail/song-detail';
import { Profile } from './components/profile/profile';
import { Register } from './components/register/register';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'home', component: Home },
  { path: 'songs/:id', component: SongDetail },
  { path: 'profile/:id', component: Profile },
  { path: 'register', component: Register },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
