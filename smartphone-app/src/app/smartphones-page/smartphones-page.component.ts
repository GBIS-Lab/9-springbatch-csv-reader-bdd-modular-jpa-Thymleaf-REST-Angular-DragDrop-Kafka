import { Component, ViewChild } from '@angular/core';
import { SmartphoneUploadComponent } from '../smartphone-upload/smartphone-upload.component';
import { SmartphoneListComponent } from '../components/smartphone-list/smartphone-list.component';

@Component({
  selector: 'app-smartphone-page',
  template: `
    <app-smartphone-upload (uploadCompleted)="onUploadCompleted()"></app-smartphone-upload>
    <app-smartphone-list #smartphoneList></app-smartphone-list>
  `,
  standalone: true,
  imports: [SmartphoneUploadComponent, SmartphoneListComponent]
})
export class SmartphonePageComponent {
  @ViewChild('smartphoneList')
  smartphoneList!: SmartphoneListComponent;

  onUploadCompleted(): void {
    console.log('📥 Upload terminé, on rafraîchit la liste');
    this.smartphoneList.refreshSmartphones();
  }
}
