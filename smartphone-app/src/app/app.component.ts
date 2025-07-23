import { Component, importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { provideHttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { SmartphoneUploadComponent } from './smartphone-upload/smartphone-upload.component';
import { SmartphonePageComponent } from './smartphones-page/smartphones-page.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HttpClientModule,RouterModule,/*SmartphoneUploadComponent,SmartphonePageComponent*/],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {
  title = 'smartphone-app';
}
