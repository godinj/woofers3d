package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyHandler extends Keys implements InputProcessor
{
	public static final int UP = W;
	public static final int FORWARD = W;
	public static final int DOWN = S;
	public static final int BACK = S;
	public static final int BACKWARD = S;
	public static final int LEFT = A;
	public static final int RIGHT = D;
	public static final int UP_ARROW = UP;
	public static final int DOWN_ARROW = DOWN;
	public static final int LEFT_ARROW = LEFT;
	public static final int RIGHT_ARROW = RIGHT;
	public static final int PAUSE = ESCAPE;

	public static final int MOUSE_LEFT = Buttons.LEFT;
	public static final int MOUSE_RIGHT = Buttons.RIGHT;
	public static final int MOUSE_MIDDLE = Buttons.MIDDLE;
	public static final int MOUSE_BACK = Buttons.BACK;
	public static final int MOUSE_FORWARD = Buttons.FORWARD;

	public static final int[] ANY_RIGHT = { RIGHT, RIGHT_ARROW };
	public static final int[] ANY_LEFT = { LEFT, LEFT_ARROW };
	public static final int[] ANY_UP = { UP, UP_ARROW };
	public static final int[] ANY_DOWN = { DOWN, DOWN_ARROW };
	public static final int[] ANY_BUTTON = { MOUSE_LEFT, MOUSE_RIGHT, MOUSE_MIDDLE, MOUSE_BACK, MOUSE_FORWARD };
	public static final int[] PRIMARY_MOUSE = { MOUSE_LEFT, MOUSE_RIGHT, MOUSE_MIDDLE };
	public static final int[] FULLSCREEN = { F11, BACKSLASH };
	public static final int[] ALT = { ALT_LEFT, ALT_RIGHT };

	private static final float MOUSE_SCALE = 1f / 10f;
	private static final float SENSITIVITY = 1.05f;
	private static final boolean INVERT_MOUSE = true;

	private static final int KEY_SIZE = 256;
	private static boolean[] keys, prevKeys;
	private static Vector2 mouse, prevMouse;

	public KeyHandler()
	{
		keys = new boolean[KEY_SIZE];
		prevKeys = new boolean[KEY_SIZE];
		mouse = new Vector2();
		prevMouse = new Vector2();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		keys[keycode] = true;
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		keys[keycode] = false;
		return true;
	}

	@Override
	public boolean mouseMoved(int x, int y)
	{
		mouse.x = x;
		mouse.y = y;
		return true;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		mouse.x = x;
		mouse.y = y;
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		return true;
	}

	public static boolean anyKey()
	{
		if (isButtonPressed(ANY_BUTTON)) return true;
		if (isDown(ANY_KEY)) return true;
		return false;
	}

	public static Vector2 getDeltaMouse()
	{
		return getMouse().sub(getPrevMouse());
	}

	private static Input getInput()
	{
		return Gdx.input;
	}

	public static Vector2 getMouse()
	{
		return mouse.cpy();
	}

	public static Vector2 getPrevMouse()
	{
		return prevMouse.cpy();
	}

	public static Vector2 getScaledMouse()
	{
		if (!Gdx.input.isCursorCatched()) return Vector2.Zero;
		final float scale = MOUSE_SCALE * SENSITIVITY;
		Vector2 mouse = getDeltaMouse();
		mouse.scl(scale, -scale);
		if (INVERT_MOUSE) mouse.scl(-1f, -1f);
		return mouse;
	}

	public static boolean isButtonPressed(int button)
	{
		return getInput().isButtonPressed(button);
	}

	public static boolean isButtonPressed(int[] buttons)
	{
		for (int button: buttons)
		{
			if (isButtonPressed(button)) return true;
		}
		return false;
	}

	public static boolean isClicked()
	{
		return isButtonPressed(PRIMARY_MOUSE);
	}

	public static boolean isDown(int keycode)
	{
		if (keycode == ANY_KEY) return true;
		return keys[keycode];
	}

	public static boolean isDown(int[] keycodes)
	{
		for (int keycode: keycodes)
		{
			if (isDown(keycode)) return true;
		}
		return false;
	}

	public static boolean isPressed(int keycode)
	{
		return keys[keycode] && !prevKeys[keycode];
	}

	public static boolean isPressed(int[] keycodes)
	{
		for (int keycode: keycodes)
		{
			if (isPressed(keycode)) return true;
		}
		return false;
	}

	public static boolean isReleased(int keycode)
	{
		return !keys[keycode] && prevKeys[keycode];
	}

	public static boolean isReleased(int[] keycodes)
	{
		for (int keycode: keycodes)
		{
			if (isReleased(keycode)) return true;
		}
		return false;
	}

	public static boolean justTouched()
	{
		return getInput().justTouched();
	}

	public static void reset()
	{
		for (int i = 0; i < KEY_SIZE; i++)
		{
			keys[i] = false;
			prevKeys[i] = false;
		}
	}

	public static void update(float dt)
	{
		for (int i = 0; i < KEY_SIZE; i++)
		{
			prevKeys[i] = keys[i];
		}
		prevMouse = mouse.cpy();
	}
}
