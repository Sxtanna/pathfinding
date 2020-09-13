package com.sxtanna.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Move
{

	enum Move2D implements Move
	{
		N(+0, -1),
		S(+0, +1),
		E(+1, +0),
		W(-1, +0);


		private final int deltaX;
		private final int deltaZ;


		Move2D(final int deltaX, final int deltaZ)
		{
			this.deltaX = deltaX;
			this.deltaZ = deltaZ;
		}


		@Contract(pure = true)
		public int getDeltaX()
		{
			return deltaX;
		}

		@Contract(pure = true)
		public int getDeltaZ()
		{
			return deltaZ;
		}

	}

	enum Move3D implements Move
	{
		N(+0, +0, -1),
		S(+0, +0, +1),
		E(+1, +0, +0),
		W(-1, +0, +0),

		U(+0, +1, +0),
		D(+0, -1, +0),

		NW(N, W),
		NE(N, E),
		SW(S, W),
		SE(S, E),


		UN(U, N),
		US(U, S),
		UE(U, E),
		UW(U, W),

		UNW(U, NW),
		UNE(U, NE),
		USW(U, SW),
		USE(U, SE),


		DN(D, N),
		DS(D, S),
		DE(D, E),
		DW(D, W),

		DNW(D, NW),
		DNE(D, NE),
		DSW(D, SW),
		DSE(D, SE);


		private final int deltaX;
		private final int deltaY;
		private final int deltaZ;


		Move3D(final int deltaX, final int deltaY, final int deltaZ)
		{
			this.deltaX = deltaX;
			this.deltaY = deltaY;
			this.deltaZ = deltaZ;
		}

		Move3D(@NotNull final Move3D... combo)
		{
			int x = 0;
			int y = 0;
			int z = 0;

			for (final Move3D move : combo)
			{
				x += move.deltaX;
				y += move.deltaZ;
				z += move.deltaZ;
			}

			this.deltaX = x;
			this.deltaY = y;
			this.deltaZ = z;
		}


		@Contract(pure = true)
		public int getDeltaX()
		{
			return deltaX;
		}

		@Contract(pure = true)
		public int getDeltaY()
		{
			return deltaY;
		}

		@Contract(pure = true)
		public int getDeltaZ()
		{
			return deltaZ;
		}


		private static final Move3D[] COMPOUND = values();
		private static final Move3D[] CARDINAL = {Move3D.N,
												  Move3D.E,
												  Move3D.S,
												  Move3D.W,
												  Move3D.U,
												  Move3D.D};


		@Contract(pure = true)
		public static Move3D[] cardinal()
		{
			return CARDINAL;
		}

		@Contract(pure = true)
		public static Move3D[] compound()
		{
			return COMPOUND;
		}

	}

}