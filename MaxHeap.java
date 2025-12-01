import java.util.ArrayList;

public class MaxHeap {

    private ArrayList<Task> heap;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new ArrayList<>();
    }

    public void insert(Task task) {
        if (heap.size() >= capacity) {
            System.out.println("Heap is full.");
            return;
        }
        heap.add(task);
        heapifyUp(heap.size() - 1);
    }

    public Task peek() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    public Task extractMax() {
        if (heap.isEmpty()) return null;

        Task max = heap.get(0);

        Task last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return max;
    }

    // ------- Remove any task from heap -------
    public boolean remove(Task task) {
        int index = heap.indexOf(task);
        if (index == -1) return false;

        Task last = heap.remove(heap.size() - 1);
        if (index < heap.size()) {
            heap.set(index, last);

            // Restore heap property
            heapifyUp(index);
            heapifyDown(index);
        }
        return true;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(index).getPriority() > heap.get(parent).getPriority()) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();

        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int largest = index;

            if (left < size &&
                heap.get(left).getPriority() > heap.get(largest).getPriority()) {
                largest = left;
            }

            if (right < size &&
                heap.get(right).getPriority() > heap.get(largest).getPriority()) {
                largest = right;
            }

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Task temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Needed for listView update
    public ArrayList<Task> getHeapArray() {
        return heap;
    }
}
