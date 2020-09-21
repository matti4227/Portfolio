import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterModule } from './register/register.module';
import { CoreModule } from './core/core.module';
import { SecurityModule } from './security/security.module';
import { RecipesModule } from './recipes/recipes.module';
import { UserModule } from './user/user.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeModule } from './home/home.module';
import { MaterialModule } from './shared/material.module';
import { CookbookModule } from './cookbook/cookbook.module';
import { AdminModule } from './admin/admin.module';
import { ToastrModule } from 'ngx-toastr';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { WelcomeModule } from './welcome/welcome.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RegisterModule,
    CoreModule,
    SecurityModule,
    RecipesModule,
    WelcomeModule,
    RouterModule,
    UserModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgbModule,
    CookbookModule,
    AdminModule,
    AppRoutingModule,
    HomeModule,
    MaterialModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
