import os
import json

class DataDir:
    def __init__(this, path):
        if not os.path.exists(path):
            os.makedirs(path)
        this.path = path

    def sub(this, subpath):
        return DataDir(this.path + "/" + subpath)
    
    def writetojavasrc(this, string, string2):
        with open(this.path, "r+") as file:
            file_content = file.read()
            split = file_content.split("public interface Blocks {")
            new_file_content = split[0] + "public interface Blocks {\n\n" + string + split[1]
            file.seek(0)
            file.write(new_file_content)
        
        with open(this.path, "r+") as file:
            file_content = file.read()
            split = file_content.split("static void onInitialize() {")
            new_file_content = split[0] + "static void onInitialize() {\n\n" + string2 + split[1]
            file.seek(0)
            file.write(new_file_content)
        
    def make(this, file, jsonobj):
        with open(this.path + "/" + file, "w+") as file:
            json.dump(jsonobj, file, indent=2)

MOD_ID = "lint"
BLOCK_CLASS_PATH = "me/hydos/lint/core/Blocks.java"

assets = DataDir("assets/" + MOD_ID)
blockstates = assets.sub("blockstates")
blockmodels = assets.sub("models/block")
itemmodels = assets.sub("models/item")
blockclass = DataDir("../java/" + BLOCK_CLASS_PATH)


loottables = DataDir("data/" + MOD_ID + "/loot_tables/blocks")

block_id = input("block id: ")

modelloc = {}
textureloc = {}
parentloc = {}
entryloc = {"type": "minecraft:item"}

j_blockstate = {"variants": {"": modelloc}}
j_blockmodel = {"parent": "block/cube_all", "textures": textureloc}
j_itemmodel = parentloc
j_loottable = {"type": "minecraft:block", "pools": [{"rolls": 1, "entries": [entryloc],"conditions": [{"condition": "minecraft:survives_explosion"}]}]}

while (block_id != ""):
    id_string = MOD_ID + ":block/" + block_id
    file_string = block_id + ".json"

    variable_string = "    Block " + block_id.upper() + " = new Block(FabricBlockSettings.of(Material.EARTH).hardness(0.5f).sounds(BlockSoundGroup.WET_GRASS).build());"

    variable_string2 = "        registerBlock(ItemGroup.BUILDING_BLOCKS, " + block_id.upper() + ", \"" + block_id + "\");"
    
    blockclass.writetojavasrc(variable_string, variable_string2)
    
    # Assets
    
    modelloc["model"] = id_string
    textureloc["all"] = id_string
    parentloc["parent"] = id_string

    blockstates.make(file_string, j_blockstate)
    blockmodels.make(file_string, j_blockmodel)
    itemmodels.make(file_string, j_itemmodel)

    # Data
    entryloc["name"] = MOD_ID + ":" + block_id
    loottables.make(file_string, j_loottable)
    
    block_id = input("block id: ")

