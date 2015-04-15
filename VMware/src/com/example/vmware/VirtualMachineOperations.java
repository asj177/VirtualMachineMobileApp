package com.example.vmware;

import java.net.URL;
import java.util.ArrayList;

import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;


public class VirtualMachineOperations {
	
	
	public ArrayList<VirtualMachine> getVirtualMachines(String ip,String userName,String password){
		URL url;
		ArrayList<VirtualMachine> virtualMachineList=new ArrayList<VirtualMachine>();
		
		try {
			url = new URL("https://"+ip+"/sdk");
			ServiceInstance si = new ServiceInstance(url, userName, password, true);
			Folder rootFolder = si.getRootFolder();
			
			String name = rootFolder.getName();
			System.out.println("root:" + name);
			ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
			
			for(int i=0;i<mes.length;i++){
				virtualMachineList.add((VirtualMachine)mes[0]);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return virtualMachineList;
	}
	
	public void operateVirtualMachine(String text,VirtualMachine vm){
		try{
		if(text.equalsIgnoreCase("START")){
			vm.powerOnVM_Task(null);
		}else{
			vm.powerOffVM_Task();
		}
		}catch(Exception e ){
			System.out.println(e);
		}
		
		
	}
	

}
