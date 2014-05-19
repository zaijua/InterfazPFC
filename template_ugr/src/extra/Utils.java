package extra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import org.json.JSONArray;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.features2d.KeyPoint;

import pfc.obj.Objeto;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class Utils {

	private static final String TAG = "Reconocimiento::Utils";

	public static String matToJson(Mat mat) {
		JsonObject obj = new JsonObject();

		if (mat.isContinuous()) {
			int cols = mat.cols();
			int rows = mat.rows();
			int elemSize = (int) mat.elemSize();

			float[] data = new float[cols * rows * elemSize];

			mat.get(0, 0, data);

			obj.addProperty("rows", mat.rows());
			obj.addProperty("cols", mat.cols());
			obj.addProperty("type", mat.type());

			// We cannot set binary data to a json object, so:
			// Encoding data byte array to Base64.
			//String dataString = new String(Base64.encode(data, Base64.DEFAULT));
			//String dataString = new String(data);
			
			
			ByteBuffer buf = ByteBuffer.allocate(data.length*4);
			
			for (int i=0; i<data.length;i++)
				buf.putFloat(data[i]);

			String dataString = new String(Base64.encode(buf.array(), Base64.DEFAULT));	

			obj.addProperty("data", dataString);

			Gson gson = new Gson();
			String json = gson.toJson(obj);

			return json;
		} else {
			Log.e(TAG, "Mat not continuous.");
		}
		return "{}";
	}

	public static Mat matFromJson(String json) {
		JsonParser parser = new JsonParser();
		JsonObject JsonObject = parser.parse(json).getAsJsonObject();

		int rows = JsonObject.get("rows").getAsInt();
		int cols = JsonObject.get("cols").getAsInt();
		int type = JsonObject.get("type").getAsInt();

		String dataString = JsonObject.get("data").getAsString();
		byte[] data = Base64.decode(dataString.getBytes(), Base64.DEFAULT);
		
		final FloatBuffer fb = ByteBuffer.wrap(data).asFloatBuffer();
		
		final float[] dst = new float[fb.capacity()];
		
		fb.get(dst);
		
		Mat mat = new Mat(rows, cols, type);
		mat.put(0, 0, dst);

		return mat;
	}

	public static String keypointsToJson(MatOfKeyPoint mat) {
		if (mat != null && !mat.empty()) {
			Gson gson = new Gson();
			
			JsonArray jsonArr = new JsonArray();

			KeyPoint[] array = mat.toArray();
			for (int i = 0; i < array.length; i++) {
				KeyPoint kp = array[i];

				JsonObject obj = new JsonObject();

				obj.addProperty("class_id", kp.class_id);
				obj.addProperty("x", kp.pt.x);
				obj.addProperty("y", kp.pt.y);
				obj.addProperty("size", kp.size);
				obj.addProperty("angle", kp.angle);
				obj.addProperty("octave", kp.octave);
				obj.addProperty("response", kp.response);

				jsonArr.add(obj);
			}

			String json = gson.toJson(jsonArr);

			return json;
		}
		return "{}";
	}

	public static MatOfKeyPoint keypointsFromJson(String json) {
		MatOfKeyPoint result = new MatOfKeyPoint();

		JsonParser parser = new JsonParser();
		JsonArray jsonArr = parser.parse(json).getAsJsonArray();

		int size = jsonArr.size();

		KeyPoint[] kpArray = new KeyPoint[size];

		for (int i = 0; i < size; i++) {
			KeyPoint kp = new KeyPoint();

			JsonObject obj = (JsonObject) jsonArr.get(i);

			Point point = new Point(obj.get("x").getAsDouble(), obj.get("y")
					.getAsDouble());

			kp.pt = point;
			kp.class_id = obj.get("class_id").getAsInt();
			kp.size = obj.get("size").getAsFloat();
			kp.angle = obj.get("angle").getAsFloat();
			kp.octave = obj.get("octave").getAsInt();
			kp.response = obj.get("response").getAsFloat();

			kpArray[i] = kp;
		}

		result.fromArray(kpArray);

		return result;
	}
	
	public static String ArrayListToJson(ArrayList<Integer> idsObjeto){
		String result = "";
		
		if (!idsObjeto.isEmpty()) {

			JsonArray jsonArr = new JsonArray();
			
			for (int i=0; i<idsObjeto.size();i++){
				JsonObject obj = new JsonObject();
				obj.addProperty("id", idsObjeto.get(i));
				jsonArr.add(obj);
			}
			Gson gson = new Gson();
			result = gson.toJson(jsonArr);

			return result;
		} else {
			Log.e(TAG, "Mat not continuous.");
		}
		return "{}";
		
	}
	
	public static ArrayList<Integer> ArrayListFromJson(String idsObjeto){
		ArrayList<Integer> result=new ArrayList<Integer>();
		
		JsonParser parser = new JsonParser();
		JsonArray jsonArr = parser.parse(idsObjeto).getAsJsonArray();
		
		for (int i=0; i< jsonArr.size(); i++)
			result.add(((JsonObject)jsonArr.get(i)).get("id").getAsInt());
		
		return result;
	}

	public static void copyFile(String in, String out) throws IOException {
		File in1 = new File(in);
		File out1 = new File(out);
		FileChannel inChannel = new FileInputStream(in1).getChannel();
		FileChannel outChannel = new FileOutputStream(out1).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}
	
	public static void toast(Objeto obj, Context context){
		Toast.makeText(context, "Id ="+obj.getId() , Toast.LENGTH_SHORT).show();
	}
	
}
