package com.blackmanatee.allmynac;
import com.blackmanatee.nacsac.*;
import android.content.*;
import java.io.*;
import org.json.*;

public final class KernelNac{
	//when are you comin' back?
	private static KernelNac me;
	
	public static KernelNac get(Context c){
		if(me == null)
			me = new KernelNac(c);
		else
			updateContext(c);
		return me;
	}
	
	public static KernelNac get(Context c,String f){
		if(me == null)
			me = new KernelNac(c,f);
		else
			updateContext(c);
		return me;
	}
	
	private static Context con;
	private Stack box;
	
	private KernelNac(Context c){
		updateContext(c);
		box = new Stack();
		
		//TODO: locate most recent version of archive file
		
		load("nacsac.json");
	}
	
	private KernelNac(Context c,String f){
		updateContext(c);
		box = new Stack();
		load(f);
	}
	
	private void load(String f){
		try
		{
			FileReader fr = new FileReader(new File(con.getExternalFilesDir("nacs"),f));
			String d = "";
			while(fr.ready()){
				d += Character.toString((char)(fr.read()));
			}
			fr.close();
			JSONObject o = new JSONObject(d);
			JSONArray a = o.getJSONArray("pile");
			for(int z=0;z<a.length();z++){
				box.createNac(a.getJSONObject(z).toString());
			}
		}
		catch (FileNotFoundException ex){
			ex.printStackTrace();
			try{
				FileWriter fw = new FileWriter(new File(con.getExternalFilesDir("nacs"), "nacbox.json"));
				fw.write("{\"pile\":\"[]\"}");
				fw.flush();
				fw.close();
			}
			catch(IOException x){
				x.printStackTrace();
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch(JSONException ex){
			ex.printStackTrace();
		}
	}
	
	private static void updateContext(Context c){
		con = c;
	}
	
	public Nac getNac(String n){
		return box.readNac(n);
	}
}
