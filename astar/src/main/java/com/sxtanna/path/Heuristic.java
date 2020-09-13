package com.sxtanna.path;

public enum Heuristic
	{

		// manhattan
		M
				{
					@Override
					public int get(final Node node0, final Node node1)
					{
						final int dx = Math.abs(node0.getX() - node1.getX());
						final int dy = Math.abs(node0.getY() - node1.getY());
						final int dz = Math.abs(node0.getZ() - node1.getZ());

						return (int) (VALUE_D0 * (dx + dy + dz));
					}
				},
		// diagonal
		D
				{
					@Override
					public int get(final Node node0, final Node node1)
					{
						final int dx = Math.abs(node0.getX() - node1.getX());
						final int dy = Math.abs(node0.getY() - node1.getY());
						final int dz = Math.abs(node0.getZ() - node1.getZ());

						return (int) (VALUE_D0 * (dx + dy + dz) + (VALUE_D1 - (2 * VALUE_D0)) * Math.min(dx, dz));
					}
				},
		// euclidean
		E
				{
					@Override
					public int get(final Node node0, final Node node1)
					{
						final int dx = Math.abs(node0.getX() - node1.getX());
						final int dy = Math.abs(node0.getY() - node1.getY());
						final int dz = Math.abs(node0.getZ() - node1.getZ());

						final double s2 = Math.sqrt((dx * dx) + (dz * dz));
						final double s3 = Math.sqrt((s2 * s2) + (dy * dy));

						return (int) s3;
					}
				};


		private static final double VALUE_D0 = 1.0;
		private static final double VALUE_D1 = Math.sqrt(2);


		public abstract int get(final Node node0, final Node node1);

	}