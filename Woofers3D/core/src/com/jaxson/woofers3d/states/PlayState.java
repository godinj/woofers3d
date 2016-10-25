package com.jaxson.woofers3d.states;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.bullet.BulletState;
import com.jaxson.lib.gdx.bullet.simulation.bodies.Floor;
import com.jaxson.lib.gdx.bullet.simulation.bodies.RigidBox;
import com.jaxson.lib.gdx.bullet.simulation.bodies.RigidSphere;
import com.jaxson.lib.gdx.bullet.simulation.bodies.SoftBox;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.EntityBody;
import com.jaxson.lib.gdx.bullet.simulation.bodies.types.RigidBody;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.graphics.color.RandomColor;
import com.jaxson.lib.gdx.graphics.g2d.FPSCounter;
import com.jaxson.lib.gdx.graphics.g2d.Text;
import com.jaxson.lib.gdx.graphics.views.TargetCamera;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.Mouse;
import com.jaxson.lib.gdx.math.random.RandomVector3;
import com.jaxson.lib.math.random.RandomNumber;
import com.jaxson.lib.util.Optional;
import com.jaxson.woofers3d.entities.Player;

public class PlayState extends BulletState
{
	private static final int BOX_AMOUNT = 25;
	private static final int SPHERE_AMOUNT = 25;
	private static final float IMPULSE_SPEED = 45f;

	private Floor floor;
	private RigidBox[] boxs;
	private RigidSphere[] spheres;
	private SoftBox softBox;
	private Player player;
	private TargetCamera camera;
	private Text text;

	private Mouse mouse;

	public PlayState(Game game)
	{
		super(game);
		setSubState(new PauseState(game));

		camera = new TargetCamera(width(), height());
		applyPhysics(camera);
		view().modelView().setCamera(camera);

		// load(new GdxFile("btscene1.g3dj"));

		floor = new Floor();
		applyPhysics(floor);
		add(floor);

		RandomNumber mass = new RandomNumber(0.9f, 1.2f);
		boxs = new RigidBox[BOX_AMOUNT];
		for (int i = 0; i < BOX_AMOUNT; i ++)
		{
			boxs[i] = new RigidBox(
					new RandomColor(new MyColor(255, 95, 0),
							new MyColor(255, 165, 50)));
			boxs[i].setSize(new RandomVector3(1f, 4f));
			boxs[i].moveTo(new RandomVector3(6f, 30f));
			boxs[i].setMass(mass.floatValue());
			applyPhysics(boxs[i]);
			add(boxs[i]);
		}

		if (game().isDesktop())
		{
			spheres = new RigidSphere[SPHERE_AMOUNT];
			for (int i = 0; i < SPHERE_AMOUNT; i ++)
			{
				spheres[i] = new RigidSphere(new RandomColor());
				spheres[i].moveTo(new RandomVector3(6f, 30f));
				spheres[i].setSize(new Vector3(2f, 2f, 2f));
				spheres[i].setMass(mass.floatValue());
				applyPhysics(spheres[i]);
				add(spheres[i]);
			}
		}

		softBox = new SoftBox(physicsWorld());
		applyPhysics(softBox);
		add(softBox);

		player = new Player(camera);
		applyPhysics(player);
		add(player);

		addHud(new FPSCounter(game()));

		text = new Text("");
		text.setLocation(20, 38);
		addHud(text);

		mouse = Inputs.mouse();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input(float dt)
	{
		if (Inputs.touchScreen().justTouched())
		{
			Ray ray = player.forwardRay();
			if (Inputs.touchScreen().exists())
			{
				ray = camera.getPickRay(mouse.x(), mouse.y());
			}
			Optional<EntityBody> body = physicsWorld().rayTrace(ray);
			if (body.exists())
			{
				if (body.unwrap() instanceof RigidBody)
				{
					RigidBody rigidBody = (RigidBody) body.unwrap();
					rigidBody.applyCentralImpulse(ray, IMPULSE_SPEED);
				}
			}
		}
	}

	@Override
	public void render(View view)
	{
		super.render(view);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		text.setText(player.accelerometer().toString());
	}
}
