import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MultipleUserStoryComponent } from './multiple-user-story.component';

@NgModule({
    declarations: [
        MultipleUserStoryComponent
    ],
    imports: [
        SharedModule,
        CommonModule,
        ReactiveFormsModule,
    ],
})
export class MultipleUserStoryModule { }
