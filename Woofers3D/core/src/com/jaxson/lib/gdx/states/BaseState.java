package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.backend.objects.MixedObjects;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;

public abstract class BaseState extends GameObject
{
	private Game game;
	private MixedObjects objects;

	protected BaseState(Game game)
	{
		this.game = game;
		this.objects = new MixedObjects(getView());
	}

	public void add(Entity entity)
	{
		objects.add(entity);
	}

	public void add(Sprite sprite)
	{
		objects.add(sprite);
	}

	public void addHud(Sprite sprite)
	{
		objects.add(sprite);
	}

	@Override
	public void dispose()
	{
		objects.dispose();
	}

	public MyEnvironment getEnvironment()
	{
		return objects.getEnvironment();
	}

	public Game getGame()
	{
		return game;
	}

	public int getHeight()
	{
		return getGame().getDisplay().getHeight();
	}

	public View getView()
	{
		return getGame().getView();
	}

	public int getWidth()
	{
		return getGame().getDisplay().getWidth();
	}

	public void remove(Entity entity)
	{
		objects.remove(entity);
	}

	public void remove(Sprite sprite)
	{
		objects.remove(sprite);
	}

	public void removeHud(Sprite sprite)
	{
		objects.removeHud(sprite);
	}

	@Override
	public void render(View view)
	{
		objects.render(view);
	}

	@Override
	public void resize(int width, int height)
	{
		objects.resize(width, height);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		objects.update(dt);
	}
}
