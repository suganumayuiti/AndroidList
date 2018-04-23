package com.example.suga.dbsamp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Viewlist();
        //DBの用意
        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        //text
        final EditText nameText = (EditText) findViewById(R.id.editName);
        final EditText ageText = (EditText) findViewById(R.id.editAge);

        //登録
        Button entryButton = (Button) findViewById(R.id.insert);
        entryButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();
                ContentValues insertValues = new ContentValues();
                insertValues.put("name", name);
                insertValues.put("age", age);
                long id = db.insert("person", name, insertValues);
                Viewlist();
            }
        });


        Button updateButton = (Button) findViewById(R.id.update);
        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(MainActivity.this, "名前を入力してください。",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues updateValues = new ContentValues();
                    updateValues.put("age", age);
                    db.update("person", updateValues, "name=?", new String[]{name});
                }
                Viewlist();
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(MainActivity.this, "名前を入力してください。",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.delete("person", "name=?", new String[] { name });
                }
                Viewlist();
            }
        });

        Button deleteAllButton = (Button) findViewById(R.id.deleteAll);
        deleteAllButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();
                db.delete("person", null, null);
                Viewlist();
            }
        });
        Button select= (Button) findViewById(R.id.selectbutton);
        select.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText selectname = (EditText) findViewById(R.id.editSelect);
                String name = selectname.getText().toString();
                SelectName(name);
            }
        });

        Button detaBaseButton = (Button) findViewById(R.id.dataBase);
        detaBaseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent(MainActivity.this,
                        ShowDataBase.class);
                startActivity(dbIntent);
            }
        });
    }
    protected void Viewlist(){
        ListView listview =(ListView)findViewById(R.id.listview1);
        MyOpenHelper helper = new MyOpenHelper(MainActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("person", new String[] { "name", "age" }, null,
                null, null, null, null);
        items = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                items.add(c.getString(0));
            } while (c.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView)super.getView(position, convertView, parent);
                view.setTextSize(10);
                return view;
            }
        };
        listview.setAdapter(adapter);
        c.close();
        db.close();
    }
    protected void SelectName(String _name) {
        ListView listview =(ListView)findViewById(R.id.listview1);


        MyOpenHelper helper = new MyOpenHelper(MainActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql    = "SELECT * FROM PERSON WHERE NAME ='" + _name +"'";
        Cursor c = db.rawQuery(sql ,null);
        items = new ArrayList<>();
        if (c.moveToFirst()) {
            do {

                items.add(String.format("%s : %d歳", c.getString(1), c.getInt(2)));
            } while (c.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, items);
        listview.setAdapter(adapter);

        c.close();
        db.close();
    }

}
