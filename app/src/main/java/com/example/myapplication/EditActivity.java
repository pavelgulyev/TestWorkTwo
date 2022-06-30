package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.Model.Writer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    EditText name,surname,Patronymic, number;
    Button back,save;
    static final int GALLERY_REQUEST = 1;
    int pos =-1;
    Writer writer;
    String path;
    ImageButton image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name=findViewById(R.id.Name);
        surname=findViewById(R.id.Surname);
        Patronymic=findViewById(R.id.Patronymic);
        back= findViewById(R.id.Back);
        save=findViewById(R.id.Save);
        image = findViewById(R.id.image);
        number=findViewById(R.id.Number);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            pos = arguments.getInt("position");
            if(arguments.containsKey("writer")) {
                writer = (Writer) arguments.getSerializable("writer");
                name.setText(writer.getName());
                surname.setText(writer.getSurname());
                Patronymic.setText(writer.getPatronymic());
                number.setText(writer.getNumber());
                try {
                    image.setImageBitmap(BitmapFactory.decodeStream(this.openFileInput(writer.getImage())));
                    path = writer.getImage();
                } catch (Exception ignored) {
                }
            }
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().equals("") && !Patronymic.getText().toString().equals("") &&
                        !surname.getText().toString().equals("") && !number.getText().toString().equals("")) {
                    Intent intent = new Intent();
                    writer = new Writer(name.getText().toString(), surname.getText().toString(),
                            Patronymic.getText().toString(), number.getText().toString(),path);
                    System.out.println(writer.toString());
                    intent.putExtra("writer", writer);
                    intent.putExtra("position", pos);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {

                }
            }
        });
    }
    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage"+pos;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    public void onAvatarClick(View view)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bitmap bitmap = null;
        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                path = createImageFromBitmap(bitmap);
                image.setImageBitmap(bitmap);
            }
        }
    }


    public void onSaveClick(View view) {
        if(!name.getText().toString().equals("") && !Patronymic.getText().toString().equals("") &&
                !surname.getText().toString().equals("") && !number.getText().toString().equals("")) {
            Intent intent = new Intent();
            writer = new Writer(name.getText().toString(), surname.getText().toString(),
                    Patronymic.getText().toString(), number.getText().toString(),path);
            intent.putExtra("writer", writer);
            intent.putExtra("position", pos);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Предупреждение!")
                    .setMessage("Вы не заполнили данные!")
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.create();
            builder.show();
        }
    }

    public void onBackClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}