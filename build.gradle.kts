plugins {
	id("fabric-loom") version "0.5.43"
	id("org.cadixdev.licenser") version "0.5.0"
}

base.archivesBaseName = "lint"
group = "me.hydos"
version = "2.0.0-SNAPSHOT"

repositories {
	maven {
		name = "Curseforge Maven"
		url = uri("https://www.cursemaven.com")
	}

	maven {
		name = "Devan-Kerman/Devan-Repo"
		url = uri("https://raw.githubusercontent.com/Devan-Kerman/Devan-Repo/master/")
	}

	maven {
		name = "Haven King"
		url = uri("https://hephaestus.dev/release/")
	}
}

// Configurations
val modImplementationAndInclude by configurations.register("modImplementationAndInclude")

dependencies {
    minecraft("net.minecraft", "minecraft", "1.16.5")
    mappings("net.fabricmc", "yarn", "1.16.5+build.6", classifier = "v2")

    modImplementation("net.fabricmc", "fabric-loader", "0.11.3")
    modImplementation("net.fabricmc.fabric-api", "fabric-api", "0.32.5+1.16")

	modImplementationAndInclude("net.devtech", "arrp", "0.3.2")
	modImplementationAndInclude("curse.maven", "geckolib-fabric-398667", "3155712")
	modImplementationAndInclude("dev.monarkhes", "myron", "1.6.0")

	if (! file("ignoreruntime.txt").exists()) {
		println("Setting Up Mod Runtimes")
		modRuntime("me.shedaniel", "RoughlyEnoughItems", "5.8.10")
		modRuntime("curse.maven", "worldedit-225608", "3135186")
		modRuntime("curse.maven", "appleskin-248787", "2987255")
		modRuntime("curse.maven", "hwyla-253449", "3033613")
	} else {
		println("Skipping Mod Runtimes")
	}

	// Setup custom configurations
	add(sourceSets.main.get().getTaskName("mod", JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME), modImplementationAndInclude)
	add(net.fabricmc.loom.util.Constants.Configurations.INCLUDE, modImplementationAndInclude)
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

loom {
	accessWidener = file("src/main/resources/lint.aw")
}

license {
	header = file("HEADER")
	include("**/*.java")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"

	if (JavaVersion.current().isJava9Compatible) {
		options.release.set(8)
	} else {
		sourceCompatibility = "8"
		targetCompatibility = "8"
	}
}

tasks.withType<AbstractArchiveTask> {
	from(file("LICENSE"))
	from(file("LICENSE.LESSER"))
}

tasks.processResources {
	filesMatching("fabric.mod.json") {
		expand("version" to project.version)
	}
}

tasks.remapJar {
	doLast {
		input.get().asFile.delete()
	}
}
