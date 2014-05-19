package es.ugr.template_ugr;

import com.ugr.template_ugr.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Actividad principal. Esta actividad se puede cambiar completamente.
 * Sólo es obligatorio enlazar de alguna manera con la actividad "AboutActivity"
 * @author Namir Sayed-Ahmad Baraza
 * @mail namirsab@gmail.com
 */

public class MainActivity extends Activity {
	ImageButton alumnos,series,resultados;

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
		
		series=(ImageButton)findViewById(R.id.buttonEj);
		series.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent seriesIntent=new Intent(getApplicationContext(), SeriesEjercicios.class);
				startActivity(seriesIntent);
			}
		});
		
	}

	public void onClickAcercaDe(View v){
		Intent acercaDeIntent = new Intent(this, AboutActivity.class);
		startActivity(acercaDeIntent);
	}
	


}
