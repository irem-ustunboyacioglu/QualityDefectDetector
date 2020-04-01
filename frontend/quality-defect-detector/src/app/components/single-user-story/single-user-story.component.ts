import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { formats, verbs } from 'src/app/shared/constants';

@Component({
  selector: 'app-single-user-story',
  templateUrl: './single-user-story.component.html',
  styleUrls: ['./single-user-story.component.scss']
})
export class SingleUserStoryComponent implements OnInit {

  formGroup: FormGroup;
  formats = formats;
  verbs = verbs;
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      formatType: [this.formats[0], Validators.required],
      userStoryRolePart: ['', Validators.required],
      userStoryGoalPart: ['', Validators.required],
      userStoryGoalPartVerb: [this.verbs[0], Validators.required],
      userStoryReasonPart: ['']
    });
  }

}
