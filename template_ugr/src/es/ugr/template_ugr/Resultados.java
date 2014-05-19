package es.ugr.template_ugr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.ugr.template_ugr.R;

import android.R.bool;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Layout;
import android.text.style.BulletSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
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
public class Resultados extends Activity {
	private ListView listView ;
	private Button semana,mes,año,mGrafica;
	private int fecha=1;
	private List<Boolean> alSelec,serSelec;
	private TableLayout tl;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultados);
		InicioResultados();
		//InsertaResultado();

	}

	private void InicioResultados(){

		
		
		listView=(ListView)findViewById(R.id.listViewResul);
		CreaLista();
		
		alSelec=new ArrayList<Boolean>();
		serSelec=new ArrayList<Boolean>();
		
		//Captación de los botones

		semana=new Button(this);
		semana=(Button)findViewById(R.id.filSemana);
		
		mes=new Button(this);
		mes=(Button)findViewById(R.id.filMes);
		
		año=new Button(this);
		año=(Button)findViewById(R.id.filAnio);
		
		semana.setBackgroundColor(getResources().getColor(R.color.tabla1));
		mes.setBackgroundColor(getResources().getColor(R.color.white));
		año.setBackgroundColor(getResources().getColor(R.color.white));
		
		mGrafica=(Button)findViewById(R.id.ResulGrafica);
		
		semana.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fecha=1;
				semana.setBackgroundColor(getResources().getColor(R.color.tabla1));
				mes.setBackgroundColor(getResources().getColor(R.color.white));
				año.setBackgroundColor(getResources().getColor(R.color.white));
			}
		});
		
		
		mes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fecha=2;
				semana.setBackgroundColor(getResources().getColor(R.color.white));
				mes.setBackgroundColor(getResources().getColor(R.color.tabla1));
				año.setBackgroundColor(getResources().getColor(R.color.white));
			}
		});
		
		año.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fecha=3;
				semana.setBackgroundColor(getResources().getColor(R.color.white));
				mes.setBackgroundColor(getResources().getColor(R.color.white));
				año.setBackgroundColor(getResources().getColor(R.color.tabla1));
			}
		});
		tl=(TableLayout)findViewById(R.id.ResulTabla);
		TableRow row=new TableRow(this);
		TextView tit1,tit2,tit3,tit4;
        tit1=new TextView(this);
        tit2=new TextView(this);
        tit3=new TextView(this);
        tit4=new TextView(this);
        
      	 tit1.setText("Alumno");
       	 tit1.setPadding(2, 0, 5, 0);
       	 
       	 tit2.setText("");
       	 tit2.setPadding(2, 0, 5, 0);
       	 
       	 tit3.setText("Serie");
       	 tit3.setPadding(2, 0, 5, 0);
       	 
       	 tit4.setText("");
       	 tit4.setPadding(2, 0, 5, 0);
       	 
		row.addView(tit1);
		row.addView(tit2);
		row.addView(tit3);
		row.addView(tit4);
		
		tl.addView(row);
		

		
		
		AlumnoDataSource ads=new AlumnoDataSource(this);
		SerieEjerciciosDataSource seds=new SerieEjerciciosDataSource(this);
		ads.open();
		seds.open();
		final List<Alumno> la=ads.getAllAlumnos();
		final List<SerieEjercicios> lse=seds.getAllSeriesEjercicios();
		ads.close();
		seds.close();
		
		
		//Añadir campo todos
		row=new TableRow(this);
		row.setBackgroundColor(getResources().getColor(R.color.tabla_resaltado));
		CheckBox cbTodosAlumnos=new CheckBox(this);
		TextView tvTodosAlumnos=new TextView(this);
		CheckBox cbTodasSeries=new CheckBox(this);
		TextView tvTodasSeries=new TextView(this);
		
		row.addView(cbTodosAlumnos);
		tvTodosAlumnos.setText("Todos los alumnos");
		row.addView(tvTodosAlumnos);
		row.addView(cbTodasSeries);
		tvTodasSeries.setText("Todas las Series");
		row.addView(tvTodasSeries);
		
		cbTodosAlumnos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox micb=(CheckBox)v;
				if (micb.isChecked())
					for(int i=0;i<la.size();i++){
						 CheckBox cb = (CheckBox)((TableRow)tl.getChildAt(i+2)).getChildAt(0);
						 cb.setChecked(true);
						 cb.setEnabled(false);
						 alSelec.set(i, true);
					}
				else
					for(int i=0;i<la.size();i++){
						 CheckBox cb = (CheckBox)((TableRow)tl.getChildAt(i+2)).getChildAt(0);
						 cb.setChecked(false);
						 cb.setEnabled(true);
						 alSelec.set(i, false);
					}
			}
		});
		
		cbTodasSeries.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox micb=(CheckBox)v;
				if (micb.isChecked())
					for(int i=0;i<lse.size();i++){
						 CheckBox cb = (CheckBox)((TableRow)tl.getChildAt(i+2)).getChildAt(2);
						 cb.setChecked(true);
						 cb.setEnabled(false);
						 serSelec.set(i, true);
					}
				else
					for(int i=0;i<lse.size();i++){
						 CheckBox cb = (CheckBox)((TableRow)tl.getChildAt(i+2)).getChildAt(2);
						 cb.setChecked(false);
						 cb.setEnabled(true);
						 serSelec.set(i, false);
					}
			}
		});
		
		
		tl.addView(row);
		
		int maximo=0;
		maximo=Math.max(la.size(), lse.size());
		
		for(int i=0;i<maximo;i++){
			 row=new TableRow(this);
			 row.setTag(i);
	       	 if(i%2==0)
	       		 row.setBackgroundColor(getResources().getColor(R.color.tabla1));
	       	 else
	       		 row.setBackgroundColor(getResources().getColor(R.color.tabla2));
	       	 
	            row.setLayoutParams(new LayoutParams(
	                    LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT)); 
	            
	         TextView alum=new TextView(this);
	         CheckBox cb2=new CheckBox(this);
	         TextView serie=new TextView(this);
	         
	         if(i<la.size()){
		         CheckBox cb=new CheckBox(this);	
	        	 alum.setText(la.get(i).getNombre()+" "+la.get(i).getApellidos());
	        	 row.addView(cb);
		         row.addView(alum);
		         alSelec.add(false);
		         final int pos=i;
		         cb.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						CheckBox aux=(CheckBox)v;
						alSelec.set(pos, aux.isChecked());
					}
				});
	         }
	         else{
	        	 TextView vacio=new TextView(this);
	        	 row.addView(vacio);
		         row.addView(alum);	        	  
	         }
	         if(i<lse.size()){
	        	 serie.setText(lse.get(i).getNombre());
		         row.addView(cb2);
	        	 row.addView(serie);
	        	 serSelec.add(false);
	        	 final int pos2=i;
		         cb2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						CheckBox aux=(CheckBox)v;
						serSelec.set(pos2, aux.isChecked());
					}
				});
	         }
	         else{
	        	 TextView vacio=new TextView(this);
	        	 row.addView(vacio);
		         row.addView(serie);	  
	         }

	         tl.addView(row);
		}
		
		mGrafica.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent graficaIntent=new Intent(getApplicationContext(), Graficas.class);

				
				//RadioButton
				RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);		
				int radioButtonID = rg.getCheckedRadioButtonId();
				View radioButton = rg.findViewById(radioButtonID);
				int radioSelec = rg.indexOfChild(radioButton);
				
				
				
				graficaIntent.putExtra("tipoFecha", fecha);
				//Lista de alumnos seleccionados
				List<Integer> listaIdAlumnos=new ArrayList<Integer>();
				for(int i=0;i<alSelec.size();i++){
					if(alSelec.get(i)==true)
						listaIdAlumnos.add(la.get(i).getIdAlumno());
				}
				
				List<Integer> listaIdSeries=new ArrayList<Integer>();
				for(int i=0;i<serSelec.size();i++){
					if(serSelec.get(i)==true)
						listaIdSeries.add(lse.get(i).getIdSerie());
				}

				
				
				graficaIntent.putExtra("tipoGrafica", radioSelec);
				graficaIntent.putIntegerArrayListExtra("listaAlumnos", (ArrayList<Integer>) listaIdAlumnos);
				graficaIntent.putIntegerArrayListExtra("listaSeries", (ArrayList<Integer>) listaIdSeries);
				startActivity(graficaIntent);

			}
		});
		
		
		
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
	
	private void InsertaResultado(){
		
		ResultadoDataSource rds=new ResultadoDataSource(this);
		rds.open();
		 Calendar cal = Calendar.getInstance();
		 cal.set(Calendar.DAY_OF_MONTH,14);
		 cal.set(Calendar.MONTH,4);
		 cal.set(Calendar.YEAR,2014);

		 Date d = cal.getTime();
		 
		 cal = Calendar.getInstance();
		 cal.set(Calendar.DAY_OF_MONTH,15);
		 cal.set(Calendar.MONTH,4);
		 cal.set(Calendar.YEAR,2014);


		 Date d2 = cal.getTime();
		 
		 
		 cal = Calendar.getInstance();
		 cal.set(Calendar.DAY_OF_MONTH,13);
		 cal.set(Calendar.MONTH,4);
		 cal.set(Calendar.YEAR,2014);

		 Date d3 = cal.getTime();
		 
		
		Resultado r=new Resultado(1, 1, 1, d, 0, 30, 18, 12, 7.2);	
		Resultado r2=new Resultado(2, 1, 1, d2, 0, 30, 18, 12, 8.9);
		Resultado r3=new Resultado(3, 2, 1, d3, 0, 30, 18, 12, 6.1);
		Resultado r4=new Resultado(4, 2, 1, d2, 0, 30, 18, 12, 5.4);
		
		rds.createResultado(r);
		rds.createResultado(r2);
		rds.createResultado(r3);
		rds.createResultado(r4);
		
		rds.close();
	}

}
