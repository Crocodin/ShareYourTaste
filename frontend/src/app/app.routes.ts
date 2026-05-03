import { Routes } from '@angular/router';
import { Login } from './login/login'
import { Home } from './home/home';
import { SongDetail } from './song-detail/song-detail';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'home', component: Home },
  { path: 'songs/:id', component: SongDetail },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
