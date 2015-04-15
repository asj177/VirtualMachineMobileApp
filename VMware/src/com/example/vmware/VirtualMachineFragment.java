package com.example.vmware;

import java.util.ArrayList;

import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.VirtualMachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class VirtualMachineFragment extends ListFragment {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        String userName=bundle.getString("userName");
        String ip=bundle.getString("ip");
        String password=bundle.getString("password");
        VirtualMachineOperations vmOperations=new VirtualMachineOperations();
        
        ArrayList<VirtualMachine> virtualMachineList=vmOperations.getVirtualMachines(ip, userName, password);
        
        VMAdapter adapter=new VMAdapter(virtualMachineList);
       
        setListAdapter(adapter);
    }
	
	
	private class VMAdapter extends ArrayAdapter<VirtualMachine> {
        public VMAdapter(ArrayList<VirtualMachine> vms) {
            super(getActivity(), android.R.layout.simple_list_item_1, vms);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.vmlist, null);
            }

            // configure the view for this Crime
            VirtualMachine vm = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.vmName);
            titleTextView.setText(vm.getName());
            TextView stateTextView =
                (TextView)convertView.findViewById(R.id.vmstate);
            VirtualMachineRuntimeInfo runtimeinfo=vm.getRuntime();
    		VirtualMachinePowerState power=runtimeinfo.getPowerState();
    		
            stateTextView.setText(power.toString());
            
            String powerButtonText="";
            if(power.toString().equalsIgnoreCase("poweredOff")){
            	powerButtonText="START";
            }else{
            	powerButtonText="STOP";
            }
            Button powerButton=(Button)convertView.findViewById(R.id.vmstart);
            powerButton.setText(powerButtonText);
            powerButton.setTag(vm);
            powerButton.setOnClickListener(new OnClickListener(){
            	
   				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Button b=(Button)v.findViewById(R.id.vmstart);
					
					String text=b.getText().toString();
					VirtualMachine virtualMachine=(VirtualMachine)b.getTag();
					VirtualMachineOperations vmoperation=new VirtualMachineOperations();
					vmoperation.operateVirtualMachine(text,virtualMachine);
					if(text.equals("START")){
						
						
						b.setText("STOP");
						
					}else{
						b.setText("START");
					}
					
					
					
					
					
				}
            	
            });
            return convertView;
        }
	}
}
