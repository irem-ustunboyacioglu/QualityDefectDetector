import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { SingleUserStoryReportResponse } from 'src/app/shared/models/single-user-story-report.response';
import { UserStoryService } from '../../services/user-story.service';
import { Observable } from 'rxjs';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SetOfUserStoriesReportResponse } from '../../shared/models/set-of-user-stories-report-response';
import { CriteriaCheckResult } from 'src/app/shared/models/criteria-check-result';

@Component({
  selector: 'app-listing-card',
  templateUrl: './listing-card.component.html',
  styleUrls: ['./listing-card.component.scss']
})
export class ListingCardComponent implements OnInit, OnDestroy, OnChanges {

  @ViewChild('popUp', { static: true })
  popUp: ElementRef;

  @Input()
  singleUserStoryReport?: SingleUserStoryReportResponse;

  @Input()
  multipleUserStoryReport?: SetOfUserStoriesReportResponse;

  criteriaToBeListed: Map<string, CriteriaCheckResult>;

  @Output()
  resetForm: EventEmitter<any> = new EventEmitter<any>();
  suggestions$: Observable<Map<string, Array<string>>>;
  modalRef: NgbModalRef;

  constructor(private userStoryService: UserStoryService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    if (this.singleUserStoryReport) {
      this.criteriaToBeListed = this.singleUserStoryReport.criteriaCheckResults;
    } else {
      this.criteriaToBeListed = this.multipleUserStoryReport.setCriteriaResults;
    }

  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes.singleUserStoryReport && changes.singleUserStoryReport.currentValue) {
      this.criteriaToBeListed = changes.singleUserStoryReport.currentValue.criteriaCheckResults;
    } else {
      this.criteriaToBeListed = changes.multipleUserStoryReport.currentValue.setCriteriaResults;
    }
  }

  showSuggestions(criteria: string) {
    const sentences = [];
    if (this.singleUserStoryReport) {
      sentences.push(this.singleUserStoryReport.userStory.userStorySentence);
    } else if (this.multipleUserStoryReport) {
      this.multipleUserStoryReport.singleUserStoryReportList.forEach(report => {
        sentences.push(report.userStory.userStorySentence);
      });
    }
    this.suggestions$ = this.userStoryService.getSuggestions(criteria, sentences);
    setTimeout(() => {
      this.modalRef = this.modalService.open(this.popUp, {
        backdrop: 'static',
        keyboard: false,
        windowClass: 'pop-up-modals'
      })
    }, 200);
  }

  showOrHideTooltip(i: number) {
    const tooltips = document.querySelectorAll('.listing-card__tooltip');
    if (tooltips[i].hasAttribute('style')) {
      tooltips[i].removeAttribute('style');
    }
    else {
      tooltips[i].setAttribute('style', 'visibility: visible; opacity: 1;');
    }
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
