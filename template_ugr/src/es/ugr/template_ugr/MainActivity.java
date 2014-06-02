package es.ugr.template_ugr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pfc.bd.AlumnoDataSource;
import pfc.bd.EjercicioDataSource;
import pfc.bd.ObjetoDataSource;
import pfc.bd.ResultadoDataSource;
import pfc.bd.SerieEjerciciosDataSource;
import pfc.obj.Alumno;
import pfc.obj.Ejercicio;
import pfc.obj.Objeto;
import pfc.obj.Resultado;
import pfc.obj.SerieEjercicios;
import pfc.obj.TiposPropios.Sexo;

import com.ugr.template_ugr.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Actividad principal. Esta actividad se puede cambiar completamente.
 * SÃ³lo es obligatorio enlazar de alguna manera con la actividad "AboutActivity"
 * @author Namir Sayed-Ahmad Baraza
 * @mail namirsab@gmail.com
 */

public class MainActivity extends Activity {
	ImageButton alumnos,series,resultados,ejercicios;
	Button reinicia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

		//Gestion alumnos
		alumnos=(ImageButton)findViewById(R.id.buttonAlum);
		alumnos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent alumnoIntent=new Intent(getApplicationContext(), GestionAlumnos.class);
				startActivity(alumnoIntent);
				
			}
		});
		
		//Resultados
		resultados=(ImageButton)findViewById(R.id.buttonEsta);
		resultados.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent resultadosIntent=new Intent(getApplicationContext(), Resultados.class);
				startActivity(resultadosIntent);
				
			}
		});
		
		
		//Gestion de series de ejercicios
		
		series=(ImageButton)findViewById(R.id.ButtonSerie);
		series.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent seriesIntent=new Intent(getApplicationContext(), SeriesEjercicios.class);
				startActivity(seriesIntent);
			}
		});
		
		ejercicios=(ImageButton)findViewById(R.id.buttonEj);
		ejercicios.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent ejerciciosIntent=new Intent(getApplicationContext(), Ejercicios.class);
				startActivity(ejerciciosIntent);
			}
		});
		
		
		reinicia=(Button)findViewById(R.id.Reinicia);
		reinicia.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlumnoDataSource ads=new AlumnoDataSource(v.getContext());
				ObjetoDataSource ods=new ObjetoDataSource(v.getContext());
				EjercicioDataSource eds=new EjercicioDataSource(v.getContext());
				SerieEjerciciosDataSource seds=new SerieEjerciciosDataSource(v.getContext());
				ResultadoDataSource rds=new ResultadoDataSource(v.getContext());
				
				//Abrir y borrar todos los datos
				ads.open();
				ods.open();
				eds.open();
				seds.open();
				rds.open();
				
				rds.borraTodosResultados();
				seds.eliminarTodasSeriesEjercicios();
				eds.eliminaTodosEjercicios();
				ods.eliminaTodosObjetos();
				ads.borraTodosAlumno();
				
				
				
				
				
				
				//Alumnos

				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH,14);
				cal.set(Calendar.MONTH,6);
				cal.set(Calendar.YEAR,1989);
				
				Date d = cal.getTime();
				 
				cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH,25);
				cal.set(Calendar.MONTH,9);
				cal.set(Calendar.YEAR,1990);


				Date d2 = cal.getTime();
				 
				 
				Alumno alumno1=ads.createAlumno("Juan Manuel", "Lucena Morales", d, Sexo.Hombre, "");
				Alumno alumno2=ads.createAlumno("Miguel", "Morales Rodríguez", d2, Sexo.Mujer, "");
				
				ads.close();
				
						
				//Crear objetos
				ObjetoDataSource obs=new ObjetoDataSource(v.getContext());
				obs.open();
				Objeto objeto1=obs.createObjeto("Pelota tenis", "", "",0,0);
				Objeto objeto2=obs.createObjeto("Pelota beisbol", "", "",0,0);
				Objeto objeto3=obs.createObjeto("Teléfono", "", "",0,0);
				Objeto objeto4=obs.createObjeto("Bolígrafo", "", "",0,0);
				Objeto objeto5=obs.createObjeto("Rotulador", "", "",0,0);
				Objeto objeto6=obs.createObjeto("Estuche", "", "",0,0);
				Objeto objeto7=obs.createObjeto("Lápiz", "", "",0,0);
				Objeto objeto8=obs.createObjeto("Vaso", "", "",0,0);
				Objeto objeto9=obs.createObjeto("Plato", "", "",0,0);
				obs.close();
				
				//Crear Ejercicios
				
				
				
				ArrayList<Integer> lista=new ArrayList<Integer>();
				lista.add((int) objeto1.getId());
				lista.add((int) objeto2.getId());
				Ejercicio p1=eds.createEjercicio("Pelota tenis y béisbol", lista,5);
				lista.clear();
				lista.add((int) objeto1.getId());
				lista.add((int) objeto3.getId());
				Ejercicio p2=eds.createEjercicio("Pelota y teléfono", lista,6);
				lista.clear();
				lista.add((int) objeto4.getId());
				lista.add((int) objeto5.getId());
				eds.createEjercicio("Bolígrafo y rotulador", lista,7);
				lista.clear();
				lista.add((int) objeto6.getId());
				lista.add((int) objeto7.getId());
				eds.createEjercicio("Estuche y lápiz", lista,8);
				lista.clear();
				lista.add((int) objeto8.getId());
				lista.add((int) objeto9.getId());
				eds.createEjercicio("Vaso y plato", lista,9);
				lista.clear();
				eds.close();
				
				
				//Crear Serie

				ArrayList<Integer> miarray=new ArrayList<Integer>();
				miarray.add(p1.getIdEjercicio());
				miarray.add(p2.getIdEjercicio());
				SerieEjercicios serie1=seds.createSerieEjercicios("PELOTAS", miarray,0, new Date());
				seds.actualizaDuracion(serie1);
				seds.close();
				
				
				//Crear Resultados
				

				cal=Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				Date fecha1=cal.getTime();
				
				cal=Calendar.getInstance();
				cal.add(Calendar.DATE, -3);
				Date fecha2=cal.getTime();
				
				cal=Calendar.getInstance();
				cal.add(Calendar.DATE, -5);
				Date fecha3=cal.getTime();
				
				cal=Calendar.getInstance();
				cal.add(Calendar.DATE, -8);
				Date fecha4=cal.getTime();
				
				 
				
				Resultado r=new Resultado(1, alumno1.getIdAlumno(), serie1.getIdSerie(), fecha1, 0, 30, 18, 12, 7.2);		
				Resultado r2=new Resultado(2, alumno1.getIdAlumno(), serie1.getIdSerie(), fecha2, 0, 30, 18, 12, 8.9);

				Resultado r3=new Resultado(3, alumno2.getIdAlumno(), serie1.getIdSerie(), fecha1, 0, 30, 18, 12, 6.1);
				Resultado r4=new Resultado(4, alumno2.getIdAlumno(), serie1.getIdSerie(), fecha3, 0, 30, 18, 12, 5.4);
				
				Resultado r5=new Resultado(5, alumno1.getIdAlumno(), serie1.getIdSerie(), fecha4, 0, 30, 18, 12, 7.8);
				Resultado r6=new Resultado(6, alumno2.getIdAlumno(), serie1.getIdSerie(), fecha4, 0, 30, 18, 12, 6.2);
				
				rds.createResultado(r);
				rds.createResultado(r2);
				rds.createResultado(r3);
				rds.createResultado(r4);
				rds.createResultado(r5);
				rds.createResultado(r6);
				
				rds.close();
				
				
				
				
				
			}
		});
		
		
	}

	public void onClickAcercaDe(View v){
		Intent acercaDeIntent = new Intent(this, AboutActivity.class);
		startActivity(acercaDeIntent);
	}
	


}
