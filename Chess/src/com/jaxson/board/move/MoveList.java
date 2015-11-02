package com.jaxson.board.move;

import com.jaxson.util.MyArrayList;

public class MoveList extends MyArrayList<Move>
{
	public MoveList()
	{
		super();
	}

	@Override
	public boolean add(Move move)
	{
		if (move == null) return false;
		if (move.isEmpty()) return false;
		return 	super.add(move);
	}

	@Override
	public String toString()
	{
		if (isEmpty()) return "\n" + "NO MOVES";
		String string = "\n";
		string += "-----POSSIBLE MOVES-----";
		for (Move move: this)
		{
			string += "\n";
			string += move.toString();
		}
		return string;
	}

}
