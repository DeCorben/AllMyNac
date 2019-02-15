package com.blackmanatee.allmynac;

import android.app.*;
import android.os.*;
import com.blackmanatee.nacsac.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import com.blackmanatee.lagoon.*;

public class NacDetailActivity extends Activity{
	private KernelNac kn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
		kn = KernelNac.get(this,"v2.json");
		
		Nac n = kn.getNac(getIntent().getStringExtra("nacId"));
		if(n == null)
			loadNac(kn.getNac("lorem")); //needs better default
		else
			loadNac(n);
    }
	
	public void loadNac(Nac n){
		switch(n.getType()){
			case "sac":
				setContentView(R.layout.sac);
				((ListView)findViewById(R.id.sacDataList)).setAdapter(new DeleteAdapter(this,R.layout.item_with_del,((Sac)n).nacList()));
				((TextView)findViewById(R.id.sacNameBox)).setText(n.getName());
				((TextView)findViewById(R.id.sacTypeBox)).setText(n.getType());
				break;
			default:
				setContentView(R.layout.nac);
				((TextView)findViewById(R.id.nacDataBox)).setText(n.getData());
				((TextView)findViewById(R.id.nacNameBox)).setText(n.getName());
				((TextView)findViewById(R.id.nacTypeBox)).setText(n.getType());
				break;
		}
	}
	
	public void editClick(View v){
		Intent in = new Intent(this,NacDetailActivity.class);
		in.putExtra("nacId",((TextView)v).getText().toString());
		startActivity(in);
	}
}
