package com.aikiinc.data.handler;

/**
 * 
 * The implementation allows the Command Pattern to be chained.
 * <p>
 * Example usage: SomeChainImplementation.showData().execute();<br>
 * Notice commands are executed one after the other - chained!
 * 
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Philip Jahmani Chauvet.
 * @Dated Oct 02, 2013 - Oct 02, 2013
 */
public interface Chain {
	/**
	 * Show data from pojo, list or database
	 * 
	 * @return
	 */
	Chain showData();

	/**
	 * Execute the desire implementation, that is: get a pojo, list or data base
	 * data. This is an end point of the chaining execution.
	 * 
	 * @return
	 */
	Object execute();
}
