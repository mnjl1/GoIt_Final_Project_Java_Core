import java.util.Random;

public class FillArray {
    public int[] fillAscending(int size) {
        int[] massive = new int[size];
        for (int i = 0; i <size ; i++) {
            massive[i]=i;
        }
        return massive;
    }
    public int[] fillDescending(int size) {
        int[] massive = new int[size];
        for (int i = size-1; i>=0 ; i--) {
            massive[i] = i;
        }
        return massive;
    }
    public int[] fillAscendingPlusElementRandom(int size) {
        Random random = new Random();
        int[] massive = new int[size];
        for (int i = 0; i <massive.length ; i++) {
            massive[i]=i;
        }
        massive[massive.length-1]=random.nextInt();
        return massive;
    }
    public int[] fillRandom(int size) {
        int[] massive = new int[size];
        Random random = new Random();
        for (int i = 0; i <size ; i++) {
            massive[i] = random.nextInt(100);
        }
        return massive;
    }
}
