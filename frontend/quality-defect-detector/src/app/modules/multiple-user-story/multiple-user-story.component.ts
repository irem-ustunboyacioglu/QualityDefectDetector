import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserStoryService } from 'src/app/services/user-story.service';
import { formats, verbs } from 'src/app/shared/constants';
import { Observable } from 'rxjs';
import { SetOfUserStoriesReportResponse } from 'src/app/shared/models/set-of-user-stories-report-response';

@Component({
  selector: 'app-multiple-user-story',
  templateUrl: './multiple-user-story.component.html',
  styleUrls: ['./multiple-user-story.component.scss']
})
export class MultipleUserStoryComponent implements OnInit {
  formGroup: FormGroup;
  formats = formats;
  verbs = verbs;
  multipleUserStoryReport$: Observable<SetOfUserStoriesReportResponse>;
  userStoryList = [];
  userStoryRange = Array(9);
  userStorySubmitted = false;

  constructor(private formBuilder: FormBuilder, private service: UserStoryService) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      userStoryAmount: [2, [Validators.required]]
    });
  }

  onSubmit() {
    const userStory = this.service.formUserStory(this.formGroup);
    this.userStoryList.push(userStory);
    this.userStorySubmitted = true;
    setTimeout(() => {
      this.userStorySubmitted = false;
      document.getElementById('success-alert').remove();
    }, 8000);
    this.reset();
  }
  reset() {
    const userStoryAmount = this.formGroup.get('userStoryAmount').value;
    this.formGroup.reset();
    this.formGroup.get('formatType').setValue(this.formats[0]);
    this.formGroup.get('userStoryGoalPartVerb').setValue(this.verbs[0]);
    this.formGroup.get('userStoryAmount').setValue(userStoryAmount);
  }
}
