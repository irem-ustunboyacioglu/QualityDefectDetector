import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-single-user-story',
  templateUrl: './single-user-story.component.html',
  styleUrls: ['./single-user-story.component.scss']
})
export class SingleUserStoryComponent implements OnInit {

  formGroup: FormGroup;
  formats = [1, 2, 3];
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      formatType: [1, Validators.required],
      userStoryRolePart: ['', Validators.required],
      userStoryGoalPart: ['', Validators.required],
      userStoryReasonPart: ['']
    });
  }

}
