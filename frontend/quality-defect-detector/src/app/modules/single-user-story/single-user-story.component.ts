import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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
    this.formGroup = this.formBuilder.group({});
  }

  onSubmit() {
    const userStory = this.service.formUserStory(this.formGroup);
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
