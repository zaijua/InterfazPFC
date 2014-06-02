package es.ugr.template_ugr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import pfc.bd.EjercicioDataSource;
import pfc.bd.ObjetoDataSource;
import pfc.bd.SerieEjerciciosDataSource;
import pfc.obj.Ejercicio;
import pfc.obj.Objeto;
import pfc.obj.SerieEjercicios;

import com.ugr.template_ugr.R;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



/**
 * @author Namir Sayed-Ahmad Baraza
 * @mail namirsab@gmail.com
 *
 */
public class Ejercicios extends Activity {
	 private ListView listView;
	 private TableLayout tablaEjercicios;
	 private Context micontexto;
	 private Dialog dialogo;
	 private List<Ejercicio> le;
	 private EjercicioDataSource eds;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ejercicios);

		//borrar
	//	insertaDatos();
		
		
		listView=(ListView)findViewById(R.id.listViewEjer);
        tablaEjercicios=(TableLayout)findViewById(R.id.tabla_ejer);
        eds=new EjercicioDataSource(this);
        eds.open();
		CreaLista();
		CreaTablaEjer();
        //final Context micontexto=this;          

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		eds.close();
	}
	

	private void CreaLista(){
		String[] values = new String[] { "Menú Principal","Gestion Alumnos","Resultados/Estadísticas","Ejercicios"
        };
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		 android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		
		
		// Assign adapter to ListView
		 listView.setAdapter(adapter);
		 listView.setOnItemClickListener(new OnItemClickListener() {
			 

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
          int itemPosition     = arg2;
          
          // ListView Clicked item value
          String  itemValue    = (String) listView.getItemAtPosition(arg2);
             
           // Show Alert
          switch (itemPosition) {
		case 0:
			Intent MainIntent=new Intent(getApplicationContext(), MainActivity.class);
			startActivity(MainIntent);
			finish();
			break;
		case 1:
			
			Intent AlumnoIntent=new Intent(getApplicationContext(), GestionAlumnos.class);
			startActivity(AlumnoIntent);
			finish();
			break;
		case 2:
			Intent ResultadosIntent=new Intent(getApplicationContext(), Resultados.class);
			startActivity(ResultadosIntent);
			finish();
			
			break;
		case 3:
			Intent SeriesIntent=new Intent(getApplicationContext(), Ejercicios.class);
			startActivity(SeriesIntent);
			finish();
			break;

		default:
			break;
		}

	}

});
	}
		
	private void CreaTablaEjer(){

		tablaEjercicios.removeAllViews();
        TableRow row;
        ImageView f1;
        TextView t2,t3,tit1,tit2,tit3;
        le=eds.getAllEjercicios();
        //Cabecera
        
        row = new TableRow(this);
        
       
        row.setBackgroundColor(getResources().getColor(R.color.tabla2));
        row.setLayoutParams(new LayoutParams(
        LayoutParams.FILL_PARENT,
        LayoutParams.WRAP_CONTENT)); 
        
        tit1=new TextView(this);
        tit2=new TextView(this);
        tit3=new TextView(this);
        
   	 tit1.setText("");
   	 tit1.setPadding(2, 0, 5, 0);
   	 tit1.setTextColor(getResources().getColor(R.color.texto_tabla));
       
   	 tit2.setText("Nombre");
   	 tit2.setPadding(2, 0, 5, 0);
   	 tit2.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 
   	 tit3.setText("TºEstimado");
   	 tit3.setPadding(2, 0, 5, 0);
   	 tit3.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 

   	 
   	 row.addView(tit1);
   	 row.addView(tit2);
   	 row.addView(tit3);

   	 
   	tablaEjercicios.addView(row, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT)); 
        
        //Cada alumno
        for(int i=0;i<le.size();i++){
       	 row=new TableRow(this);
       	 row.setTag(i);
       	// row.setId(i);
       	 if(i%2==0)
       		 row.setBackgroundColor(getResources().getColor(R.color.tabla1));
       	 else
       		 row.setBackgroundColor(getResources().getColor(R.color.tabla2));
       	 
            row.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT)); 
            
       	 f1=new ImageView(this);
       	 f1.setImageResource(R.drawable.ic6);
       	 f1.setMaxHeight(10);
       	 f1.setMaxWidth(5);
       	 f1.setPadding(2, 0, 5, 0);
       	 
       	 //f1.setLayoutParams();
       	 
       	 t2=new TextView(this);
       	 t2.setText(le.get(i).getNombre());
       	 t2.setPadding(2, 0, 5, 0);
       	 t2.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 
       	 /*
       	 Calendar c = Calendar.getInstance();
       	 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss a");
       	 String strDate = sdf.format(c.getTime());*/
       	 
       	 t3=new TextView(this);
       	 t3.setText(String.valueOf(le.get(i).getDuracion()));
       	 t3.setPadding(2, 0, 5, 0);
       	 t3.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 

       	 micontexto=this;
       	 
       	 //Si pulsa un TableRow
       	 OnClickListener miclicklistener= new OnClickListener() {
       		
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//mivista=v;
					
					TableRow tr=(TableRow)v;
					MostrarDescripcion((Integer)tr.getTag());
					//CrearModificarSeriesEjercicios(true,lse.get((Integer)tr.getTag()),false);


				}
			};
       	 row.setOnClickListener(miclicklistener);
       	
       	//Se añade a la fila del tableRow los componentes 
       	 row.addView(f1);
       	 row.addView(t2);
       	 row.addView(t3);

       	tablaEjercicios.addView(row, new TableLayout.LayoutParams(
                   LayoutParams.FILL_PARENT,
                   LayoutParams.WRAP_CONTENT));
        }
       	
  
		
	}
	

	private void MostrarDescripcion(final int pos){
		
		//Obtener datos del table Row

		//final EjercicioDataSource eds=new EjercicioDataSource(micontexto);

		
		//Lanzar Dialog
		dialogo = new Dialog(micontexto);
		dialogo.setContentView(R.layout.dialogo_ejercicios);
		dialogo.setTitle("Detalles Ejercicio");
		
		//Modificar elementos dentro del dialogo
		final TextView nomEjer=(TextView)dialogo.findViewById(R.id.textEjer);
		nomEjer.setText(le.get(pos).getNombre());
		final EditText duracion=(EditText)dialogo.findViewById(R.id.DuracionEj);
		duracion.setText(String.valueOf(le.get(pos).getDuracion()));
		final TextView descripcion=(TextView)dialogo.findViewById(R.id.textDesc);
		descripcion.setText("En este ejercicio el alumno deberá escoger entre una serie de objetos y de forma ordenada, una lista de pelotas definida previamente.");
		//Table Layout dentro dialogo
		
		TableLayout tablaObjetos=(TableLayout)dialogo.findViewById(R.id.tablaDiaEjercicios);
		tablaObjetos.removeAllViews();


        TableRow filaObj;
        TextView te1,te2;
         
         //Cabecera
         
         filaObj = new TableRow(dialogo.getContext());
         final ObjetoDataSource ods=new ObjetoDataSource(dialogo.getContext());
         ods.open();
         
         Objeto obj=new Objeto();
         
         
    	//Para cada ejercicio
    	
    	for(int j=0;j<le.get(pos).getObjetos().size();j++){
        	 filaObj=new TableRow(dialogo.getContext());
        	// row.setId(i);
        	 
        	 te1=new TextView(dialogo.getContext());
        	 te1.setText(String.valueOf(j+1));
        	 te1.setPadding(2, 0, 5, 0);
        	 
        	 
        	 te2=new TextView(dialogo.getContext());
        	 obj=ods.getObjeto(le.get(pos).getObjetos().get(j));
        	 te2.setText(obj.getNombre());
        	 te2.setPadding(2, 0, 5, 0);
        	 
        	 filaObj.addView(te1);
        	 filaObj.addView(te2);
        	 
        	 tablaObjetos.addView(filaObj);
    	}
		
    	ods.close();
       		
		WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
		lp.copyFrom(dialogo.getWindow().getAttributes());
		lp.width=WindowManager.LayoutParams.MATCH_PARENT;
		lp.height=WindowManager.LayoutParams.MATCH_PARENT;
		
		//dialog.getWindow().setLayout(760, 480);
		dialogo.show();
		dialogo.getWindow().setAttributes(lp);	
    		

		
	
		
		
		//Boton guardar
		ImageButton guardarSerie=(ImageButton)dialogo.findViewById(R.id.guardarDiaEj);
		guardarSerie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				le.get(pos).setDuracion(Double.valueOf(duracion.getText().toString()));
				eds.modificaEjercicio(le.get(pos));
				Toast.makeText(getApplicationContext(),"Tiempo estimado modificado", Toast.LENGTH_LONG).show();
			}
		});
		


	}


}
