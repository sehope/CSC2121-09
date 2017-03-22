package pqsort;

import java.util.Comparator;

public class PQFactory<E>
{
	public static <E> PQInterface<E> createPQ(Comparator<E> comp, PQType pq_type)
	{
		if(pq_type == PQType.TREE)
		{
			PQInterface<E> pqbst = new PQBST(comp);
			return pqbst;
		}
		else
		{
			PQInterface<E> pqsll = new PQSLL(comp);
			return pqsll;
		}
	}
}