import { Component, OnInit } from '@angular/core';
import { saveAs } from 'file-saver'
import { UserStoryService } from 'src/app/services/user-story.service';
import { SetOfUserStoriesReportResponse } from 'src/app/shared/models/set-of-user-stories-report-response';
import { filter } from 'rxjs/operators';
import * as XLSX from 'xlsx';
@Component({
  selector: 'app-import-file',
  templateUrl: './import-file.component.html',
  styleUrls: ['./import-file.component.scss']
})
export class ImportFileComponent implements OnInit {

  importedFile: File;
  reportSuccessfullyDownloaded = false;

  constructor(private userStoryService: UserStoryService) { }

  ngOnInit() {
  }

  fileChange(event) {
    if (event.target.files && event.target.files.length) {
      this.importedFile = event.target.files[0];
    }
  }
  submit() {
    if (!this.importedFile) {
      return;
    }
    this.userStoryService.analyseUserStoryFile(this.importedFile).pipe(filter(item => !!item)).subscribe(response => {
      this.downloadFile(response);
      this.reportSuccessfullyDownloaded = true;
      setTimeout(() => {
        document.getElementById('success-alert').remove();
      }, 8000);
    });
  }

  downloadFile(response: SetOfUserStoriesReportResponse) {
    if (this.importedFile.name.endsWith('.csv') || this.importedFile.name.endsWith('.txt')) {
      let csvString: string = 'Kriter,Tek/Set,Kullanıcı Hikayesi,Statüs,Hata Mesajı\r\n';
      const { setCriteriaResults, singleUserStoryReportList } = response;

      Object.keys(setCriteriaResults).forEach(key => {
        csvString += `${key},Set,Yok,${setCriteriaResults[key].satisfiesThisCriteria ? 'Geçti' : 'Geçemedi'}, ${setCriteriaResults[key].errorMessage ? setCriteriaResults[key].errorMessage : 'Yok'}\r\n`;
      });

      singleUserStoryReportList.forEach(userStoryRow => {
        const { userStory, criteriaCheckResults } = userStoryRow;
        Object.keys(criteriaCheckResults).forEach(key => {
          csvString += `${key},Tek,${userStory.userStorySentence},${criteriaCheckResults[key].satisfiesThisCriteria ? 'Geçti' : 'Geçemedi'}, ${criteriaCheckResults[key].errorMessage ? setCriteriaResults[key].errorMessage : 'Yok'}\r\n`;
        });
      });
      var blob = new Blob([csvString], { type: `text/${this.importedFile.name.substring(this.importedFile.name.indexOf('.') + 1)}` })
      saveAs(blob, `kullanici-hikayeleri-rapor.${this.importedFile.name.substring(this.importedFile.name.indexOf('.') + 1)}`);
      this.importedFile = undefined;
    }
    else if (this.importedFile.name.endsWith('.xlsx')) {
      const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
      const { setCriteriaResults, singleUserStoryReportList } = response;
      const xlsxArray = [];
      Object.keys(setCriteriaResults).forEach(key => {
        xlsxArray.push({
          'Kriter': key, 'Tek/Set': 'Set', 'Kullanıcı Hikayesi': 'Yok',
          'Statüs': setCriteriaResults[key].satisfiesThisCriteria ? 'Geçti' : 'Geçemedi',
          'Hata Mesajı': setCriteriaResults[key].errorMessage ? setCriteriaResults[key].errorMessage : 'Yok'
        });
      });
      singleUserStoryReportList.forEach(userStoryRow => {
        const { userStory, criteriaCheckResults } = userStoryRow;
        Object.keys(criteriaCheckResults).forEach(key => {
          xlsxArray.push({
            'Kriter': key, 'Tek/Set': 'Set', 'Kullanıcı Hikayesi': userStory.userStorySentence,
            'Statüs': criteriaCheckResults[key].satisfiesThisCriteria ? 'Geçti' : 'Geçemedi',
            'Hata Mesajı': criteriaCheckResults[key].errorMessage ? criteriaCheckResults[key].errorMessage : 'Yok'
          });
        });
      });
      const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(xlsxArray);
      const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
      const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

      const data: Blob = new Blob([excelBuffer], { type: EXCEL_TYPE });
      saveAs(data, 'kullanici-hikayeleri-rapor.xlsx');
    }
  }

}
