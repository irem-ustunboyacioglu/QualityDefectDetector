import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleUserStoryComponent } from './single-user-story.component';

describe('SingleUserStoryComponent', () => {
  let component: SingleUserStoryComponent;
  let fixture: ComponentFixture<SingleUserStoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SingleUserStoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleUserStoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
