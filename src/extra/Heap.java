package extra;

public class Heap {

    private static int heapSize;

    public static void sort(int[] list){
        int n = list.length;
        buildHeap(list);
        for (int i = n-1; i > 0; i--) {
            swap(list, 0, heapSize);
            heapSize--;
            heapify(list, 0);
        }
    }

    private static void buildHeap(int[] list){
        int n = list.length;
        heapSize = n - 1;
        for (int i = (n-1)/2; i >= 0; i--) {
            heapify(list, i);
        }
    }

    private static void heapify(int[] list, int i){
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int largest;
        if (l <= heapSize && list[l] > list[i]){
            largest = l;
        } else {
            largest = i;
        }
        if (r <= heapSize && list[r] > list[largest]){
            largest = r;
        }
        if (largest != i){
            swap(list, i, largest);
            heapify(list, largest);
        }
    }

    public static void sortDesc(int[] list){
        int n = list.length;
        buildHeapDesc(list);
        for (int i = n-1; i > 0; i--) {
            swap(list, 0, heapSize);
            heapSize--;
            heapifyDesc(list, 0);
        }
    }

    private static void buildHeapDesc(int[] list){
        int n = list.length;
        heapSize = n - 1;
        for (int i = (n-1)/2; i >= 0; i--) {
            heapifyDesc(list, i);
        }
    }

    private static void heapifyDesc(int[] list, int i){
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int largest;
        if (l <= heapSize && list[l] < list[i]){
            largest = l;
        } else {
            largest = i;
        }
        if (r <= heapSize && list[r] < list[largest]){
            largest = r;
        }
        if (largest != i){
            swap(list, i, largest);
            heapifyDesc(list, largest);
        }
    }

    private static void swap(int[] list, int a, int b){
        int aux = list[a];
        list[a] = list[b];
        list[b] = aux;
    }
}
