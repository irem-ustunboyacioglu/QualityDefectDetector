import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SideMenuComponent } from './components/side-menu/side-menu.component';
import { MainMenuComponent } from './components/main-menu/main-menu.component';
import { CommonModule } from '@angular/common';
import { ImportFileComponent } from './modules/import-file/import-file.component';
import { HttpClientModule } from '@angular/common/http';
import { SingleUserStoryModule } from './modules/single-user-story/single-user-story.module';
import { MultipleUserStoryModule } from './modules/multiple-user-story/multiple-user-story.module';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    SideMenuComponent,
    MainMenuComponent,
    ImportFileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    SingleUserStoryModule,
    MultipleUserStoryModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
