import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {PeConnectService} from '../../services/pe-connect.service';
import {CandidatConnectedService} from "../../services/candidat-connected.service";
import {environment} from "../../../../environments/environment";
import {RoutesEnum} from "../../../model/routes.enum";

@Component({
  selector: 'app-dialog-confirmation',
  templateUrl: './dialog-confirmation.component.html',
  styleUrls: ['./dialog-confirmation.component.scss']
})
export class DialogConfirmationComponent {

  title = '';
  message = '';
  confirmButtonText = 'Ok';
  cancelButtonText = 'Annuler';

  constructor(
      @Inject(MAT_DIALOG_DATA) private dataContent: any,
      private dialogRef: MatDialogRef<DialogConfirmationComponent>,
      private peConnectService: PeConnectService,
      public candidatConnected: CandidatConnectedService) {
    if (dataContent) {
      if (dataContent.buttonText) {
        this.confirmButtonText = dataContent.buttonText.ok || this.confirmButtonText;
        this.cancelButtonText = dataContent.buttonText.cancel || this.cancelButtonText;
      }
      if (dataContent.title) {
        this.title = dataContent.title;
      }
      this.message = dataContent.message || this.message;

    }
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public onClickButtonSeConnecterAvecPE(): void {
    this.peConnectService.login(`${environment.urlAcces}${RoutesEnum.PRE_REQUIS}`);
  }

  onConfirmClick(): void {
    this.dialogRef.close(true);
  }
}
