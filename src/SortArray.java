import java.util.Arrays;

public class SortArray {
    @AnnotSortMethod(sortNameMethod = "Arrays to Sort.")
    public void ArraysToSort(int[] a) {
        Arrays.sort(a);
    }

    @AnnotSortMethod(sortNameMethod = "Bubble Sort.")
    public void BubbleSort(int[] a) {
        int temp;
        for (int i = 0; i <a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[j]>a[i]){
                    temp = a[i];
                    a[i]=a[j];
                    a[j]=temp;
                }
            }
        }
    }

    @AnnotSortMethod(sortNameMethod = "Sort Choice")
    public void sortChoice (int[] a) {
        for (int min=0;min<a.length-1;min++) {
            int least = min;
            for (int j=min+1;j<a.length;j++) {
                if(a[j] < a[least]) {
                    least = j;
                }
            }
            int tmp = a[min];
            a[min] = a[least];
            a[least] = tmp;
        }
    }
}
