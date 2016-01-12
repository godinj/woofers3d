package com.jaxson.lib.gdx.states.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;
import com.jaxson.lib.gdx.graphics.g3d.Entity;

public class MixedRenderer
{
	private SpriteRenderer spriteRenderer;
	private ModelRenderer modelRenderer;

	public MixedRenderer()
	{
		this.spriteRenderer = new SpriteRenderer();
		this.modelRenderer = new ModelRenderer();
	}

	public void add(Entity model)
	{
		modelRenderer.add(model);
	}

	public void add(GdxSprite sprite)
	{
		spriteRenderer.add(sprite);
	}

	public void dispose()
	{
		spriteRenderer.dispose();
		modelRenderer.dispose();
	}

	public boolean isEmpty()
	{
		return spriteRenderer.isEmpty() && modelRenderer.isEmpty();
	}

	public void remove(Entity model)
	{
		modelRenderer.remove(model);
	}

	public void remove(GdxSprite sprite)
	{
		spriteRenderer.add(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		spriteRenderer.render(spriteBatch);
		modelRenderer.render(modelBatch, camera);
	}

	public void update(float dt)
	{
		spriteRenderer.update(dt);
		modelRenderer.update(dt);
	}
}
