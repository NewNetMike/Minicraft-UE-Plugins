import studio.bytesize.ld22.entity.ItemEntity;
import studio.bytesize.ld22.entity.Player;
import studio.bytesize.ld22.gfx.Color;
import studio.bytesize.ld22.gfx.Font;
import studio.bytesize.ld22.gfx.Screen;
import studio.bytesize.ld22.item.Item;
import studio.bytesize.ld22.item.ToolItem;
import studio.bytesize.ld22.level.Level;
import studio.bytesize.ld22.level.tile.Tile;

public class Bucket extends Item
{
	public static final int EMPTY = 0;
	public static final int WATER = 1;
	public static final int LAVA = 2;

	public static final int MAX_LEVEL = 3;
	public static final String[] LEVEL_NAMES = { "", "W.", "L." }; // Bucket names
	public static final int[] LEVEL_COLORS = { 000, 005, 500 }; // Bucket contents color

	public int level = 0;
	
	public Bucket()
	{
		this(0);
	}

	public Bucket(int level)
	{
		this.level = level;
		sheet = UltimatePlugin.ultimateSheet;
	}

	public int getColor()
	{
		return Color.get(-1, LEVEL_COLORS[level], 333, 333);
	}

	public int getSprite()
	{
		return 36;
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
		if (tile.id == Tile.get("water").id && this.level == Bucket.EMPTY) // Collect water
		{
			if (player.payStamina(2))
			{
				this.level = Bucket.WATER;
				level.setTile(xt, yt, Tile.get("hole"), 0);
				return true;
			}
		}
		else if (tile.id == Tile.get("lava").id && this.level == Bucket.EMPTY) // Collect lava
		{
			if (player.payStamina(2))
			{
				this.level = Bucket.LAVA;
				level.setTile(xt, yt, Tile.get("hole"), 0);
				return true;
			}
		}
		else if (tile.id == Tile.get("hole").id && this.level != Bucket.EMPTY) // Place water or lava
		{
			if (player.payStamina(2))
			{
				int lvl = this.level;
				this.level = Bucket.EMPTY;
				if (lvl == Bucket.WATER)
				{
					level.setTile(xt, yt, Tile.get("water"), 0);
					return true;
				}
				else if (lvl == Bucket.LAVA)
				{
					level.setTile(xt, yt, Tile.get("lava"), 0);
					return true;
				}
			}
		}
		return false;
	}

	public void onTake(ItemEntity itemEntity)
	{

	}

	public String getName()
	{
		return LEVEL_NAMES[level] + "bucket";
	}

	public boolean matches(Item item)
	{
		if (item instanceof ToolItem)
		{
			ToolItem other = (ToolItem)item;
			if (other.level != level) return false;
			return true;
		}
		return false;
	}
}
