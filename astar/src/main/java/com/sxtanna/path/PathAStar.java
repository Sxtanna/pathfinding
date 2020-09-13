package com.sxtanna.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class PathAStar implements Path
{

	protected boolean done = false;
	protected boolean pass = false;


	@NotNull
	protected final NodeAStar s;
	@NotNull
	protected final NodeAStar e;
	@NotNull
	protected final Heuristic h;

	@NotNull
	protected final Set<NodeAStar> o = new HashSet<>();
	@NotNull
	protected final Set<NodeAStar> c = new HashSet<>();

	@NotNull
	protected final List<Node> path = new ArrayList<>();


	protected PathAStar(@NotNull final Heuristic heuristic, final int sX, final int sY, final int sZ, final int eX, final int eY, final int eZ)
	{
		this.h = heuristic;

		this.s = createNode(sX, sY, sZ);
		this.e = createNode(eX, eY, eZ);
	}


	@Override
	public void load()
	{
		done = false;
		pass = false;

		if (this.s.equals(this.e))
		{
			done = true;
			pass = true;

			return; // start and end are equal, path "found"
		}

		if (!checkNode(this.s) || !checkNode(this.e))
		{
			done = true;

			return; // start or end are not permitted, path impossible
		}

		boolean sWorks = false;
		boolean eWorks = false;

		for (final Move.Move3D move : Move.Move3D.compound())
		{
			if (checkNode(createNode(this.s.getX() + move.getDeltaX(), this.s.getY() + move.getDeltaY(), this.s.getZ() + move.getDeltaZ())))
			{
				sWorks = true;
			}

			if (checkNode(createNode(this.e.getX() + move.getDeltaX(), this.e.getY() + move.getDeltaY(), this.e.getZ() + move.getDeltaZ())))
			{
				eWorks = true;
			}
		}

		if (!sWorks || !eWorks)
		{
			done = true;

			return; // start or end cannot be moved from, path impossible
		}


		addO(this.s); // add start to open set
	}

	@Override
	public void kill()
	{
		this.o.clear();
		this.c.clear();
	}


	@Override
	public final void step()
	{
		if (this.o.isEmpty() && !done)
		{
			done = true;
			return;
		}

		if (this.o.isEmpty() || done)
		{
			return;
		}


		final Optional<NodeAStar> n = best();
		if (!n.isPresent())
		{
			done = true;

			return; // no best node, path impossible
		}

		remO(n.get());
		addC(n.get());

		if (this.e.equals(n.get()))
		{
			done = true;
			pass = true;

			return; // best node was e, path found
		}


		int newF;
		int newG;
		int newH;

		final NodeAStar      last = n.get();
		final Set<NodeAStar> next = new HashSet<>();

		for (final Move.Move3D move : Move.Move3D.compound())
		{
			final NodeAStar r = createNode(last.getX() + move.getDeltaX(), last.getY() + move.getDeltaY(), last.getZ() + move.getDeltaZ());
			if (!checkNode(r) || this.c.contains(r))
			{
				continue;
			}

			next.add(r);
		}


		for (final NodeAStar node : next)
		{
			if (this.e.equals(node))
			{
				done = true;
				pass = true;

				node.setNext(n.get());

				return;
			}

			newG = node.getGScore() + 1;
			newH = this.h.get(node, e);
			newF = newG + newH;


			if (node.getFScore() == 0 || node.getFScore() > newF)
			{
				addO(node);

				node.setFScore(newF);
				node.setGScore(newG);
				node.setHScore(newH);

				node.setNext(n.get());
			}
		}
	}


	@Override
	public final boolean done()
	{
		return done;
	}

	@Override
	public final boolean pass()
	{
		return pass;
	}


	@NotNull
	@UnmodifiableView
	@Override
	public final List<Node> path()
	{
		if (!this.path.isEmpty() || (done && !pass))
		{
			return Collections.unmodifiableList(this.path);
		}

		NodeAStar n = this.e;

		do
		{
			this.path.add(n);
		}
		while ((n = n.getNext()) != null);

		this.path.add(s);

		Collections.reverse(this.path);

		return Collections.unmodifiableList(this.path);
	}


	@NotNull
	@Contract("_, _, _, -> new")
	@Override
	public abstract NodeAStar createNode(final int x, final int y, final int z);

	protected abstract boolean checkNode(@NotNull final NodeAStar node);


	private void addO(final NodeAStar node)
	{
		this.o.add(node);
	}

	private void remO(final NodeAStar node)
	{
		this.o.remove(node);
	}

	private void addC(final NodeAStar node)
	{
		this.c.add(node);
	}


	@NotNull
	private Optional<NodeAStar> best()
	{
		return this.o.stream().sorted().findFirst(); // maybe open set should be a sorted set?
	}

}
