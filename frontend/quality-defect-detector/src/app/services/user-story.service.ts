import { HttpClient } from '@angular/common/http';
import { SingleUserStoryReportResponse } from '../shared/models/single-user-story-report.response';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SetOfUserStoriesReportResponse } from '../shared/models/set-of-user-stories-report-response';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class UserStoryService {
  apiURL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {
  }

  public analyseUserStory(userStory: string): Observable<SingleUserStoryReportResponse> {
    return this.httpClient.get<SingleUserStoryReportResponse>(`${this.apiURL}/user-story-defects/analyse`,
      { params: { sentence: userStory } });
  }
  public analyseMultipleUserStory(userStoryList: string[]): Observable<SetOfUserStoriesReportResponse> {
    return this.httpClient.post<SetOfUserStoriesReportResponse>(`${this.apiURL}/user-story-defects/analyse-multiple`, {},
      { params: { sentences: userStoryList } });
  }

  public analyseUserStoryFile(file: File): Observable<SetOfUserStoriesReportResponse> {
    const data: FormData = new FormData();
    data.append('file', file);

    return this.httpClient.post<SetOfUserStoriesReportResponse>(`${this.apiURL}/user-story-defects/analyse-file`, data);
  }


  public suggestionForSpelling(userStory: string): Observable<Map<string, Array<string>>> {
    return this.httpClient.get<Map<string, Array<string>>>(`${this.apiURL}/user-story-defects/check-spelling`,
      { params: { sentence: userStory } });
  }

  public formUserStory(formGroup: FormGroup): string {
    let userStory = '';
    const formatType = formGroup.get('formatType').value;

    if (formatType === 'Format 1') {
      userStory = 'bir ' + formGroup.get('userStoryRolePart').value + ' olarak ' +
        formGroup.get('userStoryReasonPart').value + ' için '
        + formGroup.get('userStoryGoalPart').value + ' ' + formGroup.get('userStoryGoalPartVerb').value;
    }
    if (formatType === 'Format 2') {
      userStory = 'bir ' + formGroup.get('userStoryRolePart').value + ' olarak ' +
        formGroup.get('userStoryGoalPart').value + ' ' + formGroup.get('userStoryGoalPartVerb').value +
        ' böylece ' + formGroup.get('userStoryReasonPart').value;
    }
    if (formatType === 'Format 3') {
      userStory = 'bir ' + formGroup.get('userStoryRolePart').value + ' olarak ' +
        formGroup.get('userStoryGoalPart').value + ' ' + formGroup.get('userStoryGoalPartVerb').value;
    }
    return userStory;
  }
}
