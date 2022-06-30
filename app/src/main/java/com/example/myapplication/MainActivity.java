package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.Model.Writer;
import com.example.myapplication.Model.WriterAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    WriterAdapter writerAdapter;
    ArrayList<Writer> writers=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String LOG_TAG = "myLogs";
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.countriesList);
        writerAdapter = new WriterAdapter(this,
                R.layout.list_item, writers);
        Intent intent = new Intent(this, EditActivity.class);

        listView.setAdapter(writerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Writer selected = (Writer) parent.getItemAtPosition(position);
                intent.putExtra("writer",  selected);
                intent.putExtra("position",position);
                launcher.launch(intent);

            }
        });

    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        Bundle arguments = intent.getExtras();
                        if(arguments!= null)
                        {
                            Writer person = (Writer) arguments.getSerializable("writer");
                            if(arguments.getInt("position")<writers.size())
                                writers.set(arguments.getInt("position"),person);
                            else
                                writers.add(person);
                            writerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
    // Обработка нажатия кнопки
    public void sendMessage(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("position",writers.size());
        launcher.launch(intent);
    }
}