package net.thecorgi.patterns;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;

import java.util.HashMap;
import java.util.Map;

import static net.thecorgi.patterns.Patterns.id;

public class PatternsJsonDataLoader extends JsonDataLoader implements IdentifiableResourceReloader {
	public static HashMap<Identifier, Integer> map = new HashMap<>();

	public PatternsJsonDataLoader() {
		super(new Gson(), "pattern_values");
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		prepared.forEach((id, element) -> {
			if (element.isJsonObject()) {
				JsonObject obj = element.getAsJsonObject();
				for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
					System.out.println(entry.getKey() + ", " + entry.getValue().getAsInt());
					map.put(new Identifier(entry.getKey()), entry.getValue().getAsInt());
				}
			}
		});
	}

	@Override
	public Identifier getQuiltId() {
		return id("pattern_values");
	}
}
