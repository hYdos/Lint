plugins {
	id("fabric-loom") version "0.8-SNAPSHOT"
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

	maven {
		url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
	}
}

// Configurations
val modImplementationAndInclude by configurations.register("modImplementationAndInclude")

dependencies {
    minecraft("net.minecraft", "minecraft", "1.17.1")
    mappings("net.fabricmc", "yarn", "1.17.1+build.61", classifier = "v2")

    modImplementation("net.fabricmc", "fabric-loader", "0.11.7")
    modImplementation("net.fabricmc.fabric-api", "fabric-api", "0.40.1+1.17")
	modImplementation("software.bernie.geckolib", "geckolib-fabric-1.17", "3.0.5", classifier = "dev")

	modImplementationAndInclude("net.devtech", "arrp", "0.+")

	if (! file("ignoreruntime.txt").exists()) {
		println("Setting Up Mod Runtimes")
//		modRuntime("curse.maven", "worldedit-225608", "3135186")
//		modRuntime("curse.maven", "appleskin-248787", "2987255")
//		modRuntime("curse.maven", "hwyla-253449", "3033613")
	} else {
		println("Skipping Mod Runtimes")
	}

	// Setup custom configurations
	add(sourceSets.main.get().getTaskName("mod", JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME), modImplementationAndInclude)
	add(net.fabricmc.loom.util.Constants.Configurations.INCLUDE, modImplementationAndInclude)
}

java {
	sourceCompatibility = JavaVersion.VERSION_16
	targetCompatibility = JavaVersion.VERSION_16
}

minecraft {
	accessWidener = file("src/main/resources/lint.aw")
}

license {
	header = file("HEADER")
	include("**/*.java")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"

    options.release.set(16)
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
