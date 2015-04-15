package com.example.vmware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class VMActivity extends FragmentActivity {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.vmlayout);
	        Intent i=getIntent();
	        String userName=i.getStringExtra("userName");
	        String ip=i.getStringExtra("ip");
	        String password=i.getStringExtra("password");
	        
	       
	        Bundle bundle=new Bundle();
	        bundle.putString("ip", ip);
	        bundle.putString("userName", userName);
	        bundle.putString("password", password);
	        
	        FragmentManager manager = getSupportFragmentManager();
	        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
            
	        if (fragment == null) {
	        	
	            fragment = new VirtualMachineFragment();
	            fragment.setArguments(bundle);
	            manager.beginTransaction()
	                .add(R.id.fragmentContainer, fragment)
	                .commit();
	        } 
	    }
}
