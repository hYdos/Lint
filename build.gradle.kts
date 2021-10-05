plugins {
	id("fabric-loom") version "0.10-SNAPSHOT"
	id("org.cadixdev.licenser") version "0.5.0"
}

base.archivesBaseName = "lint"
group = "me.hydos"
version = "2.0.0-SNAPSHOT"

repositories {
	maven {
		name = "Devan-Kerman/Devan-Repo"
		url = uri("https://raw.githubusercontent.com/Devan-Kerman/Devan-Repo/master/")
	}

	maven {
		url = uri("https://repo.repsy.io/mvn/fadookie/particleman/")
	}

	flatDir {
		dirs("local")
	}
}

// Configurations
val modImplementationAndInclude by configurations.register("modImplementationAndInclude")

dependencies {
    minecraft("net.minecraft", "minecraft", "1.17.1")
    mappings("net.fabricmc", "yarn", "1.17.1+build.61", classifier = "v2")

    modImplementation("net.fabricmc", "fabric-loader", "0.11.7")
    modImplementation("net.fabricmc.fabric-api", "fabric-api", "0.40.1+1.17")

	// Geckolib manually
	modImplementation("local", "geckolib-fabric-1.17", "3.0.15")
	implementation("local", "geckolib-core", "1.0.4a")
	implementation("com.fasterxml.jackson.core", "jackson-databind", "2.9.0")
	implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", "2.9.0")
	implementation("com.eliotlash.molang", "molang", "SNAPSHOT.12")
	implementation("com.eliotlash.mclib", "mclib", "SNAPSHOT.12")

	include(modImplementation("net.devtech", "arrp", "0.+"))
}

java {
	sourceCompatibility = JavaVersion.VERSION_16
	targetCompatibility = JavaVersion.VERSION_16
}

minecraft {
	accessWidenerPath.set(file("src/main/resources/lint.aw"))
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
