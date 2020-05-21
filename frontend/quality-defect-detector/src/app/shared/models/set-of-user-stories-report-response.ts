import { CriteriaType } from './enums/criteria-type';
import { CriteriaCheckResult } from './criteria-check-result';
import { SingleUserStoryReportResponse } from './single-user-story-report.response';

export class SetOfUserStoriesReportResponse {
    public setCriteriaResults: Map<CriteriaType, CriteriaCheckResult>;
    public singleUserStoryReportList: SingleUserStoryReportResponse[];
}
