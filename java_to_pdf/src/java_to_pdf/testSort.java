package java_to_pdf;

import java.util.ArrayList;

public class testSort {

	public static void main(String[] args) {
		int[] arr = {21,11,13,1,23,2,3,14,20,4};
		ArrayList<Integer> fintArr = new ArrayList<Integer>();

		ArrayList<Integer> sortArr = new ArrayList<Integer>();
		for(int i=0; i<arr.length;i++) {
			sortArr.add(arr[i]);
		}
		int size = sortArr.size();
		for(int i=0; i < size; i++) {
			int temp = 0;
			for(int j = 0; j < sortArr.size(); j++) {
				
				if(sortArr.get(j) > temp) {
					temp = sortArr.get(j);
				}
			}
			
			System.out.println(temp);
			fintArr.add(temp);		
			sortArr.remove(Integer.valueOf(temp));
			
		}
		
		System.out.println(sortArr);
		System.out.println(fintArr);
		
		
		
		
//		for(int i=0; i<arr.length; i++) {
//			
//			for(int j=i+1; j<arr.length; j++) {
//				
//				if(arr[i] > arr[j]) {
//					
//					int tmp = arr[i];
//					arr[i] = arr[j];
//					arr[j] = tmp;
//				}
//			}
//		}
	}

}
