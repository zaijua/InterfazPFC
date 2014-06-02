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
public class SeriesEjercicios extends Activity {
	 private ListView listView;
	 private TableLayout tablaSeries;
	 private SerieEjerciciosDataSource seds;
	 private List<SerieEjercicios> lse;
	 private TableLayout tablaEjercicios;
	 private Context micontexto;
	 private Dialog dialogo;
	 private List<Ejercicio> le;
	 private ImageButton añadirSerie;
	 private EjercicioDataSource eds;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ejercicios);

		//borrar
	//	insertaDatos();
		
		
		listView=(ListView)findViewById(R.id.listViewSeries);
        tablaSeries=(TableLayout)findViewById(R.id.tabla_series);
        seds=new SerieEjerciciosDataSource(this);
        añadirSerie=(ImageButton)findViewById(R.id.aniadir_serie);
        seds.open();
        eds=new EjercicioDataSource(this);
        eds.open();
		CreaLista();
		CreaTablaSeries();
        //final Context micontexto=this;          

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		eds.close();
		seds.close();
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
			Intent SeriesIntent=new Intent(getApplicationContext(), SeriesEjercicios.class);
			startActivity(SeriesIntent);
			finish();
			break;

		default:
			break;
		}

	}

});
	}
		
	private void CreaTablaSeries(){

		tablaSeries.removeAllViews();
        TableRow row;
        ImageView f1;
        TextView t2,t3,t4,tit1,tit2,tit3,tit4;
        lse=seds.getAllSeriesEjercicios();
        //Cabecera
        
        row = new TableRow(this);
        
       
        row.setBackgroundColor(getResources().getColor(R.color.tabla2));
        row.setLayoutParams(new LayoutParams(
        LayoutParams.FILL_PARENT,
        LayoutParams.WRAP_CONTENT)); 
        
        tit1=new TextView(this);
        tit2=new TextView(this);
        tit3=new TextView(this);
        tit4=new TextView(this);
        
   	 tit1.setText("Borrar");
   	 tit1.setPadding(2, 0, 5, 0);
   	 tit1.setTextColor(getResources().getColor(R.color.texto_tabla));
       
   	 tit2.setText("Serie");
   	 tit2.setPadding(2, 0, 5, 0);
   	 tit2.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 
   	 tit3.setText("TºEstimado");
   	 tit3.setPadding(2, 0, 5, 0);
   	 tit3.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 
   	 tit4.setText("Última modificación");
   	 tit4.setPadding(2, 0, 5, 0);
   	 tit4.setTextColor(getResources().getColor(R.color.texto_tabla));

   	 
   	 row.addView(tit1);
   	 row.addView(tit2);
   	 row.addView(tit3);
   	 row.addView(tit4);

   	 
   	tablaSeries.addView(row, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT)); 
        
        //Cada alumno
        for(int i=0;i<lse.size();i++){
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
       	 f1.setImageResource(R.drawable.borrar2);
       	 f1.setPadding(2, 0, 5, 0);
       	 
       	 //f1.setLayoutParams();
       	 
       	 t2=new TextView(this);
       	 t2.setText(lse.get(i).getNombre());
       	 t2.setPadding(2, 0, 5, 0);
       	 t2.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 
       	 /*
       	 Calendar c = Calendar.getInstance();
       	 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss a");
       	 String strDate = sdf.format(c.getTime());*/
       	 
       	 t3=new TextView(this);
       	 t3.setText(String.valueOf(lse.get(i).getDuracion()));
       	 t3.setPadding(2, 0, 5, 0);
       	 t3.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 
       	 
       	 t4=new TextView(this);
       	 t4.setText(lse.get(i).getFecha_modificacion_AsStrign());
       	 t4.setPadding(2, 0, 5, 0);
       	 t4.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 micontexto=this;
       	 
       	 
       	 //Si pulsa la Papelera
       	 f1.setOnClickListener(new OnClickListener() {
		
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final TableRow trow=(TableRow)arg0.getParent();
					TextView tnom=(TextView)trow.getChildAt(1);
					
					AlertDialog.Builder alerta=new AlertDialog.Builder(micontexto);
					alerta.setTitle("Eliminar");
					alerta.setMessage("Se eliminará la serie: "+tnom.getText());
					alerta.setCancelable(false);
					alerta.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							boolean borrado=false;
							borrado=seds.eliminarSerieEjercicios(lse.get((Integer)trow.getTag()).getIdSerie());
							if(borrado==true){
							 Toast.makeText(getApplicationContext(),"Borrado", Toast.LENGTH_LONG).show();
							 CreaTablaSeries();
							}
							 
						}
					});
					
					
					alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					
					alerta.show();
				}
			});
       	 
       	 
       
       	 //Si pulsa un TableRow
       	 OnClickListener miclicklistener= new OnClickListener() {
       		
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//mivista=v;
					TableRow tr=(TableRow)v;
					lse=seds.getAllSeriesEjercicios();
					
					
					//arrayEjer=lse.get((Integer)tr.getTag()).getEjercicios();
					
					CrearModificarSeriesEjercicios(true,lse.get((Integer)tr.getTag()),false);

					//Toast.makeText(getApplicationContext(),str1, Toast.LENGTH_LONG).show();
				}
			};
       	 row.setOnClickListener(miclicklistener);
       	
       	//Se añade a la fila del tableRow los componentes 
       	 row.addView(f1);
       	 row.addView(t2);
       	 row.addView(t3);
       	 row.addView(t4);

       	tablaSeries.addView(row, new TableLayout.LayoutParams(
                   LayoutParams.FILL_PARENT,
                   LayoutParams.WRAP_CONTENT));
        }
       	
       	//Si pulsa el botón de añadir, sacar Dialogo
       	añadirSerie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SerieEjercicios nuevaSerie=new SerieEjercicios();
				CrearModificarSeriesEjercicios(true, nuevaSerie,true);
			}
		});


		
		
	}
	
	/*
	
	private void insertaDatos(){
		
		//Crear objetos
		ObjetoDataSource obs=new ObjetoDataSource(this);
		obs.open();
		obs.createObjeto("Pelota tenis", "", "",0,0);
		obs.createObjeto("Pelota beisbol", "", "",0,0);
		obs.createObjeto("Teléfono", "", "",0,0);
		obs.createObjeto("Bolígrafo", "", "",0,0);
		obs.createObjeto("Rotulador", "", "",0,0);
		obs.createObjeto("Estuche", "", "",0,0);
		obs.createObjeto("Lápiz", "", "",0,0);
		obs.createObjeto("Vaso", "", "",0,0);
		obs.createObjeto("Plato", "", "",0,0);
		obs.close();
		
		//Crear Ejercicios
		
		
		EjercicioDataSource eds2=new EjercicioDataSource(this);
		eds2.open();
		ArrayList<Integer> lista=new ArrayList<Integer>();
		lista.add(1);
		lista.add(2);
		Ejercicio p1=eds2.createEjercicio("Pelota tenis y béisbol", lista);
		lista.clear();
		lista.add(1);
		lista.add(3);
		Ejercicio p2=eds2.createEjercicio("Pelota y teléfono", lista);
		lista.clear();
		lista.add(4);
		lista.add(5);
		eds2.createEjercicio("Bolígrafo y rotulador", lista);
		lista.clear();
		lista.add(6);
		lista.add(7);
		eds2.createEjercicio("Estuche y lápiz", lista);
		lista.clear();
		lista.add(8);
		lista.add(9);
		eds2.createEjercicio("Vaso y plato", lista);
		lista.clear();
		
		
		//Crear Serie
		SerieEjerciciosDataSource ser=new SerieEjerciciosDataSource(this);
		ser.open();
		ArrayList<Integer> miarray=new ArrayList<Integer>();
		miarray.add(p1.getIdEjercicio());
		miarray.add(p2.getIdEjercicio());
		ser.createSerieEjercicios("PELOTAS", miarray);
		ser.close();
		
		

		
		
		
	}*/

	
	
	
	private void CrearModificarSeriesEjercicios(boolean mostrar, final SerieEjercicios serie, final boolean insertar){
		
		//Obtener datos del table Row

		//final EjercicioDataSource eds=new EjercicioDataSource(micontexto);

		
		//Lanzar Dialog
		if (mostrar==true){
		dialogo = new Dialog(micontexto);
		dialogo.setContentView(R.layout.dialogo_series);
		if (insertar==true)
			dialogo.setTitle("Insertar Serie");
		else
			dialogo.setTitle("Modificar Serie");
		}
		//Modificar elementos dentro del dialogo
		final EditText nomSerie=(EditText)dialogo.findViewById(R.id.NomSerie);
		nomSerie.setText(serie.getNombre());
		final EditText duracion=(EditText)dialogo.findViewById(R.id.Duracion);
		duracion.setText(String.valueOf(serie.getDuracion()));
		//Table Layout dentro dialogo
		
        tablaEjercicios=(TableLayout)dialogo.findViewById(R.id.tablaEjerciciosSerie);
        tablaEjercicios.removeAllViews();

        TableRow filaEjer;
        ImageView fe1;
        TextView te2,te3,tite1,tite2,tite3;
         
         //Cabecera
         
         filaEjer = new TableRow(dialogo.getContext());
         
        
         filaEjer.setBackgroundColor(getResources().getColor(R.color.tabla2));
         filaEjer.setLayoutParams(new LayoutParams(
         LayoutParams.FILL_PARENT,
         LayoutParams.WRAP_CONTENT)); 
         
         tite1=new TextView(dialogo.getContext());
         tite2=new TextView(dialogo.getContext());
         tite3=new TextView(dialogo.getContext());
         
    	 tite1.setText("Borrar");
    	 tite1.setPadding(2, 0, 5, 0);
    	 tite1.setTextColor(getResources().getColor(R.color.texto_tabla));
        
    	 tite2.setText("Orden");
    	 tite2.setPadding(2, 0, 5, 0);
    	 tite2.setTextColor(getResources().getColor(R.color.texto_tabla));
    	 
    	 tite3.setText("Ejercicio");
    	 tite3.setPadding(2, 0, 5, 0);
    	 tite3.setTextColor(getResources().getColor(R.color.texto_tabla));

    	 
    	 filaEjer.addView(tite1);
    	 filaEjer.addView(tite2);
    	 filaEjer.addView(tite3);

    	 
    	tablaEjercicios.addView(filaEjer, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT)); 
    	
		
    	//Para cada ejercicio
    	
    	for(int j=0;j<serie.getEjercicios().size();j++){
        	 filaEjer=new TableRow(dialogo.getContext());
        	 filaEjer.setTag(j);
        	// row.setId(i);
        	 if(j%2==0)
        		 filaEjer.setBackgroundColor(getResources().getColor(R.color.tabla1));
        	 else
        		 filaEjer.setBackgroundColor(getResources().getColor(R.color.tabla2));
        	 
             filaEjer.setLayoutParams(new LayoutParams(
                     LayoutParams.FILL_PARENT,
                     LayoutParams.WRAP_CONTENT)); 
             
        	 fe1=new ImageView(dialogo.getContext());
        	 fe1.setImageResource(R.drawable.borrar2);
        	 fe1.setPadding(2, 0, 5, 0);
        	 
        	 //Si se pulsa la papelera
        	 fe1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final TableRow trow=(TableRow)v.getParent();
					TextView tnom=(TextView)trow.getChildAt(2);
					AlertDialog.Builder alerta=new AlertDialog.Builder(trow.getContext());
					alerta.setTitle("Eliminar");
					alerta.setMessage("Se eliminará el ejercicio: "+tnom.getText().toString());
					alerta.setCancelable(false);
					alerta.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							serie.setNombre(nomSerie.getText().toString());
							serie.setDuracion(Double.parseDouble(duracion.getText().toString()));
							int pos=(Integer)trow.getTag();
							serie.getEjercicios().remove(pos);
							CrearModificarSeriesEjercicios(false,serie,insertar);
							
				               Toast.makeText(getApplicationContext(),
					  	                 "Boorado"+ (Integer)trow.getTag(), Toast.LENGTH_LONG)
					  	                 .show();
						}
					});

					
					alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					
					alerta.show();
				}
			});
        	 
        	 
        	 te2=new TextView(dialogo.getContext());
        	 te2.setText(String.valueOf(j+1));
        	 te2.setPadding(2, 0, 5, 0);
        	 te2.setTextColor(getResources().getColor(R.color.texto_tabla));
        	 
        	 
        	 te3=new TextView(dialogo.getContext());
        	 //eds.open();
        	 Ejercicio ej=eds.getEjercicios(serie.getEjercicios().get(j));
        	 te3.setText(ej.getNombre());
        	 te3.setPadding(2, 0, 5, 0);
        	 te3.setTextColor(getResources().getColor(R.color.texto_tabla));
        	 
        	 
        	 filaEjer.addView(fe1);
        	 filaEjer.addView(te2);
        	 filaEjer.addView(te3);

        	tablaEjercicios.addView(filaEjer, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
    	}
		
    	if (mostrar==true){
    		
    		
    		
		WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
		lp.copyFrom(dialogo.getWindow().getAttributes());
		lp.width=WindowManager.LayoutParams.MATCH_PARENT;
		lp.height=WindowManager.LayoutParams.MATCH_PARENT;
		
		//dialog.getWindow().setLayout(760, 480);
		dialogo.show();
		dialogo.getWindow().setAttributes(lp);	
    		
    	}
		
		//Boton aniadir
		ImageButton aniadirEjSer=(ImageButton)dialogo.findViewById(R.id.aniadirEjer);
		aniadirEjSer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				   le=eds.getAllEjercicios();
	               Toast.makeText(getApplicationContext(),
	  	                 "PopUp" , Toast.LENGTH_LONG)
	  	                 .show();
	               AlertDialog.Builder diaEjer= new AlertDialog.Builder(dialogo.getContext());
	               List<String> nombres=creaLista(le);
	               final CharSequence[] items=nombres.toArray(new String[nombres.size()]);
	               diaEjer.setTitle("Seleccione Ejercicio").setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						serie.setNombre(nomSerie.getText().toString());
						serie.setDuracion(Double.parseDouble(duracion.getText().toString()));
						serie.getEjercicios().add(le.get(which).getIdEjercicio());	
						//dialogo.dismiss();
						CrearModificarSeriesEjercicios(false,serie,insertar);
					}
				});
	            //diaEjer.create();
	               diaEjer.show();
			}
		});
		//Boton guardar
		ImageButton guardarSerie=(ImageButton)dialogo.findViewById(R.id.guardarSerie);
		guardarSerie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				serie.setNombre(nomSerie.getText().toString());
				serie.setDuracion(Double.parseDouble(duracion.getText().toString()));
				serie.setFecha_modificacion(new Date());
				boolean mo=false;
				if (insertar==false)
					mo=seds.modificaSerieEjercicios(serie);
				else
					mo=seds.createSerieEjercicios(serie) != null;
				if(mo){
					dialogo.dismiss();
					//Como se ha modificado la base de datos, volvemos ha acer una carga de la lista de series de ejercicios
					CreaTablaSeries();
					Toast.makeText(getApplicationContext(),
		  	                 "Modificado" , Toast.LENGTH_LONG)
		  	                 .show();
				}

			}
		});
		

		
	}

	private List<String> creaLista(List<Ejercicio> le){
		List<String> dev=new ArrayList<String>();
		for(int i=0;i<le.size();i++)
			dev.add(le.get(i).getNombre());		
		return dev;
	}
}
