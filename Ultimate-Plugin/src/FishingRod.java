import studio.bytesize.ld22.entity.ItemEntity;
import studio.bytesize.ld22.entity.Player;
import studio.bytesize.ld22.gfx.Color;
import studio.bytesize.ld22.gfx.Font;
import studio.bytesize.ld22.gfx.Screen;
import studio.bytesize.ld22.item.Item;
import studio.bytesize.ld22.item.ResourceItem;
import studio.bytesize.ld22.item.resource.Resource;
import studio.bytesize.ld22.level.Level;
import studio.bytesize.ld22.level.tile.Tile;

import java.util.Random;


public class FishingRod extends Item
{
	private Random random = new Random();

	public FishingRod()
	{
		super();
		sheet = UltimatePlugin.ultimateSheet;
	}

	public int getColor()
	{
		return Color.get(-1, 100, 321, 555);
	}

	public int getSprite()
	{
		return 0 + 0 * 32;
	}

	public void renderIcon(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0, UltimatePlugin.ultimateSheet);
	}

	public void renderInventory(Screen screen, int x, int y)
	{
		screen.render(x, y, getSprite(), getColor(), 0, UltimatePlugin.ultimateSheet);
		Font.draw(this.getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555));
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir)
	{
		// If tile being hit is water then drop raw fish, star fish, or nothing.
		if (tile.id == Tile.get("water").id)
		{
			if (random.nextInt(35) != 0) return false;

			if (player.payStamina(3))
			{
				int count = 1;
				for (int i = 0; i < count; i++)
				{
					if (random.nextInt(3) != 0) level.add(new ItemEntity(new ResourceItem(Resource.get("R.fish")), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3));
					else level.add(new ItemEntity(new ResourceItem(Resource.get("star")), xt * 16 + random.nextInt(10) + 3, yt * 16 + random.nextInt(10) + 3));

				}
				return true;
			}
		}
		return false;
	}

	public String getName()
	{
		return "Fishn Rod";
	}
}
