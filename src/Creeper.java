import studio.bytesize.ld22.entity.Entity;
import studio.bytesize.ld22.entity.Mob;
import studio.bytesize.ld22.entity.Player;
import studio.bytesize.ld22.gfx.Color;
import studio.bytesize.ld22.gfx.Screen;
import studio.bytesize.ld22.level.tile.Tile;
import studio.bytesize.ld22.sound.Sound;

public class Creeper extends Mob
{
	private static int MAX_FUSE_TIME = 60;
	private static int BLAST_RADIUS = 30;
	private static int BLAST_DAMAGE = 20;

	private int xa, ya;
	private int randomWalkTime = 0;
	private int fuseTime = 0;
	private boolean fuseLit = false;

	public Creeper()
	{
		x = random.nextInt(64 * 16);
		y = random.nextInt(64 * 16);
		spawnChance = 4;
	}

	public void setLvl(int lvl)
	{
		this.lvl = lvl;
		health = maxHealth = lvl * 20;
	}

	public boolean canSpawn(int level)
	{
		return random.nextInt(5) == 0 && level == 0;
	}

	public boolean move(int xa, int ya)
	{
		boolean result = super.move(xa, ya);
		if (xa == 0 && ya == 0) walkDist = 0;
		return result;
	}

	public void tick()
	{
		super.tick();

		if (fuseTime == 0)
		{
			if (!fuseLit)
			{
				if (level.player != null && randomWalkTime == 0)
				{
					int xd = level.player.x - x;
					int yd = level.player.y - y;
					if (xd * xd + yd * yd < 50 * 50)
					{
						xa = 0;
						ya = 0;
						if (xd < 0) xa = -1;
						if (xd > 0) xa = +1;
						if (yd < 0) ya = -1;
						if (yd > 0) ya = +1;
					}
				}

				int speed = tickTime & 1;
				if (!move(xa * speed, ya * speed) || random.nextInt(200) == 0)
				{
					randomWalkTime = 60;
					xa = (random.nextInt(3) - 1) * random.nextInt(2);
					ya = (random.nextInt(3) - 1) * random.nextInt(2);
				}
				if (randomWalkTime > 0) randomWalkTime--;
			}
			else
			{
				// blow up
				int pdx = Math.abs(level.player.x - x);
				int pdy = Math.abs(level.player.y - y);
				if (pdx < BLAST_RADIUS && pdy < BLAST_RADIUS)
				{
					float pd = (float)Math.sqrt(pdx * pdx + pdy * pdy);
					int dmg = (int)(BLAST_DAMAGE * (1 - (pd / BLAST_RADIUS))) + 1;
					level.player.hurt(this, dmg, 0);
					level.player.payStamina(dmg * 2);

					// figure out which tile the mob died on
					int xt = x >> 4;
					int yt = (y - 2) >> 4;

					// change tile to an appropriate crater
					if (lvl == 4)
					{
						level.setTile(xt, yt, Tile.get("infiniteFall"), 0);
					}
					else if (lvl == 3)
					{
						level.setTile(xt, yt, Tile.get("lava"), 0);
					}
					else
					{
						level.setTile(xt, yt, Tile.get("hole"), 0);
					}

					super.die();
				}
				else
				{
					fuseTime = 0;
					fuseLit = false;
				}
			}
		}
		else
		{
			fuseTime--;
		}

	}

	public void render(Screen screen)
	{
		int xt = 0;
		int yt = 0;

		if (walkDist > 0)
		{
			if (random.nextInt(2) == 0)
			{
				xt += 2;
			}
			else
			{
				xt += 4;
			}
		}
		else
		{
			xt = 4;
		}

		int xo = x - 8;
		int yo = y - 11;

		int col = Color.get(-1, 10, 252, 242);
		if (lvl == 2) col = Color.get(-1, 200, 262, 232);
		if (lvl == 3) col = Color.get(-1, 200, 272, 222);
		if (lvl == 4) col = Color.get(-1, 200, 292, 282);

		if (fuseLit)
		{
			if (fuseTime % 6 == 0)
			{
				col = Color.get(-1, 252, 252, 252);
			}
		}

		if (hurtTime > 0)
		{
			col = Color.get(-1, 555, 555, 555);
		}

		screen.render(xo + 0, yo + 0, xt + yt * 32, col, 0, CreeperPlugin.creeperSheet);
		screen.render(xo + 8, yo + 0, xt + 1 + yt * 32, col, 0, CreeperPlugin.creeperSheet);
		screen.render(xo + 0, yo + 8, xt + (yt + 1) * 32, col, 0, CreeperPlugin.creeperSheet);
		screen.render(xo + 8, yo + 8, xt + 1 + (yt + 1) * 32, col, 0, CreeperPlugin.creeperSheet);
	}

	protected void touchedBy(Entity entity)
	{
		if (entity instanceof Player)
		{
			if (fuseTime == 0)
			{
				Sound.play("fuse");
				fuseTime = MAX_FUSE_TIME;
				fuseLit = true;
			}
			entity.hurt(this, 1, dir);
		}
	}

	protected void die()
	{
		super.die();

		int count = random.nextInt(2) + 1;
		for (int i = 0; i < count; i++)
		{
			// level.add(new ItemEntity(new ResourceItem(Resource.cloth), x + random.nextInt(11) - 5, y + random.nextInt(11) - 5));
		}

		if (level.player != null)
		{
			level.player.score += 75 * lvl;
		}
	}

}