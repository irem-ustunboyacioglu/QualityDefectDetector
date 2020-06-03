import { CriteriaCheckResult } from './criteria-check-result';
import { SingleUserStoryReportResponse } from './single-user-story-report.response';

export class SetOfUserStoriesReportResponse {
    public setCriteriaResults: Map<string, CriteriaCheckResult>;
    public singleUserStoryReportList: SingleUserStoryReportResponse[];
}
