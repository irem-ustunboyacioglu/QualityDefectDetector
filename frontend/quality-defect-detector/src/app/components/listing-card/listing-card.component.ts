import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { SingleUserStoryReportResponse } from 'src/app/shared/models/single-user-story-report.response';
import { UserStoryService } from '../../services/user-story.service';
import { Observable } from 'rxjs';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-listing-card',
  templateUrl: './listing-card.component.html',
  styleUrls: ['./listing-card.component.scss']
})
export class ListingCardComponent implements OnInit, OnDestroy {

  @ViewChild('popUp', { static: true })
  popUp: ElementRef;

  @Input()
  singleUserStoryReport: SingleUserStoryReportResponse;

  @Output()
  resetForm: EventEmitter<any> = new EventEmitter<any>();
  suggestions$: Observable<Map<string, Array<string>>>;
  modalRef: NgbModalRef;

  constructor(private userStoryService: UserStoryService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
  }

  showSuggestions() {
    this.suggestions$ = this.userStoryService.suggestionForSpelling(this.singleUserStoryReport.userStory.userStorySentence);
    setTimeout(() => {
      this.modalRef = this.modalService.open(this.popUp, {
        backdrop: 'static',
        keyboard: false,
        windowClass: 'pop-up-modals'
      })
    }, 200);
  }
  close() {
    this.modalRef.close();
  }
  reset() {
    this.resetForm.emit();
  }
  ngOnDestroy() {
    if (this.modalRef) {
      this.modalRef.close();
    }
  }
}
