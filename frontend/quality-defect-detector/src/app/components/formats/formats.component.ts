import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { formats } from 'src/app/shared/constants';

@Component({
  selector: 'app-formats',
  templateUrl: './formats.component.html',
  styleUrls: ['./formats.component.scss']
})
export class FormatsComponent implements OnInit {

  @Input()
  formGroup: FormGroup;
  @Input()
  disableFormat?= false;

  formats = formats;

  constructor() { }

  ngOnInit() {
    this.formGroup.addControl('formatType', new FormControl(this.formats[0], [Validators.required]));
  }
}
