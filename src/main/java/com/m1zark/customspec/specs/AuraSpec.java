package com.m1zark.customspec.specs;

import com.pixelmonmod.pixelmon.api.pokemon.ISpecType;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.SpecValue;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.List;

public class AuraSpec extends SpecValue<String> implements ISpecType {
	public AuraSpec(String value) {
		super("aura", value);
	}
	
	@Override public List<String> getKeys() {
		return Arrays.asList("aura");
	}
	
	@Override public Class<? extends SpecValue<?>> getSpecClass() {
		return this.getClass();
	}
	
	@Override public Class<String> getValueClass() {
		return String.class;
	}
	
	@Override public AuraSpec parse(String arg) {
		return new AuraSpec(arg);
	}
	
	@Override public String toParameterForm(SpecValue<?> value) {
		return value.key + ":" + value.value.toString();
	}
	
	@Override public SpecValue<String> clone() {
		return new AuraSpec(this.value);
	}
	
	@Override public AuraSpec readFromNBT(NBTTagCompound nbt) {
		return parse(nbt.getString("entity-particles:particle"));
	}
	
	@Override public void writeToNBT(NBTTagCompound nbt, SpecValue<?> value) {
		nbt.setString("entity-particles:particle", value.value.toString());
	}
	
	@Override public boolean matches(EntityPixelmon pixelmon) {
		return this.matches(pixelmon.getPokemonData());
	}
	
	@Override public boolean matches(Pokemon pokemon) {
		return pokemon.getPersistentData().hasKey("entity-particles:particle") && pokemon.getPersistentData().getString("entity-particles:particle").equals(this.value);
	}
	
	@Override public void apply(EntityPixelmon pixelmon) {
		pixelmon.getPokemonData().getPersistentData().setString("entity-particles:particle", this.value);
	}
	
	@Override public void apply(Pokemon pokemon) {
		pokemon.getPersistentData().setString("entity-particles:particle", this.value);
	}
}
