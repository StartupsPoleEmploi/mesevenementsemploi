import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-annulation',
  templateUrl: './dialog-annulation.component.html',
  styleUrls: ['./dialog-annulation.component.scss']
})
export class DialogAnnulationComponent {

  title = '';
  message = 'Confirmez-vous ?';
  confirmButtonText = 'Ok';
  cancelButtonText = 'Annuler';

  constructor(
      @Inject(MAT_DIALOG_DATA) private data: any,
      private dialogRef: MatDialogRef<DialogAnnulationComponent>) {
    if (data) {
      if (data.title) {
        this.title = data.title;
      }
      this.message = data.message || this.message;
      if (data.buttonText) {
        this.confirmButtonText = data.buttonText.ok || this.confirmButtonText;
        this.cancelButtonText = data.buttonText.cancel || this.cancelButtonText;
      }
    }
  }

  onConfirmClick(): void {
    this.dialogRef.close(true);
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

}
