import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { SingleUserStoryComponent } from './single-user-story.component';
import { ListingCardComponent } from '../../components/listing-card/listing-card.component';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore.pipe';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
@NgModule({
    declarations: [
        SingleUserStoryComponent,
        ListingCardComponent,
        RemoveUnderscorePipe
    ],
    imports: [
        BrowserModule,
        CommonModule,
        ReactiveFormsModule,
        NgbModalModule
    ],
})
export class SingleUserStoryModule { }
