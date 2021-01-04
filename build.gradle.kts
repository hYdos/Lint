plugins {
	id("fabric-loom") version "0.5.43"
	id("org.cadixdev.licenser") version "0.5.0"
}

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
		name = "CurseMaven"
		url = uri("https://www.cursemaven.com/")
	}
}

dependencies {
	minecraft("net.minecraft", "minecraft", "1.16.4")
	mappings("net.fabricmc", "yarn", "1.16.4+build.7", classifier = "v2")

	modImplementation("net.fabricmc", "fabric-loader", "0.10.8")
	modImplementation("net.fabricmc.fabric-api", "fabric-api", "0.29.1+1.16")

	include(modImplementation("net.devtech", "arrp", "0.3.2"))
	include(modImplementation("curse.maven", "geckolib-fabric-398667", "3155712"))

	modRuntime("me.shedaniel", "RoughlyEnoughItems", "5.8.10")
	modRuntime("curse.maven", "worldedit-225608", "3135186")
	modRuntime("curse.maven", "appleskin-248787", "2987255")
	modRuntime("curse.maven", "hwyla-253449", "3033613")
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
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

	if (JavaVersion.current().isJava9Compatible) {
		options.release.set(8)
	} else {
		sourceCompatibility = "8"
		targetCompatibility = "8"
	}
}

tasks.withType<AbstractArchiveTask> {
	from(rootProject.file("LICENSE"))
	from(rootProject.file("LICENSE.LESSER"))
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
