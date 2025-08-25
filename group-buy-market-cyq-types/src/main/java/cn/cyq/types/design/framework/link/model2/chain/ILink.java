package cn.cyq.types.design.framework.link.model2.chain;

public interface ILink<E> {

    boolean add(E e);

    boolean addFirst(E e);

    boolean addLast(E e);

    boolean remove(Object e);

    E get(int index);

    void printLinkList();
}
