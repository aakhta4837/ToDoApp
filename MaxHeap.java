public class MaxHeap {
    private Task[] heap;
    private int size;

    public MaxHeap(int capacity) {
        heap = new Task[capacity];
        size = 0;
    }

    public void insert(Task t) {
        if (size == heap.length) {
            throw new IllegalStateException("Heap is full!");
        }
        heap[size] = t;
        heapifyUp(size);
        size++;
    }

    public Task extractMax() {
        if (size == 0) return null;

        Task max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return max;
    }

    public Task peek() {
        return size == 0 ? null : heap[0];
    }

    public int getSize() { return size; }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].getPriority() > heap[parent].getPriority()) {
                Task temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;
                index = parent;
            } else break;
        }
    }

    private void heapifyDown(int index) {
        while (index < size) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int largest = index;

            if (left < size && heap[left].getPriority() > heap[largest].getPriority()) {
                largest = left;
            }
            if (right < size && heap[right].getPriority() > heap[largest].getPriority()) {
                largest = right;
            }
            if (largest != index) {
                Task temp = heap[index];
                heap[index] = heap[largest];
                heap[largest] = temp;
                index = largest;
            } else break;
        }
    }

    public Task[] getHeapArray() {
        Task[] out = new Task[size];
        for (int i = 0; i < size; i++) out[i] = heap[i];
        return out;
    }
}
