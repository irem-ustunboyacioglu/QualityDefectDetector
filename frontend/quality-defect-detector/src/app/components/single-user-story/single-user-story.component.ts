import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { formats, verbs } from 'src/app/shared/constants';
import { UserStoryService } from 'src/app/services/user-story.service';
import { Observable } from 'rxjs';
import { SingleUserStoryReportResponse } from '../../shared/models/single-user-story-report.response';

@Component({
  selector: 'app-single-user-story',
  templateUrl: './single-user-story.component.html',
  styleUrls: ['./single-user-story.component.scss']
})
export class SingleUserStoryComponent implements OnInit {

  formGroup: FormGroup;
  formats = formats;
  verbs = verbs;
  singleUserStoryReport$: Observable<SingleUserStoryReportResponse>;
  showInputs = true;

  constructor(private formBuilder: FormBuilder, private service: UserStoryService) {
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      formatType: [this.formats[0], Validators.required],
      userStoryRolePart: ['', Validators.required],
      userStoryGoalPart: ['', Validators.required],
      userStoryGoalPartVerb: [this.verbs[0], Validators.required],
      userStoryReasonPart: ['']
    });
  }

  onSubmit() {
    let userStory = '';
    const formatType = this.formGroup.get('formatType').value;

    if (formatType === 'Format 1') {
      userStory = 'bir ' + this.formGroup.get('userStoryRolePart').value + ' olarak ' +
        this.formGroup.get('userStoryReasonPart').value + ' için '
        + this.formGroup.get('userStoryGoalPart').value + ' ' + this.formGroup.get('userStoryGoalPartVerb').value;
    }
    if (formatType === 'Format 2') {
      userStory = 'bir ' + this.formGroup.get('userStoryRolePart').value + ' olarak ' +
        this.formGroup.get('userStoryGoalPart').value + ' ' + this.formGroup.get('userStoryGoalPartVerb').value +
        ' böylece ' + this.formGroup.get('userStoryReasonPart').value;
    }
    if (formatType === 'Format 3') {
      userStory = 'bir ' + this.formGroup.get('userStoryRolePart').value + ' olarak ' +
        this.formGroup.get('userStoryGoalPart').value + ' ' + this.formGroup.get('userStoryGoalPartVerb').value;
    }
    this.singleUserStoryReport$ = this.service.analyseUserStory(userStory);
    this.showOrHideInputs();
  }
  showOrHideInputs() {
    this.formGroup.reset();
    this.formGroup.get('formatType').setValue(this.formats[0]);
    this.formGroup.get('userStoryGoalPartVerb').setValue(this.verbs[0]);
    this.showInputs = !this.showInputs;
  }
}
