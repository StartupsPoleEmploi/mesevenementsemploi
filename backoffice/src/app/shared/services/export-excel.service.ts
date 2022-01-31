import {ErrorHandler, Injectable} from '@angular/core';
import {ICandidat} from '../../model/ICandidat';
import {Workbook} from 'exceljs';
import * as FileSaver from 'file-saver';
import {IEvent} from '../../model/IEvent';
import {TokenStorageService} from './token-storage.service';
import IAgency from '../../model/IAgency';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ApiService} from './api.service';

@Injectable({
  providedIn: 'root'
})

export class ExportExcelService extends ApiService {

  public naf: any;
  public sheetName: string | undefined = 'ExportEvenements';
  public fileName = this.sheetName;
  public columns = [];
  public nafCode: any;
  public libelle = '';
  public agence: IAgency | undefined;

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }


  public generateExcelCandidatsInscrits(dataCandidats: ICandidat[], event: IEvent | undefined): void {
    const headerCandidats = ['Identifiant (BNI)', 'Nom', 'Prenom', 'Date de naissance', 'Email', 'Présence', 'Modalité participation', 'Date de l\'événement', 'Titre de l\'événement'];
    const colonneIdDE = 1;
    const colonneNom = 2;
    const colonnePrenom = 3;
    const colonneEmail = 4;
    const colonnePresence = 6;
    const colonneModalite = 7;
    const colonneDateNaissance = 5;

    const colonneDateEvenement = 8;
    const colonneTitreEveneemnt = 9;
    const colonneDateInscription = 10;

    // Create workbook and worksheet
    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Candidats inscrits');
    const colTitreEvt = event?.titre;
    const colDateEvt = event?.dateEvenement;

    // Add Header Row
    const headerRow = worksheet.addRow(headerCandidats);

    // Cell Style : Fill and Border
    // tslint:disable-next-line:variable-name
    headerRow.eachCell((cell, number) => {
      cell.fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: {argb: 'FFEBEDF8'},
        bgColor: {argb: 'FF0000FF'}
      };
      cell.border = {top: {style: 'thin'}, left: {style: 'thin'}, bottom: {style: 'thin'}, right: {style: 'thin'}};
    });

    const result = [];
    let col: any[];

    // tslint:disable-next-line:forin
    for (const i in dataCandidats) {
      col = Object.values(dataCandidats[i]);
      result.push(col);
    }

    result.push(colTitreEvt);
    result.push(colDateEvt);

    result.forEach(d => {
        worksheet.addRow(d);
      }
    );

    for (let i = 2; i <= dataCandidats.length + 1; i++) {
      const row = worksheet.getRow(i);
      const cell = row.getCell(1).value;
      if (cell) {
        row.splice(1, 1);
        row.splice(1, 1);
        row.splice(2, 1);
      }

      const cellPresence = row.getCell(colonnePresence).value;
      if (cellPresence === false) {
        row.getCell(colonnePresence).value = 'NON';
      }
      if (cellPresence === true) {
        row.getCell(colonnePresence).value = 'OUI';
      }

      const cellModalite = row.getCell(colonneModalite).value;
      if (cellModalite === 0) {
        row.getCell(colonneModalite).value = 'à distance';
      } else if (cellModalite === 1) {
        row.getCell(colonneModalite).value = 'en physique';
      }

      // @ts-ignore
      row.getCell(colonneTitreEveneemnt).value = colTitreEvt.toString();
      // @ts-ignore
      row.getCell(colonneDateEvenement).value = colDateEvt.toString();

    }

    worksheet.getColumn(colonneIdDE).width = 30;
    worksheet.getColumn(colonneNom).width = 20;
    worksheet.getColumn(colonnePrenom).width = 20;
    worksheet.getColumn(colonneEmail).width = 20;
    worksheet.getColumn(colonneModalite).width = 40;
    worksheet.getColumn(colonnePresence).width = 20;
    worksheet.getColumn(colonneDateNaissance).width = 30;
    worksheet.getColumn(colonneDateInscription).width = 30;
    worksheet.getColumn(colonneTitreEveneemnt).width = 40;
    worksheet.getColumn(colonneDateEvenement).width = 40;

    // tslint:disable-next-line:no-shadowed-variable
    workbook.xlsx.writeBuffer().then((dataJson) => {
      const blob = new Blob([dataJson], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
      FileSaver.saveAs(blob, 'CandidatsInscrits.xlsx');
    });
  }

}
