import { UserStory } from './user-story';
import { CriteriaType } from './enums/criteria-type';
import { CriteriaCheckResult } from './criteria-check-result';

export class SingleUserStoryReportResponse {
  public criteriaCheckResults: Map<CriteriaType, CriteriaCheckResult>;
  public userStory: UserStory;
}
