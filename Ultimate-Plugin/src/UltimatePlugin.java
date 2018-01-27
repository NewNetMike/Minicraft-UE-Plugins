import net.xeoh.plugins.base.annotations.PluginImplementation;
import studio.bytesize.ld22.Game;
import studio.bytesize.ld22.MinicraftPlugin;
import studio.bytesize.ld22.crafting.Crafting;
import studio.bytesize.ld22.crafting.FurnitureRecipe;
import studio.bytesize.ld22.crafting.ItemRecipe;
import studio.bytesize.ld22.crafting.ResourceRecipe;
import studio.bytesize.ld22.gfx.Color;
import studio.bytesize.ld22.gfx.SpriteSheet;
import studio.bytesize.ld22.item.resource.DyeResource;
import studio.bytesize.ld22.item.resource.FoodResource;
import studio.bytesize.ld22.item.resource.PlantableResource;
import studio.bytesize.ld22.item.resource.Resource;
import studio.bytesize.ld22.level.Level;
import studio.bytesize.ld22.level.levelgen.CustomLevelGen;
import studio.bytesize.ld22.level.tile.BetterFlowerTile;
import studio.bytesize.ld22.level.tile.Tile;

import javax.imageio.ImageIO;
import java.util.Random;

@PluginImplementation
public class UltimatePlugin implements MinicraftPlugin
{
	public static SpriteSheet ultimateSheet;
	public static SpriteSheet icons2;
	public static SpriteSheet icons3;
	private static final Random random = new Random();

	public void onLoad(Game game)
	{
		Tile.load("flower", new BetterFlowerTile());
		Tile.load("mushroom", new MushroomTile());
		Tile.load("r.mushroom", new MushroomTile(0));
		Tile.load("b.mushroom", new MushroomTile(1));

		Tile.load("daisy", new BetterFlowerTile(0));
		Tile.load("rose", new BetterFlowerTile(1));
		Tile.load("salvia", new BetterFlowerTile(2));
		Tile.load("b.rose", new BetterFlowerTile(3));
		Tile.load("woodtile", new WoodTile());
		Tile.load("stonewalltile", new StoneWallTile());

		Resource.load(new PlantableResource("Wood", 1 + 4 * 32, Color.get(-1, 200, 531, 430), "woodtile", "grass", "dirt"));
		Resource.load(new PlantableResource("Stone", 2 + 4 * 32, Color.get(-1, 111, 333, 555), "stonewalltile", "grass", "dirt"));

		
		Resource.load(new PlantableResource("daisy", 0 + 4 * 32, Color.get(-1, 10, 555, 440), "daisy", "grass"));
		Resource.load(new PlantableResource("rose", 0 + 4 * 32, Color.get(-1, 10, 511, 400), "rose", "grass"));
		Resource.load(new PlantableResource("salvia", 0 + 4 * 32, Color.get(-1, 10, 115, 445), "salvia", "grass"));
		Resource.load(new PlantableResource("b.rose", 0 + 4 * 32, Color.get(-1, 10, 111, 000), "b.rose", "grass"));

		Resource.load(new DyeResource("white", 2 + 4 * 32, 555));
		Resource.load(new DyeResource("red", 2 + 4 * 32, 400));
		Resource.load(new DyeResource("blue", 2 + 4 * 32, 115));
		Resource.load(new DyeResource("black", 2 + 4 * 32, 111));
		Resource.load(new DyeResource("green", 2 + 4 * 32, 40));
		Resource.load(new DyeResource("pink", 2 + 4 * 32, 522));
		Resource.load(new DyeResource("purple", 2 + 4 * 32, 313));
		Resource.load(new DyeResource("yellow", 2 + 4 * 32, 440));
		Resource.load(new DyeResource("orange", 2 + 4 * 32, 520));
		Resource.load(new DyeResource("gray", 2 + 4 * 32, 222));

		try
		{
			ultimateSheet = new SpriteSheet(ImageIO.read(this.getClass().getResource("/ultimate.png")));
			icons2 = new SpriteSheet(ImageIO.read(this.getClass().getResource("/icons2.png")));
			icons3 = new SpriteSheet(ImageIO.read(this.getClass().getResource("/icons3.png")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Resource.load(new FoodResource("r.mshrm", 5 + 0 * 32, Color.get(-1, 533, 500, 220), ultimateSheet, 1, 5));
		Resource.load(new FoodResource("b.mshrm", 5 + 0 * 32, Color.get(-1, 110, 110, 321), ultimateSheet, 1, 5));
		
		Resource.load(new PlantableResource("r.spore", 2 + 4 * 32, Color.get(-1, -1, -1, 500), "r.mushroom", "grass"));
		Resource.load(new PlantableResource("b.spore", 2 + 4 * 32, Color.get(-1, -1, -1, 321), "b.mushroom", "grass"));

		Resource.load(new Resource("star", 35, Color.get(-1, 110, 440, 440), ultimateSheet));
		Resource.load(new Resource("R.fish", 32, Color.get(-1, 0, 135, 245), ultimateSheet));
		Resource.load(new FoodResource("C.FISH", 32, Color.get(-1, 0, 511, 410), ultimateSheet, 2, 5));
		Resource.load(new Resource("Arrow", 33, Color.get(-1, 555, 555, 321), ultimateSheet));
		Resource.load(new FoodResource("g.apple", 34, Color.get(-1, 110, 440, 553), ultimateSheet, 5, 4));

		try
		{
			Crafting.workbenchRecipes.add(new ItemRecipe(FishingRod.class).addCost("wood", 5).addCost("cloth", 5));
			Crafting.workbenchRecipes.add(new ItemRecipe(Bow.class).addCost("wood", 5).addCost("cloth", 5));
			Crafting.workbenchRecipes.add(new ResourceRecipe("Arrow").addCost("wood", 1).addCost("stone", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("g.apple").addCost("apple", 1).addCost("gold", 1));

			Crafting.workbenchRecipes.add(new ResourceRecipe("white").addCost("daisy", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("red").addCost("rose", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("blue").addCost("salvia", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("black").addCost("b.rose", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("green").addCost("cactus", 3));
			Crafting.workbenchRecipes.add(new ResourceRecipe("pink").addCost("red", 1).addCost("white", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("purple").addCost("red", 1).addCost("blue", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("yellow").addCost("star", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("orange").addCost("red", 1).addCost("yellow", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("gray").addCost("white", 1).addCost("black", 1));
			
			Crafting.workbenchRecipes.add(new ResourceRecipe("r.spore").addCost("r.mshrm", 1));
			Crafting.workbenchRecipes.add(new ResourceRecipe("b.spore").addCost("b.mshrm", 1));
			
			Crafting.workbenchRecipes.add(new FurnitureRecipe(JackOLantern.class).addCost(Pumpkin.class, 1));
			
			Crafting.anvilRecipes.add(new ItemRecipe(Bucket.class).addCost("i.ore", 5));

			Crafting.ovenRecipes.add(new ResourceRecipe("C.FISH").addCost("r.fish", 1).addCost("coal", 1));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Level.customLevelGenCode.add(new CustomLevelGen()
		{
			@Override
			public void go(Level level)
			{
				//Calendar rightNow = Calendar.getInstance();
				//int m = rightNow.get(Calendar.MONTH) + 1;
				//int d = rightNow.get(Calendar.DAY_OF_MONTH);
				
				// Only on halloween?
				//if(m != 10 && d != 31) return;
				
				for (int i = 0; i < level.w * level.h / 800; i++)
				{
					int x = random.nextInt(level.w);
					int y = random.nextInt(level.h);
					int col = random.nextInt(4);
					for (int j = 0; j < 30; j++)
					{
						int xx = x + random.nextInt(5) - random.nextInt(5);
						int yy = y + random.nextInt(5) - random.nextInt(5);
						if (xx >= 0 && yy >= 0 && xx < level.w && yy < level.h)
						{
							if (level.tiles[xx + yy * level.w] == Tile.get("grass").id)
							{
								level.tiles[xx + yy * level.w] = Tile.get("mushroom").id;
								level.data[xx + yy * level.w] = (byte)(col + random.nextInt(4) * 16); // creates unique flowers
							}
						}
					}
				}
				
				// Spawn pumpkins
				for (int i = 0; i < level.w * level.h / 1200; i++)
				{
					int x = random.nextInt(level.w);
					int y = random.nextInt(level.h);
					//int col = random.nextInt(4);
					for (int j = 0; j < 8; j++)
					{
						int xx = x + random.nextInt(5) - random.nextInt(5);
						int yy = y + random.nextInt(5) - random.nextInt(5);
						if (xx >= 0 && yy >= 0 && xx < level.w && yy < level.h)
						{
							if (level.tiles[xx + yy * level.w] == Tile.get("grass").id)
							{
								Pumpkin p = new Pumpkin();
								p.x = xx * 16 + 8;
								p.y = yy * 16 + 8;
								level.add(p);
							}
						}
					}
				}
			}

			@Override
			public int getLevel()
			{
				return 0;
			}
		});
	}

	public String getName()
	{
		return "Ultimate Plugin";
	}

	@Override
	public boolean autoEnabled()
	{
		// TODO Auto-generated method stub
		return true;
	}
}
