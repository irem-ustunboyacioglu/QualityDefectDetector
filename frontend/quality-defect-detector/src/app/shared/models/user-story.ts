import {UserStoryType} from './enums/user-story-type';

export class UserStory {
  public userStoryType: UserStoryType;
  public userStorySentence: string;
  public role: string;
  public goal: string;
  public reason: string;
}
