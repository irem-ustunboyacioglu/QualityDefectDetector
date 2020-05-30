import { UserStory } from './user-story';
import { CriteriaCheckResult } from './criteria-check-result';

export class SingleUserStoryReportResponse {
  public criteriaCheckResults: Map<string, CriteriaCheckResult>;
  public userStory: UserStory;
}
