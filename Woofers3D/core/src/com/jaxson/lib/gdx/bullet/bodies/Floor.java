package com.jaxson.lib.gdx.bullet.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Floor extends RigidBox
{
	private static final float THICKNESS = 1f / 3f;
	private static final float WIDTH = 100f;
	private static final float HEIGHT = 100f;
	private static final float MASS = 0f;

	public Floor()
	{
		this(WIDTH, HEIGHT, COLOR);
	}

	public Floor(float width, float height)
	{
		this(width, height, COLOR);
	}

	public Floor(float width, float height, Color color)
	{
		super(color);
		setMass(MASS);
		setScale(new Vector3(width, THICKNESS, height));
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void update(float dt)
	{

	}
}
