import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SingleUserStoryComponent } from './components/single-user-story/single-user-story.component';
import { MultipleUserStoryComponent } from './components/multiple-user-story/multiple-user-story.component';
import { ImportFileComponent } from './components/import-file/import-file.component';


const routes: Routes = [
  {
    path: '',
    component: SingleUserStoryComponent
  },
  {
    path: 'single-user-story',
    component: SingleUserStoryComponent
  },
  {
    path: 'multiple-user-story',
    component: MultipleUserStoryComponent
  },
  {
    path: 'import-file',
    component: ImportFileComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
