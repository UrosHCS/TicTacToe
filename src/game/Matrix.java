package game;

public class Matrix {
	public static int[] indexOfZero (int a[][], int x) {
		int indexOfZeroInRcd = -3; // Broj -3 je prilicno proizvoljan broj...
		int[] rcdArr = getRCDArray(a, x);
		for (int i = 0; i < 3; i++) {
			if (rcdArr[i] == 0) {
				indexOfZeroInRcd = i;
				if (x >= 0 && x <= 2) return new int[] {x, indexOfZeroInRcd};
				if (x >= 3 && x <= 5) return new int[] {indexOfZeroInRcd, x - 3};
				if (x == 6) return new int[] {indexOfZeroInRcd, indexOfZeroInRcd};
				if (x == 7) return new int[] {2 - indexOfZeroInRcd, indexOfZeroInRcd};
				break;
			}
		}
		if (indexOfZeroInRcd == -3) return new int[] {-3, -3};

		return new int[] {42, 42};
	}
	
	public static int arraySum (int a[][], int x) {
		int[] arrForSum = getRCDArray(a, x);
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			sum = sum + arrForSum[i];
		}
		return sum;
		
	}
	
	//this method explains the meaning of x
	public static int[] getRCDArray(int a[][], int x) {
		if (x >= 0 && x <= 2) return rowArray(a, x);
		if (x >= 3 && x <= 5) return columnArray(a, x - 3);
		if (x >= 6 && x <= 7) return diagArray(a, x - 6);
		return new int[3];
	}
	
	public static int[] rowArray(int a[][], int r) {
		return a[r];
	}
	
	public static int[] columnArray(int a[][], int c) {
		int[] column = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			column[i] = a[i][c];
		}
		return column;
	}
	
	 //ako je bin = 0 uzmi diagonalu gorelevo-doledesno, a ako je bin = 1 uzmi diagonalu goredesno-dolelevo
	public static int[] diagArray(int a[][], int bin) {
		int[] column = new int[a.length];
		if (bin == 0) {
			for (int i = 0; i < a.length; i++) {
				column[i] = a[i][i];
			}
			
		}
		if (bin == 1) {
			for (int i = 0; i < a.length; i++) {
				column[i] = a[2 - i][i];
			}
		}
		return column;
	}

}