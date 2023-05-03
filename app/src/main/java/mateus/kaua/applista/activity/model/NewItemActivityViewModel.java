package mateus.kaua.applista.activity.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {
    Uri selectedPhotoLocation = null;
    public Uri getSelectedPhotoLocation(){
        return selectedPhotoLocation;
    }
    public void setSelectedPhotoLocation(Uri selectedPhotoLocation){
        this.selectedPhotoLocation = selectedPhotoLocation;
    }
}
