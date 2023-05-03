package mateus.kaua.applista.activity.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import mateus.kaua.applista.R;
import mateus.kaua.applista.activity.model.MainActivityViewModel;
import mateus.kaua.applista.activity.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;
    /*Uri photoSelected = null;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm =  new ViewModelProvider(this).get(NewItemActivityViewModel.class);
        Uri selectPhotoLocation = vm.getSelectedPhotoLocation();
        if (selectPhotoLocation != null){
            ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
            imvPhotoPreview.setImageURI(selectPhotoLocation);
        }
        ImageButton ImgCI = findViewById(R.id.imbCI);
        ImgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,PHOTO_PICKER_REQUEST);
            }
        });
        /*Passo 9 - Transmissão de Dados entre Activities na Forma de Retorno*/
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewItemActivityViewModel vm = new ViewModelProvider(NewItemActivity.this).get(NewItemActivityViewModel.class);
                Uri selectPhotoLocation = vm.getSelectedPhotoLocation();
                if (selectPhotoLocation==null){
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if (title.isEmpty()){
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um titulo", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent();
                i.setData(selectPhotoLocation);
                i.putExtra("title",title);
                i.putExtra("description",description);
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });
    }

    /*Passo 8 - Como Escolher e Obter uma Imagem da Galeria do Celular*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PHOTO_PICKER_REQUEST){
            if(resultCode== Activity.RESULT_OK){
                Uri photoSelected = data.getData();
                ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
                imvPhotoPreview.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                vm.setSelectedPhotoLocation(photoSelected);
            }
        }
    }
}