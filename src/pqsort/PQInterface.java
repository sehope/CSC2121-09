package pqsort;

public interface PQInterface<E>
{
	public void pqInsert(E item) throws PQException;
	
	public E pqRemove() throws PQException;
	
	public boolean pqIsEmpty();
}