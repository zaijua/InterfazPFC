package es.ugr.template_ugr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.ugr.template_ugr.R;

import android.app.ActionBar.LayoutParams;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import pfc.bd.*;
import pfc.obj.*;
import pfc.obj.TiposPropios.Sexo;

/**
 * @author Namir Sayed-Ahmad Baraza
 * @mail namirsab@gmail.com
 *
 */
public class GestionAlumnos extends Activity {
	 ListView listView ;
	 private DatePickerDialog Fecha;
	 private TextView mFecha;
	 private AlumnoDataSource ads;
	 private TableLayout tablaAlumnos;
	 private List<Alumno>ls;
	 private Sexo sexo;
	 private Context micontexto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gestion_alumnos);

        ads=new AlumnoDataSource(this);
        ads.open();
        
		//Crea la listView
		listView=(ListView)findViewById(R.id.listViewResul);
		CreaLista();
         
         
         
         //Fin List_View

         tablaAlumnos=(TableLayout)findViewById(R.id.tabla_alumnos);
         CreaTablaAlumnos();
        	
         
         micontexto=this;
        	//Si pulsa el botón de añadir, sacar Dialogo
        	
            ImageButton BotonAniadir=new ImageButton(this);
            BotonAniadir=(ImageButton)findViewById(R.id.aniadir_alumno);
            final Context micontexto=this;
            BotonAniadir.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Alumno nuevo=new Alumno();
					CrearModificarAlumnos(true, nuevo);

					//FinAniadir
				}
			});
			
        	 
	 
         
         
         

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ads.close();
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
	
	private void CreaTablaAlumnos(){
        TableRow row;
        ImageView f1,f2;
        TextView t3,t4,t5,tit1,tit2,tit3,tit4,tit5;
        
        tablaAlumnos.removeAllViews();

        
        //Inicia dataSource

        //Actualiza lista de alumnos         
        ls=ads.getAllAlumnos();
        
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
        tit5=new TextView(this);
        
   	 tit1.setText("Borrar");
   	 tit1.setPadding(2, 0, 5, 0);
   	 tit1.setTextColor(getResources().getColor(R.color.texto_tabla));
       
   	 tit2.setText("Sexo");
   	 tit2.setPadding(2, 0, 5, 0);
   	 tit2.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 
   	 tit3.setText("Nombre");
   	 tit3.setPadding(2, 0, 5, 0);
   	 tit3.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 
   	 tit4.setText("Apellidos");
   	 tit4.setPadding(2, 0, 5, 0);
   	 tit4.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 
   	 tit5.setText("Edad");
   	 tit5.setPadding(2, 0, 5, 0);
   	 tit5.setTextColor(getResources().getColor(R.color.texto_tabla));
   	 
   	 row.addView(tit1);
   	 row.addView(tit2);
   	 row.addView(tit3);
   	 row.addView(tit4);
   	 row.addView(tit5);
   	 
   	tablaAlumnos.addView(row, new TableLayout.LayoutParams(
               LayoutParams.FILL_PARENT,
               LayoutParams.WRAP_CONTENT)); 
        
        //Cada alumno
        //for(int i=0;i<nom.length;i++){
        for(int i=0;i<ls.size();i++){	 
       	 row=new TableRow(this);
       	 row.setTag(i);
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
       	 
       	 f2=new ImageView(this);
       	 switch (ls.get(i).getSexo()) {
		case Hombre:
			f2.setImageResource(R.drawable.boy);
			break;
		case Mujer:
			f2.setImageResource(R.drawable.girl);
			break;
		default:
			f2.setImageResource(R.drawable.varon);
			break;
		}
       	 
       	 f2.setPadding(2, 0, 5, 0);
       	 
       	 t3=new TextView(this);
       	 //t3.setText(nom[i]);
       	 t3.setText(ls.get(i).getNombre());
       	 t3.setPadding(2, 0, 5, 0);
       	 t3.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 
       	 
       	 t4=new TextView(this);
       	 t4.setText(ls.get(i).getApellidos());
       	 t4.setPadding(2, 0, 5, 0);
       	 t4.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 
       	 t5=new TextView(this);
       	 t5.setText(anios(ls.get(i).getFecha_nac_AsDate())+" Años");
       	 t5.setPadding(2, 0, 5, 0);
       	 t5.setTextColor(getResources().getColor(R.color.texto_tabla));
       	 final Context context=this;
       	 
       	 
       	 //Si pulsa la Papelera
       	 f1.setOnClickListener(new OnClickListener() {
		
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final TableRow trow=(TableRow)arg0.getParent();
					TextView tnom=(TextView)trow.getChildAt(2);
					TextView tape=(TextView)trow.getChildAt(3);
					AlertDialog.Builder alerta=new AlertDialog.Builder(context);
					alerta.setTitle("Eliminar");
					alerta.setMessage("Se eliminará el alumno: "+tnom.getText()+" "+tape.getText());
					alerta.setCancelable(false);
					alerta.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							boolean borra;
							borra=ads.borraAlumno(ls.get((Integer)trow.getTag()).getIdAlumno());
							if (borra==true){
							Toast.makeText(getApplicationContext(),"Borrado", Toast.LENGTH_LONG).show();
							CreaTablaAlumnos();
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
					TableRow trow=(TableRow)v;
					final Alumno modifica=ls.get((Integer)trow.getTag());
					CrearModificarAlumnos(false, modifica);
					
					
					
					
					//Obtener datos del table Row
				}
			};
       	 row.setOnClickListener(miclicklistener);
       	 
       	 
       	 row.addView(f1);
       	 row.addView(f2);
       	 row.addView(t3);
       	 row.addView(t4);
       	 row.addView(t5);
       	tablaAlumnos.addView(row, new TableLayout.LayoutParams(
                   LayoutParams.FILL_PARENT,
                   LayoutParams.WRAP_CONTENT));
        }
	}
	
		
	private class PickDate implements DatePickerDialog.OnDateSetListener {


		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			 view.updateDate(year, monthOfYear, dayOfMonth);
			 int mes=monthOfYear+1;
		        mFecha.setText(dayOfMonth+"/"+mes+"/"+year);
		        Fecha.hide();
		}
	    
	}
	
	
	
	private void CrearModificarAlumnos(final boolean insertar, final Alumno alumno){
	
		//Lanzar Dialog
		final Dialog dialog = new Dialog(micontexto);
		dialog.setContentView(R.layout.dialogo_alumnos);
		if (insertar==false)
			dialog.setTitle("Modificar Alumno");
		else
			dialog.setTitle("Crear Alumno");
		
		//Captacion de los componentes
		final Button GuardarDia, CancelarDialog;
		final EditText enombre;
		final EditText eapellidos;
		final EditText eobservaciones;
		final ImageButton chico,chica;
		TextView efecha;
		
		//Componentes del layout
		enombre=(EditText)dialog.findViewById(R.id.daNombre);
		eapellidos=(EditText)dialog.findViewById(R.id.daApellidos);
		efecha=(TextView)dialog.findViewById(R.id.MuestraFecha);
		eobservaciones=(EditText)dialog.findViewById(R.id.daObserva);					
		chico=(ImageButton)dialog.findViewById(R.id.BotonHombre);
		chica=(ImageButton)dialog.findViewById(R.id.BotonMujer);
		GuardarDia=(Button)dialog.findViewById(R.id.gAlumnos);
		CancelarDialog=(Button)dialog.findViewById(R.id.cAlumnos);
		
		
		//Asignacion de los valores del tipo alumno
		enombre.setText(alumno.getNombre());
		eapellidos.setText(alumno.getApellidos());
		efecha.setText(alumno.getFecha_nac_AsStrign());
		eobservaciones.setText(alumno.getObservaciones());
		switch (alumno.getSexo()) {
		case Hombre:
			chico.setBackgroundColor(getResources().getColor(R.color.tabla1));
			chica.setBackgroundColor(getResources().getColor(R.color.white));
			sexo=Sexo.Hombre;
			break;
		case Mujer:
			chica.setBackgroundColor(getResources().getColor(R.color.tabla1));
			chico.setBackgroundColor(getResources().getColor(R.color.white));
			sexo=Sexo.Mujer;
			break;

		default:
			chico.setBackgroundColor(getResources().getColor(R.color.white));
			chica.setBackgroundColor(getResources().getColor(R.color.white));
			sexo=Sexo.NoDef;
			break;
		}

		

		
		//Controlador Fecha
		Button cFecha;
		
		
		cFecha=(Button)dialog.findViewById(R.id.CambiarFecha);
		mFecha=(TextView)dialog.findViewById(R.id.MuestraFecha);
		Fecha=null;
		cFecha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar dtTxt=null;
				String preExistingDate=(String)mFecha.getText().toString();
				 if(preExistingDate != null && !preExistingDate.equals("")){
		              StringTokenizer st = new StringTokenizer(preExistingDate,"/");
		                  String initialDate = st.nextToken();
		                  String initialMonth = st.nextToken();
		                  String initialYear = st.nextToken();
		                  if(Fecha == null)
		                  Fecha = new DatePickerDialog(arg0.getContext(),
		                                   new PickDate(),Integer.parseInt(initialYear),
		                                   Integer.parseInt(initialMonth)-1,
		                                   Integer.parseInt(initialDate));
		                  Fecha.updateDate(Integer.parseInt(initialYear),
		                                   Integer.parseInt(initialMonth)-1,
		                                   Integer.parseInt(initialDate));
				 } else {
		              dtTxt = Calendar.getInstance();
		              if(Fecha == null)
		              Fecha = new DatePickerDialog(arg0.getContext(),new PickDate(),dtTxt.get(Calendar.YEAR),dtTxt.get(Calendar.MONTH),
		                                                  dtTxt.get(Calendar.DAY_OF_MONTH));
		              Fecha.updateDate(dtTxt.get(Calendar.YEAR),dtTxt.get(Calendar.MONTH),
                              dtTxt.get(Calendar.DAY_OF_MONTH));
		          }
				 Fecha.show();
			}
		});
		
		//FechaF
		
		WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width=WindowManager.LayoutParams.MATCH_PARENT;
		lp.height=WindowManager.LayoutParams.MATCH_PARENT;
		
		//dialog.getWindow().setLayout(760, 480);
		dialog.show();
		dialog.getWindow().setAttributes(lp);

		GuardarDia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Guardar los cambios, primero en un Alumno, y luego en BD
				//alumno.setIdAlumno(modifica.getIdAlumno());
				alumno.setNombre(enombre.getText().toString());
				alumno.setApellidos(eapellidos.getText().toString());
				alumno.setObservaciones(eobservaciones.getText().toString());
				alumno.setSexo(sexo);
				SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
				Date fecha = null;
				try {

				fecha = formatoDelTexto.parse(mFecha.getText().toString());

				} catch (ParseException ex) {

				ex.printStackTrace();

				}
				alumno.setFecha_nac(fecha);
				
				
				//Si es modificar
				boolean correcto=false;
				if (insertar==false){
				correcto=ads.modificaAlumno(alumno);
				if (correcto==true)
					 Toast.makeText(getApplicationContext(),
			                 "Alumno modificado" , Toast.LENGTH_SHORT)
			                 .show();
				}else{
					//Si es insertar
					correcto=ads.createAlumno(alumno) != null;
				if (correcto==true)
						 Toast.makeText(getApplicationContext(),
				                 "Alumno creado" , Toast.LENGTH_SHORT)
				                 .show();
				}
				dialog.dismiss();
				CreaTablaAlumnos();
			}
		});
		
		CancelarDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  dialog.dismiss();
			}
		});
		//listener Botones e imagen Sexo
		
		chico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				chico.setBackgroundColor(getResources().getColor(R.color.tabla1));
				chica.setBackgroundColor(getResources().getColor(R.color.white));
				sexo=Sexo.Hombre;
				
			}
		});
		
		chica.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chica.setBackgroundColor(getResources().getColor(R.color.tabla1));
				chico.setBackgroundColor(getResources().getColor(R.color.white));
				sexo=Sexo.Mujer;
				
			}
		});
		
		//Toast.makeText(getApplicationContext(),str1, Toast.LENGTH_LONG).show();

		
	}
		
	private int anios(Date d) {
		
	    Calendar dob = Calendar.getInstance();
	    dob.setTime(d);
	    Calendar today = Calendar.getInstance();
 
	    int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

	    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
	        age--; 
	    }

	    return age;  
	}


}
