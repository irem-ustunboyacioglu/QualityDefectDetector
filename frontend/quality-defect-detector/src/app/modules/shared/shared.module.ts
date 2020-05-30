import { NgModule } from '@angular/core';
import { ListingCardComponent } from 'src/app/components/listing-card/listing-card.component';
import { RemoveUnderscorePipe } from 'src/app/pipes/remove-underscore.pipe';
import { FormatsComponent } from 'src/app/components/formats/formats.component';
import { EnterUserStoryComponent } from 'src/app/components/enter-user-story/enter-user-story.component';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    declarations: [
        ListingCardComponent,
        RemoveUnderscorePipe,
        FormatsComponent,
        EnterUserStoryComponent
    ],
    imports: [
        BrowserModule,
        CommonModule,
        ReactiveFormsModule,
        NgbModalModule
    ],
    exports: [
        ListingCardComponent,
        FormatsComponent,
        EnterUserStoryComponent
    ]
})
export class SharedModule { }
