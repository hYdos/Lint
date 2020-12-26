import os
import json

class DataDir:
    def __init__(this, path):
        if not os.path.exists(path):
            os.makedirs(path)
        this.path = path

    def sub(this, subpath):
        return DataDir(this.path + "/" + subpath)

    def make(this, file, jsonobj):
        with open(this.path + "/" + file, "w+") as file:
            json.dump(jsonobj, file, indent=2)

MOD_ID = "lint"

assets = DataDir("src/main/resources/assets/" + MOD_ID)
blockstates = assets.sub("blockstates")
blockmodels = assets.sub("models/block")
itemmodels = assets.sub("models/item")

loottables = DataDir("src/main/resources/data/" + MOD_ID + "/loot_tables/blocks")

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

