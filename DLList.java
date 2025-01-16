public class DLList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size; 

    public DLList(){
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.setNext(tail);
        head.setPrev(null);
        tail.setNext(null);
        tail.setPrev(head);
        size = 0;
    }
    public Node<E> getNode(int index){
        Node<E> current;
        if(index < (size/2)){
            current = head.next();
            for(int i=0; i<index; i++){
                current = current.next();
            }
        } else {
            current = tail.prev();
            for(int i=size-1; i>index; i--){
                current = current.prev();
            }
        }
        return current;
    }
    public void add(E e) {
        Node<E> curr = new Node<>(e);
        Node<E> prev = tail.prev();
        prev.setNext(curr);
        curr.setNext(tail);
        tail.setPrev(curr);
        curr.setPrev(prev);
        size++;
    }
    public void add(int ind, E e) {
        Node<E> add = new Node<>(e);
        
        if(ind<size/2) {
            Node<E> curr = head.next();
            for(int i=0; i<ind; i++) {
                curr = curr.next();
            }
            Node<E> prev = curr.prev();
            prev.setNext(add);
            add.setPrev(prev);
            add.setNext(curr);
            curr.setPrev(add);
        } else {
            Node<E> curr = tail.prev();
            for(int i=size-1; i>=ind; i--) {
                curr = curr.prev();
            }
            Node<E> next = curr.next();
            curr.setNext(add);
            add.setPrev(curr);
            add.setNext(next);
            next.setPrev(add);
        }
        size++;
    }
    public E get(int index){
        return getNode(index).get();
    }
    public void remove(int index){
        Node<E> current = getNode(index);
        current.next().setPrev(current.prev());
        current.prev().setNext(current.next());
        size--;
    }
    public void remove(E e){
        Node<E> current = head.next();
        int count = 0;
        while(!(current.get().equals(e))){
            current = current.next();
            count++;
        }
        size--;
        remove(count);
    }
    public int size(){
        return size;
    }
    public String toString(){
        String string = "";
        Node<E> current = head.next();
        while(current != tail){
            string = string + current.get().toString() + " ";
            current = current.next();
        }
        return string;
    }
    public void set(int index, E item) {
        Node<E> tmp = head;
        while(index>0) {
          if(tmp==null) {
            return;
          }
          index--;
          tmp = tmp.next();
        }
        tmp.set(item);
    }

}
