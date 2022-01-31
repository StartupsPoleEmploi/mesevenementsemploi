import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor(private snackBar: MatSnackBar) {
  }

  public showToast = (message: string, type: string, duration = 3000, button = 'OK'): void => {
    this.snackBar.open(message, button, {
      duration : duration,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: [type]
    })
  }
}
