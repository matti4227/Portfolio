import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {
    path: 'recipes',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'welcome',
    loadChildren: () => import('./welcome/welcome.module').then(m => m.WelcomeModule)
  },
  { path: '',  pathMatch: 'full', redirectTo: 'recipes'},
  { path: '**', pathMatch: 'full',  redirectTo: 'authenticate' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
