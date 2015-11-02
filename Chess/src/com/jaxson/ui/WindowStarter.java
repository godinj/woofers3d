package com.jaxson.ui;

import com.jaxson.ui.board.ChessWindow;

public class WindowStarter
{
	public WindowStarter(int width, int height)
	{
		javax.swing.SwingUtilities.invokeLater(new WindowStarterRunnable(width, height));
	}
}

class WindowStarterRunnable implements Runnable
{
	private int width, height;

	public WindowStarterRunnable(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public void run()
	{
		new ChessWindow(width, height);
	}
}
