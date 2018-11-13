// TODO: Make sure to make this class a part of the synthesizer package
 package synthesizer;
import java.io.IOException;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue implements Iterable<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;


    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.first = 0;
        this.last = 0;
        if (capacity > 8) {
            capacity|= capacity >>> 1;
            capacity|= capacity >>> 2;
            capacity|= capacity >>> 4;
            capacity|= capacity >>> 8;
            capacity|= capacity >>> 16;
        } else {
            capacity = 3;
        }
        this.capacity = capacity + 1;
        rb = (T[]) new Object[this.capacity];
        this.fillCount = 0;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int fillCount() {
        return this.fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(Object x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()){
            try {
                throw new IOException("already Full");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        rb[this.last] = (T) x;
        this.last = (this.last + 1) & (this.capacity - 1);
        this.fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()){
            try {
                throw new IOException("already empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        T result = rb[this.first];
        rb[this.first] = null;
        this.first = (this.first + 1) & (this.capacity -1);
        this.fillCount -= 1;
        return result;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()){
            try {
                throw new IOException("already empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rb[first];
    }

    private class bufferIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return (first == last) ? false:true;
        }

        @Override
        public T next() {
            return peek();
        }
    }

    public Iterator iterator() {
        return new bufferIterator();
    }

}
