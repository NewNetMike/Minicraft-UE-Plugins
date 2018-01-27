import studio.bytesize.ld22.entity.ItemEntity;
import studio.bytesize.ld22.entity.Mob;
import studio.bytesize.ld22.entity.Player;
import studio.bytesize.ld22.gfx.Color;
import studio.bytesize.ld22.gfx.Screen;
import studio.bytesize.ld22.item.Item;
import studio.bytesize.ld22.item.ResourceItem;
import studio.bytesize.ld22.item.ToolItem;
import studio.bytesize.ld22.item.ToolType;
import studio.bytesize.ld22.item.resource.Resource;
import studio.bytesize.ld22.level.Level;
import studio.bytesize.ld22.level.tile.GrassTile;
import studio.bytesize.ld22.level.tile.Tile;

// Can create specific flower tile directly, or by using the
// flowerCol level data notch left in the level gen code
public class MushroomTile extends GrassTile
{
	int type = -1;
	int flowerColor;
	
	// flower color 0: daisy
	// flower color 1: rose
	// flower color 2: salvia
	// flower color 3: black rose

	public MushroomTile()
	{
		super();
		connectsToGrass = true;
		type = -1;
	}

	public MushroomTile(int type)
	{
		super();
		connectsToGrass = true;
		this.type = type;
	}

	public void render(Screen screen, Level level, int x, int y)
	{
		super.render(screen, level, x, y); // Render the grass
		int data = level.getData(x, y);
		int shape = (data / 16) % 2;
		flowerColor = data % 16;
		if (type != -1) flowerColor = type;

		int flowerCol = 0;
		if (flowerColor == 0 || flowerColor == 3) flowerCol = Color.get(533, level.grassColor, 500, 220);
		if (flowerColor == 1 || flowerColor == 2) flowerCol = Color.get(110, level.grassColor, 110, 321);

		// Unique flower shapes (TL, TR, BL, BR)
		if (shape == 0) screen.render(x * 16 + 0, y * 16 + 0, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
		if (shape == 1) screen.render(x * 16 + 8, y * 16 + 0, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
		if (shape == 2) screen.render(x * 16 + 0, y * 16 + 8, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
		if (shape == 3) screen.render(x * 16 + 8, y * 16 + 8, 4 + 0 * 32, flowerCol, 0, UltimatePlugin.ultimateSheet);
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir)
	{
		int data = level.getData(x, y);
		flowerColor = data % 16;
		if (type != -1) flowerColor = type;

		int count = random.nextInt(2) + 1;
		for (int i = 0; i < count; i++)
		{
			if (flowerColor == 0 || flowerColor == 3) level.add(new ItemEntity(new ResourceItem(Resource.get("r.mshrm")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			if (flowerColor == 1 || flowerColor == 2) level.add(new ItemEntity(new ResourceItem(Resource.get("b.mshrm")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
		}
		level.setTile(x, y, Tile.get("grass"), 0);
	}

	public boolean interact(Level level, int x, int y, Player player, Item item, int attackDir)
	{
		if (item instanceof ToolItem)
		{
			ToolItem tool = (ToolItem)item;
			if (tool.type == ToolType.shovel)
			{
				if (player.payStamina(4 - tool.level))
				{
					level.setTile(x, y, Tile.get("dirt"), 0);

					int data = level.getData(x, y);
					flowerColor = data % 16;
					if (type != -1) flowerColor = type;

					if (flowerColor == 0 || flowerColor == 3) level.add(new ItemEntity(new ResourceItem(Resource.get("r.mshrm")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
					if (flowerColor == 1 || flowerColor == 2) level.add(new ItemEntity(new ResourceItem(Resource.get("b.mshrm")), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
					return true;
				}
			}
		}
		return false;
	}
}
