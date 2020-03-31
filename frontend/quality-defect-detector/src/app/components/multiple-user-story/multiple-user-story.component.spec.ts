import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultipleUserStoryComponent } from './multiple-user-story.component';

describe('MultipleUserStoryComponent', () => {
  let component: MultipleUserStoryComponent;
  let fixture: ComponentFixture<MultipleUserStoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultipleUserStoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultipleUserStoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
