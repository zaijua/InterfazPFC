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
	ImageButton alumnos,series,resultados;
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
				
				
				
				ArrayList<Integer> lista=new ArrayList<Integer>();
				lista.add(1);
				lista.add(2);
				Ejercicio p1=eds.createEjercicio("Pelota tenis y béisbol", lista,5);
				lista.clear();
				lista.add(1);
				lista.add(3);
				Ejercicio p2=eds.createEjercicio("Pelota y teléfono", lista,6);
				lista.clear();
				lista.add(4);
				lista.add(5);
				eds.createEjercicio("Bolígrafo y rotulador", lista,7);
				lista.clear();
				lista.add(6);
				lista.add(7);
				eds.createEjercicio("Estuche y lápiz", lista,8);
				lista.clear();
				lista.add(8);
				lista.add(9);
				eds.createEjercicio("Vaso y plato", lista,9);
				lista.clear();
				eds.close();
				
				
				//Crear Serie

				ArrayList<Integer> miarray=new ArrayList<Integer>();
				miarray.add(p1.getIdEjercicio());
				miarray.add(p2.getIdEjercicio());
				SerieEjercicios serie1=seds.createSerieEjercicios("PELOTAS", miarray,0, new Date());
				//seds.actualizaDuracion(serie1);
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
				
				 
				
				Resultado r=new Resultado(1, alumno1.getIdAlumno(), serie1.getIdSerie(), fecha1, 0, 30, 18, 12, 7.2);		
				Resultado r2=new Resultado(2, alumno1.getIdAlumno(), serie1.getIdSerie(), fecha2, 0, 30, 18, 12, 8.9);
				Resultado r3=new Resultado(3, alumno2.getIdAlumno(), serie1.getIdSerie(), fecha1, 0, 30, 18, 12, 6.1);
				Resultado r4=new Resultado(4, alumno2.getIdAlumno(), serie1.getIdSerie(), fecha3, 0, 30, 18, 12, 5.4);
				
				rds.createResultado(r);
				rds.createResultado(r2);
				rds.createResultado(r3);
				rds.createResultado(r4);
				
				rds.close();
				
				
				
				
				
			}
		});
		
		
	}

	public void onClickAcercaDe(View v){
		Intent acercaDeIntent = new Intent(this, AboutActivity.class);
		startActivity(acercaDeIntent);
	}
	


}
