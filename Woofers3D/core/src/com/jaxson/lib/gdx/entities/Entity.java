package com.jaxson.lib.gdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public abstract class Entity extends ModelInstance
{
	protected static final Vector3 LOCATION = Vector3.Zero;

	public Entity(String modelPath)
	{
		this(modelPath, LOCATION);
	}

	public Entity(String modelPath, Vector3 location)
	{
		this(new ObjLoader().loadModel(Gdx.files.internal(modelPath)), location);
	}

	public Entity(Model model)
	{
		this(model, LOCATION);
	}

	public Entity(Model model, Vector3 location)
	{
		super(model, location);
	}

	public void dispose()
	{
		model.dispose();
	}

	public Model getModel()
	{
		return model;
	}

	public Vector3 getLocation()
	{
		float[] matrix = transform.getValues();
		return new Vector3(matrix[12], matrix[13], matrix[14]);
	}

	protected abstract void input();

	public void update(float dt)
	{
		input();
	}
}
