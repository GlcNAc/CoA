package prj.coa.test.file;

import java.io.File;

public class Disk {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		String file = "\\\\192.168.0.27\\D$";
		
		File diskPartition = new File(file);
		
		long totalCapacity = diskPartition.getTotalSpace(); 
		  
        long freePartitionSpace = diskPartition.getFreeSpace(); 
        long usablePatitionSpace = diskPartition.getUsableSpace(); 
        
        System.out.println("**** Sizes in Mega Bytes ****\n");
        
        System.out.println("Total D partition size : " + totalCapacity / (1024*1024) + " MB");
        System.out.println("Usable Space : " + usablePatitionSpace / (1024 *1024) + " MB");
        System.out.println("Free Space : " + freePartitionSpace / (1024 *1024) + " MB");
  
        System.out.println("\n**** Sizes in Giga Bytes ****\n");
  
        System.out.println("Total D partition size : " + totalCapacity / (1024*1024*1024) + " GB");
        System.out.println("Usable Space : " + usablePatitionSpace / (1024 *1024*1024) + " GB");
        System.out.println("Free Space : " + freePartitionSpace / (1024 *1024*1024) + " GB");
	}
}
