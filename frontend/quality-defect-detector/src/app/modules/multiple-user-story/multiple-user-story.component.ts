import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserStoryService } from 'src/app/services/user-story.service';
import { formats, verbs } from 'src/app/shared/constants';
import { SetOfUserStoriesReportResponse } from 'src/app/shared/models/set-of-user-stories-report-response';
import { filter } from 'rxjs/operators';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-multiple-user-story',
  templateUrl: './multiple-user-story.component.html',
  styleUrls: ['./multiple-user-story.component.scss']
})
export class MultipleUserStoryComponent implements OnInit {

  @ViewChild('reportPopUp', { static: true })
  popUp: ElementRef;

  formGroup: FormGroup;
  formats = formats;
  verbs = verbs;
  multipleUserStoryReport: SetOfUserStoriesReportResponse;
  userStoryList: string[] = [];
  userStoryRange = Array(9);
  userStorySubmitted = false;
  currentIndexOfUserStoryDisplayed = 0;
  modalRef: NgbModalRef;

  constructor(private formBuilder: FormBuilder, private service: UserStoryService, private modalService: NgbModal) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      userStoryAmount: [2, [Validators.required]]
    });
  }

  onSubmit() {
    if (!this.formGroup.valid) {
      return;
    }
    const userStory = this.service.formUserStory(this.formGroup);
    this.userStoryList.push(userStory);
    this.userStorySubmitted = true;
    setTimeout(() => {
      this.userStorySubmitted = false;
      document.getElementById('success-alert') ? document.getElementById('success-alert').remove() : null;
    }, 8000);

    if (this.userStoryList.length === parseInt(this.formGroup.get('userStoryAmount').value)) {
      this.service.analyseMultipleUserStory(this.userStoryList).pipe(filter(item => !!item)).subscribe(result => {
        this.multipleUserStoryReport = result;
        setTimeout(() => {
          this.modalRef = this.modalService.open(this.popUp, {
            backdrop: 'static',
            keyboard: false,
            windowClass: 'pop-up-modals'
          })
        }, 200);
      });
    }
    this.reset();
  }
  changeIndex(index: number) {
    if (index < 0 || index > this.userStoryList.length - 1) {
      return;
    }
    this.currentIndexOfUserStoryDisplayed = index;
  }

  reset() {
    const userStoryAmount = this.formGroup.get('userStoryAmount').value;
    const format = this.formGroup.get('formatType').value;
    this.formGroup.reset();
    this.formGroup.get('formatType').setValue(format);
    this.formGroup.get('userStoryGoalPartVerb').setValue(this.verbs[0]);
    this.formGroup.get('userStoryAmount').setValue(userStoryAmount);
  }

  closeReportModal() {
    if (this.modalRef) {
      this.modalRef.close();
      this.formGroup.reset();
      this.formGroup.get('formatType').setValue(this.formats[0]);
      this.formGroup.get('userStoryAmount').setValue(2);
      this.formGroup.get('userStoryGoalPartVerb').setValue(this.verbs[0]);
      this.userStoryList = [];
    }
  }
}
