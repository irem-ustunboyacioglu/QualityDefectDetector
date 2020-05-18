import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImportFileComponent } from './import-file.component';

@NgModule({
    declarations: [
        ImportFileComponent
    ],
    imports: [
        BrowserModule,
        CommonModule
    ],
})

export class ImportFileModule { }
