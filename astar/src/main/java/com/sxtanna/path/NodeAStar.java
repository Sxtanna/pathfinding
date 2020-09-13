package com.sxtanna.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class NodeAStar implements Node, Comparable<NodeAStar>
{

	private final int x;
	private final int y;
	private final int z;

	private int fScore;
	private int gScore;
	private int hScore;


	@Nullable
	private NodeAStar next;


	@Contract(pure = true)
	private NodeAStar(final int x, final int y, final int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	@Contract(pure = true)
	@Override
	public int getX()
	{
		return x;
	}

	@Contract(pure = true)
	@Override
	public int getY()
	{
		return y;
	}

	@Contract(pure = true)
	@Override
	public int getZ()
	{
		return z;
	}


	@Contract(pure = true)
	public int getFScore()
	{
		return fScore;
	}

	@Contract(mutates = "this")
	public void setFScore(final int fScore)
	{
		this.fScore = fScore;
	}

	@Contract(pure = true)
	public int getGScore()
	{
		return gScore;
	}

	@Contract(mutates = "this")
	public void setGScore(final int gScore)
	{
		this.gScore = gScore;
	}

	@Contract(pure = true)
	public int getHScore()
	{
		return hScore;
	}

	@Contract(mutates = "this")
	public void setHScore(final int hScore)
	{
		this.hScore = hScore;
	}


	@Nullable
	public NodeAStar getNext()
	{
		return next;
	}

	@Contract(mutates = "this")
	public void setNext(@NotNull final NodeAStar next)
	{
		this.next = next;
	}


	@Contract(pure = true)
	@Override
	public int compareTo(final NodeAStar that)
	{
		return Integer.compare(this.fScore, that.fScore);
	}


	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NodeAStar))
		{
			return false;
		}

		final NodeAStar that = (NodeAStar) o;
		return this.x == that.x &&
			   this.y == that.y &&
			   this.z == that.z;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.x,
							this.y,
							this.z);
	}

	@Override
	public String toString()
	{
		return String.format("NodeAStar[{%d, %d, %d}|{%d, %d, %d}]",
							 this.x,
							 this.y,
							 this.z,
							 this.fScore,
							 this.gScore,
							 this.hScore);
	}


	@NotNull
	@Contract(value = "_, _, _ -> new", pure = true)
	public static NodeAStar of(final int x, final int y, final int z)
	{
		return new NodeAStar(x, y, z);
	}

}
