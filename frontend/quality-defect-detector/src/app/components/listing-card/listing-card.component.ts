import {Component, OnInit, Input} from '@angular/core';
import {SingleUserStoryReportResponse} from 'src/app/shared/models/single-user-story-report.response';
import {SingleUserStoryService} from '../../services/single-user-story.service';

@Component({
  selector: 'app-listing-card',
  templateUrl: './listing-card.component.html',
  styleUrls: ['./listing-card.component.scss']
})
export class ListingCardComponent implements OnInit {

  constructor(private service: SingleUserStoryService) {
  }

  @Input()
  singleUserStoryReport: SingleUserStoryReportResponse;

  ngOnInit(): void {
  }

  onSubmitSuggestions() {
    this.service.suggestionForSpelling(this.singleUserStoryReport.userStory.userStorySentence)
      .subscribe((response) => {console.log(response); });
  }
}
