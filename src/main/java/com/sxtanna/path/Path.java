package com.sxtanna.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;

public interface Path
{

	void load();

	void kill();

	void step();


	boolean done();

	boolean pass();


	@NotNull
	@UnmodifiableView
	List<Node> path();

	@NotNull
	@Contract("_, _, _, -> new")
	Node createNode(final int x, final int y, final int z);


	default boolean find()
	{
		return find(Long.MAX_VALUE);
	}

	default boolean find(final long stepLimit)
	{
		if (done())
		{
			return pass();
		}

		long steps = 0L;

		load();

		while (!done() && steps++ < stepLimit)
		{
			step();
		}

		kill();

		return pass();
	}

}
