package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

/**
 * An {@link AbstractEntitiesQueryCrawlerOperation} is an 
 * {@link AbstractQueryCrawlerOperation} that crawls on an
 * {@link EntitiesQuery}.
 * 
 * @param <Q> 
 *        Type of query being executed.
 * @param <P>
 *        Type of entity fetched by the query.
 */
public abstract class AbstractEntitiesQueryCrawlerOperation<Q extends EntitiesQuery<P>, P> 
extends AbstractQueryCrawlerOperation<Q> {

	/**
	 * Creates a new AbstractEntitiesQueryCrawlerOperation.
	 * 
	 * @param query
	 */
	public AbstractEntitiesQueryCrawlerOperation(Q query) {
		super(query);
	}

	@Override
	protected void handleRow() throws LogicException, DataException {
		P p = query.getEntity();
		handleEntity(p);
	}
	
	/**
	 * Handles the specified entity.
	 * 
	 * @param p
	 *        entity to handle.
	 */
	protected abstract void handleEntity(P p) throws LogicException, DataException; 

}
