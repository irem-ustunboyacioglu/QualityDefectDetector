import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SingleUserStoryReportResponse } from 'src/app/shared/models/single-user-story-report.response';
import { SingleUserStoryService } from '../../services/single-user-story.service';

@Component({
  selector: 'app-listing-card',
  templateUrl: './listing-card.component.html',
  styleUrls: ['./listing-card.component.scss']
})
export class ListingCardComponent implements OnInit {

  @Input()
  singleUserStoryReport: SingleUserStoryReportResponse;

  @Output()
  resetForm: EventEmitter<any> = new EventEmitter<any>();

  constructor(private service: SingleUserStoryService) {
  }

  ngOnInit(): void {
  }

  onSubmitSuggestions() {
    this.service.suggestionForSpelling(this.singleUserStoryReport.userStory.userStorySentence)
      .subscribe((response) => { console.log(response); });
  }
  reset() {
    this.resetForm.emit();
  }
}
