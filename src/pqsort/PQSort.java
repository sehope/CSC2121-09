package pqsort;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class PQSort<E>
{
	public static <E> List<E> pqSort(List<E> unsorted_items, Comparator<E> comp, PQType pq_type)
	{
		//PQSLL<E> sll = new PQSLL<E>();
		//PQBST<E> bst = new PQBST<E>();
		List<E> sorted = new ArrayList<E>();
		
		PQInterface pq = PQFactory.createPQ(comp, pq_type);
		
		for(int b = 0; b < unsorted_items.size(); b++)
		{
			pq.pqInsert(unsorted_items.get(b));
		}
		for(int r = 0; r < unsorted_items.size(); r++)
		{
			E element = (E)pq.pqRemove();
			sorted.add(element);
		}
		
		return sorted;
		
		/*if(!tree)
		{
		    for(int b = 0; b < unsorted_items.length; b++)
			{
				bst.pqInsert(unsorted_items[b]);
			}
			for(int r = 0; r < unsorted_items.length; r++)
			{
				unsorted_items[r] = bst.pqRemove();
			}
			return unsorted_items;
		}
		else
		{
		    for(int b = 0; b < unsorted_items.length; b++)
			{
				sll.pqInsert(unsorted_items[b]);
			}
			for(int r = 0; r < unsorted_items.length; r++)
			{
				unsorted_items[r] = sll.pqRemove();
			}
			return unsorted_items;
		}*/
	}
}