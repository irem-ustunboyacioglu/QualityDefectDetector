import { HttpClient } from '@angular/common/http';
import { SingleUserStoryReportResponse } from '../shared/models/single-user-story-report.response';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SetOfUserStoriesReportResponse } from '../shared/models/set-of-user-stories-report-response';

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

  public analyseUserStoryFile(file: File): Observable<SetOfUserStoriesReportResponse> {
    const data: FormData = new FormData();
    data.append('file', file);

    return this.httpClient.post<SetOfUserStoriesReportResponse>(`${this.apiURL}/user-story-defects/analyse-file`, data);
  }


  public suggestionForSpelling(userStory: string): Observable<Map<string, Array<string>>> {
    return this.httpClient.get<Map<string, Array<string>>>(`${this.apiURL}/user-story-defects/check-spelling`,
      { params: { sentence: userStory } });
  }
}
