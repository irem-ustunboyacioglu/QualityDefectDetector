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
        this.reportSuccessfullyDownloaded = false;
        document.getElementById('success-alert').remove();
      }, 8000);
    });
  }

  downloadFile(response: SetOfUserStoriesReportResponse) {
    if (this.importedFile.name.endsWith('.csv') || this.importedFile.name.endsWith('.txt')) {
      const { setCriteriaResults, singleUserStoryReportList } = response;
      let csvString = 'Set Kriterleri,Durum,Hata Mesajı\r\n';

      Object.keys(setCriteriaResults).forEach(key => {
        csvString += `${key}` + ',' +
          `${setCriteriaResults[key].satisfiesThisCriteria ? 'Geçti' : 'Geçemedi'}` + ',' +
          `${setCriteriaResults[key].errorMessage ? setCriteriaResults[key].errorMessage : 'Yok'}\r\n`;
      });

      csvString += ',Kullanıcı Hikayesi,Tam Cümle, Atomik,İyi Biçimlendirilmiş,Minimal, Probleme Yönelik, Yazım\r\n';

      singleUserStoryReportList.forEach(( userStoryRow, index) => {
        const {userStory, criteriaCheckResults} = userStoryRow;
        csvString += `KH ${index} , ${userStory.userStorySentence}` + ',' +
       `${criteriaCheckResults['Tam Cümle'].satisfiesThisCriteria ? 'Geçti' : criteriaCheckResults['Tam Cümle'].errorMessage ? criteriaCheckResults['Tam Cümle'].errorMessage : 'Yok'}` + ',' +
          `${criteriaCheckResults['Atomik'].satisfiesThisCriteria ? 'Geçti' : criteriaCheckResults['Atomik'].errorMessage ? criteriaCheckResults['Atomik'].errorMessage : 'Yok'}` + ',' +
          `${criteriaCheckResults['İyi Biçimlendirilmiş'].satisfiesThisCriteria ? 'Geçti' : criteriaCheckResults['İyi Biçimlendirilmiş'].errorMessage ? criteriaCheckResults['İyi Biçimlendirilmiş'].errorMessage : 'Yok'}` + ',' +
          `${criteriaCheckResults['Minimal'].satisfiesThisCriteria ? 'Geçti' : criteriaCheckResults['Minimal'].errorMessage ? criteriaCheckResults['Minimal'].errorMessage : 'Yok'}` + ',' +
          `${criteriaCheckResults['Probleme Yönelik'].satisfiesThisCriteria ? 'Geçti' : criteriaCheckResults['Probleme Yönelik'].errorMessage ? criteriaCheckResults['Probleme Yönelik'].errorMessage : 'Yok'}` + ',' +
          `${criteriaCheckResults['Yazım'].satisfiesThisCriteria ? 'Geçti' : criteriaCheckResults['Yazım'].errorMessage ? criteriaCheckResults['Yazım'].errorMessage : 'Yok'}\r\n`;
      });

      const blob = new Blob([csvString], {type: `text/${this.importedFile.name.substring(this.importedFile.name.indexOf('.') + 1)}`});
      saveAs(blob, `kullanici-hikayeleri-rapor.${this.importedFile.name.substring(this.importedFile.name.indexOf('.') + 1)}`);
      this.importedFile = undefined;
    } else if (this.importedFile.name.endsWith('.xlsx')) {
      const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
      const {setCriteriaResults, singleUserStoryReportList} = response;
      const xlsxArray = [];
      const xlsxArray2 = [];
      Object.keys(setCriteriaResults).forEach(key => {
        xlsxArray.push({
          'Set Kriterleri': key, 'Durum': setCriteriaResults[key].satisfiesThisCriteria ? 'Geçti' : 'Geçmedi',
          'Hata Mesajı': setCriteriaResults[key].errorMessage ? setCriteriaResults[key].errorMessage : 'Yok'
        });
      });
      singleUserStoryReportList.forEach(( userStoryRow, index ) => {
        const {userStory, criteriaCheckResults} = userStoryRow;
        xlsxArray2.push({
          '': 'KH' + (index + 1) + '',
          'Kullanıcı Hikayesi': userStory.userStorySentence,
          'Tam Cümle': criteriaCheckResults['Tam Cümle'].satisfiesThisCriteria ? 'Geçti' :
            criteriaCheckResults['Tam Cümle'].errorMessage ? criteriaCheckResults['Tam Cümle'].errorMessage : 'Yok',
          'Atomik': criteriaCheckResults['Atomik'].satisfiesThisCriteria ? 'Geçti' :
            criteriaCheckResults['Atomik'].errorMessage ? criteriaCheckResults['Atomik'].errorMessage : 'Yok',
          'İyi Biçimlendirilmiş': criteriaCheckResults['İyi Biçimlendirilmiş'].satisfiesThisCriteria ? 'Geçti' :
            criteriaCheckResults['İyi Biçimlendirilmiş'].errorMessage ? criteriaCheckResults['İyi Biçimlendirilmiş'].errorMessage : 'Yok',
          'Minimal': criteriaCheckResults['Minimal'].satisfiesThisCriteria ? 'Geçti' :
            criteriaCheckResults['Minimal'].errorMessage ? criteriaCheckResults['Minimal'].errorMessage : 'Yok',
          'Probleme Yönelik': criteriaCheckResults['Probleme Yönelik'].satisfiesThisCriteria ? 'Geçti' :
            criteriaCheckResults['Probleme Yönelik'].errorMessage ? criteriaCheckResults['Probleme Yönelik'].errorMessage : 'Yok',
          'Yazım': criteriaCheckResults['Yazım'].satisfiesThisCriteria ? 'Geçti' :
            criteriaCheckResults['Yazım'].errorMessage ? criteriaCheckResults['Yazım'].errorMessage : 'Yok',
        });
      });
      const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(xlsxArray);
      const worksheet2: XLSX.WorkSheet = XLSX.utils.json_to_sheet(xlsxArray2);
      const workbook: XLSX.WorkBook = {
        Sheets: {
          'Set Kriter Sonuçları': worksheet,
          'Kullanıcı Hikaye Sonuçları': worksheet2
        }, SheetNames: ['Set Kriter Sonuçları', 'Kullanıcı Hikaye Sonuçları']
      };
      const excelBuffer: any = XLSX.write(workbook, {bookType: 'xlsx', type: 'array'});

      const data: Blob = new Blob([excelBuffer], { type: EXCEL_TYPE });
      saveAs(data, 'kullanici-hikayeleri-rapor.xlsx');
    }
  }

}
