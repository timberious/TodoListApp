package com.example.todolistapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.*
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var todoListItems = ArrayList<String>()
    var listAdapter: ArrayAdapter<String>? = null
    var listView: ListView? = null



    val FILE_NAME = "ArchivoTodoList.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView =findViewById(R.id.todoList)

        populateListView()
        
    }

    fun populateListView()
    {
        val archivos = readList()
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,archivos)
        listView?.adapter = adapter
        listView!!.onItemLongClickListener = AdapterView.OnItemLongClickListener{ parent, view, position, id ->
            archivos!!.removeAt(position)
            adapter!!.notifyDataSetChanged()
            true
        }

    }

    fun agregar(view: View){

        var entries = findViewById<EditText>(R.id.tareaNuevaEditText).text.toString()


        Log.i("CLASE","guardando la tarea $entries")
        val output = PrintStream(openFileOutput(FILE_NAME, Context.MODE_APPEND))
        output.println(entries)
        output.close()
        populateListView()

        var editable = findViewById<EditText>(R.id.tareaNuevaEditText)
        editable.text.clear()



    }



    fun readList(): ArrayList<String>{
        val tareaArealizar = ArrayList<String>()

        try {
            val scan = Scanner(openFileInput(FILE_NAME))
            while(scan.hasNextLine()){
                val row = scan.nextLine()
                tareaArealizar.add(row)
            }
            scan.close()
        }
        catch(e: Exception){
            Log.e("CLASE","Error en el archivo",e)
        }

        Log.i("CLASE","las notas del archivo son: $tareaArealizar")
        return tareaArealizar
    }

}
