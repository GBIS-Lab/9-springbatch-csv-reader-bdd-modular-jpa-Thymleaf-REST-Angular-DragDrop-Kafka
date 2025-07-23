import { Component, EventEmitter, Output, ViewChild  } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { SmartphoneListComponent } from '../components/smartphone-list/smartphone-list.component';


@Component({
  selector: 'app-smartphone-upload',
  standalone: true,
  templateUrl: './smartphone-upload.component.html',
  styleUrls: ['./smartphone-upload.component.scss'],
  imports: [CommonModule]
})
export class SmartphoneUploadComponent {
  @ViewChild(SmartphoneListComponent)
  smartphoneListComponent!: SmartphoneListComponent;
  selectedFile?: File;
  message = '';
  isHovering = false; // Pour effet visuel pendant le drag

  private readonly uploadUrl = 'http://localhost:8080/api/upload';
   @Output() uploadCompleted = new EventEmitter<void>();

  constructor(private http: HttpClient) {}

  // 🔄 Survol de la zone : nécessaire pour permettre le drop
  onDragOver(event: DragEvent) {
    event.preventDefault();
    this.isHovering = true;
  }

  // ⛔ Sortie de la zone
  onDragLeave(event: DragEvent) {
    event.preventDefault();
    this.isHovering = false;
  }

  // ✅ Fichier déposé
  onFileDropped(event: DragEvent) {
    event.preventDefault();
    this.isHovering = false;

    const file = event.dataTransfer?.files?.[0];
    if (!file) {
      this.message = 'Aucun fichier détecté.';
      return;
    }

    if (!file.name.endsWith('.csv')) {
      this.message = 'Seuls les fichiers .csv sont autorisés';
      return;
    }

    this.selectedFile = file;
    this.message = `Fichier sélectionné : ${file.name}`;
  }

  // 📤 Envoi du fichier
  uploadFile() {
    if (!this.selectedFile) {
      this.message = 'Aucun fichier sélectionné';
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.http.post(this.uploadUrl, formData, {
      reportProgress: true,
      observe: 'events',
      responseType: 'text'
    }).subscribe({
      next: event => {
        if (event.type === HttpEventType.Response) {
          this.message = 'Fichier envoyé et traitement lancé !';
          this.uploadCompleted.emit();
          //Rafraîchir la liste après succès
          this.smartphoneListComponent.refreshSmartphones();
        }
      },
      error: (err) => {
        console.error('Erreur lors de l’envoi du fichier', err);
        this.message = 'Erreur lors de l’envoi du fichier';
      }
    });
  }
}
