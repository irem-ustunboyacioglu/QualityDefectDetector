import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { verbs, formats } from 'src/app/shared/constants';

@Component({
  selector: 'app-enter-user-story',
  templateUrl: './enter-user-story.component.html',
  styleUrls: ['./enter-user-story.component.scss']
})
export class EnterUserStoryComponent implements OnInit {

  @Input()
  formGroup: FormGroup;
  @Input()
  showInputs: boolean;
  @Input()
  buttonText: string;

  formats = formats;
  verbs = verbs;

  constructor() { }

  ngOnInit() {
    this.formGroup.addControl('userStoryRolePart', new FormControl('', [Validators.required]));
    this.formGroup.addControl('userStoryGoalPart', new FormControl('', [Validators.required]));
    this.formGroup.addControl('userStoryGoalPartVerb', new FormControl(this.verbs[0], [Validators.required]));
    this.formGroup.addControl('userStoryReasonPart', new FormControl(''));
  }
}
