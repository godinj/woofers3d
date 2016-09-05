package com.jaxson.lib.gdx.bullet.simulation.bodies.types;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.jaxson.lib.gdx.bullet.simulation.collision.types.ConvexShape;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Key;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.math.GdxMath;
import com.jaxson.lib.math.MyMath;

public abstract class PlayerBody extends ShapeBody<btPairCachingGhostObject, ConvexShape>
{
	private static final float GHOST_MASS = -1f;
	private static final float STEP_HEIGHT = 1f / 4f;
	private static final float SPEED = 0.2f;
	private static final float ROTATION_SPEED = 2f;
	private static final float GRAVITY_SCALE = 3f;
	private static final float GRAVITY = GdxMath.GRAVITY_EARTH * GRAVITY_SCALE;
	private static final float FALL_SPEED = 55f;
	private static final float JUMP_SPEED = 10f;

	private btKinematicCharacterController characterController;
	private btGhostPairCallback callback;
	private Vector3 walkDirection;
	private float speed;
	private float rotationSpeed;
	private float stepHeight;

	private Keyboard keyboard;
	private Key forwardKey;
	private Key backwardKey;
	private Key leftKey;
	private Key rightKey;
	private Key jumpKey;

	public PlayerBody(Model model, ConvexShape shape)
	{
		super(model, shape, GHOST_MASS);
		setBody(new btPairCachingGhostObject());
		setCollisionShape(shape);
		setStepHeight(STEP_HEIGHT);
		this.callback = new btGhostPairCallback();
		this.walkDirection = new Vector3();
		setGravity(GRAVITY);
		setSpeed(SPEED);
		setRotationSpeed(ROTATION_SPEED);
		setJumpSpeed(JUMP_SPEED);
		setFallSpeed(FALL_SPEED);

		this.keyboard = Inputs.getKeyboard();
		this.forwardKey = keyboard.getKey("W");
		this.backwardKey = keyboard.getKey("S");
		this.leftKey = keyboard.getKey("A");
		this.rightKey = keyboard.getKey("D");
		this.jumpKey = keyboard.getKey("Space");
		System.out.println(forwardKey);
	}

	public PlayerBody(String modelPath, ConvexShape shape)
	{
		this(readModel(modelPath), shape);
	}

	public boolean canJump()
	{
		return getCharacterController().canJump();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	public btGhostPairCallback getCallback()
	{
		return callback;
	}

	public btKinematicCharacterController getCharacterController()
	{
		return characterController;
	}

	public float getGravity()
	{
		return getCharacterController().getGravity();
	}

	public float getMaxSlope()
	{
		return getMaxSlopeRad() * MyMath.RADIANS_TO_DEGREES;
	}

	public float getMaxSlopeRad()
	{
		return getCharacterController().getMaxSlope();
	}

	public float getRotationSpeed()
	{
		return rotationSpeed;
	}

	public float getSpeed()
	{
		return speed;
	}

	public float getStepHeight()
	{
		return stepHeight;
	}

	public void jump()
	{
		getCharacterController().jump();
	}

	public boolean onGround()
	{
		return getCharacterController().onGround();
	}

	public void setFallSpeed(float fallSpeed)
	{
		getCharacterController().setFallSpeed(fallSpeed);
	}

	public void setGravity(float gravity)
	{
		getCharacterController().setGravity(gravity);
	}

	public void setJumpSpeed(float jumpSpeed)
	{
		getCharacterController().setJumpSpeed(jumpSpeed);
	}

	@Override
	public void setLocation(Vector3 location)
	{
		super.setLocation(location);
		getCharacterController().warp(location);
	}

	public void setMaxSlope(float maxSlope)
	{
		setMaxSlope(maxSlope * MyMath.DEGREES_TO_RADIANS);
	}

	public void setMaxSlopeRad(float maxSlope)
	{
		getCharacterController().setMaxSlope(maxSlope);
	}

	public void setRotationSpeed(float rotationSpeed)
	{
		this.rotationSpeed = rotationSpeed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	public void setStepHeight(float stepHeight)
	{
		this.stepHeight = stepHeight;
		this.characterController = createController(stepHeight);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}

	@Override
	protected void input(float dt)
	{
		walkDirection.setZero();
		if (keyboard.exists())
		{
			if (onGround())
			{
				if (leftKey.isDown())
				{
					rotate(getRotationSpeed(), 0f, 0f);
				}
				if (rightKey.isDown())
				{
					rotate(-getRotationSpeed(), 0f, 0f);
				}
			}
			if (forwardKey.isDown())
			{
				walkDirection.add(getDirection());
			}
			if (backwardKey.isDown())
			{
				walkDirection.sub(getDirection());
			}
			if (jumpKey.isDown())
			{
				jump();
			}
		}
		if (Inputs.getTouchScreen().exists())
		{
			if (Inputs.getTouchScreen().justTouched())
			{
				jump();
			}
		}
		if (Inputs.getAccelerometer().exists())
		{
			if (Inputs.getAccelerometer().isAccelerometerForward())
			{
				walkDirection.add(getDirection());
				walkDirection.scl(Inputs.getAccelerometer().getAccelerometerForward());
			}
			if (Inputs.getAccelerometer().isAccelerometerBack())
			{
				walkDirection.sub(getDirection());
				walkDirection.scl(Inputs.getAccelerometer().getAccelerometerBack());
			}
		}
		walkDirection.scl(getSpeed());
		getCharacterController().setWalkDirection(walkDirection);
		bodyToTransform();
	}

	private btKinematicCharacterController createController(float stepHeight)
	{
		ConvexShape shape = getCollisionShape();
		return new btKinematicCharacterController(getBody(), shape.getCollisionShape(), stepHeight);
	}
}
