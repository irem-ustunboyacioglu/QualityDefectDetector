import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { EnterUserStoryComponent } from './enter-user-story.component';

describe('FormatsComponent', () => {
  let component: EnterUserStoryComponent;
  let fixture: ComponentFixture<EnterUserStoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EnterUserStoryComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnterUserStoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
