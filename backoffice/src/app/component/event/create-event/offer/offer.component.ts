import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {OfferService} from '../../../../shared/services/offer.service';
import {FormControl, FormGroup} from '@angular/forms';
import {IOffer} from "../../../../model/IOffer";

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {

  @Output() newOfferEvent = new EventEmitter<any>();
  @Output() removeOfferEvent = new EventEmitter<any>();

  public offer: IOffer | undefined;
  public offerForm: any;
  public offersList: IOffer[] = [];
  public errorMessage = '';
  public offerToSearch = '';

  constructor(private offerS: OfferService) { }

  ngOnInit(): void {
    this.offerForm = this.initForm();
  }

  public getOfferById(offerId: string): void {
    this.offerS.getOfferById(offerId).subscribe( item => {
      this.offer = item;
    });
  }

  public initForm(): FormGroup {
    return new FormGroup({
      offer: new FormControl(null)
    });
  }

  public addOffer(): void {
    this.searchOffer(this.offerForm.get('offer').value);
  }

  public searchOffer(offerId: string): void {
    this.errorMessage = '';
    if (offerId !== null) {
      this.offerToSearch = offerId;

      this.offerS.getOfferById(this.offerToSearch).subscribe( item => {
        if(item !== undefined && item.id === this.offerToSearch) {
          this.pushOffer(item);
        }
        else {
         this.errorMessage = `L'offre n°${this.offerToSearch} n'existe pas.`;
        }
      });
    }
  }

  public pushOffer(offerToPush: IOffer): void {
    const index = this.offersList.findIndex(offer => offerToPush.id === offer.id);
    if (this.offersList.length === 0 || index === -1) {
      this.newOfferEvent.emit(offerToPush);
      this.offersList.push(offerToPush);
      this.offerForm = this.initForm();
    } else {
      this.errorMessage = `L'offre n°${offerToPush.id} est déjà ajoutée.`;
    }
  }

  public removeOffer(offer: any): void {
    const index = this.offersList.indexOf(offer);
    this.offersList.splice(index, 1);
    this.removeOfferEvent.emit(offer);
    this.offerForm = this.initForm();
    this.errorMessage = '';
  }

  public setValue(offers: []): void {
    offers.forEach((offer: { id: string; }) => {

      this.offerS.getOfferById(offer.id).subscribe( item => {
        if(item !== undefined && item.id === offer.id) {
          this.pushOffer(item);
        }
        else {
          this.errorMessage = `L'offre n°${offer.id} n'existe pas.`;
        }
      });
    });
  }
}
